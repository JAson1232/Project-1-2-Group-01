package com.mygdx.game;

public class Wall implements Obstacle{
    private String name= "Wall";
    private Vector position;
    private static double absorption = -0.8; // how much power we lose
    private double radius;

    public Wall(double x, double y, double radiusSphere){
        position = new Vector(x,y,0,null,0,0);
        this.radius =radiusSphere;
    }

    public Wall(Vector position, double radiusSphere){
        this.position=position;
        this.radius =radiusSphere;
    }

    public boolean collision(Vector currentPos){
        return (currentPos.equals(position, radius));
    }

    public void effect(Vector currentVelocity, Vector currentPos){
        currentVelocity.setX(currentVelocity.getX()*absorption);
        currentVelocity.setY(currentVelocity.getY()*absorption);
    }

    public String getName(){return name;}

    public static double getAbsorption(){ return absorption;}

    public Vector getPosition(){ return position;}

    public double getRadius(){ return radius;}

}
