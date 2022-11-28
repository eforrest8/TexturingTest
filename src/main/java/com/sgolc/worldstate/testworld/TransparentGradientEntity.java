package com.sgolc.worldstate.testworld;

import com.sgolc.graphicsmodel.texture.TestGradientTexture;
import com.sgolc.graphicsmodel.texture.Texture;
import com.sgolc.graphicsmodel.texture.TransparentTexture;

public class TransparentGradientEntity extends ScreenSpaceEntity {
    private final static Texture GRADIENT_TEXTURE = new TestGradientTexture();

    public void setTransparency(double alpha) {
        setTexture(new TransparentTexture(GRADIENT_TEXTURE, (float)alpha));
    }

    public TransparentGradientEntity(double alpha) {
        setTransparency(alpha);
        setTranslate(0.25, 0.25);
        setScale(0.5, 0.5);
    }
}
