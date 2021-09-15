package com.company.part1.lab2.b;

import java.util.concurrent.Semaphore;

public class Ivanov implements Runnable{
    private final Yard yard;
    private final Semaphore semaphoreYard;
    private final Storage storage;
    private final long duration;
    public Ivanov(Storage storage, Yard yard, Semaphore semaphoreYard,long duration) {
        this.yard = yard;
        this.storage = storage;
        this.semaphoreYard = semaphoreYard;
        this.duration = duration;
    }

    @Override
    public void run() {
        while (!Thread.interrupted())
        {
            try {
                Box box = storage.getNextBox();
                if(box == null) break;
                semaphoreYard.acquire();
                yard.addBox(box);//Ivanov gets box from storage and puts it to yard
                semaphoreYard.release();
                System.out.println("Ivanov moved the box from storage to yard.");
                Thread.sleep(duration);
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
        storage.finish();
        System.out.println("#Ivanov has done his job. Storage is empty.");
    }
}
