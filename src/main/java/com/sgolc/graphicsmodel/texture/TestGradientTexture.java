package com.sgolc.graphicsmodel.texture;

import com.sgolc.graphicsmodel.coordinates.Point;

import java.awt.*;

public class TestGradientTexture implements Texture {

    @Override
    public Color getColorAtCoordinate(Point coordinate) {
        if (Math.abs(coordinate.getX()) > 0.5 || Math.abs(coordinate.getY()) > 0.5) {
            return new Color(0,0,0,0);
        }
        float R = (float) coordinate.getX() + 0.5f;
        float G = (float) coordinate.getY() + 0.5f;
        float B = (float) (1 - ((coordinate.getX()+0.5) * (coordinate.getY()+0.5)));
        return new Color(R, G, B);
    }
}
