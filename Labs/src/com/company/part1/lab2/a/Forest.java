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
    private final ArrayList<ForestLine> container;
    private final int rows;
    private final int columns;
    private static final String winnie = "☺";
    private static final String empty = "○";
    private static final String bee = "♦";
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
    private final SharedValue nextIndex = new SharedValue(0);

    public Forest(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        container = new ArrayList<>();
        for (int i = 0; i < rows; i++)
        {
            container.add(new ForestLine(columns));
        }
        putWinnie();
    }

    private void putWinnie()
    {
        int i = (int) (Math.random()*(rows)), j = (int) (Math.random()*(columns));
        container.get(i).putWinnie(j);
    }
    public ForestLine getNextLine()
    {
        synchronized(nextIndex)
        {
            int index = nextIndex.getValue();
            if(index < rows)
            {
                nextIndex.setValue(index + 1);
                return container.get(index);
            }
            return new ForestLine(0);
        }
    }
    public ArrayList<ForestLine> getContainer() {
        return container;
    }
}
