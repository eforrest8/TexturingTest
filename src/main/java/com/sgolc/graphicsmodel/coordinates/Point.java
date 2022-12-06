package com.sgolc.graphicsmodel.coordinates;

import com.sgolc.utils.Matrix;

public class Point extends Matrix {

    public Point() {
        this(0, 0);
    }

    public Point(double x, double y) {
        super(new double[][]{{x}, {y}});
    }

    public Point(Point p) {
        this(p.getX(), p.getY());
    }

    public static Point fromMatrix(Matrix m) {
        if (m.getWidth() != 1 || m.getHeight() != 2) {
            throw new IllegalArgumentException("Provided matrix does not match required dimensions!");
        }
        return new Point(m.getElement(0, 0), m.getElement(1,0));
    }

    public static double wedge(Point a, Point b) {
        //v.x*w.y - v.y*w.x
        return a.getX() * b.getY() - a.getY() * b.getX();
    }

    public double getX() {
        return getElement(0,0);
    }

    public double getY() {
        return getElement(1,0);
    }

    public void setX(double value) {
        setElement(0,0,value);
    }

    public void setY(double value) {
        setElement(1,0,value);
    }

    public void setLocation(Point p) {
        setData(new double[][]{{p.getX()}, {p.getY()}});
    }

    @Override
    public <T extends Matrix> Point subtract(T m) {
        return Point.fromMatrix(super.subtract(m));
    }

    public double distance(double x, double y) {
        return Math.sqrt(Math.pow(getX() - x, 2) + Math.pow(getY() - y, 2));
    }

    @Override
    public String toString() {
        return "Point(x=" + getX() + ",y=" + getY() + ")";
    }
}
