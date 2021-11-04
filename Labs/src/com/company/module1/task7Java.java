package com.company.module1;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

class Tunnel{
    private final Semaphore semaphore;
    private final long runningTime;
    private final String name;
    private int trainCount = 0;
    public Tunnel(long runningTime, String name) {
        this.semaphore = new Semaphore(1,true);
        this.runningTime = runningTime;
        this.name = name;
    }

    public int getTrainCount() {
        return trainCount;
    }

    boolean tryToRun(long waitTime, String name){
        try {
            if(semaphore.tryAcquire(waitTime, TimeUnit.MILLISECONDS)){
                System.out.println(name + " is running through " + this.name);
                trainCount++;
                Thread.sleep(runningTime);
                semaphore.release();
                return true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
}
class Train implements Runnable{
    private final Tunnel tunnel1;
    private final Tunnel tunnel2;
    private final String name;
    private final long waitTime;

    public Train(Tunnel tunnel1, Tunnel tunnel2, String name, long waitTime) {
        this.tunnel1 = tunnel1;
        this.tunnel2 = tunnel2;
        this.name = name;
        this.waitTime = waitTime;
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (!Thread.interrupted()){
            System.out.println(name + " try to run through the first tunnel");
            if(tunnel1.tryToRun(waitTime, name)){
                System.out.println(name+" has already passed through the first tunnel!");
                break;
            }
            System.out.println(name + " try to run through the second tunnel");
            if(tunnel2.tryToRun(waitTime, name)){
                System.out.println(name+" has already passed through the second tunnel!");
                break;
            }
        }
        System.out.println("First tunnel: " + tunnel1.getTrainCount() + " trains\n"+
                "Second tunnel: " + tunnel2.getTrainCount() + " trains\n"+name+" go away!");
    }
}
public class task7Java {
    public static void main(String[] args) {
        int trainsNumber = 100;
        Tunnel tunnel1 = new Tunnel(2000,"Tunnel1"),
                tunnel2 = new Tunnel( 3500, "Tunnel2");
        for(int i = 0; i < trainsNumber; i++){
            new Train(tunnel1, tunnel2, "Train"+i, 1000*i);
        }
    }
}
