package com.sgolc.graphicsmodel.coordinates;

import java.awt.*;

public class AspectRatioCorrector implements CoordinateMapper {

    private double xScale = 1;
    private double yScale = 1;

    public AspectRatioCorrector(Dimension internalResolution, Dimension outputResolution) {
        xScale = outputResolution.getWidth() / internalResolution.getWidth();
        yScale = outputResolution.getHeight() / internalResolution.getHeight();
    }

    public double getSmallerFactor() {
        return Math.min(xScale, yScale);
    }

    public double getLargerFactor() {
        return Math.max(xScale, yScale);
    }

    public boolean isXScaleSmaller() {
        return xScale < yScale;
    }

    @Override
    public Point mapCoordinate(Point coordinate) {
        return new Point(
                (coordinate.x * xScale) - (xScale-1)/2,
                (coordinate.y * yScale) - (yScale-1)/2);
    }
}
