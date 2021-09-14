package com.company.part1.lab2.a;

import javax.swing.*;
import java.util.ArrayList;

public class Bee implements Runnable{
    private ArrayList<JLabel> currentLine;

    public void setCurrentLine(ArrayList<JLabel> currentLine) {
        this.currentLine = currentLine;
    }

    @Override
    public void run() {
        while (!Thread.interrupted())
        {
            for(var hole:currentLine)
            {

            }
        }
    }
}
