package com.company.part1.lab4.c;

import com.company.part1.lab4.c.graph.Graph;

import java.util.ArrayList;
import java.util.concurrent.locks.ReadWriteLock;

public abstract class BaseClass implements Runnable{
    protected final Graph graph;
    protected final ReadWriteLock locker;
    protected final ArrayList<String> cities;

    public BaseClass(Graph graph, ReadWriteLock locker, ArrayList<String> cities) {
        this.graph = graph;
        this.locker = locker;
        this.cities = cities;
        new Thread(this).start();
    }
    protected String getRandomCity(){
        int index = (int)(Math.random() * cities.size());
        return cities.get(index);
    }
    protected String getRandomCity(String cityA){
        String cityB = cityA;
        while (cityB == cityA){
            cityB = getRandomCity();
        }
        return cityB;
    }
    protected boolean toRemove(){
        double remove = 0.5;
        double rand = Math.random();
        return remove < rand;
    }
}
