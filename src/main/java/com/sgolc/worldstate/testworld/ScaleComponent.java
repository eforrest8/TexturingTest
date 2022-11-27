package com.sgolc.worldstate.testworld;

import com.sgolc.graphicsmodel.coordinates.CoordinateMapper;
import com.sgolc.graphicsmodel.coordinates.Point;
import com.sgolc.graphicsmodel.coordinates.QuadTransform;
import com.sgolc.worldstate.entitycomponent.Component;

public record ScaleComponent(double x, double y) implements Mapper {
    public CoordinateMapper getMapper() {
        return new QuadTransform(
                new Point(0, 0),
                new Point(x, 0),
                new Point(x, y),
                new Point(0, y)
        );
    }
}
