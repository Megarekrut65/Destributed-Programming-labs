package com.company.part1.lab2.b;


import java.util.concurrent.Semaphore;

public class Nechiporchuk implements Runnable{
    private final Yard yard;
    private final Storage storage;
    private final Truck truck;
    private final Semaphore semaphoreTruck;
    private final long duration;
    private int index = 0;
    private int price = 0;
    public Nechiporchuk(Storage storage, Yard yard, Truck truck, Semaphore semaphoreTruck, long duration) {
        this.yard = yard;
        this.storage = storage;
        this.truck = truck;
        this.semaphoreTruck = semaphoreTruck;
        this.duration = duration;
    }

    @Override
    public void run() {
        while (!Thread.interrupted())
        {
            try {
                while (!truck.isNotCount(index)&&!yard.isFinished()&&!storage.isFinished()){}
                semaphoreTruck.acquire();
                Box box = truck.getBox(index);
                semaphoreTruck.release();
                if(box != null)
                {
                    index++;
                    System.out.println("Nechiporchuk counted next box price. Sum is "+
                            price+"+"+box.getPrice()+"="+(price+box.getPrice()));
                    price+= box.getPrice();
                }else if(storage.isFinished()&& yard.isFinished()) break;
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("#Nechiporchuk has done his job. Sum price is "+price+".");
    }
}
