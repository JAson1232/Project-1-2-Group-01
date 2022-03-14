package com.mygdx.game;

public class Ball {

    Vector state;
    private double x;
    private double y;
    private int z;
    private double mass;
    private double currentVelocity;
    private double acceleration;

    public Ball(Vector state){
        this.state = state.clone();
    }

    public Ball(double x, double y, int z, double mass){
        this.x = x;
        this.y = y;
        this.z = z;
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
    public int getZ(){
        return z;
    }
    public void setZ(int z){
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
