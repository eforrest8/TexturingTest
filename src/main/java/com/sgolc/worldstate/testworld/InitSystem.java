package com.sgolc.worldstate.testworld;

import com.sgolc.input.swing.SwingKeyboardDeviceProvider;
import com.sgolc.worldstate.entitycomponent.ECSystem;
import com.sgolc.worldstate.entitycomponent.Entity;
import com.sgolc.worldstate.entitycomponent.EntityStore;

import java.awt.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class InitSystem extends ECSystem {

    private Entity dimensionsSettings;

    public void update(EntityStore manager) {
        TransparentGradientEntityBuilder.build(manager);
        CheckerboardEntityBuilder.build(manager);
        dimensionsSettings = manager.createEntity(
                new DimensionSettingComponent("internal dimension", new Dimension(256, 256)),
                new DimensionSettingComponent("output dimension", new Dimension(256, 256))
        );
        InputPollingSystem input = new InputPollingSystem();
        input.updateDevices(new SwingKeyboardDeviceProvider());
        new ScheduledThreadPoolExecutor(1).scheduleAtFixedRate(input::poll, 100, 10, TimeUnit.MILLISECONDS);
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
