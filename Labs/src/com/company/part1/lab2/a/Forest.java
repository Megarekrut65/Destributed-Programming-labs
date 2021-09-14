package com.company.part1.lab2.a;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

class SharedValue
{
    private int value;

    public SharedValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
public class Forest {
    private final JLabel infoLabel;
    private final ArrayList<ForestLine> container;
    private final int rows;
    private final int columns;
    private static final String winnie = "☺";
    private static final String empty = "○";
    private static final String bee = "♥";
    private static final String beeTrack = "♦";
    private int winnieI;
    private int winnieJ;
    public static String getWinnieSign()
    {
        return winnie;
    }
    public static String getEmptySign()
    {
        return empty;
    }
    public static String getBeeSign()
    {
        return bee;
    }
    public static String getBeeTrackSign()
    {
        return beeTrack;
    }
    private final SharedValue nextIndex = new SharedValue(0);
    private final ArrayList<Integer> indexes;
    public Forest(int rows, int columns,JLabel infoLabel) {
        this.infoLabel = infoLabel;
        this.rows = rows;
        this.columns = columns;
        container = new ArrayList<>();
        for (int i = 0; i < rows; i++)
        {
            container.add(new ForestLine(columns));
        }
        indexes = new ArrayList<>();
        refresh();
    }
    public void refresh()
    {
        for(var row:container)
        {
            var line = row.getLine();
            for(var label:line)
            {
                label.setText(empty);
                label.setForeground(Color.BLACK);
            }
        }
        putWinnie();
        createIndexes();
        nextIndex.setValue(getRandomIndex());
    }
    private void createIndexes()
    {
        indexes.clear();
        for(int i = 0; i < rows; i++)
        {
            indexes.add(i);
        }
    }
    private void putWinnie()
    {
        winnieI = (int) (Math.random()*(rows));
        winnieJ = (int) (Math.random()*(columns));
        container.get(winnieI).putWinnie(winnieJ);
    }
    private int getRandomIndex()
    {
        int newIndex = rows;
        if(indexes.size() > 0)
        {
            int rand = (int) (Math.random()*indexes.size());
            newIndex = indexes.get(rand);
            indexes.remove(rand);
        }
        return newIndex;
    }
    public ForestLine getNextLine()
    {
        synchronized(nextIndex)
        {
            int index = nextIndex.getValue();
            if(index < rows)
            {
                nextIndex.setValue(getRandomIndex());
                return container.get(index);
            }
            return null;
        }
    }
    public ArrayList<ForestLine> getContainer() {
        return container;
    }
    public void punishWinnie() throws InterruptedException {
        synchronized(nextIndex)
        {
            nextIndex.setValue(rows);
        }
        var label = container.get(winnieI).getLine().get(winnieJ);
        label.setText(winnie);
        label.setForeground(new Color(255, 0, 0));
        infoLabel.setText("Winnie is found!");
        Thread.sleep(50);
        label.setText("x");
        Thread.sleep(100);
        label.setText("X");
        Thread.sleep(100);
        label.setText("x");
        Thread.sleep(50);
        label.setText(empty);
    }
}
