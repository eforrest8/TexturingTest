package com.sgolc.graphicsmodel.coordinates;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

public class RotatorTest {

    private static final double EPSILON = 1e-10;

    public static Stream<Arguments> cases() {
        return Stream.of(
                Arguments.of(new Point(10,0), Math.PI*2, new Point(10,0)),
                Arguments.of(new Point(0,10), Math.PI*2, new Point(0,10)),
                Arguments.of(new Point(10,10), Math.PI*2, new Point(10,10)),
                Arguments.of(new Point(-10,0), Math.PI, new Point(10,0)),
                Arguments.of(new Point(0,-10), Math.PI, new Point(0,10)),
                Arguments.of(new Point(-10,-10), Math.PI, new Point(10,10)),
                Arguments.of(new Point(0,10), Math.PI/2, new Point(10,0)),
                Arguments.of(new Point(-10,0), Math.PI/2, new Point(0,10)),
                Arguments.of(new Point(-10,10), Math.PI/2, new Point(10,10)),
                Arguments.of(new Point(Math.cos(Math.PI/4),Math.sin(Math.PI/4)), Math.PI/4, new Point(1, 0)),
                Arguments.of(new Point(Math.cos(3*Math.PI/4),Math.sin(3*Math.PI/4)), Math.PI/4, new Point(0,1)),
                Arguments.of(new Point(Math.cos(Math.PI/2)*Math.sqrt(2),Math.sin(Math.PI/2)*Math.sqrt(2)), Math.PI/4, new Point(1,1)),
                Arguments.of(new Point(10,0), 0, new Point(10,0)),
                Arguments.of(new Point(0,10), 0, new Point(0,10)),
                Arguments.of(new Point(0,0), 0, new Point(0,0)),
                Arguments.of(new Point(Math.cos(1),Math.sin(1)), 1, new Point(1,0)),
                Arguments.of(new Point(-Math.sin(1),Math.cos(1)), 1, new Point(0,1)),
                Arguments.of(new Point(Math.cos(1) - Math.sin(1),Math.cos(1) + Math.sin(1)), 1, new Point(1,1)),
                Arguments.of(new Point(Math.cos(-1) - Math.sin(-1),Math.cos(-1) + Math.sin(-1)), -1, new Point(1,1)),
                Arguments.of(new Point(0,0), 1, new Point(0,0))
                );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void mapCoordinate(Point expected, double angle, Point input) {
        Point actual = new Rotator(angle).mapCoordinate(input);
        Assertions.assertTrue(Math.abs(expected.x - actual.x) < EPSILON && Math.abs(expected.y - actual.y) < EPSILON,
                "Expected: " + expected + " but was " + actual + "\nAngle was " + angle + ", input was " + input + "\n");
    }

    public static Stream<Arguments> offsetCases() {
        return Stream.of(
                Arguments.of(new Point(2,0), Math.PI*2, new Point(1,1), new Point(2,0)),
                Arguments.of(new Point(0,2), Math.PI*2, new Point(1,1), new Point(0,2)),
                Arguments.of(new Point(2,2), Math.PI*2, new Point(1,1), new Point(2,2)),
                Arguments.of(new Point(1+Math.cos(Math.PI/4),1+Math.sin(Math.PI/4)), Math.PI/4, new Point(1,1), new Point(2, 1)),
                Arguments.of(new Point(1+Math.cos(3*Math.PI/4),1+Math.sin(3*Math.PI/4)), Math.PI/4, new Point(1,1), new Point(1,2)),
                Arguments.of(new Point(1+Math.cos(Math.PI/2)*Math.sqrt(2),1+Math.sin(Math.PI/2) * Math.sqrt(2)), Math.PI/4, new Point(1,1), new Point(2,2))
                );
    }

    @ParameterizedTest
    @MethodSource("offsetCases")
    void mapCoordinateWithOffset(Point expected, double angle, Point origin, Point input) {
        Point actual = new Rotator(angle, origin).mapCoordinate(input);
        Assertions.assertTrue(Math.abs(expected.x - actual.x) < EPSILON && Math.abs(expected.y - actual.y) < EPSILON,
                "Expected: " + expected + " but was " + actual +
                        "\nAngle was " + angle + ", origin was " + origin + " input was " + input + "\n");
    }
}
