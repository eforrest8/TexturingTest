package com.sgolc.graphicsmodel.coordinates;

public class OneToOneCoordinateMapper implements CoordinateMapper {
    @Override
    public Point mapCoordinate(Point coordinate) {
        return coordinate;
    }
}
