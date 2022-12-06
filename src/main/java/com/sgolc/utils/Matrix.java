package com.sgolc.utils;

import java.util.Arrays;

public class Matrix {

    protected double[][] data;

    public Matrix(double[][] newData) {
        data = newData;
    }

    public int getWidth() {
        return data[0].length;
    }

    public int getHeight() {
        return data.length;
    }

    public <T extends Matrix> Matrix add(T m) {
        if (!isSameSize(m)) {
            throw new ArithmeticException("Can't add matrices of different sizes!");
        }
        double[][] result = new double[getHeight()][getWidth()];
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                result[i][j] = data[i][j] + m.data[i][j];
            }
        }
        return new Matrix(result);
    }

    public <T extends Matrix> Matrix subtract(T m) {
        if (!isSameSize(m)) {
            throw new ArithmeticException("Can't subtract matrices of different sizes!");
        }
        double[][] result = new double[getHeight()][getWidth()];
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                result[i][j] = data[i][j] - m.data[i][j];
            }
        }
        return new Matrix(result);
    }

    public Matrix multiply(double s) {
        double[][] result = new double[getHeight()][getWidth()];
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                result[i][j] = data[i][j] * s;
            }
        }
        return new Matrix(result);
    }

    public Matrix multiply(double[] v) {
        if (v.length != getWidth()) {
            throw new IllegalArgumentException("Vector length does not match matrix!");
        }
        double[][] result = new double[getHeight()][1];
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                result[i][0] += data[i][j] * v[j];
            }
        }
        return new Matrix(result);
    }

    public <T extends Matrix> Matrix multiply(T m) {
        if (getWidth() != m.getHeight()) {
            throw new IllegalArgumentException("Matrix dimensions do not match!");
        }
        double[][] result = new double[getHeight()][m.getWidth()];
        for (int row = 0; row < getHeight(); row++) {
            for (int col = 0; col < m.getWidth(); col++) {
                for (int k = 0; k < getWidth(); k++) {
                    result[row][col] += data[row][k] * m.data[k][col];
                }
            }
        }
        return new Matrix(result);
    }

    public void setData(double[][] newData) {
        data = newData;
    }

    public double getElement(int row, int col) {
        return data[row][col];
    }

    public void setElement(int row, int col, double value) {
        data[row][col] = value;
    }

    private boolean isSameSize(Matrix m) {
        return this.getHeight() == m.getHeight() && this.getWidth() == m.getWidth();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Matrix m) {
            return isSameSize(m) && Arrays.deepEquals(data, m.data);
        }
        return false;
    }

    @Override
    public String toString() {
        return Arrays.deepToString(data);
    }
}
