package com.mygdx.game;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Ball {

    public Vector state;
    private double x;
    private double y;
    private double z;
    private double mass;
    private double currentVelocity;
    private double acceleration;
    public boolean holeIn = false;
    public boolean moving = false;
    public boolean inWater;
    public boolean readVelocity = false;
    public double vx;
    public double vy;
    public boolean winner = false;
    ArrayList<int[]> velocities;
    public int distanceToHole;

    /**
     * Constructor; clones state of ball in parameter
     * @param state Current state of ball (coordinates, velocities, etc.)
     */
    public Ball(Vector state){
        this.state = state.clone();
        this.velocities = new ArrayList<>();
        this.distanceToHole = 9999;
    }

    /**
     * @return the holeIn
     */
    public boolean isHoleIn() {
        return holeIn;
    }

    /**
     * @param holeIn the holeIn to set
     */
    public void setHoleIn(boolean holeIn) {
        this.holeIn = holeIn;
    }

    /**
     * Constructor creating ball state
     * @param x
     * @param y
     * @param z
     * @param mass
     * @throws FileNotFoundException
     */
    public Ball(double x, double y, int z, double mass) throws FileNotFoundException {
        this.x = x;
        this.y = y;
        HeightFunction hf = new HeightFunction();
        this.z = hf.f(x,y,0,0);
        this.mass = mass;
    }

    /**
     * Getter method
     * @return x
     */
    public double getX(){
        return x;
    }

    /**
     * Setter method
     * @param x
     */
    public void setX(double x){
        this.x = x;
    }

    /**
     * Getter method
     * @return y
     */
    public double getY(){
        return y;
    }

    /**
     * Setter method
     * @param y
     */
    public void setY(double y){
        this.y = y;
    }

    /**
     * Getter method
     * @return z
     */
    public double getZ(){
        return z;
    }

    /**
     * Setter method
     * @param z
     */
    public void setZ(double z){
        this.z = z;
    }

    /**
     * Getter method
     * @return mass
     */
    public double getMass(){
        return mass;
    }

    /**
     * Setter method
     * @param mass
     */
    public void setMass(double mass){
        this.mass = mass;
    }

    /**
     * Getter method
     * @return currentVelocity
     */
    public double getCurrentVelocity(){
        return currentVelocity;
    }

    /**
     * Setter method
     * @param currentVelocity
     */
    public void setCurrentVelocity(double currentVelocity){
        this.currentVelocity = currentVelocity;
    }

    /**
     * Getter method
     * @return acceleration
     */
    public double getAcceleration(){
        return acceleration;
    }

    /**
     * Setter method
     * @param acceleration
     */
    public void setAcceleration(double acceleration){
        this.acceleration = acceleration;
    }

    /**
     * Determines whether ball is moving
     * @return T/F
     */
    public boolean isMoving(){
        if(currentVelocity == 0){
            return false;
        }
        return true;
    }

    /**
     * Getter method
     * @return Vector of ball state
     */
    public Vector getState(){
        return this.state;
    }

    public double getVx(){
        return this.vx;
    }
    public double getVy(){
        return this.vy;
    }
}
