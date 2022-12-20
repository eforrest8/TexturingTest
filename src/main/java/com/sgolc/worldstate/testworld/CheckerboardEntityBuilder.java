package com.sgolc.worldstate.testworld;

import com.sgolc.graphicsmodel.coordinates.Point;
import com.sgolc.graphicsmodel.texture.CheckerboardTexture;
import com.sgolc.worldstate.entitycomponent.Entity;
import com.sgolc.worldstate.entitycomponent.EntityStore;

import java.awt.*;

public class CheckerboardEntityBuilder {
    private CheckerboardEntityBuilder() {}

    public static Entity build(EntityStore manager) {
        return manager.createEntity(
                new TextureComponent(new CheckerboardTexture(Color.BLACK, Color.WHITE)),
                new TranslateComponent(0.4, 0.4),
                new ScaleComponent(0.2, 0.2),
                new QuadTransformComponent(new Point(0.5,0.1), new Point(0.8,0), new Point(1,0.5), new Point(0,1))
        );
    }
}
