package com.company.part1.lab5.c;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SumProgram {
    public static void main(String[] args) {
        int size = 1000;
        int threadNumber = 3;
        List<List<Integer>> lists = new ArrayList<>();
        for(int i = 0; i < threadNumber; i++){
            lists.add(getList(size));
        }
        ReadWriteLock lock = new ReentrantReadWriteLock();
        for (int i = 0; i < threadNumber; i++){
            int index = i;
            new Thread(()->{
                while (!Thread.interrupted()){
                    lock.readLock().lock();
                    int ownSum = getSum(lists.get(index));
                    lock.readLock().unlock();
                    int equalsSum = 1;
                    for (int j = 0; j < threadNumber; j++){
                        if(j != index){
                            lock.readLock().lock();
                            int sum = getSum(lists.get(j));
                            if(ownSum == sum) equalsSum++;
                            lock.readLock().unlock();
                            if(ownSum > sum){
                                changeList(lists.get(index), -1, lock);
                            }else if (ownSum < sum){
                                changeList(lists.get(index), 1, lock);
                            }
                        }
                    }
                    if(equalsSum == threadNumber) break;
                }
                System.out.println("("+index+")Sum are equals!\n"+lists.get(index)+"\n"+getSum(lists.get(index)));
            }).start();
        }
    }
    private static void changeList(List<Integer> list, Integer value, ReadWriteLock lock){
        int index = (int)(Math.random()*list.size());
        lock.writeLock().lock();
        list.set(index, list.get(index) + value);
        lock.writeLock().unlock();
    }
    private static int getSum(List<Integer> list){
        int sum = 0;
        for (Integer integer : list) {
            sum += integer;
        }
        return sum;
    }
    private static List<Integer> getList(int size){
        List<Integer> list = new ArrayList<>();
        Random random = new Random();
        for(int i = 0; i < size; i++){
            list.add(random.nextInt(size));
        }
        return list;
    }
}
