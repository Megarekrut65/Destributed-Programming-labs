package com.company.part1.lab2.a;

import java.util.ArrayList;

public class Hive {
    private final int beeNumber;
    private final ArrayList<Bee> bees;
    private final ArrayList<Thread> threads = new ArrayList<>();
    public Hive(int beeNumber,Forest forest, long duration) {
        this.beeNumber = beeNumber;
        bees = new ArrayList<>();
        for(int i = 0; i < beeNumber; i++)
        {
            bees.add(new Bee(forest, duration));
        }
    }
    public void start()
    {
        for(int i = 0; i < beeNumber; i++)
        {
            Thread thread = new Thread(bees.get(i));
            threads.add(thread);
            thread.setDaemon(true);
            thread.start();
        }
    }
    public void stop()
    {
        for(var th:threads)
        {
            if(th.isAlive()) th.interrupt();
        }
    }
}
