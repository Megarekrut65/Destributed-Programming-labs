package com.company.part1.lab4.c;

import com.company.part1.lab4.c.graph.Graph;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

public class CityEditor extends BaseClass{
    private final ArrayList<String> newCities;
    public CityEditor(Graph graph, ReadWriteLock locker, ArrayList<String> cities, ArrayList<String> newCities) {
        super(graph, locker, cities);
        this.newCities = newCities;
    }
    private void removeCity(){
        String city = getRandomCity();
        graph.removeVertex(city);
        newCities.add(city);
        cities.remove(city);
        System.out.println("City editor removed city " + city);
    }
    private void addCity(){
        if(newCities.size() == 0) return;
        String city = newCities.remove(0);
        graph.addVertex(city);
        cities.add(city);
        System.out.println("City editor added city " + city);
    }
    @Override
    public void run() {
        while (!Thread.interrupted()){
            Lock lock = locker.writeLock();
            if(toRemove()) removeCity();
            else addCity();
            lock.unlock();
        }
    }
}
