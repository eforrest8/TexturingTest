package com.sgolc.input;

import java.util.Optional;
import java.util.stream.Stream;

public interface InputDevice {
    Optional<String> getProperty(String key);
    InputDeviceForm getForm();
    Stream<InputEvent> getBufferedEvents();
}
