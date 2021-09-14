package com.company.part1.lab2.a;

import javax.swing.*;
import java.awt.*;

public class WinnieProgram {
    public static void main(String[] args) {
        int rows = 50, columns = 50;
        Forest forest = new Forest(rows,columns);
        JFrame win = new JFrame("Winnie Program");
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(rows+1,columns));
        var container = forest.getContainer();
        for(var row:container)
        {
            for(var column:row.getLine())
            {
                panel.add(column);
            }
        }
        JButton start = new JButton("Start");
        panel.add(start);
        win.setSize(rows*35,columns*35 + 50);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setLocationRelativeTo(null);
        win.setContentPane(panel);
        Hive hive = new Hive(10,forest);
        win.setVisible(true);
        hive.start();
    }
}
