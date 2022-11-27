package com.sgolc.graphicsmodel.coordinates;

import java.awt.geom.Point2D;
import java.util.Objects;

public class Point extends Point2D {

    public float x;
    public float y;

    public Point() {
        this(0, 0);
    }

    public Point(float x, float y) {
        setLocation(x, y);
    }

    public Point(double x, double y) {
        setLocation(x, y);
    }

    public Point(Point p) {
        this(p.x, p.y);
    }

    public Point add(Point p) {
        return new Point(this.x + p.x, this.y + p.y);
    }

    public Point subtract(Point p) {
        return new Point(this.x - p.x, this.y - p.y);
    }

    public Point multiply(Point p) {
        return new Point(this.x * p.x, this.y * p.y);
    }

    public Point multiply(double s) {
        return new Point(this.x * s, this.y * s);
    }

    public Point divide(Point p) {
        return new Point(this.x / p.x, this.y / p.y);
    }

    public Point divide(double s) {
        return this.multiply(1/s);
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public void setLocation(double x, double y) {
        this.x = (float) x;
        this.y = (float) y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Point point = (Point) o;
        return java.lang.Float.compare(point.x, x) == 0 && java.lang.Float.compare(point.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), x, y);
    }

    @Override
    public String toString() {
        return "Point(x=" + x + ",y=" + y + ")";
    }
}
