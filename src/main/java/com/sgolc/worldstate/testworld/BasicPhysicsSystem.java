package com.sgolc.worldstate.testworld;

import com.sgolc.worldstate.entitycomponent.ECSystem;
import com.sgolc.worldstate.entitycomponent.Entity;
import com.sgolc.worldstate.entitycomponent.EntityStore;

public class BasicPhysicsSystem extends ECSystem {

    private static final EntityStore.Query rotateQuery = new EntityStore.Query(
            new EntityStore.QueryPart<>(SpinningComponent.class, a -> true, EntityStore.ResultMergeMode.OR),
            new EntityStore.QueryPart<>(RotateComponent.class, a -> true, EntityStore.ResultMergeMode.AND)
    );

    public void update() {
        query(rotateQuery).forEach(BasicPhysicsSystem::rotateEntity);
    }

    private static void rotateEntity(Entity entity) {
        double dAngle = entity.getComponentByClass(SpinningComponent.class).orElseThrow().rotation();
        entity.replaceComponent(c ->
                c instanceof RotateComponent
                        ? new RotateComponent(((RotateComponent) c).angle() + dAngle)
                        : c);
    }
}
