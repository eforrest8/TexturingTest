package com.sgolc.worldstate.testworld;

import com.sgolc.input.InputDevice;
import com.sgolc.input.InputDeviceService;
import com.sgolc.worldstate.entitycomponent.ECSystem;

public class InputPollingSystem extends ECSystem {

    private InputDevice[] devices;

    public void updateDevices(InputDeviceService service) {
        devices = service.enumerateDevices();
    }

    public void poll() {
        for (InputDevice device : devices) {
            device.getBufferedEvents().forEach(System.out::println);
        }
    }

}
