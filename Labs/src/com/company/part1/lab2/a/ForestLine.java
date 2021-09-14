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
    boolean check(int index)
    {
        if(index >= line.size())
            throw new ArrayIndexOutOfBoundsException("The index "+index + " is biggest than size " + line.size());
        var label = line.get(index);
        label.setText(Forest.getBeeSign());
        label.setForeground(new Color(26, 148, 0));
        return Objects.equals(line.get(index).getText(), Forest.getWinnieSign());
    }
    private JLabel getLabel()
    {
        JLabel label = new JLabel(Forest.getEmptySign());
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont (label.getFont ().deriveFont (24.0f));
        return label;
    }
    public void putWinnie(int index)
    {
        if(index >= line.size())
            throw new ArrayIndexOutOfBoundsException("The index "+index + " is biggest than size " + line.size());
        JLabel label = line.get(index);
        label.setText(Forest.getWinnieSign());
        label.setForeground(new Color(255, 0, 0));
    }

    public ArrayList<JLabel> getLine() {
        return line;
    }
}
