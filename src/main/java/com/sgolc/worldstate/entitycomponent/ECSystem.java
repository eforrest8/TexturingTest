package com.sgolc.worldstate.entitycomponent;

import java.util.Set;
import java.util.TreeSet;

/**
 * An ECSystem contains the actions performed on Entities and their Components.
 */
public abstract class ECSystem {

    public final Set<Entity> entities = new TreeSet<>();

    public abstract void update();
}
