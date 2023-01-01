package com.sgolc.input;

/**
 * Catalogue of known gamepad inputs.
 * Used to map gamepads from different manufacturers to a common format.
 */
public enum GamepadIdentity implements InputIdentity {
    POV_UP,
    POV_DOWN,
    POV_LEFT,
    POV_RIGHT,
    FACE_NORTH,
    FACE_SOUTH,
    FACE_WEST,
    FACE_EAST,
    SELECT,
    START,
    HOME,
    LEFT_STICK_ANALOG,
    LEFT_STICK_BUTTON,
    RIGHT_STICK_ANALOG,
    RIGHT_STICK_BUTTON,
    LEFT_SHOULDER_NEAR,
    LEFT_SHOULDER_FAR,
    RIGHT_SHOULDER_NEAR,
    RIGHT_SHOULDER_FAR
}
