package com.sgolc.graphicsmodel.coordinates;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

public class RotatorTest {

    private static final double epsilon = 1e-10;

    public static Stream<Arguments> cases() {
        return Stream.of(
                Arguments.of(new Point(10,0), Math.PI*2, new Point(10,0)),
                Arguments.of(new Point(0,10), Math.PI*2, new Point(0,10)),
                Arguments.of(new Point(-10,0), Math.PI, new Point(10,0)),
                Arguments.of(new Point(0,-10), Math.PI, new Point(0,10)),
                Arguments.of(new Point(0,10), Math.PI/2, new Point(10,0)),
                Arguments.of(new Point(-10,0), Math.PI/2, new Point(0,10)),
                Arguments.of(new Point(10 * Math.cos(Math.PI/4),10 * Math.sin(Math.PI/4)), Math.PI/4, new Point(10, 0)),
                Arguments.of(new Point(-10 * Math.cos(Math.PI/4),10 * Math.sin(Math.PI/4)), Math.PI/4, new Point(0,10)),
                Arguments.of(new Point(10,0), 0, new Point(10,0)),
                Arguments.of(new Point(0,10), 0, new Point(0,10)),
                Arguments.of(new Point(0,0), 0, new Point(0,0))
                );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void mapCoordinate(Point expected, double angle, Point input) {
        Point actual = new Rotator(angle).mapCoordinate(input);
        Assertions.assertTrue(Math.abs(expected.x - actual.x) < epsilon,
                "Expected: " + expected + " but was " + actual + "\nAngle was " + angle + "\n");
        Assertions.assertTrue(Math.abs(expected.y - actual.y) < epsilon,
                "Expected: " + expected + " but was " + actual + "\nAngle was " + angle + "\n");
    }
}
