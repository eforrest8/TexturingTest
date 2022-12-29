package com.sgolc.input;

/**
 * Holds the name, version, and system architecture of a platform.
 * Values should always match those returned by System.getProperty() with arguments of
 * "os.name", "os.version", or "os.arch".
 */
public record Platform(String name, String version, String arch) {
    public Platform getCurrentPlatform() {
        return new Platform(
                System.getProperty("os.name"),
                System.getProperty("os.version"),
                System.getProperty("os.arch")
        );
    }
}
