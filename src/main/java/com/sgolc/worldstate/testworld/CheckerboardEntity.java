package com.sgolc.worldstate.testworld;

import com.sgolc.graphicsmodel.texture.CheckerboardTexture;

import java.awt.*;

public class CheckerboardEntity extends ScreenSpaceEntity {
    public CheckerboardEntity() {
        setTexture(new CheckerboardTexture(Color.BLACK, Color.WHITE));
        setScale(0.2, 0.2);
    }
}
