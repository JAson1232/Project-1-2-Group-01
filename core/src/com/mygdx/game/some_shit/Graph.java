package com.mygdx.game.some_shit;

import java.util.*;

public class Graph<T> {
    private List<Vertex<T>> verticies;
    private List<Edge<T>> edges;
    private Vertex<T> rootVertex;

    public Graph() {
        verticies = new ArrayList<Vertex<T>>();
        edges = new ArrayList<Edge<T>>();
    }

    public boolean isEmpty() {
        return verticies.size() == 0;
    }

    public boolean addVertex(Vertex<T> v) {
        boolean added = false;
        if (verticies.contains(v) == false) {
            added = verticies.add(v);
        }
        return added;
    }

    public int size() {
        return verticies.size();
    }

    public Vertex<T> getRootVertex() {
        return rootVertex;
    }

    public void setRootVertex(Vertex<T> root) {
        this.rootVertex = root;
        if (verticies.contains(root) == false)
            this.addVertex(root);
    }

    public Vertex<T> getVertex(int n) {
        return verticies.get(n);
    }

    public List<Vertex<T>> getVerticies() {
        return this.verticies;
    }

    public boolean addEdge(Vertex<T> from, Vertex<T> to, int cost) throws IllegalArgumentException {
        if (verticies.contains(from) == false)
            throw new IllegalArgumentException("from is not in graph");
        if (verticies.contains(to) == false)
            throw new IllegalArgumentException("to is not in graph");

        Edge<T> e = new Edge<T>(from, to, cost);
        if (from.findEdge(to) != null)
            return false;
        else {
            from.addEdge(e);
            to.addEdge(e);
            edges.add(e);
            return true;
        }
    }

    public boolean insertBiEdge(Vertex<T> from, Vertex<T> to, int cost)
            throws IllegalArgumentException {
        return addEdge(from, to, cost) && addEdge(to, from, cost);
    }

    public List<Edge<T>> getEdges() {
        return this.edges;
    }

    public boolean removeVertex(Vertex<T> v) {
        if (!verticies.contains(v))
            return false;
        verticies.remove(v);
        if (v == rootVertex)
            rootVertex = null;
        // Remove the edges associated with v
        for (int n = 0; n < v.getOutgoingEdgeCount(); n++) {
            Edge<T> e = v.getOutgoingEdge(n);
            v.remove(e);
            Vertex<T> to = e.getTo();
            to.remove(e);
            edges.remove(e);
        }
        for (int n = 0; n < v.getIncomingEdgeCount(); n++) {
            Edge<T> e = v.getIncomingEdge(n);
            v.remove(e);
            Vertex<T> predecessor = e.getFrom();
            predecessor.remove(e);
        }
        return true;
    }

    public boolean removeEdge(Vertex<T> from, Vertex<T> to) {
        Edge<T> e = from.findEdge(to);
        if (e == null)
            return false;
        else {
            from.remove(e);
            to.remove(e);
            edges.remove(e);
            return true;
        }
    }

    public void clearMark() {
        for (Vertex<T> w : verticies)
            w.clearMark();
    }

    public void clearEdges() {
        for (Edge<T> e : edges)
            e.clearMark();
    }

    public Vertex<T> findVertexByName(String name) {
        Vertex<T> match = null;
        for (Vertex<T> v : verticies) {
            if (name.equals(v.getName())) {
                match = v;
                break;
            }
        }
        return match;
    }

    public Vertex<T> findVertexByData(T data, Comparator<T> compare) {
        Vertex<T> match = null;
        for (Vertex<T> v : verticies) {
            if (compare.compare(data, v.getData()) == 0) {
                match = v;
                break;
            }
        }
        return match;
    }
}
