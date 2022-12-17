package com.sgolc.view;

import com.sgolc.graphicsmodel.*;
import com.sgolc.worldstate.entitycomponent.EntityManager;
import com.sgolc.worldstate.testworld.BasicPhysicsSystem;
import com.sgolc.worldstate.testworld.InitSystem;
import com.sgolc.worldstate.testworld.TexturedEntityRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GraphicsPanel extends JPanel {

    private final static Dimension PREFERRED_SIZE = new Dimension(256, 256);
    private final TextureRenderer textureRenderer = new TextureRenderer(this::repaint);
    private final EntityManager manager = new EntityManager();
    private final TexturedEntityRenderer renderer = new TexturedEntityRenderer(textureRenderer);
    private final BasicPhysicsSystem physics = new BasicPhysicsSystem();

    public GraphicsPanel() {
        super();
        new InitSystem().update(manager);
        renderer.update(manager);
        new ScheduledThreadPoolExecutor(1).scheduleAtFixedRate(
                () -> physics.update(manager), 1000, 200, TimeUnit.MILLISECONDS);
        new ScheduledThreadPoolExecutor(1).scheduleAtFixedRate(
                () -> renderer.update(manager), 1000, 100, TimeUnit.MILLISECONDS);
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
