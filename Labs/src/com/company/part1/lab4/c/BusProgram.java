package com.company.part1.lab4.c;

import com.company.part1.lab4.c.graph.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class BusProgram {

    public static void main(String[] args) {
        ArrayList<String> cities = CityGenerator.randCities(20);
        Graph graph = CityGenerator.randGraph(cities);
        ArrayList<String> newCities = CityGenerator.randCities(10);
        ReadWriteLock locker = new ReentrantReadWriteLock();
        PriceEditor priceEditor = new PriceEditor(graph,locker,cities);
        TripEditor tripEditor = new TripEditor(graph,locker,cities);
        CityEditor cityEditor = new CityEditor(graph,locker,cities,newCities);
        WayFinder wayFinder = new WayFinder(graph,locker,cities);
    }
}
