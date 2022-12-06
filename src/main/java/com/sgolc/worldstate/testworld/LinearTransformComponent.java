package com.sgolc.worldstate.testworld;

import com.sgolc.graphicsmodel.coordinates.CoordinateMapper;
import com.sgolc.graphicsmodel.coordinates.LinearTransform;
import com.sgolc.utils.Matrix;

public class LinearTransformComponent implements Mapper {

    public final Matrix matrix;

    public LinearTransformComponent(Matrix m) {
        matrix = m;
    }

    @Override
    public CoordinateMapper getMapper() {
        return new LinearTransform(matrix);
    }
}
