package com.sgolc.graphicsmodel.coordinates;

public class TranslationMapper implements CoordinateMapper {

    private final double dx;
    private final double dy;

    public TranslationMapper(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public Point mapCoordinate(Point coordinate) {
        return new Point(coordinate.x + dx, coordinate.y + dy);
    }
}
