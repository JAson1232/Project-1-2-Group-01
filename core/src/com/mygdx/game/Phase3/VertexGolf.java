package com.mygdx.game.Phase3;

import java.util.ArrayList;
import java.util.List;

public class VertexGolf {

    public  int xCord;
    public  int yCord;
    public VertexGolf previous;
    public  List<VertexGolf> neigh;
    public boolean vizited = false;
    public int pathCost = Integer.MAX_VALUE;
    public char type;

    public VertexGolf(int xCord, int yCord){
        this.neigh = new ArrayList<VertexGolf>();
        this.xCord = xCord;
        this.yCord = yCord;

    }
//    public double getXCord(){
//        return this.xCord;
//    }
//    public void setXCord(double newXCord){
//        this.xCord = newXCord;
//    }
//    public double getYCord(){
//        return this.yCord;
//    }
//    public void setYCord(double newYCord){
//        this.yCord = newYCord;
//    }
//    public EdgeGolf getIncomingEdge(){
//        return incomingEdge;
//    }
//    public void setIncomingEdge(EdgeGolf n){
//        this.incomingEdge = n;
//    }
//    public List<EdgeGolf> getOutgoingEdge(){
//        return this.neigh;
//    }
//    public void setOutgoingEdge(List<EdgeGolf> n){
//        this.neigh = n;
//    }
//    public boolean getVisited(){
//        return this.vizited;
//    }
//    public void setVizitedTrue(){
//        this.vizited = true;
//    }
//    public void setVizitedFalse(){
//        this.vizited = false;
//    }
//    public boolean getGoal(){
//        return this.goal;
//    }
//    public void setGoalTrue(){
//        this.goal = true;
//    }
//    public void setGoalFalse(){
//        this.goal = false;
//    }
//    public double getPathCost(){
//        return this.pathCost;
//    }
//    public void setPathCost(double n){
//        this.pathCost = n;
//    }
//    public List<VertexGolf> getPathToThis(){
//        return this.pathToThis;
//    }
//    public void addToPath(VertexGolf n){
//        this.pathToThis.add(n);
//    }
//    public EdgeGolf findEdge(VertexGolf dest) {
//        for (EdgeGolf e : neigh) {
//            if (e.getTo() == dest)
//                return e;
//        }
//        return null;
//    }
//    public boolean remove(EdgeGolf e){
//        if(e.getFrom() == this)
//            incomingEdge = null;
//        else if (e.getTo() == this)
//            neigh.remove(e);
//        else
//            return false;
//        return true;
//    }
}
