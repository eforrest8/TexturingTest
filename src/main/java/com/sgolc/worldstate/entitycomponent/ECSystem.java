package com.sgolc.worldstate.entitycomponent;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * An ECSystem contains the actions performed on Entities and their Components.
 */
public abstract class ECSystem {

    public final Set<Entity> entities = new TreeSet<>();

    public void addEntityWithComponents(Component... components) {
        Entity entity = new Entity();
        entity.components.addAll(List.of(components));
        entities.add(entity);
    }

    public abstract void update();
}
