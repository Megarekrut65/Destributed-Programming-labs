package com.company.part1.lab2.a;

import java.util.ArrayList;

public class Hive {
    private final int beeNumber;
    private final ArrayList<Bee> bee;
    public Hive(int beeNumber,Forest forest) {
        this.beeNumber = beeNumber;
        bee = new ArrayList<>();
        for(int i = 0; i < beeNumber; i++)
        {
            bee.add(new Bee(forest));
        }
    }
    public void start()
    {
        for(int i = 0; i < beeNumber; i++)
        {
            Thread thread = new Thread(bee.get(i));
            thread.setDaemon(true);
            thread.start();
        }
    }
}
