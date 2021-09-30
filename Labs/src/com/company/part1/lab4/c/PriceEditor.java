package com.company.part1.lab4.c;

import com.company.part1.lab4.c.graph.Graph;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

public class PriceEditor extends BaseClass{
    public PriceEditor(Graph graph, ReadWriteLock locker, ArrayList<String> cities) {
        super(graph, locker, cities);
    }

    @Override
    public void run() {
        while (!Thread.interrupted()){
            Lock lock = locker.writeLock();
            String cityA = getRandomCity();
            String cityB = getRandomCity(cityA);
            graph.editEdge(cityA,cityB, (int)(Math.random() * 1000));
            System.out.println("Price editor changed ticket price between " + cityA + " and "+ cityB);
            lock.unlock();
        }
    }
}
