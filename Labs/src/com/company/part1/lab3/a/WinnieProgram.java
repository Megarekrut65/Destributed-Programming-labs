package com.company.part1.lab3.a;


public class WinnieProgram {
    private static int beesNumber = 3;
    private static int honeySize = 20;
    private static HoneyPot honeyPot;
    private static Winnie winnie;
    public static void main(String[] args) {
        honeyPot = new HoneyPot(honeySize);
        winnie = new Winnie(honeyPot);
        for(int i = 0; i < beesNumber; i++)
        {
            new Bee(winnie,honeyPot);
        }
    }
}
