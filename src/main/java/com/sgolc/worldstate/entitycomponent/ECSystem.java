package com.sgolc.worldstate.entitycomponent;

import java.util.List;

/**
 * An ECSystem contains the actions performed on Entities and their Components.
 */
public abstract class ECSystem {

    private EntityStore store;

    public void setEntityStore(EntityStore store) {
        this.store = store;
    }

    public List<Entity> query(EntityStore.Query query) {
        return store.computeQuery(query);
    }

}
