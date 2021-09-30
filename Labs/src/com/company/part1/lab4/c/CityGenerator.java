package com.company.part1.lab4.c;

import com.company.part1.lab4.a.AuthorInfo;
import com.company.part1.lab4.c.graph.Graph;

import java.util.ArrayList;

public class CityGenerator {
    public static String randName(){
        return AuthorInfo.randString();
    }
    public static Integer randPrice(){
        return (int)(Math.random() * 1000);
    }
    public static ArrayList<String> randCities(int size){
        ArrayList<String> cities = new ArrayList<>();
        for (int i = 0; i < size; i++){
            cities.add(randName());
        }
        return cities;
    }
    public static Graph randGraph(ArrayList<String> cities)
    {
        Graph graph = new Graph();
        for (var city: cities){
            graph.addVertex(city);
        }
        for (String city : cities) {
            for (String s : cities) {
                if (randPrice() > 600) {
                    graph.addEdge(city, s, randPrice());
                }
            }
        }
        return graph;
    }
    public static Graph randGraph(int citiesNumber){
        return randGraph(randCities(citiesNumber));
    }
}
