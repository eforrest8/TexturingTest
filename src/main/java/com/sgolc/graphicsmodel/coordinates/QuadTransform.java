package com.sgolc.graphicsmodel.coordinates;

/**
 * This class represents a 2D quad defined by its corners.
 * It includes a CoordinateMapper to convert calls to its
 * constituent Texture to the appropriate coordinate space.
 */
public class QuadTransform implements CoordinateMapper {

    private final Point[] points = new Point[4];

    public QuadTransform() {
        this(
                new Point(0, 0),
                new Point(1, 0),
                new Point(1, 1),
                new Point(0, 1)
        );
    }

    /**
     * Create a new QuadTransform from a given set of four points.
     * Points are assumed to be in counter-clockwise winding
     * and positive values are assumed to be right and up.
     */
    public QuadTransform(Point first, Point second, Point third, Point fourth) {
        this.points[0] = first;
        this.points[1] = second;
        this.points[2] = third;
        this.points[3] = fourth;
    }

    @Override
    public Point mapCoordinate(Point coordinate) {
        // sorry future me for the mathy variable names but this is all i got

        Point q = Point.fromMatrix(coordinate.subtract(points[0]));
        Point b1 = Point.fromMatrix(points[1].subtract(points[0]));
        Point b2 = Point.fromMatrix(points[3].subtract(points[0]));
        Point b3 = Point.fromMatrix(((points[0].subtract(points[1])).subtract(points[3])).add(points[2]));

        double A = Point.wedge(b2, b3);
        double B = Point.wedge(b3, q) - Point.wedge(b1, b2);
        double C = Point.wedge(b1, q);

        Point uv = new Point();

        if (Math.abs(A) < 0.001) {
            uv.setY(-C/B);
        } else {
            double discrim = B*B - 4*A*C;
            uv.setY(0.5 * (-B - Math.sqrt(discrim)) / A);
        }

        Point denom = Point.fromMatrix(b1.add(b3.multiply(uv.getY())));
        if (Math.abs(denom.getX()) > Math.abs(denom.getY())) {
            uv.setX((q.getX() - b2.getX() * uv.getY()) / denom.getX());
        } else {
            uv.setX((q.getY() - b2.getY() * uv.getY()) / denom.getY());
        }

        return uv;
    }

}
