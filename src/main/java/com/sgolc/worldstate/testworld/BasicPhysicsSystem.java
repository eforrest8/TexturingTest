package com.sgolc.worldstate.testworld;

import com.sgolc.worldstate.entitycomponent.ECSystem;
import com.sgolc.worldstate.entitycomponent.Entity;
import com.sgolc.worldstate.entitycomponent.EntityManager;

public class BasicPhysicsSystem extends ECSystem {

    private static final EntityManager.Query rotateQuery = new EntityManager.Query(
            new EntityManager.QueryPart(SpinningComponent.class, a -> true, EntityManager.ResultMergeMode.OR),
            new EntityManager.QueryPart(RotateComponent.class, a -> true, EntityManager.ResultMergeMode.AND)
    );

    @Override
    public EntityManager.Query query() {
        return rotateQuery;
    }

    @Override
    public void operation(Entity entity) {
        double dAngle = entity.getComponentByClass(SpinningComponent.class).orElseThrow().rotation();
        entity.replaceComponent(c ->
                c instanceof RotateComponent
                        ? new RotateComponent(((RotateComponent) c).angle() + dAngle)
                        : c);
    }
}
