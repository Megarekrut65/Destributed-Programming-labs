package com.company.part1.lab1.b;

import com.company.part1.lab1.Side;

import javax.swing.*;
import java.util.Objects;
import java.util.concurrent.Semaphore;

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
    private static MyThread threadRight;
    private static MyThread threadLeft;
    private static Semaphore semaphore;
    private static JSlider slider;
    private static final int valueLeft = -1;
    private static final int valueRight = 1;
    public static void main(String[] args) {
        int semaphoreNumber = 1;
        semaphore = new Semaphore(semaphoreNumber,true);
        JFrame win = getFrame();
        slider = getSlider();
        threadRight = getThread(valueRight);
        threadLeft = getThread(valueLeft);
        JPanel panel = getPanel();
        win.setContentPane(panel);
        win.setVisible(true);
    }
    private static JLabel getPriorityLabel(Side side)
    {
        int priority = 0;
        if(side == Side.LEFT) priority = threadLeft.getPriority();
        else priority = threadRight.getPriority();
        JLabel textPriority = new JLabel(String.valueOf(priority));
        textPriority.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        return textPriority;
    }
    private static void createFramePart(Side side, JPanel panel)
    {
        JLabel textPriority = getPriorityLabel(side);
        panel.add(getChangeButton(side,textPriority,1,"+"));
        panel.add(textPriority);
        panel.add(getChangeButton(side,textPriority,-1,"-"));
    }
    private static JPanel getPanel() {
        JPanel panel = new JPanel();
        createFramePart(Side.LEFT,panel);//Create buttons for left Thread
        panel.add(slider);
        createFramePart(Side.RIGHT,panel);//Create buttons for right Thread
        createStartStopButtons(panel);
        return panel;
    }
    private static void createStartStopButtons(JPanel panel)
    {
        JButton startLeft = new JButton("Start1");
        JButton startRight = new JButton("Start2");
        JButton stopLeft = new JButton("Stop1");
        JButton stopRight = new JButton("Stop2");
        startLeft.addActionListener(e->{
            startRight.setEnabled(false);
            stopLeft.setEnabled(true);
            threadLeft = getThread(valueLeft);
            threadLeft.setPriority(Thread.MIN_PRIORITY);
            threadLeft.start();
        });
        stopLeft.addActionListener(e->{
            threadLeft.interrupt();
            startRight.setEnabled(true);
            stopLeft.setEnabled(false);
        });
        startRight.addActionListener(e->{
            startLeft.setEnabled(false);
            stopRight.setEnabled(true);
            threadRight = getThread(valueRight);
            threadRight.setPriority(Thread.MAX_PRIORITY);
            threadRight.start();
        });
        stopRight.addActionListener(e->{
            threadRight.interrupt();
            startLeft.setEnabled(true);
            stopRight.setEnabled(false);
        });
        stopLeft.setEnabled(false);
        stopRight.setEnabled(false);
        panel.add(startLeft);
        panel.add(stopLeft);
        panel.add(startRight);
        panel.add(stopRight);
    }
    private static MyThread getThread(int value) {
        MyThread thread = new MyThread(new SliderEditor(slider), value, semaphore);
        thread.setPriority(Thread.NORM_PRIORITY);
        return thread;
    }

    /**
     *  Creates btn for editing thread priority
     */
    private static JButton getChangeButton(Side side, JLabel textPriorityLeft, int value, String title)
    {
        JButton button = new JButton(title);
        button.addActionListener(e->{
            Thread thread;
            if(side == Side.LEFT) thread = threadLeft;
            else thread = threadRight;
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
        slider.setEnabled(false);
        return slider;
    }

    private static JFrame getFrame() {
        JFrame win = new JFrame("TwoThreadProgram");
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setSize(450, 120);
        win.setResizable(false);
        win.setLocationRelativeTo(null);
        return win;
    }
}
