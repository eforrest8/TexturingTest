package com.sgolc.worldstate.testworld;

import com.sgolc.worldstate.entitycomponent.Component;

public interface KeyValueComponent<K, V> extends Component {
    K getKey();
    V getValue();
}
