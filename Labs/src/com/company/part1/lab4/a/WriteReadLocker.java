package com.company.part1.lab4.a;

public class WriteReadLocker {
    private int writers = 0;
    private int readers = 0;
    private int waitWriters = 0;

    public synchronized void writeLock() throws InterruptedException {
        waitWriters++;
        while (writers > 0 || readers > 0){
            wait();
        }
        waitWriters--;
        writers++;
    }
    public synchronized void writeFree(){
        writers--;
        notifyAll();
    }
    public synchronized void readLock() throws InterruptedException {
        while (writers > 0 || waitWriters > 0){
            wait();
        }
        readers++;
    }
    public synchronized void readFree(){
        readers--;
        notifyAll();
    }
}
