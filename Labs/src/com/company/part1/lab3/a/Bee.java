package com.company.part1.lab3.a;

import java.util.concurrent.Semaphore;

public class Bee implements Runnable{
    private Winnie winnie;
    private HoneyPot honeyPot;
    private static int currentId = 0;
    private int id;
    public Bee(Winnie winnie, HoneyPot honeyPot) {
        this.winnie = winnie;
        this.honeyPot = honeyPot;
        currentId++;
        id = currentId;
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (!Thread.interrupted())
        {
            try {
                Thread.sleep(id* 1000L);
                if(honeyPot.addHoney(id))
                {
                    winnie.wakeUp(id);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
