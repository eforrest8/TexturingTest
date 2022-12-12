package com.sgolc.worldstate.testworld;

import com.sgolc.worldstate.entitycomponent.Component;

public record ZIndexComponent(int zIndex) implements Component {
    @Override
    public int compareTo(Component o) {
        if (o instanceof ZIndexComponent c) {
            return Integer.compare(zIndex(), c.zIndex());
        }
        throw new RuntimeException();
    }
}
