package com.sgolc.worldstate.entitycomponent;

import java.util.*;

/**
 * An Entity is a distinct "thing." It primarily exists for organizational purposes,
 * acting as a container for Components and target for Systems.
 */
public class Entity implements Comparable<Entity> {
    public final UUID uuid = UUID.randomUUID();
    public final Set<Component> components = new HashSet<>();

    @Override
    public int compareTo(Entity o) {
        return uuid.compareTo(o.uuid);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Entity entity) {
            return uuid.equals(entity.uuid);
        } else {
            return false;
        }
    }
}
