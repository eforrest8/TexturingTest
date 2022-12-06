package com.sgolc.worldstate.testworld;

import com.sgolc.graphicsmodel.coordinates.Point;
import com.sgolc.graphicsmodel.texture.CheckerboardTexture;

import java.awt.*;

public class CheckerboardEntity extends ScreenSpaceEntity {
    public CheckerboardEntity() {
        setTexture(new CheckerboardTexture(Color.BLACK, Color.WHITE));
        setTranslate(0.4, 0.4);
        setScale(0.2, 0.2);
        components.add(new QuadTransformComponent(new Point(0.5,0.1), new Point(0.8,0), new Point(1,0.5), new Point(0,1)));
    }
}
