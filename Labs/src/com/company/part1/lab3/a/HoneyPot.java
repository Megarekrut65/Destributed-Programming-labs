package com.company.part1.lab3.a;

public class HoneyPot {
    private final int max;
    private int current = 0;

    public HoneyPot(int max) {
        this.max = max;
    }
    public boolean addHoney()
    {
        current++;
        return current >= max;
    }
    public void eatHoney()
    {
        current = 0;
    }
}
