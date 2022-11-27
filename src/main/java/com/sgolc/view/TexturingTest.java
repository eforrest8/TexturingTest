package com.sgolc.view;

import javax.swing.*;
import javax.swing.event.ChangeListener;

public class TexturingTest {

    private static final GraphicsPanel graphicsPanel = new GraphicsPanel();

    private static final JFrame graphics = new JFrame();

    private static void createAndShowGUI() {
        //Create and set up the window.
        graphics.setBounds(320, 320, graphics.getWidth(), graphics.getHeight());
        graphics.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        graphics.add(new GraphicsPanel());
        //Display the window.
        graphics.pack();
        graphics.setVisible(true);

        createAndShowControls();
    }

    private static void createAndShowControls() {
        JFrame window = new JFrame();
        JPanel controls = new JPanel();
        window.add(controls);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JSlider xSlider = new JSlider(0,255);
        JSlider ySlider = new JSlider(0,255);

        JSpinner widthSpinner = new JSpinner(new SpinnerNumberModel(256, 256, 1024, 8));
        JSpinner heightSpinner = new JSpinner(new SpinnerNumberModel(256, 256, 768, 8));
        ChangeListener spinnerListener = e -> {
            int newWidth = ((SpinnerNumberModel) widthSpinner.getModel()).getNumber().intValue();
            int newHeight = ((SpinnerNumberModel) heightSpinner.getModel()).getNumber().intValue();
                graphicsPanel.setSize(newWidth, newHeight);
                graphics.setSize(
                        newWidth + graphics.getInsets().left + graphics.getInsets().right,
                        newHeight + graphics.getInsets().top + graphics.getInsets().bottom);
        };
        widthSpinner.addChangeListener(spinnerListener);
        heightSpinner.addChangeListener(spinnerListener);

        controls.add(xSlider);
        controls.add(ySlider);
        controls.add(widthSpinner);
        controls.add(heightSpinner);
        controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));

        window.pack();
        window.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TexturingTest::createAndShowGUI);
    }
}
