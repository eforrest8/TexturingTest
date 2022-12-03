package com.sgolc.view;

import com.sgolc.graphicsmodel.*;
import com.sgolc.worldstate.testworld.GradientAndCheckerRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

public class GraphicsPanel extends JPanel {

    private final static Dimension PREFERRED_SIZE = new Dimension(256, 256);
    private final TextureRenderer textureRenderer = new TextureRenderer();
    private final GradientAndCheckerRenderer renderer = new GradientAndCheckerRenderer(textureRenderer);

    public GraphicsPanel() {
        super();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(PREFERRED_SIZE.width, PREFERRED_SIZE.height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        textureRenderer.setOutputDimension(getSize());
        textureRenderer.setInternalDimension(new Dimension(256,256));
        renderer.update();
        BufferedImage bufferedImage = new BufferedImage(
                ColorModel.getRGBdefault(),
                textureRenderer.render(),
                true,
                null
        );
        Image scaledImage = bufferedImage.getScaledInstance(getWidth(), getHeight(), Image.SCALE_AREA_AVERAGING);
        g.drawImage(scaledImage, 0, 0, null);
    }

}
