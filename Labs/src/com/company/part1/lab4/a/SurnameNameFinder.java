package com.company.part1.lab4.a;

import java.util.ArrayList;

public class SurnameNameFinder extends BaseClass{
    private final ArrayList<String> mobiles;
    public SurnameNameFinder(AuthorManager authorManager, WriteReadLocker locker, ArrayList<String> mobiles) {
        super(authorManager, locker);
        this.mobiles = mobiles;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()){
            for (var mobile: mobiles){
                try {
                    Thread.sleep(1300);
                    locker.readLock();
                    System.out.println("Surname Name Finder("+Thread.currentThread().getName()+") is finding");
                    AuthorInfo info = authorManager.findByMobile(mobile);
                    locker.readFree();
                    if(info != null)
                        System.out.println("Surname Name Finder("+
                                Thread.currentThread().getName()+") has found person: " +
                                info.getSurname() + " " + info.getName() + ", mobile: " + mobile);
                    else System.out.println("Surname Name Finder("+Thread.currentThread().getName()+
                            ") can't find person with mobile: "+ mobile);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }
}
