package com.sgolc.worldstate.testworld;

import com.sgolc.graphicsmodel.coordinates.CoordinateMapper;
import com.sgolc.graphicsmodel.coordinates.Scaler;

public record ScaleComponent(double x, double y) implements Mapper {
    public CoordinateMapper getMapper() {
        return new Scaler(x,y);
    }
}
