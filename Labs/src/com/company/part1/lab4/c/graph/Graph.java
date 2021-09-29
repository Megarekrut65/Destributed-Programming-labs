package com.company.part1.lab4.c.graph;

import java.util.*;

class Vertex {
    String label;
    Integer price;
    Vertex(String label, Integer price) {
        this.label = label;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return label.equals(vertex.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }

    @Override
    public String toString() {
        if(price == null) return label;
        return "($"+price+")->"+label;
    }
}
public class Graph {
    private final Map<Vertex, ArrayList<Vertex>> adjVertices;

    public Graph() {
        adjVertices = new HashMap<>();
    }

    public void addVertex(String label) {
        adjVertices.putIfAbsent(new Vertex(label, null), new ArrayList<>());
    }

    public void removeVertex(String label) {
        Vertex v = new Vertex(label, null);
        adjVertices.values().forEach(e -> e.remove(v));
        adjVertices.remove(new Vertex(label, null));
    }
    public void addEdge(String label1, String label2, Integer price) {
        Vertex v1 = new Vertex(label1, price);
        Vertex v2 = new Vertex(label2, price);
        adjVertices.get(v1).add(v2);
        adjVertices.get(v2).add(v1);
    }
    public void removeEdge(String label1, String label2) {
        Vertex v1 = new Vertex(label1, null);
        Vertex v2 = new Vertex(label2, null);
        List<Vertex> eV1 = adjVertices.get(v1);
        List<Vertex> eV2 = adjVertices.get(v2);
        if (eV1 != null)
            eV1.remove(v2);
        if (eV2 != null)
            eV2.remove(v1);
    }
    public void editEdge(String label1, String label2, Integer newPrice) {
        Vertex v1 = new Vertex(label1, null);
        Vertex v2 = new Vertex(label2, null);
        int index1 = adjVertices.get(v1).indexOf(v2);
        adjVertices.get(v1).get(index1).price = newPrice;
        int index2 = adjVertices.get(v2).indexOf(v1);
        adjVertices.get(v2).get(index2).price = newPrice;
    }
    @Override
    public String toString() {
        return "Price list: " + adjVertices +
                '.';
    }
}
