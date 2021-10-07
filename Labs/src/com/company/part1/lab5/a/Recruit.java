package com.company.part1.lab5.a;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Recruit {
    private final RecruitLine line;
    private final int size;
    public Recruit(int size) {
        line = new RecruitLine();
        this.size = size;
        line.generateList(size);
    }
    public void executeCommand(Command command, int minThreadNumber){
        System.out.println("Command " + command);
        Lock lock = new ReentrantLock();
        for(int i = 0; i < size; i+= minThreadNumber) {
            int start = i;
            int end = start + minThreadNumber;
            new Thread(() -> {
                System.out.println("Create thread " + Thread.currentThread().getName());
                System.out.println(Thread.currentThread().getName() + " start: "+ start + " end: " + end);
                while(!Thread.interrupted()){
                    int j = (int)(Math.random()*end);
                    boolean needLock = (j == start && j != 0 || j == end - 1 && j != size -1);
                    if(needLock) lock.lock();
                    if(line.checkNear(j) && line.check()) break;
                    if(needLock){
                        System.out.println(line);
                        lock.unlock();
                    }
                }
                System.out.println(line);
                System.out.println("Close thread " + Thread.currentThread().getName());
            }).start();
        }
    }
}
