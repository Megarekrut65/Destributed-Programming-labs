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
    private static JLabel priorityTextLeft;
    private static JLabel priorityTextRight;
    private static JLabel info;
    private static final int valueLeft = -1;
    private static final int valueRight = 1;
    private static int threadActiveNumber = 0;
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
        int priority;
        JLabel textPriority = new JLabel();
        textPriority.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        if(side == Side.LEFT)
        {
            priorityTextLeft = textPriority;
            priority = threadLeft.getPriority();
        }else
        {
            priorityTextRight = textPriority;
            priority = threadRight.getPriority();
        }
        textPriority.setText(String.valueOf(priority));
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
        info = new JLabel("Free");
        panel.add(info);
        return panel;
    }
    private static void changeInfo(boolean isStart)
    {
        if(isStart)
        {
            info.setText("Busy with the thread");
            threadActiveNumber++;
        }
        else
        {
            threadActiveNumber--;
            if(threadActiveNumber == 0) info.setText("Free");
        }
    }
    private static void createStartButtonEvent(JButton start,JButton stop,int priority,
            JLabel priorityText, Side side)
    {
        start.addActionListener(e->{
                    Thread thread;
                    if(side == Side.LEFT) thread = threadLeft;
                    else thread = threadRight;
                    stop.setEnabled(true);
                    start.setEnabled(false);
                    thread.setPriority(priority);
                    priorityText.setText(String.valueOf(thread.getPriority()));
                    thread.start();
                    changeInfo(true);
                }
        );
    }
    private static void createStopButtonEvent(JButton start,JButton stop, Side side)
    {
        stop.addActionListener(e->{
            if(side == Side.LEFT)
            {
                threadLeft.interrupt();
                threadLeft = getThread(valueLeft);
            }
            else
            {
                threadRight.interrupt();
                threadRight = getThread(valueRight);
            }
            start.setEnabled(true);
            stop.setEnabled(false);
            changeInfo(false);
        });
    }
    private static void createStartStopButtons(JPanel panel)
    {
        JButton startLeft = new JButton("Start1");
        JButton startRight = new JButton("Start2");
        JButton stopLeft = new JButton("Stop1");
        JButton stopRight = new JButton("Stop2");
        createStartButtonEvent(startLeft,stopLeft, Thread.MIN_PRIORITY,priorityTextLeft, Side.LEFT);
        createStopButtonEvent(startLeft,stopLeft,Side.LEFT);
        createStartButtonEvent(startRight,stopRight,Thread.MAX_PRIORITY, priorityTextRight, Side.RIGHT);
        createStopButtonEvent(startRight,stopRight,Side.RIGHT);
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
            if(thread.isInterrupted()) return;
            int priority = thread.getPriority() + value;
            if(priority>= Thread.MIN_PRIORITY && priority <= Thread.MAX_PRIORITY)
            {
                thread.setPriority(priority);
                textPriorityLeft.setText(String.valueOf(thread.getPriority()));
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
        win.setSize(450, 150);
        win.setResizable(false);
        win.setLocationRelativeTo(null);
        return win;
    }
}
