package com.company.part1.lab2.a;

import javax.swing.*;
import java.awt.*;

public class WinnieProgram {
    public static void main(String[] args) {
        int rows = 10, columns = 10;
        Forest forest = new Forest(rows,columns);
        JFrame win = new JFrame("Winnie Program");
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(rows,columns));
        var container = forest.getContainer();
        for(var row:container)
        {
            for(var column:row.getLine())
            {
                panel.add(column);
            }
        }
        win.setSize(rows*35,columns*35);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setLocationRelativeTo(null);
        win.setContentPane(panel);
        win.setVisible(true);
    }
}
