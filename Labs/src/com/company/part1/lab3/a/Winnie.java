package com.company.part1.lab3.a;

import java.util.concurrent.Semaphore;

public class Winnie implements Runnable{
    private final HoneyPot honeyPot;
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
        synchronized (honeyPot){
            honeyPot.notify();
        }
    }

    @Override
    public void run() {
        while (!Thread.interrupted())
        {
            try {
                synchronized (honeyPot){
                    honeyPot.wait();
                }
                semaphore.acquire();
                System.out.println("Winnie eats all honey!");
                honeyPot.eatHoney();
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
