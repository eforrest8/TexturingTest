package com.sgolc.input;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface InputDevice {
    Optional<String> getProperty(String key);
    InputDeviceForm getForm();
    List<Input> getInputs();
    Stream<InputEvent> getBufferedEvents();
}
