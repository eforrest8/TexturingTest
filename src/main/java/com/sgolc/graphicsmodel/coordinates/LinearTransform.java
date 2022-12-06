package com.sgolc.graphicsmodel.coordinates;

import com.sgolc.utils.Matrix;

public class LinearTransform implements CoordinateMapper {

    private final Matrix transform;

    public LinearTransform(Matrix m) {
        transform = m;
    }

    @Override
    public Point mapCoordinate(Point coordinate) {
        return Point.fromMatrix(transform.multiply(new double[]{coordinate.getX(), coordinate.getY()}));
    }
}
