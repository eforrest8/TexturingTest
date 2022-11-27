package com.sgolc.utils;

import com.sgolc.graphicsmodel.coordinates.Point;

final public class MathUtils {

    private MathUtils(){}

    public static int floatToIntSpace(float r, float min, float max) {
        // adjust r to fit within range -1 to 1
        if (r == min) {
            return Integer.MIN_VALUE;
        }
        float normalized = (Math.nextDown(-1) * (max - r) + 1 * (r - min)) / (max - min);
        return Math.round(normalized * Integer.MAX_VALUE);
    }

    public static int floatToIntSpace(float r) {
        return floatToIntSpace(r, -Float.MAX_VALUE, Float.MAX_VALUE);
    }

    /**
     * Perform inverse linear interpolation/extrapolation; that is, determine what value
     * you would have to linearly interpolate by to achieve the value at.
     * @return If at is between from and to, a value in the range [0..1].
     * Otherwise, a value outside (but proportional to) that range.
     */
    public static float inverseLerp(float from, float to, float at) {
        if (to == from) {
            return 1;
        }
        return (at - from) / (to - from);
    }

    public static float wedge(Point a, Point b) {
        //v.x*w.y - v.y*w.x
        return a.x * b.y - a.y * b.x;
    }
}
