package com.sgolc.input;

/**
 * Provides an abstraction layer for input devices of various sorts,
 * to be accessed over various protocols.
 */
public interface InputDeviceService {

    /**
     * List all platforms supported by this service.
     */
    Platform[] supportedPlatforms();

    /**
     * List all input devices currently accessible via this service.
     */
    InputDevice[] enumerateDevices();

}