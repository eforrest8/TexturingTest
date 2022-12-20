package com.sgolc.worldstate.entitycomponent;

import java.util.*;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * An Entity is a distinct "thing." It primarily exists for organizational purposes,
 * acting as a container for Components and target for Systems.
 */
public record Entity(int id, EntityStore manager, Component... components) implements Comparable<Entity> {

    @SuppressWarnings("unchecked")
    public <A extends Component> Optional<A> getComponentByClass(Class<A> aClass) {
        return Arrays.stream(components())
                .parallel()
                .map(c -> aClass.isInstance(c) ? (A)c : null)
                .filter(Objects::nonNull)
                .findAny();
    }

    @SuppressWarnings("unchecked")
    public <A extends Component> Stream<A> getAllComponentsByClass(Class<A> aClass) {
        return Arrays.stream(components())
                .parallel()
                .map(c -> aClass.isInstance(c) ? (A)c : null)
                .filter(Objects::nonNull);
    }

    public void replaceComponent(UnaryOperator<Component> operator) {
        manager().replaceComponent(this, operator);
    }

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
