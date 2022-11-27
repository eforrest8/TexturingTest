package com.sgolc.graphicsmodel.texture;

import com.sgolc.graphicsmodel.coordinates.Point;

import java.awt.*;

public class TestGradientTexture implements Texture {

    @Override
    public Color getColorAtCoordinate(Point coordinate) {
        if (coordinate.x < 0 || coordinate.x > 1 || coordinate.y < 0 || coordinate.y > 1) {
            return new Color(0,0,0,0);
        }
        float R = coordinate.x;
        float G = coordinate.y;
        float B = 1 - (coordinate.x * coordinate.y);
        return new Color(R, G, B);
    }
}
