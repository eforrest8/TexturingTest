package com.sgolc.graphicsmodel.coordinates;

public class Scaler implements CoordinateMapper {

    private final double xScale;
    private final double yScale;

    public Scaler(double xScale, double yScale) {
        this.xScale = xScale;
        this.yScale = yScale;
    }

    public Scaler(double scale) {
        this(scale, scale);
    }

    @Override
    public Point mapCoordinate(Point coordinate) {
        return new Point(
                xScale * coordinate.x,
                yScale *coordinate.y);
    }
}
