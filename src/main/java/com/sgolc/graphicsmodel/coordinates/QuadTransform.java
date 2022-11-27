package com.sgolc.graphicsmodel.coordinates;

import com.sgolc.utils.MathUtils;

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

        Point q = new Point(coordinate.subtract(points[0]));
        Point b1 = new Point(points[1].subtract(points[0]));
        Point b2 = new Point(points[3].subtract(points[0]));
        Point b3 = new Point(((points[0].subtract(points[1])).subtract(points[3])).add(points[2]));

        float A = MathUtils.wedge(b2, b3);
        float B = MathUtils.wedge(b3, q) - MathUtils.wedge(b1, b2);
        float C = MathUtils.wedge(b1, q);

        Point uv = new Point();

        if (Math.abs(A) < 0.001) {
            uv.y = -C/B;
        } else {
            float discrim = B*B - 4*A*C;
            uv.y = (float) (0.5 * (-B - Math.sqrt(discrim)) / A);
        }

        Point denom = new Point(b1.add(b3.multiply(uv.y)));
        if (Math.abs(denom.x) > Math.abs(denom.y)) {
            uv.x = (q.x - b2.x * uv.y) / denom.x;
        } else {
            uv.x = (q.y - b2.y * uv.y) / denom.y;
        }

        return uv;
    }

}
