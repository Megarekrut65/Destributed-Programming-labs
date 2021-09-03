package com.company.part1.lab1.a;

import javax.swing.*;

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
    public static void main(String[] args) {
        JFrame win = getFrame();
        JSlider slider = getSlider();
        Thread threadRight = createThread(1, slider);
        Thread threadLeft = createThread(-1, slider);
        JPanel panel = getPanel(slider, threadRight, threadLeft);
        win.setContentPane(panel);
        win.setVisible(true);
        threadRight.start();
        threadLeft.start();
    }

    private static JPanel getPanel(JSlider slider, Thread threadRight, Thread threadLeft) {
        JPanel panel = new JPanel();
        JTextArea textPriorityLeft = new JTextArea(String.valueOf(threadLeft.getPriority()));
        JTextArea textPriorityRight= new JTextArea(String.valueOf(threadRight.getPriority()));
        panel.add(createButton(threadLeft,textPriorityLeft,1,"+"));
        panel.add(textPriorityLeft);
        panel.add(createButton(threadLeft,textPriorityLeft,-1,"-"));
        panel.add(slider);
        panel.add(createButton(threadRight, textPriorityRight, 1,"+"));
        panel.add(textPriorityRight);
        panel.add(createButton(threadRight, textPriorityRight, -1,"-"));
        return panel;
    }

    static Thread createThread(int value, JSlider slider) {
        Thread thread = new Thread(new MyRunnable(new SliderEditor(slider), value));
        thread.setPriority(Thread.NORM_PRIORITY);
        return thread;
    }
    static JButton createButton(Thread thread, JTextArea textPriorityLeft, int value, String title)
    {
        JButton button = new JButton(title);
        button.addActionListener(e->{
            int priority = thread.getPriority() + value;
            if(priority>= Thread.MIN_PRIORITY && priority <= Thread.MAX_PRIORITY)
            {
                thread.setPriority(priority);
                textPriorityLeft.setText(String.valueOf(priority));
            }
        });
        return button;
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
