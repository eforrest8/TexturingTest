package com.sgolc.worldstate.testworld;

public record IntSettingComponent(String key, Integer value) implements KeyValueComponent<String, Integer> {
    @Override
    public String getKey() {
        return key();
    }

    @Override
    public Integer getValue() {
        return value();
    }
}
