package com.company.part1.lab2.b;

import java.util.ArrayList;

public class Yard {
    private final ArrayList<Box> boxes;
    private boolean finished = false;
    public Yard() {
        this.boxes = new ArrayList<>();
    }
    public Box removeBox()
    {
        if(boxes.size()>0) return boxes.remove(0);
        return null;
    }
    public void addBox(Box box)
    {
        boxes.add(box);
    }
    public synchronized void finish()
    {
        finished = true;
    }
    public synchronized boolean isFinished()
    {
        return finished;
    }
    public synchronized boolean empty()
    {
        return boxes.isEmpty();
    }
}
