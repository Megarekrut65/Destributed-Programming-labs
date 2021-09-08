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
            System.out.println(Thread.currentThread().getName()+" get semaphore");
            while (!Thread.interrupted())
            {
                editor.changeValue(value);
                System.out.println(Thread.currentThread().getName()+" is active");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                    break;
                }
            }
            semaphore.release();
            System.out.println(Thread.currentThread().getName()+" free semaphore");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
