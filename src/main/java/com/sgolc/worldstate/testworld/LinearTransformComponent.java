package com.sgolc.worldstate.testworld;

import com.sgolc.graphicsmodel.coordinates.CoordinateMapper;
import com.sgolc.graphicsmodel.coordinates.LinearTransform;
import com.sgolc.utils.Matrix;

public record LinearTransformComponent(Matrix matrix) implements Mapper {

    @Override
    public CoordinateMapper getMapper() {
        return new LinearTransform(matrix);
    }
}
