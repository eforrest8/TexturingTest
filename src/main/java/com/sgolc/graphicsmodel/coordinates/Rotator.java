package com.sgolc.graphicsmodel.coordinates;

public class Rotator implements CoordinateMapper {

    private final double angle;

    /**
     * Applies a rotation to a given coordinate relative to the origin (0,0).
     * Does not modify coordinate space.
     * @param angle Angle of rotation in radians.
     */
    public Rotator(double angle) {
        this.angle = angle;
    }

    @Override
    public Point mapCoordinate(Point coordinate) {
        if (coordinate.equals(new Point(0,0))) {
            return coordinate;
        }
        double radius = Math.sqrt(coordinate.x*coordinate.x + coordinate.y*coordinate.y);
        double theta;
        if (coordinate.x == 0) {
            theta = coordinate.y > 0 ? Math.PI/2 : -Math.PI/2;
        } else {
            theta = Math.acos(
                    (coordinate.x * coordinate.x + radius * radius - coordinate.y * coordinate.y) /
                            (2 * coordinate.x * radius));
        }
        theta += angle;
        double beta = Math.PI/2 - theta;
        double knownRelation = radius / Math.sin(Math.PI/2);
        return new Point(Math.sin(beta) * knownRelation, Math.sin(theta) * knownRelation);
    }
}
