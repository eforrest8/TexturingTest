package com.sgolc.worldstate.testworld;

import com.sgolc.worldstate.entitycomponent.ECSystem;
import com.sgolc.worldstate.entitycomponent.EntityManager;

import java.util.Arrays;

public class BasicPhysicsSystem extends ECSystem {

    private final EntityManager manager;
    private static final EntityManager.Query rotateQuery = new EntityManager.Query(
            new EntityManager.QueryPart(SpinningComponent.class, a -> true, EntityManager.ResultMergeMode.OR),
            new EntityManager.QueryPart(RotateComponent.class, a -> true, EntityManager.ResultMergeMode.AND)
    );

    public BasicPhysicsSystem(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    protected void operation() {
        manager.computeQuery(rotateQuery)
                .forEach(entity -> {
                    double dAngle = entity.getComponentByClass(SpinningComponent.class).orElseThrow().rotation();
                    entity.replaceComponent(c ->
                            c instanceof RotateComponent
                                    ? new RotateComponent(((RotateComponent) c).angle() + dAngle)
                                    : c);
                });
    }
}
