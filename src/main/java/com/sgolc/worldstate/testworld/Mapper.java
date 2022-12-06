package com.sgolc.worldstate.testworld;

import com.sgolc.graphicsmodel.coordinates.CoordinateMapper;
import com.sgolc.worldstate.entitycomponent.Component;

public interface Mapper extends Component {
    CoordinateMapper getMapper();
}
