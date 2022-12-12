package com.sgolc.worldstate.testworld;

import com.sgolc.graphicsmodel.coordinates.CoordinateMapper;
import com.sgolc.graphicsmodel.coordinates.LinearTransform;
import com.sgolc.utils.Matrix;
import com.sgolc.worldstate.entitycomponent.Component;

public record RotateComponent(double angle) implements Mapper {

    @Override
    public CoordinateMapper getMapper() {
        return new LinearTransform(new Matrix(new double[][]{
                {Math.cos(angle),Math.sin(angle)},
                {-Math.sin(angle),Math.cos(angle)}
        }));
    }

    @Override
    public int compareTo(Component o) {
        if (o instanceof RotateComponent r) {
            return Double.compare(angle(), r.angle());
        }
        throw new RuntimeException();
    }
}
