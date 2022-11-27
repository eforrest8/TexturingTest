package com.sgolc.worldstate.testworld;

import com.sgolc.graphicsmodel.texture.Texture;
import com.sgolc.worldstate.entitycomponent.Component;

public record TextureComponent(Texture texture) implements Component {}
