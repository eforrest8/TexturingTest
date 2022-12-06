package com.sgolc.graphicsmodel.coordinates;

import java.awt.*;

public class AspectRatioCorrector implements CoordinateMapper {

    private final double xScale;
    private final double yScale;

    public AspectRatioCorrector(Dimension internalResolution, Dimension outputResolution) {
        xScale = outputResolution.getWidth() / internalResolution.getWidth();
        yScale = outputResolution.getHeight() / internalResolution.getHeight();
    }

    public double getSmallerFactor() {
        return Math.min(xScale, yScale);
    }

    @Override
    public Point mapCoordinate(Point coordinate) {
        return new Point(
                (coordinate.getX() * xScale) - (xScale-1)/2,
                (coordinate.getY() * yScale) - (yScale-1)/2);
    }
}
