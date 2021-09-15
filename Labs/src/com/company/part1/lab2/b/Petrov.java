package com.company.part1.lab2.b;

import java.util.concurrent.Semaphore;

public class Petrov implements Runnable{
    private final Storage storage;
    private final Yard yard;
    private final Truck truck;
    private final Semaphore semaphoreYard;
    private final Semaphore semaphoreTruck;

    private final long duration;
    public Petrov(Storage storage, Yard yard, Truck truck, Semaphore semaphoreYard, Semaphore semaphoreTruck, long duration) {
        this.storage = storage;
        this.yard = yard;
        this.truck = truck;
        this.semaphoreYard = semaphoreYard;
        this.semaphoreTruck = semaphoreTruck;
        this.duration = duration;
    }

    @Override
    public void run() {
        while (!Thread.interrupted())
        {
            try {
                while (yard.empty()&&!storage.isFinished()){}
                semaphoreYard.acquire();
                Box box = yard.removeBox();
                semaphoreYard.release();
                if(box != null)
                {
                    semaphoreTruck.acquire();
                    truck.addBox(box);//Petrov gets box from yard and puts it to truck
                    semaphoreTruck.release();
                    System.out.println("Petrov moved the box from yard to truck.");
                }else if(storage.isFinished()) break;
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        yard.finish();
        System.out.println("#Petrov has done his job. All boxes are in truck.");
    }
}
