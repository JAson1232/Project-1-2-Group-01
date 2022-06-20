package com.mygdx.game.PSO;

public class Velocity {
    private double vx ;
    private double vy;

    public Velocity(double x, double y){
        this.vx=x;
        this.vy=y;
    }

    public double getVx(){
        return this.vx;
    }

    public double getVy(){
        return this.vy;
    }

    public void setVx(double x){
        this.vx=x;
    }
    public void setVy(double y){
        this.vy =y;
    }

    public String toString(){
        return this.getVx()+ "," + this.getVy();
    }
}
















































