package com.company.part1.lab2.b;

import java.util.concurrent.Semaphore;

public class Ivanov implements Runnable{
    private final Yard yard;
    private final Semaphore semaphore;
    private final long duration;
    public Ivanov(Yard yard, Semaphore semaphore,long duration) {
        this.yard = yard;
        this.semaphore = semaphore;
        this.duration = duration;
    }

    @Override
    public void run() {
        while (!Thread.interrupted())
        {
            try {
                semaphore.acquire();
                yard.addBox(Storage.getBox());//Ivanov gets box from storage and puts it to yard
                semaphore.release();
                System.out.println("Ivanov moved the box to yard from storage");
                Thread.sleep(duration);
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
