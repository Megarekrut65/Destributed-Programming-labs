package com.company.part1.lab2.b;

import java.util.concurrent.Semaphore;

public class Petrov implements Runnable{
    private final Yard yard;
    private final Semaphore semaphore;
    private final Truck truck;
    private final long duration;
    public Petrov(Yard yard, Truck truck, Semaphore semaphore, long duration) {
        this.yard = yard;
        this.semaphore = semaphore;
        this.truck = truck;
        this.duration = duration;
    }

    @Override
    public void run() {
        while (!Thread.interrupted())
        {
            try {
                while (yard.empty()){}
                semaphore.acquire();
                Box box = yard.removeBox();
                semaphore.release();
                truck.addBox(box);//Petrov gets box from yard and puts it to truck
                System.out.println("Petrov moved the box to truck from yard");
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
