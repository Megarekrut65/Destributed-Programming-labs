package com.company.part1.lab4.c;

import com.company.part1.lab4.c.graph.Graph;

public class BusProgram {

    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.addVertex("Kiev");
        graph.addVertex("Odesa");
        graph.addVertex("Lviv");
        graph.addVertex("Dnipro");
        graph.addVertex("Kiro");
        graph.addEdge("Kiev","Odesa", 500);
        graph.addEdge("Kiev","Lviv", 800);
        graph.addEdge("Dnipro","Odesa", 5500);
        System.out.println(graph);
        graph.editEdge("Kiev","Odesa",100);
        System.out.println(graph);
        System.out.println(graph.dijkstra("Kiev","Kiro"));
    }
}
