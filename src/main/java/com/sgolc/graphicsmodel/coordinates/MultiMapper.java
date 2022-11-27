package com.sgolc.graphicsmodel.coordinates;

public class MultiMapper implements CoordinateMapper {

    private final CoordinateMapper[] mappers;

    public MultiMapper(CoordinateMapper... mappers) {
        this.mappers = mappers;
    }
    @Override
    public Point mapCoordinate(Point coordinate) {
        Point result = new Point();
        result.setLocation(coordinate);
        for (CoordinateMapper mapper : mappers) {
            result = mapper.mapCoordinate(result);
        }
        return result;
    }
}
