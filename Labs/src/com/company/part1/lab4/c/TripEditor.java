package com.company.part1.lab4.c;

import com.company.part1.lab4.c.graph.Graph;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

public class TripEditor extends BaseClass{
    public TripEditor(Graph graph, ReadWriteLock locker, ArrayList<String> cities) {
        super(graph, locker, cities);
    }
    private void removeTrip(String cityA, String cityB){
        graph.removeEdge(cityA,cityB);
        System.out.println("Trip editor removed trip between " + cityA + " and "+ cityB);
    }
    private void addTrip(String cityA, String cityB){
        graph.addEdge(cityA,cityB,(int)(Math.random() * 1500));
        System.out.println("Trip editor added trip between " + cityA + " and "+ cityB);
    }
    @Override
    public void run() {
        while (!Thread.interrupted()){
            Lock lock = locker.writeLock();
            String cityA = getRandomCity();
            String cityB = getRandomCity(cityA);
            if(toRemove()) removeTrip(cityA,cityB);
            else addTrip(cityA,cityB);
            lock.unlock();
        }
    }
}
