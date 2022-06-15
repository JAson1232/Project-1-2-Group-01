package com.mygdx.game.some_shit;

public class EdgeGolf {

    private VertexGolf from;
    private VertexGolf to;
    private double cost;

    public EdgeGolf(VertexGolf from, VertexGolf to){
        this.from = from;
        this.to = to;
    }
    public EdgeGolf(VertexGolf from, VertexGolf to, double cost){
        this.from = from;
        this.to = to;
        this.cost = cost;
    }
    public VertexGolf getFrom(){
        return this.from;
    }
    public void setFrom(VertexGolf n){
        this.from = n;
    }
    public VertexGolf getTo(){
        return this.to;
    }
    public void setTo(VertexGolf n){
        this.to = n;
    }
    public double getCost(){
        return this.cost;
    }
    public void setCost(double n){
        this.cost = n;
    }
}
