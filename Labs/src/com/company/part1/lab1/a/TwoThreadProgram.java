package com.company.part1.lab1.a;

import javax.swing.*;

class SliderEditor {
    JSlider slider;

    public SliderEditor(JSlider slider) {
        this.slider = slider;
    }

    public synchronized void changeValue(int value) {
        slider.setValue(slider.getValue() + value);
    }
}
public class TwoThreadProgram {
    public static void main(String[] args) {
        JFrame win = createFrame();
        JSlider slider = createSlider();
        Thread threadRight = createThread(1, slider);
        Thread threadLeft = createThread(-1, slider);
        JPanel panel = createPanel(slider, threadRight, threadLeft);
        win.setContentPane(panel);
        win.setVisible(true);
        threadRight.start();
        threadLeft.start();
    }

    private static JPanel createPanel(JSlider slider, Thread threadRight, Thread threadLeft) {
        JPanel panel = new JPanel();
        JTextArea textPriorityLeft = new JTextArea(String.valueOf(threadLeft.getPriority()));
        JTextArea textPriorityRight= new JTextArea(String.valueOf(threadRight.getPriority()));
        panel.add(createChangeButton(threadLeft,textPriorityLeft,1,"+"));
        panel.add(textPriorityLeft);
        panel.add(createChangeButton(threadLeft,textPriorityLeft,-1,"-"));
        panel.add(slider);
        panel.add(createChangeButton(threadRight, textPriorityRight, 1,"+"));
        panel.add(textPriorityRight);
        panel.add(createChangeButton(threadRight, textPriorityRight, -1,"-"));
        return panel;
    }

    static Thread createThread(int value, JSlider slider) {
        Thread thread = new Thread(new MyRunnable(new SliderEditor(slider), value));
        thread.setPriority(Thread.NORM_PRIORITY);
        return thread;
    }
    static JButton createChangeButton(Thread thread, JTextArea textPriorityLeft, int value, String title)
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
    private static JSlider createSlider() {
        JSlider slider = new JSlider(10,90,50);
        return slider;
    }

    private static JFrame createFrame() {
        JFrame win = new JFrame("TwoThreadProgram");
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setSize(450, 300);
        win.setResizable(false);
        win.setLocationRelativeTo(null);
        return win;
    }
}
