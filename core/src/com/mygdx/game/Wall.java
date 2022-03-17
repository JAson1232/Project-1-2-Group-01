package com.mygdx.game;

public class Wall implements Obstacle{
    private String name = "Wall";
    private Vector position;
    private static double absorption = -0.8; // How much power lost
    private double radius;

    /**
     * Constructor
     * @param x
     * @param y
     * @param radiusSphere
     */
    public Wall(double x, double y, double radiusSphere){
        position = new Vector(x,y,0,null,0,0);
        this.radius =radiusSphere;
    }

    /**
     * Constructor
     * @param position
     * @param radiusSphere
     */
    public Wall(Vector position, double radiusSphere){
        this.position=position;
        this.radius =radiusSphere;
    }

    /**
     * Whether collision occurred
     * @return T/F
     */
    public boolean collision(Vector currentPos){
        return (currentPos.equals(position, radius));
    }

    /**
     * Effect from collision
     * @param currentVelocity
     * @param currentPos
     */
    public void effect(Vector currentVelocity, Vector currentPos){
        currentVelocity.setX(currentVelocity.getX()*absorption);
        currentVelocity.setY(currentVelocity.getY()*absorption);
    }

    /**
     * Getter method
     * @return name
     */
    public String getName(){return name;}

    /**
     * Getter method
     * @return absorption
     */
    public static double getAbsorption(){ return absorption;}

    /**
     * Getter method
     * @return position
     */
    public Vector getPosition(){ return position;}

    /**
     * Getter method
     * @return radius
     */
    public double getRadius(){ return radius;}
}
