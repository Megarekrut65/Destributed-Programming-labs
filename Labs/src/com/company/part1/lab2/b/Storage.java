package com.company.part1.lab2.b;

import java.util.ArrayList;

public class Storage {
    private final ArrayList<Box> boxes;
    private boolean finished = false;
    public Storage(int size) {
        boxes = new ArrayList<>();
        int price = 0;
        for(int i = 0; i < size; i++)
        {
            var box = createNewBox();
            price += box.getPrice();
            boxes.add(box);
        }
        System.out.println("Price: " +price);
    }
    public Box getNextBox()
    {
        if(boxes.isEmpty()) return null;
        return boxes.remove(0);
    }
    public synchronized void finish()
    {
        finished = true;
    }
    public synchronized boolean isFinished()
    {
        return finished;
    }
    private Box createNewBox()
    {
        return new Box((int)(Math.random()*10 + 1));
    }
}
