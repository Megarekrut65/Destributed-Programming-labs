package com.company.part1.lab2.b;

import java.util.ArrayList;

public class Yard {
    private final ArrayList<Box> boxes;

    public Yard() {
        this.boxes = new ArrayList<>();
    }
    public synchronized Box removeBox()
    {
        System.out.println("Box "+boxes.get(0)+" take from yard");
        if(boxes.size()>0) return boxes.remove(0);
        return null;
    }
    public synchronized void addBox(Box box)
    {
        boxes.add(box);
        System.out.println("Box "+box+" put to yard");
    }
}
