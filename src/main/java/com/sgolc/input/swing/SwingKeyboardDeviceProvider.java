package com.sgolc.input.swing;

import com.sgolc.input.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.Instant;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Stream;

public class SwingKeyboardDeviceProvider implements InputDeviceService {
    @Override
    public Platform[] supportedPlatforms() {
        return new Platform[0];
    }

    @Override
    public InputDevice[] enumerateDevices() {
        return new InputDevice[] {new SwingKeyboard()};
    }
}

/**
 * Provides keyboard input via Java's built-in API.
 * Note that this class MUST be instantiated in the application's main thread.
 */
class SwingKeyboard implements InputDevice {

    private final Queue<InputEvent> eventQueue = new LinkedList<>();
    private final KeyboardFocusManager keyboard;

    public SwingKeyboard() {
        keyboard = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        keyboard.addKeyEventDispatcher(this::keyEventDispatcher);
    }

    public void removeListener() {
        keyboard.removeKeyEventDispatcher(this::keyEventDispatcher);
    }

    private boolean keyEventDispatcher(KeyEvent e) {
        if (e.getID() == KeyEvent.KEY_PRESSED || e.getID() == KeyEvent.KEY_RELEASED) {
            eventQueue.offer(new InputEvent(
                    this,
                    new Input(InputType.DIGITAL, () -> "KEYCODE_" + e.getKeyCode()),
                    Instant.ofEpochMilli(e.getWhen()),
                    e.getID() == KeyEvent.KEY_PRESSED ? 1f : 0f
            ));
        }
        return false;
    }

    @Override
    public Optional<String> getProperty(String key) {
        return Optional.empty();
    }

    @Override
    public InputDeviceForm getForm() {
        return InputDeviceForm.KEYBOARD;
    }

    @Override
    public Stream<InputEvent> getBufferedEvents() {
        if (eventQueue.isEmpty()) {
            return Stream.empty();
        }
        Stream.Builder<InputEvent> output = Stream.builder();
        while (!eventQueue.isEmpty()) {
            output.add(eventQueue.poll());
        }
        return output.build();
    }
}
