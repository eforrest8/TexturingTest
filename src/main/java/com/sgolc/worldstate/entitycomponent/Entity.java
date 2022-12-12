package com.sgolc.worldstate.entitycomponent;

import java.util.*;

/**
 * An Entity is a distinct "thing." It primarily exists for organizational purposes,
 * acting as a container for Components and target for Systems.
 */
public record Entity(int id, Component... components) implements Comparable<Entity> {

    @Override
    public int compareTo(Entity o) {
        return Integer.compare(id, o.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Entity entity) {
            return id == entity.id;
        } else {
            return false;
        }
    }
}
