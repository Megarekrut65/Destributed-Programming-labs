package com.company.part1.lab2.b;

import java.util.concurrent.Semaphore;

public class EnsignProgram {
    public static void main(String[] args) {
        Semaphore semaphoreYard = new Semaphore(1), semaphoreTruck = new Semaphore(1);
        Yard yard = new Yard();
        Truck truck = new Truck();
        Storage storage = new Storage(20);
        Thread thIvanov = new Thread(new Ivanov(storage, yard, semaphoreYard,500));
        Thread thPetrov = new Thread(new Petrov(storage,yard, truck,semaphoreYard, semaphoreTruck, 1000));
        Thread thNechiporchuk = new Thread(new Nechiporchuk(storage, yard, truck, semaphoreTruck, 1200));
        thIvanov.start();
        thPetrov.start();
        thNechiporchuk.start();
    }
}
