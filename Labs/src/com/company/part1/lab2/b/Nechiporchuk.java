package com.company.part1.lab2.b;


public class Nechiporchuk implements Runnable{
    private final Truck truck;
    private final long duration;
    private int index = 0;
    private int price = 0;
    public Nechiporchuk(Truck truck, long duration) {
        this.truck = truck;
        this.duration = duration;
    }

    @Override
    public void run() {
        while (!Thread.interrupted())
        {
            Box box = truck.getBox(index);
            if(box != null)
            {
                index++;
                System.out.println("Nechiporchuk counted next box price. It is "+
                        price+"+"+box.getPrice()+"="+price+box.getPrice());
                price+= box.getPrice();
            }
            try {
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
