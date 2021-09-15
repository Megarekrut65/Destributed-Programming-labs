package com.company.part1.lab2.b;

import java.util.ArrayList;

public class Truck {
    private final ArrayList<Box> boxes;

    public Truck() {
        this.boxes = new ArrayList<>();
    }
    public synchronized boolean isNotCount(int index)
    {
        return boxes.size() > index;
    }
    public Box getBox(int index)
    {
        if(boxes.size()>index) return boxes.get(index);
        return null;
    }
    public void addBox(Box box)
    {
        boxes.add(box);
    }
}
