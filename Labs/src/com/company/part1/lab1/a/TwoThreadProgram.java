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
    private static Thread threadRight;
    private static Thread threadLeft;
    public static void main(String[] args) {
        JFrame win = getFrame();
        JSlider slider = getSlider();
        threadRight = getThread(1, slider);
        threadLeft = getThread(-1, slider);
        JPanel panel = getPanel(slider);
        win.setContentPane(panel);
        win.setVisible(true);
    }
    private static JLabel getPriorityLabel(String text)
    {
        JLabel textPriority = new JLabel(text);
        textPriority.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        return textPriority;
    }
    private static void createFramePart(Thread thread, JPanel panel)
    {
        JLabel textPriority = getPriorityLabel(String.valueOf(thread.getPriority()));
        panel.add(getChangeButton(thread,textPriority,1,"+"));
        panel.add(textPriority);
        panel.add(getChangeButton(thread,textPriority,-1,"-"));
    }
    private static JPanel getPanel(JSlider slider) {
        JPanel panel = new JPanel();
        createFramePart(threadLeft,panel);//Create buttons and label for left Thread
        panel.add(slider);
        createFramePart(threadRight,panel);//Create buttons and label for right Thread
        panel.add(getStartButton());
        return panel;
    }
    /**
     *  Creates btn for starting program
     */
    private static JButton getStartButton()
    {
        JButton startBtn = new JButton("Start");
        var ref = new Object() {
            boolean isStart = false;
        };
        startBtn.addActionListener(e->{
            if(!ref.isStart)
            {
                threadRight.start();
                threadLeft.start();
                ref.isStart = true;
            }
        });
        return startBtn;
    }
    private static Thread getThread(int value, JSlider slider) {
        Thread thread = new Thread(new MyRunnable(new SliderEditor(slider), value));
        thread.setPriority(Thread.NORM_PRIORITY);
        return thread;
    }

    /**
     *  Creates btn for editing thread priority
     */
    private static JButton getChangeButton(Thread thread, JLabel textPriorityLeft, int value, String title)
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
