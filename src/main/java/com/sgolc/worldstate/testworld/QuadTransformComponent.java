package com.sgolc.worldstate.testworld;

import com.sgolc.graphicsmodel.coordinates.CoordinateMapper;
import com.sgolc.graphicsmodel.coordinates.Point;
import com.sgolc.graphicsmodel.coordinates.QuadTransform;

public class QuadTransformComponent implements Mapper {

    private final CoordinateMapper mapper;

    public QuadTransformComponent(Point first, Point second, Point third, Point fourth) {
        mapper = new QuadTransform(first, second, third, fourth);
    }

    @Override
    public CoordinateMapper getMapper() {
        return mapper;
    }
}
