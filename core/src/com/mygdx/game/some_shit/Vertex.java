package com.mygdx.game.some_shit;

import java.util.ArrayList;
import java.util.List;

public class Vertex<T> {
    private List<Edge<T>> incomingEdges;
    private List<Edge<T>> outgoingEdges;
    private String name;
    private boolean mark;
    private int markState;
    private T data;
    private boolean goal = false;
    private int minPathCost = 999999;

    public Vertex(String name, T data){
        incomingEdges = new ArrayList<Edge<T>>();
        outgoingEdges = new ArrayList<Edge<T>>();
        this.name = name;
        this.mark = false;
        this.data = data;
    }

    public String getName(){
        return this.name;
    }

    public T getData(){
        return this.data;
    }

    public boolean isGoal(){
        return goal;
    }

    public void setGoal(boolean newGoal){
        goal = newGoal;
    }

    public void setData(T data){
        this.data = data;
    }

    public int getMinPathCost(){
        return minPathCost;
    }

    public void setMinPathCost(int newMinPathCost){
        minPathCost = newMinPathCost;
    }

    public boolean addEdge(Edge<T> e){
        if(e.getFrom() == this){
            outgoingEdges.add(e);
        }
        else if(e.getTo() == this){
            incomingEdges.add(e);
        }
        else return false;
        return true;
    }

    public void addOutgoingEdge(Vertex<T> to, int cost) {
        Edge<T> out = new Edge<T>(this, to, cost);
        outgoingEdges.add(out);
    }

    public void addIncomingEdge(Vertex<T> from, int cost) {
        Edge<T> out = new Edge<T>(this, from, cost);
        incomingEdges.add(out);
    }

    public boolean hasEdge(Edge<T> e){
        if(e.getFrom() == this)
            return incomingEdges.contains(e);
        else if(e.getTo() == this)
            return outgoingEdges.contains(e);
        else
            return false;
    }

    public boolean remove(Edge<T> e){
        if(e.getFrom() == this)
            incomingEdges.remove(e);
        else if (e.getTo() == this)
            outgoingEdges.remove(e);
        else
            return false;
        return true;
    }

    public int getIncomingEdgeCount() {
        return incomingEdges.size();
    }

    public Edge<T> getIncomingEdge(int i) {
        return incomingEdges.get(i);
    }

    public List getIncomingEdges() {
        return this.incomingEdges;
    }

    public int getOutgoingEdgeCount() {
        return outgoingEdges.size();
    }

    public Edge<T> getOutgoingEdge(int i) {
        return outgoingEdges.get(i);
    }

    public List getOutgoingEdges() {
        return this.outgoingEdges;
    }

    //Search the outgoing edges looking for an edge whose edge.to == dest.
    public Edge<T> findEdge(Vertex<T> dest) {
        for (Edge<T> e : outgoingEdges) {
            if (e.getTo() == dest)
                return e;
        }
        return null;
    }

    public Edge<T> findEdge(Edge<T> e) {
        if (outgoingEdges.contains(e))
            return e;
        else
            return null;
    }

    public int cost(Vertex<T> dest) {
        if (dest == this)
            return 0;

        Edge<T> e = findEdge(dest);
        int cost = Integer.MAX_VALUE;
        if (e != null)
            cost = e.getCost();
        return cost;
    }

    public boolean hasEdge(Vertex<T> dest) {
        return (findEdge(dest) != null);
    }

    public boolean visited() {
        return mark;
    }

    public void mark() {
        mark = true;
    }

    public void setMarkState(int state) {
        markState = state;
    }

    public int getMarkState() {
        return markState;
    }

    public void visit() {
        mark();
    }

    public void clearMark() {
        mark = false;
    }
}
