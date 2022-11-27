package com.sgolc.worldstate.testworld;

import com.sgolc.graphicsmodel.coordinates.CoordinateMapper;
import com.sgolc.graphicsmodel.coordinates.Point;
import com.sgolc.graphicsmodel.coordinates.QuadTransform;
import com.sgolc.worldstate.entitycomponent.Component;

public record ScreenPositionComponent(double x, double y) implements Mapper {
    public CoordinateMapper getMapper() {
        return new QuadTransform(
                new Point(x, y),
                new Point(x+1, y),
                new Point(x+1, y+1),
                new Point(x, y+1)
        );
    }
}
