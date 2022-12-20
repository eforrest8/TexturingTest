package com.sgolc.worldstate.testworld;

import com.sgolc.graphicsmodel.MappedTexture;
import com.sgolc.graphicsmodel.coordinates.*;
import com.sgolc.graphicsmodel.coordinates.Point;
import com.sgolc.graphicsmodel.texture.CompositingTexture;
import com.sgolc.graphicsmodel.texture.Texture;
import com.sgolc.worldstate.entitycomponent.ECSystem;
import com.sgolc.worldstate.entitycomponent.Entity;
import com.sgolc.worldstate.entitycomponent.EntityStore;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.DataBuffer;
import java.awt.image.SinglePixelPackedSampleModel;
import java.awt.image.WritableRaster;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.*;
import java.util.function.Consumer;

public class TexturedEntityRenderer extends ECSystem {

    private final ForkJoinPool executor = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
    private final SortedMap<ZIndexComponent, MappedTexture> processedTextures = new TreeMap<>(this::zIndexSort);
    private final Consumer<WritableRaster> renderCallback;
    private static final int[] BIT_MASKS = {0xFF0000, 0xFF00, 0xFF, 0xFF000000};
    private static final EntityStore.Query TEXTURE_QUERY = new EntityStore.Query(
            new EntityStore.QueryPart<>(TextureComponent.class, (a) -> true, EntityStore.ResultMergeMode.OR)
    );

    private static final EntityStore.Query DIMENSION_QUERY = new EntityStore.Query(
            new EntityStore.QueryPart<>(
                    DimensionSettingComponent.class,
                    k -> k.key().equals("internal dimension"),
                    EntityStore.ResultMergeMode.OR),
            new EntityStore.QueryPart<>(
                    DimensionSettingComponent.class,
                    k -> k.key().equals("output dimension"),
                    EntityStore.ResultMergeMode.AND)
    );

    private Dimension internalDimension = new Dimension(256, 256);
    private Dimension outputDimension = new Dimension(256, 256);

    public TexturedEntityRenderer(Consumer<WritableRaster> renderCallback) {
        this.renderCallback = renderCallback;
    }

    public void update() {
        updateDimensions();
        updateEntities();
        render();
    }

    private void updateDimensions() {
        List<Entity> dimensions = query(DIMENSION_QUERY);
        if (!dimensions.isEmpty()) {
            dimensions.get(0).getAllComponentsByClass(DimensionSettingComponent.class).forEach(c -> {
                if (c.getKey().equals("internal dimension")) {
                    internalDimension = c.getValue();
                } else if (c.getKey().equals("output dimension")) {
                    outputDimension = c.getValue();
                }
            });
        }
    }

    public void updateEntities() {
        processedTextures.clear();
        query(TEXTURE_QUERY).forEach(entity -> processedTextures.putIfAbsent(entity.getComponentByClass(ZIndexComponent.class)
                .orElse(new ZIndexComponent(Integer.MIN_VALUE)), buildMappedTexture(entity)));
    }

    private int zIndexSort(ZIndexComponent a, ZIndexComponent b) {
        return Integer.compare(a.zIndex(), b.zIndex());
    }

    private MappedTexture buildMappedTexture(Entity entity) {
        TextureComponent tex = entity.getComponentByClass(TextureComponent.class).orElseThrow();
        CoordinateMapper[] maps = entity.getAllComponentsByClass(Mapper.class)
                .map(Mapper::getMapper)
                .toArray(CoordinateMapper[]::new);
        return new MappedTexture(tex.texture(), new MultiMapper(maps));
    }

    private void render() {
        CompositingTexture screenTexture = new CompositingTexture(processedTextures.values().toArray(Texture[]::new));
        CoordinateMapper normalizer = new NormalizingCoordinateMapper(internalDimension.width, internalDimension.height);
        AspectRatioCorrector aspectCorrection = new AspectRatioCorrector(internalDimension, outputDimension);
        CoordinateMapper scaler = new Scaler(1/aspectCorrection.getLargerFactor());
        CoordinateMapper prescaleTranslate = new TranslationMapper(-0.5,-0.5);
        CoordinateMapper postscaleTranslate = new TranslationMapper(0.5,0.5);
        LinkedList<RenderTask> tasks = new LinkedList<>();
        for (int y = 0; y < internalDimension.height; y++) {
            for (int x = 0; x < internalDimension.width; x++) {
                tasks.add((RenderTask) executor.submit(new RenderTask(
                        screenTexture,
                        new MultiMapper(normalizer, aspectCorrection, prescaleTranslate, scaler, postscaleTranslate),
                        x,
                        y)
                ));
            }
        }
        WritableRaster raster = WritableRaster.createWritableRaster(
                new SinglePixelPackedSampleModel(
                        DataBuffer.TYPE_INT,
                        internalDimension.width,
                        internalDimension.height,
                        BIT_MASKS
                ),
                null);
        executor.awaitQuiescence(500, TimeUnit.MILLISECONDS);
        tasks.forEach(task ->
        {
            try {
                Color pixel = task.get();
                raster.setPixel(task.x, task.y,
                        task.isCompletedNormally() ?
                            new int[]{pixel.getRed(), pixel.getGreen(), pixel.getBlue(), pixel.getAlpha()} :
                            new int[]{255, 0, 255, 255});
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
        renderCallback.accept(raster);
    }

    private static class RenderTask extends RecursiveTask<Color> {
        private final int x;
        private final int y;
        private final Texture texture;
        private final CoordinateMapper mapper;

        public RenderTask(Texture texture, CoordinateMapper mapper, int x, int y) {
            this.texture = texture;
            this.mapper = mapper;
            this.x = x;
            this.y = y;
        }

        @Override
        protected Color compute() {
            return texture.getColorAtCoordinate(mapper.mapCoordinate(new Point(x, y)));
        }
    }
}
