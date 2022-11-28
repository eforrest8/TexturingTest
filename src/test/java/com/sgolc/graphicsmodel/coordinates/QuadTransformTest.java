package com.sgolc.graphicsmodel.coordinates;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuadTransformTest {

    private static Stream<Arguments> nullTransformCases() {
        return Stream.of(
                Arguments.of(new Point(0,0), new Point(0,0)),
                Arguments.of(new Point(0.5,0.5), new Point(0.5,0.5)),
                Arguments.of(new Point(1,1), new Point(1,1)),
                Arguments.of(new Point(1,0), new Point(1,0))
        );
    }

    @ParameterizedTest
    @MethodSource("nullTransformCases")
    void mapCoordinateGivesCorrectValueForNullTransform(Point expected, Point input) {
        QuadTransform quad = new QuadTransform();
        Point actual = quad.mapCoordinate(input);
        assertEquals(expected, actual);
    }

    private static Stream<Arguments> shrinkArguments() {
        return Stream.of(
                Arguments.of(new Point(0,0), new Point(0,0)),
                Arguments.of(new Point(0.5,0.5), new Point(5,5)),
                Arguments.of(new Point(1,1), new Point(10,10)),
                Arguments.of(new Point(2,2), new Point(20,20))
        );
    }

    @ParameterizedTest
    @MethodSource("shrinkArguments")
    void mapCoordinateGivesCorrectValueForShrink(Point expected, Point input) {
        QuadTransform quad = new QuadTransform(
                new Point(0, 0),
                new Point(10, 0),
                new Point(10, 10),
                new Point(0, 10)
        );
        Point actual = quad.mapCoordinate(input);
        assertEquals(expected, actual);
    }

    private static Stream<Arguments> translateArguments() {
        return Stream.of(
                Arguments.of(new Point(-1,-1), new Point(0,0)),
                Arguments.of(new Point(0,0), new Point(5,5)),
                Arguments.of(new Point(0.5,0.5), new Point(7.5,7.5)),
                Arguments.of(new Point(1,1), new Point(10,10))
        );
    }

    @ParameterizedTest
    @MethodSource("translateArguments")
    void mapCoordinateGivesCorrectValueForTranslate(Point expected, Point input) {
        QuadTransform quad = new QuadTransform(
                new Point(5, 5),
                new Point(10, 5),
                new Point(10, 10),
                new Point(5, 10)
        );
        Point actual = quad.mapCoordinate(input);
        assertEquals(expected, actual);
    }

    private static Stream<Arguments> deformArguments() {
        return Stream.of(
                Arguments.of(new Point(0,0), new Point(0,0)),
                Arguments.of(new Point(0.5,2.0/3), new Point(5,5)),
                Arguments.of(new Point(1,1), new Point(10,5)),
                Arguments.of(new Point(1,2), new Point(10,10))
        );
    }

    @ParameterizedTest
    @MethodSource("deformArguments")
    void mapCoordinateGivesCorrectValueForDeform(Point expected, Point input) {
        QuadTransform quad = new QuadTransform(
                new Point(0, 0),
                new Point(10, 0),
                new Point(10, 5),
                new Point(0, 10)
        );
        Point actual = quad.mapCoordinate(input);
        assertEquals(expected, actual);
    }

    private static Stream<Arguments> horDeformArguments() {
        return Stream.of(
                Arguments.of(new Point(0,0), new Point(0,0)),
                Arguments.of(new Point(2.0/3,0.5), new Point(5,5)),
                Arguments.of(new Point(4.0/3,0.5), new Point(10,5)),
                Arguments.of(new Point(2,1), new Point(10,10))
        );
    }

    @ParameterizedTest
    @MethodSource("horDeformArguments")
    void mapCoordinateGivesCorrectValueForHorizontalDeform(Point expected, Point input) {
        QuadTransform quad = new QuadTransform(
                new Point(0, 0),
                new Point(10, 0),
                new Point(5, 10),
                new Point(0, 10)
        );
        Point actual = quad.mapCoordinate(input);
        assertEquals(expected, actual);
    }

    private static Stream<Arguments> skewArguments() {
        return Stream.of(
                Arguments.of(new Point(0,0), new Point(0,0)),
                Arguments.of(new Point(0.25,0.5), new Point(5,5)),
                Arguments.of(new Point(0.5,1), new Point(10,10)),
                Arguments.of(new Point(1,1), new Point(15,10))
        );
    }

    @ParameterizedTest
    @MethodSource("skewArguments")
    void mapCoordinateGivesCorrectValueForSkew(Point expected, Point input) {
        QuadTransform quad = new QuadTransform(
                new Point(0, 0),
                new Point(10, 0),
                new Point(15, 10),
                new Point(5, 10)
        );
        Point actual = quad.mapCoordinate(input);
        assertEquals(expected, actual);
    }

}