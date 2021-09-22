package com.company.part1.lab3.a;

import java.util.concurrent.Semaphore;

public class Bee implements Runnable{
    private Winnie winnie;
    private HoneyPot honeyPot;
    private Semaphore semaphore;
    private Thread thread;
    private static int currentId = 0;
    private int id;
    public Bee(Winnie winnie, HoneyPot honeyPot, Semaphore semaphore) {
        this.winnie = winnie;
        this.honeyPot = honeyPot;
        this.semaphore = semaphore;
        thread = new Thread(this);
        thread.start();
        currentId++;
        id = currentId;
    }

    @Override
    public void run() {
        while (!Thread.interrupted())
        {
            try {
                Thread.sleep(1000);
                semaphore.acquire();
                System.out.println("Bee " + id + " puts honey to pot!");
                if(honeyPot.addHoney())
                {
                    System.out.println("Bee " + id + " wakes up Winnie!");
                    winnie.wakeUp();
                }
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
