package com.company.part1.lab3.a;

public class HoneyPot {
    private final int max;
    private int current = 0;

    public HoneyPot(int max) {
        this.max = max;
    }
    public void print()
    {
        System.out.println("The pot is " + (current*100)/max + "% full");
    }
    public synchronized boolean addHoney(int beeId)
    {
        System.out.println("Bee-" + beeId + " puts honey to pot!");
        current++;
        print();
        return current >= max;
    }
    public synchronized void eatHoney() throws InterruptedException {
        System.out.println("Winnie eats all honey!");
        current = 0;
        print();
    }
}
