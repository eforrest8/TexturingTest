package com.sgolc.graphicsmodel.coordinates;

public class Rotator implements CoordinateMapper {

    private final double angle;
    private final Point origin;

    /**
     * Applies a rotation to a given coordinate relative to the origin.
     * Origin is (0,0) by default.
     * @param angle Angle of rotation in radians.
     */
    public Rotator(double angle) {
        this(angle, new Point(0,0));
    }

    public Rotator(double angle, Point origin) {
        this.angle = angle;
        this.origin = origin;
    }

    @Override
    public Point mapCoordinate(Point coordinate) {
        // normalize input to unit circle
        Point normalized = coordinate.subtract(origin);
        // check for null transform
        if (normalized.equals(new Point(0,0)) || angle == 0) {
            return coordinate;
        }
        //find radius (length of hypotenuse)
        double radius = normalized.distance(0,0);
        double theta;
        if (normalized.getX() == 0) { // angle is vertical
            theta = normalized.getY() > 0 ? Math.PI/2 : -Math.PI/2;
        } else {
            theta = Math.acos(
                    (normalized.getX() * normalized.getX() + radius * radius - normalized.getY() * normalized.getY()) /
                            (2 * normalized.getX() * radius));
            theta = normalized.getY() > 0 ? theta : -theta;
        }
        theta += angle;
        return new Point(origin.getX() + Math.cos(theta) * radius, origin.getY() + Math.sin(theta) * radius);
    }
}
