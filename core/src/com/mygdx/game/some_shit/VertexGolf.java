package com.mygdx.game.some_shit;

import java.util.List;
import java.util.Queue;

public class VertexGolf {

    private double xCord;
    private double yCord;
    private EdgeGolf incomingEdge;
    private List<EdgeGolf> outgoingEdge;
    private boolean vizited;
    private boolean goal;
    private double pathCost = 9999999;
    private Queue<VertexGolf> pathToThis;

    public VertexGolf(double xCord, double yCord, boolean vizited, boolean goal){
        this.xCord = xCord;
        this.yCord = yCord;
        this.vizited = vizited;
        this.goal = goal;
    }
    public double getXCord(){
        return this.xCord;
    }
    public void setXCord(double newXCord){
        this.xCord = newXCord;
    }
    public double getYCord(){
        return this.yCord;
    }
    public void setYCord(double newYCord){
        this.yCord = newYCord;
    }
    public EdgeGolf getIncomingEdge(){
        return incomingEdge;
    }
    public void setIncomingEdge(EdgeGolf n){
        this.incomingEdge = n;
    }
    public List<EdgeGolf> getOutgoingEdge(){
        return this.outgoingEdge;
    }
    public void setOutgoingEdge(List<EdgeGolf> n){
        this.outgoingEdge = n;
    }
    public boolean getVisited(){
        return this.vizited;
    }
    public void setVizitedTrue(){
        this.vizited = true;
    }
    public void setVizitedFalse(){
        this.vizited = false;
    }
    public boolean getGoal(){
        return this.goal;
    }
    public void setGoalTrue(){
        this.goal = true;
    }
    public void setGoalFalse(){
        this.goal = false;
    }
    public double getPathCost(){
        return this.pathCost;
    }
    public void setPathCost(double n){
        this.pathCost = n;
    }
    public Queue<VertexGolf> getPathToThis(){
        return this.pathToThis;
    }
    public void addToPath(VertexGolf n){
        this.pathToThis.add(n);
    }
    public EdgeGolf findEdge(VertexGolf dest) {
        for (EdgeGolf e : outgoingEdge) {
            if (e.getTo() == dest)
                return e;
        }
        return null;
    }
    public boolean remove(EdgeGolf e){
        if(e.getFrom() == this)
            incomingEdge = null;
        else if (e.getTo() == this)
            outgoingEdge.remove(e);
        else
            return false;
        return true;
    }
}
