package com.company.part1.lab1.a;

import javax.swing.*;

class SliderEditor
{
    JSlider slider;

    public SliderEditor(JSlider slider) {
        this.slider = slider;
    }
    public synchronized void changeValue(int value)
    {
        int newValue = slider.getValue() + value;
        if(newValue >= 10 && newValue <= 90)
        {
            slider.setValue(newValue);
        }
    }
}
class MyRunnable implements Runnable
{
    SliderEditor editor;
    int value;

    public MyRunnable(SliderEditor editor, int value) {
        this.editor = editor;
        this.value = value;
    }

    @Override
    public void run() {
        while (!Thread.interrupted())
        {
            editor.changeValue(value);
        }
    }
}
public class TwoThreadProgram {
    static Thread thread1;
    static Thread thread2;
    static Thread createThread(int value, JSlider slider)
    {
        return new Thread(new MyRunnable(new SliderEditor(slider),value));
    }
    public static void main(String[] args) {
        JFrame win = new JFrame("TwoThreadProgram");
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setSize(800, 400);
        win.setResizable(false);
        win.setLocationRelativeTo(null);
        JPanel panel = new JPanel();

        win.setContentPane(panel);
        win.setVisible(true);
    }
}
