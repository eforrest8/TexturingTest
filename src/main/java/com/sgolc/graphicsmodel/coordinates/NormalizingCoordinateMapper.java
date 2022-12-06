package com.sgolc.graphicsmodel.coordinates;

public class NormalizingCoordinateMapper implements CoordinateMapper {
    private final int xScale;
    private final int yScale;

    public NormalizingCoordinateMapper(int xScale, int yScale) {
        this.xScale = xScale;
        this.yScale = yScale;
    }

    @Override
    public Point mapCoordinate(Point coordinate) {
        return new Point(coordinate.getX()/xScale, coordinate.getY()/yScale);
    }
}
