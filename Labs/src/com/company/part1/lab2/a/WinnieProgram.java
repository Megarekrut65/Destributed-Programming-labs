package com.company.part1.lab2.a;

import javax.swing.*;
import java.awt.*;

public class WinnieProgram {
    private static Forest forest;
    private static Hive hive;
    private static final int rows = 200;
    private static final int columns = 400;
    private static JLabel infoLabel;
    private static int beeNumber = 4;
    private static long duration = 10;
    private static JFrame win;
    public static void main(String[] args) {
        infoLabel = new JLabel("", JLabel.CENTER);
        forest = new Forest(rows,columns, infoLabel);
        win = getFrame();
        JPanel panel = getMainPanel();
        win.setContentPane(panel);
        hive = new Hive(beeNumber,forest, duration);
        win.setVisible(true);
        refreshSimulation();
    }
    private static void refreshSimulation()
    {
        infoLabel.setText("Bees are in hive.");
        infoLabel.setFont(infoLabel.getFont ().deriveFont (20.0f));
    }
    private static JPanel getMainPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JPanel forestPanel = getForestPanel();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setSize(win.getWidth(), 500);
        scrollPane.add(forestPanel);
        panel.add(scrollPane, BorderLayout.NORTH);
        panel.add(getInfoPanel(), BorderLayout.SOUTH);
        return panel;
    }
    private static JPanel getInfoPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3,1));
        panel.add(infoLabel);
        JButton startBtn = new JButton("Start catching!");
        var ref = new Object(){
            public boolean isPressed = false;};
        startBtn.addActionListener(e->{
            if(!ref.isPressed)
            {
                hive.start();
                ref.isPressed = true;
                infoLabel.setText("The " + beeNumber+" bees are trying to catch Winnie!");
            }

        });
        panel.add(startBtn);
        JButton refresh = new JButton("Refresh");
        refresh.addActionListener(e->{
            if(ref.isPressed)
            {
                hive.stop();
                forest.refresh();
                refreshSimulation();
                ref.isPressed = false;
            }
                });
        panel.add(refresh);
        return panel;
    }
    private static JPanel getForestPanel()
    {
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
        return panel;
    }
    private static JFrame getFrame()
    {
        JFrame win = new JFrame("Winnie Program");
        win.setSize(columns*25,640);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setLocationRelativeTo(null);
        return win;
    }
}
