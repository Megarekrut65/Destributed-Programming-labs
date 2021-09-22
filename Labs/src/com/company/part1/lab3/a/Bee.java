package com.company.part1.lab3.a;

import java.util.concurrent.Semaphore;

public class Bee implements Runnable{
    private Winnie winnie;
    private HoneyPot honeyPot;
    private Semaphore semaphore;
    private Thread thread;
    private static int id = 0;

    public Bee(Winnie winnie, HoneyPot honeyPot, Semaphore semaphore) {
        this.winnie = winnie;
        this.honeyPot = honeyPot;
        this.semaphore = semaphore;
        thread = new Thread(this);
        thread.start();
        id++;
    }

    @Override
    public void run() {
        while (!Thread.interrupted())
        {
            try {
                semaphore.acquire();
                if(honeyPot.addHoney()) winnie.wakeUp();
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
