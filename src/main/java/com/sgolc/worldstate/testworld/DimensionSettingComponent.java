package com.sgolc.worldstate.testworld;

import com.sgolc.worldstate.entitycomponent.Component;

import java.awt.*;

public record DimensionSettingComponent(String key, Dimension value) implements KeyValueComponent<String, Dimension> {
    @Override
    public String getKey() {
        return key;
    }

    @Override
    public Dimension getValue() {
        return value;
    }
}
