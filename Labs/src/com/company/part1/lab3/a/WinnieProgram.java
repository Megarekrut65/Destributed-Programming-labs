package com.company.part1.lab3.a;

import java.util.concurrent.Semaphore;

public class WinnieProgram {
    private static int beesNumber = 3;
    private static int honeySize = 50;
    private static Semaphore semaphore;
    private static HoneyPot honeyPot;
    private static Winnie winnie;
    public static void main(String[] args) {
        semaphore = new Semaphore(1, true);
        honeyPot = new HoneyPot(honeySize);
        winnie = new Winnie(honeyPot,semaphore);
        for(int i = 0; i < beesNumber; i++)
        {
            new Bee(winnie,honeyPot,semaphore);
        }
    }
}
