package com.sgolc.worldstate.testworld;

import com.sgolc.graphicsmodel.coordinates.CoordinateMapper;
import com.sgolc.graphicsmodel.coordinates.TranslationMapper;

public record TranslateComponent(double x, double y) implements Mapper {
    public CoordinateMapper getMapper() {
        return new TranslationMapper(x, y);
    }
}
