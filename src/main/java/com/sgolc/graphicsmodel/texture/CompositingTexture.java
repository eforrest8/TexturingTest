package com.sgolc.graphicsmodel.texture;

import com.sgolc.graphicsmodel.coordinates.Point;

import java.awt.*;

public class CompositingTexture implements Texture {

    private final Texture[] textures;

    public CompositingTexture(Texture... textures) {
        this.textures = textures;
    }

    @Override
    public Color getColorAtCoordinate(Point coordinate) {
        Color result = new Color(0,0,0,0);
        for (int i = textures.length-1; i >= 0 && result.getAlpha() < 255; i--) {
            Color sample = textures[i].getColorAtCoordinate(coordinate);
            if (result.getAlpha() + sample.getAlpha() > 255) {
                sample = new Color(sample.getRed(), sample.getGreen(), sample.getBlue(), 255 - result.getAlpha());
            }
            double weight = sample.getAlpha()/255d;
            result = colorLerp(result, sample, weight, (result.getAlpha() + sample.getAlpha())/255f);
        }
        return result;
    }

    private Color colorLerp(Color from, Color to, double weight, float alpha) {
        float[] fromComp = from.getRGBComponents(null);
        float[] toComp = to.getRGBComponents(null);
        return new Color(
                (float) lerp(fromComp[0], toComp[0], weight),
                (float) lerp(fromComp[1], toComp[1], weight),
                (float) lerp(fromComp[2], toComp[2], weight),
                alpha
        );
    }

    private double lerp(double from, double to, double at) {
        return (from * (1 - at)) + (to * at);
    }
}
