package com.sgolc.view;

import com.sgolc.graphicsmodel.*;
import com.sgolc.worldstate.entitycomponent.EntityStore;
import com.sgolc.worldstate.testworld.BasicPhysicsSystem;
import com.sgolc.worldstate.testworld.InitSystem;
import com.sgolc.worldstate.testworld.TexturedEntityRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.HierarchyBoundsAdapter;
import java.awt.event.HierarchyEvent;
import java.awt.image.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GraphicsPanel extends JPanel {

    private final static Dimension PREFERRED_SIZE = new Dimension(256, 256);
    private final EntityStore manager = new EntityStore();
    private final TexturedEntityRenderer renderer = new TexturedEntityRenderer(this::repaint);
    private final BasicPhysicsSystem physics = new BasicPhysicsSystem();
    private WritableRaster latestFrame;

    public GraphicsPanel() {
        super();
        this.setDoubleBuffered(true);
        InitSystem init = new InitSystem();
        init.update(manager);
        init.setEntityStore(manager);
        renderer.setEntityStore(manager);
        physics.setEntityStore(manager);
        addHierarchyBoundsListener(new HierarchyBoundsAdapter() {
            @Override
            public void ancestorResized(HierarchyEvent e) {
                init.updateOutputDimension(e.getChanged().getSize());
            }
        });
        renderer.update();
        new ScheduledThreadPoolExecutor(1).scheduleAtFixedRate(
                physics::update, 1000, 100, TimeUnit.MILLISECONDS);
        new ScheduledThreadPoolExecutor(1).scheduleAtFixedRate(
                renderer::update, 1000, 100, TimeUnit.MILLISECONDS);
    }

    public void repaint(WritableRaster raster) {
        latestFrame = raster;
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(PREFERRED_SIZE.width, PREFERRED_SIZE.height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage bufferedImage = new BufferedImage(
                ColorModel.getRGBdefault(),
                latestFrame,
                true,
                null
        );
        Image scaledImage = bufferedImage.getScaledInstance(getWidth(), getHeight(), Image.SCALE_AREA_AVERAGING);
        g.drawImage(scaledImage, 0, 0, null);
    }

}
