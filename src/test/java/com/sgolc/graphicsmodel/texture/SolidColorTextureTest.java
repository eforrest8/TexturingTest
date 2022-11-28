package com.sgolc.graphicsmodel.texture;

import com.sgolc.graphicsmodel.coordinates.Point;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.awt.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SolidColorTextureTest {

    private static final Color TEST_COLOR = Color.CYAN;
    private static final Color MISS_COLOR = new Color(0,0,0,0);

    private static Stream<Arguments> getColorAtCoordinateCases() {
        return Stream.of(
                Arguments.of(new Point(0,0), TEST_COLOR),
                Arguments.of(new Point(1,1), TEST_COLOR),
                Arguments.of(new Point(0.5,0.5), TEST_COLOR),
                Arguments.of(new Point(-1,-1), MISS_COLOR),
                Arguments.of(new Point(1.1,1.1), MISS_COLOR)
        );
    }

    @ParameterizedTest
    @MethodSource("getColorAtCoordinateCases")
    void getColorAtCoordinate(Point point, Color expected) {
        SolidColorTexture texture = new SolidColorTexture(TEST_COLOR);
        assertEquals(expected, texture.getColorAtCoordinate(point));
    }
}