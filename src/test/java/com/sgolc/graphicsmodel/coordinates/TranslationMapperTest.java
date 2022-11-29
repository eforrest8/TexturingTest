package com.sgolc.graphicsmodel.coordinates;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class TranslationMapperTest {

    private static Stream<Arguments> translateCases() {
        return Stream.of(
                Arguments.of(new Point(0,0), new Point(0,0)),
                Arguments.of(new Point(1,1), new Point(1,1)),
                Arguments.of(new Point(-1,-1), new Point(-1,-1))
        );
    }

    @ParameterizedTest
    @MethodSource("translateCases")
    void mapCoordinate(Point expected, Point delta) {
        TranslationMapper mapper = new TranslationMapper(delta.x, delta.y);
        Assertions.assertEquals(expected, mapper.mapCoordinate(new Point(0,0)), "Delta was: " + delta);
    }
}