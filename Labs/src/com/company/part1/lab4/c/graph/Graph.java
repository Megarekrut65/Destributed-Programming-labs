package com.company.part1.lab4.c.graph;

import java.util.*;

class Vertex implements Comparable{
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

    @Override
    public int compareTo(Object o) {
        return 0;
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
        if(adjVertices.containsKey(v)) {
            adjVertices.values().forEach(e -> e.remove(v));
            adjVertices.remove(new Vertex(label, null));
        }
    }
    public void addEdge(String label1, String label2, Integer price) {
        Vertex v1 = new Vertex(label1, price);
        Vertex v2 = new Vertex(label2, price);
        if(adjVertices.containsKey(v1) && adjVertices.containsKey(v2)) {
            adjVertices.get(v1).add(v2);
            adjVertices.get(v2).add(v1);
        }
    }
    public void removeEdge(String label1, String label2) {
        Vertex v1 = new Vertex(label1, null);
        Vertex v2 = new Vertex(label2, null);
        if(adjVertices.containsKey(v1) && adjVertices.containsKey(v2)) {
            List<Vertex> eV1 = adjVertices.get(v1);
            List<Vertex> eV2 = adjVertices.get(v2);
            if (eV1 != null)
                eV1.remove(v2);
            if (eV2 != null)
                eV2.remove(v1);
        }
    }
    public void editEdge(String label1, String label2, Integer newPrice) {
        Vertex v1 = new Vertex(label1, null);
        Vertex v2 = new Vertex(label2, null);
        if(adjVertices.containsKey(v1) && adjVertices.containsKey(v2))
        {
            int index1 = adjVertices.get(v1).indexOf(v2);
            if(index1 < 0) return;
            adjVertices.get(v1).get(index1).price = newPrice;
            int index2 = adjVertices.get(v2).indexOf(v1);
            adjVertices.get(v2).get(index2).price = newPrice;
        }
    }
    @Override
    public String toString() {
        return "Price list: " + adjVertices +
                '.';
    }
    public Integer dijkstra(String from, String to)
    {
        Map<String, Integer> dist = new HashMap<>();
        adjVertices.forEach((vertex,list)->{
            dist.put(vertex.label, Integer.MAX_VALUE);
        });
        Queue<Vertex> pq = new PriorityQueue<>();
        pq.add(new Vertex(from, 0));
        dist.put(from,0);
        Set<Vertex> settled = new HashSet<>();
        while (settled.size() != adjVertices.size()) {
            if(pq.isEmpty()) break;
            Vertex u = pq.remove();
            settled.add(u);
            neighbours(u, settled, dist,pq);
        }
        return dist.get(to);
    }
    private void neighbours(Vertex u, Set<Vertex> settled, Map<String, Integer> dist,Queue<Vertex> pq)
    {
        int edgeDistance = -1;
        int newDistance = -1;
        if(adjVertices.get(u) == null) return;
        for (int i = 0; i < adjVertices.get(u).size(); i++) {
            Vertex v = adjVertices.get(u).get(i);
            if (!settled.contains(v)) {
                edgeDistance = v.price;
                newDistance = dist.get(u.label) + edgeDistance;
                if (dist.get(v.label) != null && newDistance < dist.get(v.label))
                    dist.put(v.label, newDistance);
                pq.add(new Vertex(v.label, dist.get(v.label)));
            }
        }
    }
}
