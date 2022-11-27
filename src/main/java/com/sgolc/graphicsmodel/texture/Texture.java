package com.sgolc.graphicsmodel.texture;

import com.sgolc.graphicsmodel.coordinates.CoordinateMapper;
import com.sgolc.graphicsmodel.coordinates.Point;

import java.awt.*;

public interface Texture {
    Color getColorAtCoordinate(Point coordinate);
    default Color getColorAtCoordinate(Point coordinate, CoordinateMapper mapper) {
        return getColorAtCoordinate(mapper.mapCoordinate(coordinate));
    }
}
