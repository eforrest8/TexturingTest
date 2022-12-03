package com.sgolc.worldstate.testworld;

import com.sgolc.graphicsmodel.coordinates.CoordinateMapper;
import com.sgolc.graphicsmodel.coordinates.Point;
import com.sgolc.graphicsmodel.coordinates.Rotator;

public class RotateComponent implements Mapper {

    public double angle;
    public Point origin = new Point(0,0);

    public RotateComponent(double angle) {
        this.angle = angle;
    }

    public RotateComponent(double angle, Point origin) {
        this.angle = angle;
        this.origin = origin;
    }

    @Override
    public CoordinateMapper getMapper() {
        return new Rotator(angle, origin);
    }
}
