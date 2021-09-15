package com.company.part1.lab2.b;

import java.util.ArrayList;

public class Truck {
    private final ArrayList<Box> boxes;

    public Truck() {
        this.boxes = new ArrayList<>();
    }
    public Box getBox(int index)
    {
        if(boxes.size()>index) return boxes.get(index);
        return null;
    }
    public synchronized void addBox(Box box)
    {
        boxes.add(box);
    }
}
