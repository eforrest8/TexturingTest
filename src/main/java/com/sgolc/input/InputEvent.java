package com.sgolc.input;

import java.time.Instant;

public record InputEvent(InputDevice device, Input input, Instant timestamp, float value) {
}
