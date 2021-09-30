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
            try {
                Thread.sleep(1000);
                locker.writeLock().lock();
                String cityA = getRandomCity();
                String cityB = getRandomCity(cityA);
                graph.editEdge(cityA,cityB, CityGenerator.randPrice());
                System.out.println("Price editor changed ticket price between " + cityA + " and "+ cityB);
                locker.writeLock().unlock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
