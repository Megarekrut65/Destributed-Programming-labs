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
        while (!Thread.interrupted()&&line.getSize() > 0)
        {
            for(int i = 0; i < line.getSize(); i++)
            {
                if(line.check(i)) {
                    try {
                        forest.punishWinnie();
                        return;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            line = forest.getNextLine();
        }
    }
}
