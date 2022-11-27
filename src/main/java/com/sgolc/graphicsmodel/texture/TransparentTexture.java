package com.sgolc.graphicsmodel.texture;

import com.sgolc.graphicsmodel.coordinates.Point;

import java.awt.*;

public class TransparentTexture implements Texture {

    private final Texture texture;
    private final float alphaScale;

    public TransparentTexture(Texture texture, float alphaScale) {
        this.texture = texture;
        this.alphaScale = alphaScale;
    }

    @Override
    public Color getColorAtCoordinate(Point coordinate) {
        Color baseColor = texture.getColorAtCoordinate(coordinate);
        return new Color(
                baseColor.getRed(),
                baseColor.getGreen(),
                baseColor.getBlue(),
                (int)Math.max(0, Math.min(255, baseColor.getAlpha() * alphaScale))
        );
    }
}
