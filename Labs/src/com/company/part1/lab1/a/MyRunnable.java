package com.company.part1.lab1.a;

public class MyRunnable implements Runnable {
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