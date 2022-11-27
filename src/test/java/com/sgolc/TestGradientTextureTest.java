package com.sgolc;

import com.sgolc.graphicsmodel.coordinates.Point;
import com.sgolc.graphicsmodel.texture.TestGradientTexture;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.awt.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestGradientTextureTest {

    private static Stream<Arguments> generalCoordinateCases() {
        return Stream.of(
                Arguments.of(new Point(0,0), new Color(0,0,255)),
                Arguments.of(new Point(1,0), new Color(255,0,255)),
                Arguments.of(new Point(0,1), new Color(0,255,255)),
                Arguments.of(new Point(1,1), new Color(255,255,0)),
                Arguments.of(new Point(0.5,0.5), new Color(128,128,191))
        );
    }

    @ParameterizedTest
    @MethodSource("generalCoordinateCases")
    void getColorAtCoordinate(Point point, Color expected) {
        TestGradientTexture gradientTexture = new TestGradientTexture();
        Color actual = gradientTexture.getColorAtCoordinate(point);
        assertEquals(expected, actual);
    }
}