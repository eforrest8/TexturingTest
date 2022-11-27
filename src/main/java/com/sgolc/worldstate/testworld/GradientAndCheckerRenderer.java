package com.sgolc.worldstate.testworld;

import com.sgolc.graphicsmodel.MappedTexture;
import com.sgolc.graphicsmodel.TextureRenderer;
import com.sgolc.graphicsmodel.coordinates.CoordinateMapper;
import com.sgolc.graphicsmodel.coordinates.MultiMapper;
import com.sgolc.graphicsmodel.texture.*;
import com.sgolc.worldstate.entitycomponent.Component;
import com.sgolc.worldstate.entitycomponent.ECSystem;
import com.sgolc.worldstate.entitycomponent.Entity;

import java.awt.*;

public class GradientAndCheckerRenderer extends ECSystem {

    private final TextureRenderer target;

    public GradientAndCheckerRenderer(TextureRenderer target) {
        this.target = target;

        Component checker = new TextureComponent(
                new CheckerboardTexture(Color.BLACK, Color.WHITE)
        );
        Component checkScale = new ScaleComponent(0.2, 0.2);
        Component checkZ = new ZIndexComponent(0);
        Component gradient = new TextureComponent(
                new TransparentTexture(new TestGradientTexture(), 0.5f)
        );
        Component gradTranslate = new ScreenPositionComponent(0.05, 0.05);
        Component gradScale = new ScaleComponent(0.9, 0.9);
        Component gradZ = new ZIndexComponent(1);
        addEntityWithComponents(checker, checkScale, checkZ);
        addEntityWithComponents(gradient, gradScale, gradTranslate, gradZ);
    }

    @Override
    public void update() {
        try {
            Texture compositor = new CompositingTexture(
                    entities.stream()
                            .sorted(this::zIndexComparator)
                            .map(entity -> new MappedTexture(
                            ((TextureComponent) entity.components.stream()
                                    .filter(c -> c instanceof TextureComponent)
                                    .findAny()
                                    .orElseThrow()).texture(),
                            new MultiMapper(
                                    entity.components.stream()
                                            .filter(c -> c instanceof Mapper)
                                            .map(c -> ((Mapper) c).getMapper())
                                            .toList().toArray(new CoordinateMapper[0]))
                            )
                    ).toList().toArray(new Texture[0])
            );
            target.setScreenTexture(compositor);
        } catch (ClassCastException ex) {
            ex.printStackTrace();
        }
    }

    private int zIndexComparator(Entity a, Entity b) {
        int first = ((ZIndexComponent) a.components.stream()
                .filter(c -> c instanceof ZIndexComponent)
                .findAny()
                .orElse(new ZIndexComponent(Integer.MIN_VALUE))).zIndex();
        int second = ((ZIndexComponent) b.components.stream()
                .filter(c -> c instanceof ZIndexComponent)
                .findAny()
                .orElse(new ZIndexComponent(Integer.MIN_VALUE))).zIndex();
        return Integer.compare(first, second);
    }
}
