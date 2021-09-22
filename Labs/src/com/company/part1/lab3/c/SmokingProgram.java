package com.company.part1.lab3.c;

import java.util.concurrent.Semaphore;

public class SmokingProgram {
    private static Agent agent;
    private static Smoker tobaccoSmoker;
    private static Smoker paperSmoker;
    private static Smoker matchesSmoker;
    private static Table table;
    private static Semaphore semaphore;

    public static void main(String[] args) {
        semaphore = new Semaphore(1,false);
        table = new Table();
        agent = new Agent(table,semaphore);
        tobaccoSmoker = new Smoker(Component.TOBACCO, table,semaphore);
        paperSmoker = new Smoker(Component.PAPER, table, semaphore);
        matchesSmoker = new Smoker(Component.MATCHES, table, semaphore);
    }
}
