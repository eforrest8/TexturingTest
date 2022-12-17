package com.sgolc.worldstate.testworld;

import com.sgolc.worldstate.entitycomponent.ECSystem;
import com.sgolc.worldstate.entitycomponent.Entity;
import com.sgolc.worldstate.entitycomponent.EntityManager;

public class InitSystem extends ECSystem {
    @Override
    public EntityManager.Query query() {
        return null;
    }

    @Override
    public void operation(Entity entity) {}

    @Override
    public void update(EntityManager manager) {
        TransparentGradientEntityBuilder.build(manager);
        CheckerboardEntityBuilder.build(manager);
    }
}
