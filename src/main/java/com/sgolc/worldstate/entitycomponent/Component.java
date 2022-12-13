package com.sgolc.worldstate.entitycomponent;

import java.io.Serializable;

/**
 * A Component holds data common to Entities with a given function.
 * A "Position" which holds an X/Y/Z coordinate would be a good component, for example.
 */
public interface Component extends Comparable<Component>, Serializable {
    @Override
    default int compareTo(Component o) {
        return Integer.compare(this.hashCode(), o.hashCode());
    }
}
