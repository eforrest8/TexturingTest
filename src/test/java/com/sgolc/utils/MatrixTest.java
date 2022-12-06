package com.sgolc.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MatrixTest {

    @Test
    void add() {
        Matrix A = new Matrix(new double[][]{{0,1,2},{3,4,5},{6,7,8}});
        Matrix B = new Matrix(new double[][]{{1,1,2},{3,2,2},{4,1,2}});
        Assertions.assertEquals(new Matrix(new double[][]{{1,2,4},{6,6,7},{10,8,10}}), A.add(B));
    }

    @Test
    void scalarMultiply() {
        Matrix A = new Matrix(new double[][]{{0,1,2},{3,4,5},{6,7,8}});
        Assertions.assertEquals(new Matrix(new double[][]{{0,2,4},{6,8,10},{12,14,16}}), A.multiply(2));
    }

    @Test
    void vectorMultiply() {
        Matrix A = new Matrix(new double[][]{{0,1,2},{3,4,5},{6,7,8}});
        Assertions.assertEquals(new Matrix(new double[][]{{11},{38},{65}}), A.multiply(new double[]{2,3,4}));
    }

    @Test
    void matrixMultiply() {
        Matrix A = new Matrix(new double[][]{{0,1,2},{3,4,5},{6,7,8}});
        Matrix B = new Matrix(new double[][]{{1,1,2},{3,2,2},{4,1,2}});
        Assertions.assertEquals(new Matrix(new double[][]{{11,4,6},{35,16,24},{59,28,42}}), A.multiply(B));
    }
}