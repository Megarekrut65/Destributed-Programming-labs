package com.company.part1.lab3.c;

import java.util.concurrent.Semaphore;

public class Agent implements Runnable{
    private final Table table;
    private final Semaphore semaphore;

    public Agent(Table table, Semaphore semaphore) {
        this.table = table;
        this.semaphore = semaphore;
        new Thread(this).start();
    }
    @Override
    public void run() {
        while (!Thread.interrupted())
        {
            try {
                semaphore.acquire();
                    if(!table.checkComponents())
                    {
                        Components components = Components.createComponents();
                        System.out.println("Agent puts " +
                                Components.componentToString(components.getComponent1())+
                                " and " +
                                Components.componentToString(components.getComponent2())+
                                " components on table!");
                        table.putComponents(components);
                    }
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
