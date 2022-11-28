package com.sgolc.graphicsmodel.coordinates;

public class Scaler implements CoordinateMapper {

    private final double xScale;
    private final double yScale;

    public Scaler(double xScale, double yScale) {
        this.xScale = xScale;
        this.yScale = yScale;
    }

    @Override
    public Point mapCoordinate(Point coordinate) {
        return new Point(coordinate.x * xScale, coordinate.y * yScale);
    }
}
