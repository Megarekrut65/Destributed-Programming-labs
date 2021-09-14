package com.company.part1.lab2.a;

import javax.swing.*;
import java.util.ArrayList;

public class Bee implements Runnable{
    private Forest forest;

    public Bee(Forest forest) {
        this.forest = forest;
    }

    @Override
    public void run() {
        ForestLine line = forest.getNextLine();
        for(int i = 0; i < line.getSize(); i++)
        {
            line.check(i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
