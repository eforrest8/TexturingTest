package com.sgolc.graphicsmodel.texture;

import com.sgolc.graphicsmodel.coordinates.Point;

import java.awt.*;

public class SolidColorTexture implements Texture {

    private final Color color;

    public SolidColorTexture(Color color) {
        this.color = color;
    }

    @Override
    public Color getColorAtCoordinate(Point coordinate) {
        if (coordinate.getX() < 0 || coordinate.getX() > 1 || coordinate.getY() < 0 || coordinate.getY() > 1) {
            return new Color(0,0,0,0);
        }
        return color;
    }
}
