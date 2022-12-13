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

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GradientAndCheckerRenderer extends ECSystem {

    private TextureRenderer target;
    private EntityManager manager;
    private static final EntityManager.Query query = new EntityManager.Query(
            new EntityManager.QueryPart(TextureComponent.class, (a) -> true, EntityManager.ResultMergeMode.OR)
    );

    @Override
    protected void operation() {
        List<Entity> queryResults = manager.computeQuery(query);
        Texture compositor = new CompositingTexture(queryResults.stream()
                .sorted(this::zIndexSort)
                .map(this::buildMappedTexture)
                .toArray(Texture[]::new));
        target.setScreenTexture(compositor);
    };

    private int zIndexSort(Entity a, Entity b) {
        int aIndex = Arrays.stream(a.components())
                .map(c -> c instanceof ZIndexComponent z ? z : null)
                .filter(Objects::nonNull)
                .findAny()
                .orElse(new ZIndexComponent(0))
                .zIndex();
        int bIndex = Arrays.stream(b.components())
                .map(c -> c instanceof ZIndexComponent z ? z : null)
                .filter(Objects::nonNull)
                .findAny()
                .orElse(new ZIndexComponent(0))
                .zIndex();
        return Integer.compare(aIndex, bIndex);
    }

    private MappedTexture buildMappedTexture(Entity entity) {
        TextureComponent tex = Arrays.stream(entity.components())
                .map(component -> component instanceof TextureComponent t ? t : null)
                .filter(Objects::nonNull)
                .findAny()
                .orElseThrow();
        CoordinateMapper[] maps = Arrays.stream(entity.components())
                .map(component -> component instanceof Mapper m ? m : null)
                .filter(Objects::nonNull)
                .map(Mapper::getMapper)
                .toArray(CoordinateMapper[]::new);
        return new MappedTexture(tex.texture(), new MultiMapper(maps));
    }

    public GradientAndCheckerRenderer(TextureRenderer target, EntityManager manager) {
        this.target = target;
        this.manager = manager;
        Entity gradient = TransparentGradientEntityBuilder.build(manager);
        Entity check = CheckerboardEntityBuilder.build(manager);
        manager.addComponentToEntity(gradient, new ZIndexComponent(1));
        manager.addComponentToEntity(check, new ZIndexComponent(0));
    }
}
