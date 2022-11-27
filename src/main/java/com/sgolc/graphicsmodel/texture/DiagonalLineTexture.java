package com.sgolc.graphicsmodel.texture;

import com.sgolc.graphicsmodel.coordinates.Point;

import java.awt.*;

public class DiagonalLineTexture implements Texture {

    private final Color color;

    public DiagonalLineTexture(Color color) {
        this.color = color;
    }

    @Override
    public Color getColorAtCoordinate(Point coordinate) {
        if (coordinate.x < 0 || coordinate.x > 1 || coordinate.y < 0 || coordinate.y > 1) {
            return new Color(0,0,0,0);
        }
        if (Math.abs(coordinate.x - coordinate.y) < 0.02) {
            return color;
        }
        return color.darker();
    }
}
