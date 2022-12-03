package com.sgolc.graphicsmodel.coordinates;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class ScalerTest {

    private static Stream<Arguments> scaleCases() {
        return Stream.of(
                Arguments.of(new Point(0,0), new Point(0,0), new Point(0,0)),
                Arguments.of(new Point(0,0), new Point(0,0), new Point(1,1)),
                Arguments.of(new Point(0,0), new Point(1,1), new Point(0,0)),
                Arguments.of(new Point(1,1), new Point(1,1), new Point(1,1)),
                Arguments.of(new Point(0,0), new Point(0.5,0.5), new Point(0,0)),
                Arguments.of(new Point(0.5,0.5), new Point(0.5,0.5), new Point(1,1)),
                Arguments.of(new Point(0,0), new Point(2,2), new Point(0,0)),
                Arguments.of(new Point(1,1), new Point(2,2), new Point(0.5,0.5)),
                Arguments.of(new Point(2,2), new Point(2,2), new Point(1,1)),
                Arguments.of(new Point(-0.0,-0.0), new Point(-1,-1), new Point(0,0)),
                Arguments.of(new Point(-1,-1), new Point(-1,-1), new Point(1,1))
        );
    }

    @ParameterizedTest
    @MethodSource("scaleCases")
    void mapCoordinate(Point expected, Point scale, Point input) {
        Scaler mapper = new Scaler(scale.x, scale.y);
        Assertions.assertEquals(expected, mapper.mapCoordinate(input), "Scale was: " + scale);
    }

}