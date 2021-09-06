package com.company.part1.lab1.b;

import java.util.concurrent.Semaphore;

public class MyThread extends Thread{
    SliderEditor editor;
    int value;
    Semaphore semaphore;

    public MyThread(SliderEditor editor, int value, Semaphore semaphore) {
        this.editor = editor;
        this.value = value;
        this.semaphore = semaphore;
    }
    @Override
    public void run() {
        try {
            semaphore.acquire();
            while (!Thread.interrupted())
            {
                editor.changeValue(value);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }
}
