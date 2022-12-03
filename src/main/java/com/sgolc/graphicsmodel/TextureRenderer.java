package com.sgolc.graphicsmodel;

import com.sgolc.graphicsmodel.coordinates.*;
import com.sgolc.graphicsmodel.coordinates.Point;
import com.sgolc.graphicsmodel.texture.Texture;

import java.awt.*;
import java.awt.image.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TextureRenderer implements Serializable {

    private final ExecutorService executor = Executors.newWorkStealingPool();
    private final int[] bitMasks = {0xFF0000, 0xFF00, 0xFF, 0xFF000000};
    private Dimension outputDimension = new Dimension(1, 1);
    private Dimension internalDimension = new Dimension(256, 256);
    private Texture screenTexture = null;
    private final Logger renderLogger = Logger.getLogger("com.sgolc.view.TexturingTest.rendering");

    public TextureRenderer() {
        renderLogger.setLevel(Level.WARNING);
        renderLogger.setFilter(e -> e.getMessage() != null);
    }

    public void setOutputDimension(Dimension outputDimension) {
        this.outputDimension = outputDimension;
    }

    public void setInternalDimension(Dimension dimension) {
        this.internalDimension = dimension;
    }

    public void setScreenTexture(Texture screenTexture) {
        this.screenTexture = screenTexture;
    }

    public WritableRaster render() {
        if (screenTexture == null) {
            RuntimeException ex = new RuntimeException("Can't render; texture has not been set!");
            renderLogger.throwing("TextureRenderer", "render", ex);
            throw ex;
        }
        long startTime = System.currentTimeMillis();
        CoordinateMapper normalizer = new NormalizingCoordinateMapper(internalDimension.width, internalDimension.height);
        AspectRatioCorrector aspectCorrection = new AspectRatioCorrector(internalDimension, outputDimension);
        CoordinateMapper scaler = new Scaler(1/aspectCorrection.getSmallerFactor());
        CoordinateMapper prescaleTranslate = new TranslationMapper(-0.5,-0.5);
        CoordinateMapper postscaleTranslate = new TranslationMapper(0.5,0.5);
        LinkedList<Future<?>> results = new LinkedList<>();
        WritableRaster raster = WritableRaster.createWritableRaster(
                new SinglePixelPackedSampleModel(
                        DataBuffer.TYPE_INT,
                        internalDimension.width,
                        internalDimension.height,
                        bitMasks
                ),
                null);
        for (int y = 0; y < internalDimension.height; y++) {
            for (int x = 0; x < internalDimension.width; x++) {
                int finalX = x;
                int finalY = y;
                results.add(executor.submit(() -> {
                    Color pixel = screenTexture.getColorAtCoordinate(
                            new MultiMapper(normalizer, aspectCorrection, prescaleTranslate, scaler, postscaleTranslate)
                                    .mapCoordinate(new Point(finalX, finalY)));
                    raster.setPixel(finalX, finalY, new int[]{pixel.getRed(), pixel.getGreen(), pixel.getBlue(), pixel.getAlpha()});
                }));
            }
        }
        renderLogger.info("All tasks submitted. Took " + (System.currentTimeMillis() - startTime) + " milliseconds.");
        int exceptions = 0;
        for (Future<?> future : results) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                exceptions++;
            }
        }
        renderLogger.info("All tasks completed. Took " + (System.currentTimeMillis() - startTime) + " milliseconds in total.");
        String exceptionWarning = "There were " + exceptions + " rendering exceptions in total.";
        int finalExceptions = exceptions;
        renderLogger.warning(() -> finalExceptions > 0 ? exceptionWarning : null);
        return raster;
    }
}