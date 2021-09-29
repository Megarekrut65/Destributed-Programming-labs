package com.company.part1.lab4.a;

import java.util.ArrayList;

public class MobileFinder extends BaseClass{
    private final ArrayList<String> surnames;
    public MobileFinder(AuthorManager authorManager, WriteReadLocker locker, ArrayList<String> surnames) {
        super(authorManager, locker);
        this.surnames = surnames;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()){
            for (var surname: surnames){
                try {
                    Thread.sleep(1300);
                    locker.readLock();
                    System.out.println("Mobile Finder("+Thread.currentThread().getName()+") is finding");
                    AuthorInfo info = authorManager.findBySurname(surname);
                    locker.readFree();
                    if(info != null)
                        System.out.println("Mobile Finder("+Thread.currentThread().getName()+") has found mobile: " + info.getMobile() + " of " + surname);
                    else System.out.println("Mobile Finder("+Thread.currentThread().getName()+") can't find mobile of "+ surname);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }
}
