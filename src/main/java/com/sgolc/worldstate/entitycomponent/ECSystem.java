package com.sgolc.worldstate.entitycomponent;

import java.util.LinkedList;
import java.util.List;

/**
 * An ECSystem contains the actions performed on Entities and their Components.
 */
public abstract class ECSystem {

    protected final List<Runnable> preUpdateCallbacks = new LinkedList<>();
    protected final List<Runnable> postUpdateCallbacks = new LinkedList<>();
    protected List<Entity> queryResult;

    public abstract EntityManager.Query query();
    public abstract void operation(Entity entity);

    public void update(EntityManager manager) {
        preUpdateCallbacks.forEach(Runnable::run);
        queryResult = manager.computeQuery(query());
        queryResult.forEach(this::operation);
        postUpdateCallbacks.forEach(Runnable::run);
    }

}
