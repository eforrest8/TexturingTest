package com.sgolc.worldstate.testworld;

import com.sgolc.worldstate.entitycomponent.ECSystem;
import com.sgolc.worldstate.entitycomponent.Entity;
import com.sgolc.worldstate.entitycomponent.EntityStore;

import java.awt.*;

public class InitSystem extends ECSystem {

    private Entity dimensionsSettings;

    public void update(EntityStore manager) {
        TransparentGradientEntityBuilder.build(manager);
        CheckerboardEntityBuilder.build(manager);
        dimensionsSettings = manager.createEntity(
                new DimensionSettingComponent("internal dimension", new Dimension(256, 256)),
                new DimensionSettingComponent("output dimension", new Dimension(256, 256))
        );
    }

    public void updateInternalDimension(Dimension d) {
        dimensionsSettings.replaceComponent(c -> {
            if (c instanceof DimensionSettingComponent dsc) {
                if (dsc.getKey().equals("internal dimension")) {
                    return new DimensionSettingComponent("internal dimension", d);
                }
            }
            return c;
        });
    }

    public void updateOutputDimension(Dimension d) {
        dimensionsSettings.replaceComponent(c -> {
            if (c instanceof DimensionSettingComponent dsc) {
                if (dsc.getKey().equals("output dimension")) {
                    return new DimensionSettingComponent("output dimension", d);
                }
            }
            return c;
        });
    }

}
