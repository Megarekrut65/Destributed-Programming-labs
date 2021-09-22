package com.company.part1.lab3.c;

import java.util.concurrent.Semaphore;

public class Smoker implements Runnable{
    private final Component component;
    private final Table table;
    private final Semaphore semaphore;
    private static int currentId = 0;
    private final int id;

    public Smoker(Component component, Table table, Semaphore semaphore) {
        this.component = component;
        this.semaphore = semaphore;
        this.table = table;
        currentId++;
        id = currentId;
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (!Thread.interrupted())
        {
            try {
                semaphore.acquire();
                Components components = table.getComponents(component);
                if(components != null)
                {
                    System.out.println("Smoker-"+id+"("+Components.componentToString(component)+") makes cigarette ans smocks...");
                    Thread.sleep(2000);
                }
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
