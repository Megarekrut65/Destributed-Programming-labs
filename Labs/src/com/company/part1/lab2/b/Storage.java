package com.company.part1.lab2.b;

public class Storage {
    public static Box getBox()
    {
        return new Box((int)(Math.random()*1000));
    }
}
