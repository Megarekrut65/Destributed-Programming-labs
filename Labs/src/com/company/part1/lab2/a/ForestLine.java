package com.company.part1.lab2.a;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class ForestLine {
    private final ArrayList<JLabel> line;

    public ForestLine(int size) {
        line = new ArrayList<>();
        for(int j = 0; j < size; j++)
        {
            line.add(getLabel());
        }
    }
    boolean check(int index) throws InterruptedException {
        if(index >= line.size())
            throw new ArrayIndexOutOfBoundsException("The index "+index + " is biggest than size " + line.size());
        var label = line.get(index);
        boolean isFound = Objects.equals(label.getText(), Forest.getWinnieSign());
        label.setText(Forest.getBeeSign());
        label.setForeground(new Color(255, 152, 0));
        Thread.sleep(150);
        label.setText(Forest.getBeeTrackSign());
        label.setForeground(new Color(218, 186, 11));
        return isFound;
    }
    private JLabel getLabel()
    {
        JLabel label = new JLabel(Forest.getEmptySign());
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont (label.getFont ().deriveFont (16.0f));
        return label;
    }
    public void putWinnie(int index)
    {
        if(index >= line.size())
            throw new ArrayIndexOutOfBoundsException("The index "+index + " is biggest than size " + line.size());
        JLabel label = line.get(index);
        label.setText(Forest.getWinnieSign());
        label.setForeground(new Color(107, 42, 0));
    }
    public int getSize()
    {
        return line.size();
    }
    public ArrayList<JLabel> getLine() {
        return line;
    }
}
