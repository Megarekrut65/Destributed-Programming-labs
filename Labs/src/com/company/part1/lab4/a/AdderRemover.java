package com.company.part1.lab4.a;

import java.util.ArrayList;

public class AdderRemover extends BaseClass{
    private final ArrayList<AuthorInfo> authorsToRemove;
    private final ArrayList<AuthorInfo> authorsToAdd;
    public AdderRemover(AuthorManager authorManager, WriteReadLocker locker, ArrayList<AuthorInfo> authorsToRemove, ArrayList<AuthorInfo> authorsToAdd) {
        super(authorManager, locker);
        this.authorsToRemove = authorsToRemove;
        this.authorsToAdd = authorsToAdd;
    }
    private boolean toRemove(){
        int remove = authorsToRemove.size();
        int add = authorsToAdd.size();
        int range = remove + add;
        int rand = (int) (Math.random() * range);
        return remove > rand;
    }
    private int randIndex(ArrayList<AuthorInfo> arrayList){
        int size = arrayList.size();
        return (int)(Math.random()*size);
    }
    private void removeAuthor(){
        int index = randIndex(authorsToRemove);
        AuthorInfo authorInfo = authorsToRemove.remove(index);
        authorsToAdd.add(authorInfo);
        try {
            locker.writeLock();
            System.out.println("Adder Remover("+Thread.currentThread().getName()+") is finding");
            authorManager.deleteAuthor(authorInfo);
            locker.writeFree();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Adder Remover("+Thread.currentThread().getName()+") has removed: "+
                authorInfo);
    }
    private void addAuthor(){
        int index = randIndex(authorsToAdd);
        AuthorInfo authorInfo = authorsToAdd.remove(index);
        authorsToRemove.add(authorInfo);
        try {
            locker.writeLock();
            System.out.println("Adder Remover("+Thread.currentThread().getName()+") is finding");
            authorManager.append(authorInfo);
            locker.writeFree();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Adder Remover("+Thread.currentThread().getName()+") has added: "+
                authorInfo);
    }
    @Override
    public void run() {
        while (!Thread.interrupted()){
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(toRemove()) removeAuthor();
            else addAuthor();
        }
    }
}
