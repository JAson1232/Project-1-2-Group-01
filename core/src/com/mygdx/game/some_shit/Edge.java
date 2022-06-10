package com.mygdx.game.some_shit;

public class Edge<T> {

    private Vertex<T> from;
    private Vertex<T> to;
    private int cost;
    private boolean mark;

    public Edge(Vertex<T> from, Vertex<T> to) {
        this(from, to, 0);
    }

    public Edge(Vertex<T> from, Vertex<T> to, int cost) {
        this.from = from;
        this.to = to;
        this.cost = cost;
        mark = false;
    }

    public Vertex<T> getTo() {
        return to;
    }

    public Vertex<T> getFrom() {
        return from;
    }

    public int getCost() {
        return cost;
    }

    public void mark() {
        mark = true;
    }

    public void clearMark() {
        mark = false;
    }

    public boolean isMarked() {
        return mark;
    }
}
