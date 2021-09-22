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
    public boolean addHoney()
    {
        current++;
        print();
        return current >= max;
    }
    public void eatHoney()
    {
        current = 0;
        print();
    }
}
