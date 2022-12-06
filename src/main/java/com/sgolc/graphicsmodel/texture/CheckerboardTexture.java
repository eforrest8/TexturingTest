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
        //test colors for corner coordinates
        boolean leftside = Math.abs(coordinate.getX()) < 0.05;
        boolean topside = Math.abs(coordinate.getY()) < 0.05;
        boolean rightside = Math.abs(1-coordinate.getX()) < 0.05;
        boolean bottomside = Math.abs(1-coordinate.getY()) < 0.05;
        if(leftside && topside) {
            return Color.RED;
        } else if (rightside && topside) {
            return Color.ORANGE;
        } else if (rightside && bottomside) {
            return Color.GREEN;
        } else if (leftside && bottomside) {
            return Color.BLUE;
        }

        float horizontalChecks = 1/checkWidth;
        float verticalChecks = 1/checkHeight;
        int parity = (int) ((Math.floor(horizontalChecks * coordinate.getX()) + Math.floor(verticalChecks * coordinate.getY())) % 2);
        return parity == 0 ? evenColor : oddColor;
    }
}
