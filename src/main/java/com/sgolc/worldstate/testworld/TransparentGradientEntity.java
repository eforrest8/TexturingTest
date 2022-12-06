package com.sgolc.worldstate.testworld;

import com.sgolc.graphicsmodel.texture.TestGradientTexture;
import com.sgolc.graphicsmodel.texture.Texture;
import com.sgolc.graphicsmodel.texture.TransparentTexture;
import com.sgolc.utils.Matrix;

public class TransparentGradientEntity extends ScreenSpaceEntity {
    private final static Texture GRADIENT_TEXTURE = new TestGradientTexture();

    public void setTransparency(double alpha) {
        setTexture(new TransparentTexture(GRADIENT_TEXTURE, (float)alpha));
    }

    public TransparentGradientEntity(double alpha) {
        setTransparency(alpha);
        setTranslate(0.5, 0.5);
        double rotate = Math.PI / 4;
        components.add(new LinearTransformComponent(new Matrix(new double[][]{
                {Math.cos(rotate)*2,Math.sin(rotate)*2},
                {-Math.sin(rotate)*2,Math.cos(rotate)*2}
        })));
    }
}
