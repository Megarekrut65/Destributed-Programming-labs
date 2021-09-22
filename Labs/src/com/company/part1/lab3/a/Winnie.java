package com.company.part1.lab3.a;

import java.util.concurrent.Semaphore;

public class Winnie implements Runnable{
    private HoneyPot honeyPot;
    private Semaphore semaphore;
    private Thread thread;

    public Winnie(HoneyPot honeyPot, Semaphore semaphore) {
        this.honeyPot = honeyPot;
        this.semaphore = semaphore;
        thread = new Thread(this);
        thread.start();
    }

    public void wakeUp()
    {
        thread.notify();
    }

    @Override
    public void run() {
        while (!Thread.interrupted())
        {
            try {
                thread.wait();
                semaphore.acquire();
                honeyPot.eatHoney();
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
