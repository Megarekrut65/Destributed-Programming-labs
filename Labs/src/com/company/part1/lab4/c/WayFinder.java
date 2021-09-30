package com.company.part1.lab4.c;

import com.company.part1.lab4.c.graph.Graph;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

public class WayFinder extends BaseClass{
    public WayFinder(Graph graph, ReadWriteLock locker, ArrayList<String> cities) {
        super(graph, locker, cities);
    }

    @Override
    public void run() {
        while (!Thread.interrupted()){
            Lock lock = locker.readLock();
            String cityA = getRandomCity();
            String cityB = getRandomCity(cityA);
            Integer dist = graph.dijkstra(cityA,cityB);
            System.out.println("Way Finder found way between "+cityA + " and " + cityB + " = " + dist);
            lock.unlock();
        }
    }
}
