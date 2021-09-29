package com.company.part1.lab4.a;


public abstract class BaseClass implements Runnable{
    protected final AuthorManager authorManager;
    protected final WriteReadLocker locker;

    public BaseClass(AuthorManager authorManager, WriteReadLocker locker) {
        this.authorManager = authorManager;
        this.locker = locker;
        new Thread(this).start();
    }

    @Override
    public abstract void run();
}
