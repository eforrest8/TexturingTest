package com.sgolc.worldstate.entitycomponent;

import java.util.LinkedList;
import java.util.List;

/**
 * An ECSystem contains the actions performed on Entities and their Components.
 */
public abstract class ECSystem {

    //private final List<Runnable> preUpdateCallbacks = new LinkedList<>();
    //private final List<Runnable> postUpdateCallbacks = new LinkedList<>();
    protected abstract void operation();

    public void update() {
        //preUpdateCallbacks.forEach(Runnable::run);
        operation();
        //postUpdateCallbacks.forEach(Runnable::run);
    }

}
