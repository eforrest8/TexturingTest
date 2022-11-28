package com.sgolc.worldstate.testworld;

import com.sgolc.graphicsmodel.TextureRenderer;
import com.sgolc.graphicsmodel.texture.CompositingTexture;
import com.sgolc.graphicsmodel.texture.Texture;
import com.sgolc.worldstate.entitycomponent.ECSystem;

import java.util.List;
import java.util.Objects;

public class GradientAndCheckerRenderer extends ECSystem {

    private final TextureRenderer target;
    private final CheckerboardEntity checkerboard = new CheckerboardEntity();
    private final TransparentGradientEntity gradient = new TransparentGradientEntity(0.6);

    public GradientAndCheckerRenderer(TextureRenderer target) {
        this.target = target;
        gradient.setzIndex(1);
        entities.addAll(List.of(checkerboard, gradient));
    }

    @Override
    public void update() {
        Texture compositor = new CompositingTexture(entities.stream()
                .map(e -> e instanceof ScreenSpaceEntity sse ? sse : null)
                .filter(Objects::nonNull)
                .sorted(this::zIndexComparator)
                .map(ScreenSpaceEntity::getMappedTexture)
                .toArray(Texture[]::new));
        target.setScreenTexture(compositor);
    }

    private int zIndexComparator(ScreenSpaceEntity a, ScreenSpaceEntity b) {
        return Integer.compare(a.getzIndex(), b.getzIndex());
    }
}
