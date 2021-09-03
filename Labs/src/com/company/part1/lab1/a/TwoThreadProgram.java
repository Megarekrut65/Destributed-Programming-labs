package com.company.part1.lab1.a;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class SliderEditor {
    JSlider slider;

    public SliderEditor(JSlider slider) {
        this.slider = slider;
    }

    public synchronized void changeValue(int value) {
        slider.setValue(slider.getValue() + value);
        /*int newValue = slider.getValue() + value;
        if (newValue >= 10 && newValue <= 90) {
            slider.setValue(newValue);
        }*/
    }
}

class MyRunnable implements Runnable {
    SliderEditor editor;
    int value;

    public MyRunnable(SliderEditor editor, int value) {
        this.editor = editor;
        this.value = value;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            editor.changeValue(value);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class TwoThreadProgram {
    static Thread threadRight;
    static Thread threadLeft;

    static Thread createThread(int value, JSlider slider) {
        return new Thread(new MyRunnable(new SliderEditor(slider), value));
    }

    public static void main(String[] args) {
        JFrame win = getFrame();
        JPanel panel = new JPanel();
        JSlider slider = getSlider();
        threadRight = createThread(1, slider);
        threadLeft = createThread(-1, slider);
        threadRight.setPriority(Thread.MAX_PRIORITY);
        threadLeft.setPriority(Thread.MIN_PRIORITY);
        panel.add(slider);
        win.setContentPane(panel);
        win.setVisible(true);
        threadRight.start();
        threadLeft.start();
    }

    private static JSlider getSlider() {
        JSlider slider = new JSlider(10,90,50);
        return slider;
    }

    private static JFrame getFrame() {
        JFrame win = new JFrame("TwoThreadProgram");
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setSize(400, 300);
        win.setResizable(false);
        win.setLocationRelativeTo(null);
        return win;
    }
}
