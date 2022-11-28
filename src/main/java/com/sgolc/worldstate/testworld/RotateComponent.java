package com.sgolc.worldstate.testworld;

import com.sgolc.graphicsmodel.coordinates.CoordinateMapper;
import com.sgolc.graphicsmodel.coordinates.Rotator;

public class RotateComponent implements Mapper {

    public double angle;

    public RotateComponent(double angle) {
        this.angle = angle;
    }

    @Override
    public CoordinateMapper getMapper() {
        return new Rotator(angle);
    }
}
