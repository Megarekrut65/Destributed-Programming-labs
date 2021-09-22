package com.company.part1.lab3.a;

import java.util.concurrent.Semaphore;

public class Winnie implements Runnable{
    private final HoneyPot honeyPot;
    private Thread thread;

    public Winnie(HoneyPot honeyPot) {
        this.honeyPot = honeyPot;
        thread = new Thread(this);
        thread.start();
    }

    public void wakeUp(int beeID)
    {
        synchronized (honeyPot){
            System.out.println("Bee-" + beeID + " wakes up Winnie!");
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
                honeyPot.eatHoney();
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
