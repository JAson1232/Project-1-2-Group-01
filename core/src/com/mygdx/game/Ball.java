package com.mygdx.game;

import java.io.FileNotFoundException;

public class Ball {

    Vector state;
    private double x;
    private double y;
    private double z;
    private double mass;
    private double currentVelocity;
    private double acceleration;

    public Ball(Vector state){
        this.state = state.clone();
    }

    public Ball(double x, double y, int z, double mass) throws FileNotFoundException {
        this.x = x;
        this.y = y;
        HeightFunction hf = new HeightFunction();
        this.z = hf.f(x,y,0,0);
        this.mass = mass;
    }
    public double getX(){
        return x;
    }
    public void setX(double x){
        this.x = x;
    }
    public double getY(){
        return y;
    }
    public void setY(double y){
        this.y = y;
    }
    public double getZ(){
        return z;
    }
    public void setZ(double z){
        this.z = z;
    }
    public double getMass(){
        return mass;
    }
    public void setMass(double mass){
        this.mass = mass;
    }
    public double getCurrentVelocity(){
        return currentVelocity;
    }
    //????? needs physics all above
    public void setCurrentVelocity(double currentVelocity){
        this.currentVelocity = currentVelocity;
    }
    public double getAcceleration(){
        return acceleration;
    }
    public void setAcceleration(double acceleration){
        this.acceleration = acceleration;
    }
    public boolean isMoving(){
        if(currentVelocity == 0){
            return true;
        }
        return false;
    }

    public Vector getState(){
        return this.state;
    }
}
