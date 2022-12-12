package com.sgolc.worldstate.testworld;

import com.sgolc.graphicsmodel.texture.TestGradientTexture;
import com.sgolc.graphicsmodel.texture.Texture;
import com.sgolc.graphicsmodel.texture.TransparentTexture;
import com.sgolc.worldstate.entitycomponent.Entity;
import com.sgolc.worldstate.entitycomponent.EntityManager;

public class TransparentGradientEntityBuilder {
    private final static Texture GRADIENT_TEXTURE = new TestGradientTexture();
    private final static double ROTATE = Math.PI / 4;

    private TransparentGradientEntityBuilder() {}

    public static Entity build(EntityManager manager) {
        return manager.createEntity(
                new TextureComponent(new TransparentTexture(GRADIENT_TEXTURE, 0.6f)),
                new TranslateComponent(0.5, 0.5),
                new RotateComponent(ROTATE),
                new ScaleComponent(0.5, 0.5)
        );
    }
}
