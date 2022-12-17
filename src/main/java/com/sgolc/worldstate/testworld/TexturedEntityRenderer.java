package com.sgolc.worldstate.testworld;

import com.sgolc.graphicsmodel.MappedTexture;
import com.sgolc.graphicsmodel.TextureRenderer;
import com.sgolc.graphicsmodel.coordinates.CoordinateMapper;
import com.sgolc.graphicsmodel.coordinates.MultiMapper;
import com.sgolc.graphicsmodel.texture.CompositingTexture;
import com.sgolc.graphicsmodel.texture.Texture;
import com.sgolc.worldstate.entitycomponent.ECSystem;
import com.sgolc.worldstate.entitycomponent.Entity;
import com.sgolc.worldstate.entitycomponent.EntityManager;

import java.util.SortedMap;
import java.util.TreeMap;

public class TexturedEntityRenderer extends ECSystem {

    private final SortedMap<ZIndexComponent, MappedTexture> processedTextures = new TreeMap<>(this::zIndexSort);
    private static final EntityManager.Query QUERY = new EntityManager.Query(
            new EntityManager.QueryPart(TextureComponent.class, (a) -> true, EntityManager.ResultMergeMode.OR)
    );

    public TexturedEntityRenderer(TextureRenderer target) {
        preUpdateCallbacks.add(processedTextures::clear);
        postUpdateCallbacks.add(() -> this.triggerNewFrame(target));
    }

    private void triggerNewFrame(TextureRenderer target) {
        target.drawFrame(new CompositingTexture(processedTextures.values().toArray(Texture[]::new)));
    }

    @Override
    public EntityManager.Query query() {
        return QUERY;
    }

    @Override
    public void operation(Entity entity) {
        processedTextures.putIfAbsent(entity.getComponentByClass(ZIndexComponent.class)
                .orElse(new ZIndexComponent(Integer.MIN_VALUE)), buildMappedTexture(entity));
    }

    private int zIndexSort(ZIndexComponent a, ZIndexComponent b) {
        return Integer.compare(a.zIndex(), b.zIndex());
    }

    private MappedTexture buildMappedTexture(Entity entity) {
        TextureComponent tex = entity.getComponentByClass(TextureComponent.class).orElseThrow();
        CoordinateMapper[] maps = entity.getAllComponentsByClass(Mapper.class)
                .map(Mapper::getMapper)
                .toArray(CoordinateMapper[]::new);
        return new MappedTexture(tex.texture(), new MultiMapper(maps));
    }
}
