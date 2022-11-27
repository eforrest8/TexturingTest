package com.sgolc.graphicsmodel;

import com.sgolc.graphicsmodel.coordinates.CoordinateMapper;
import com.sgolc.graphicsmodel.coordinates.Point;
import com.sgolc.graphicsmodel.texture.Texture;

import java.awt.*;

public class MappedTexture implements Texture, CoordinateMapper {
    private final Texture texture;
    private final CoordinateMapper mapper;

    public MappedTexture(Texture texture, CoordinateMapper mapper) {
        this.texture = texture;
        this.mapper = mapper;
    }

    @Override
    public Color getColorAtCoordinate(com.sgolc.graphicsmodel.coordinates.Point coordinate) {
        return texture.getColorAtCoordinate(coordinate, this);
    }

    @Override
    public Color getColorAtCoordinate(com.sgolc.graphicsmodel.coordinates.Point coordinate, CoordinateMapper mapper) {
        return texture.getColorAtCoordinate(coordinate, mapper);
    }

    @Override
    public com.sgolc.graphicsmodel.coordinates.Point mapCoordinate(Point coordinate) {
        return mapper.mapCoordinate(coordinate);
    }
}
