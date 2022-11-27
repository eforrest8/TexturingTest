package com.sgolc.graphicsmodel.texture;

import com.sgolc.graphicsmodel.coordinates.Point;

import java.awt.*;

public class CheckerboardTexture implements Texture {

    private final float checkWidth;
    private final float checkHeight;
    private final Color evenColor;
    private final Color oddColor;

    public CheckerboardTexture(float checkWidth, float checkHeight, Color evenColor, Color oddColor) {
        this.checkWidth = checkWidth;
        this.checkHeight = checkHeight;
        this.evenColor = evenColor;
        this.oddColor = oddColor;
    }

    public CheckerboardTexture(Color evenColor, Color oddColor) {
        this(1f, 1f, evenColor, oddColor);
    }

    @Override
    public Color getColorAtCoordinate(Point coordinate) {
        float horizontalChecks = 1/checkWidth;
        float verticalChecks = 1/checkHeight;
        int parity = (int) ((Math.floor(horizontalChecks * coordinate.x) + Math.floor(verticalChecks * coordinate.y)) % 2);
        return parity == 0 ? evenColor : oddColor;
    }
}
