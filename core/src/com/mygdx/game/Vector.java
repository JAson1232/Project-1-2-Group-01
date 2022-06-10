package com.mygdx.game;

public class Vector {
    private double x,y,z;
    private double vx;
    private double vy;
    Tile t;

    /**
     * Constructor to create Vector
     * @param x
     * @param y
     * @param z
     * @param t
     * @param vx
     * @param vy
     */
    public Vector(double x, double y, double z, Tile t, double vx, double vy) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.t = t;
        this.vx = vx;
        this.vy = vy;
    }

    /**
     * Getter method
     * @return x
     */

    public Tile getTile(){
        return this.t;
    }
    public double getX() {
        return this.x;
    }

    /**
     * Getter method
     * @return y
     */
    public double getY() {
        return this.y;
    }

    /**
     * Getter method
     * @return z
     */
    public double getZ() {
        return this.z;
    }

    /**
     * Setter method
     * @param x
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Setter method
     * @param y
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Setter method
     * @param z
     */
    public void setZ(double z) {
        this.z = z;
    }

    /**
     * Determines distance from one vector to Vector v
     * @param v
     */
    public double distanceTo(Vector v){
        return Math.sqrt(((v.x-this.x)*(v.x-this.x))+((v.y-this.y)*(v.y-this.y)) + ((v.z - this.z)*(v.z - this.z)));
    }

    /**
     * Getter method
     * @return vx
     */
    public double getVx() {
        return vx;
    }

    /**
     * Setter method
     * @param vx
     */
    public void setVx(double vx) {
        this.vx = vx;
    }

    /**
     * Getter method
     * @return vy
     */
    public double getVy() {
        return vy;
    }

    /**
     * Setter method
     * @param vy
     */
    public void setVy(double vy) {
        this.vy = vy;
    }

    @Override
    /**
     * Clones vector
     * @return Cloned vector
     */
    public Vector clone(){
        return new Vector(this.x,this.y,this.z,this.t,this.vx,this.vy);
    }

    /**
     * Adds another vector to current vector
     * @param v
     * @return Added vectors
     */
    public Vector sum(Vector v){
        return new Vector(this.x + v.x,this.y+v.y,this.z+v.z,this.t,this.vx+v.vx,this.vy+v.vy);
    }

    /**
     * Subtracts another vector from current vector
     * @param v
     * @return Added vectors
     */
    public Vector subtract(Vector v){
        return new Vector(this.x - v.x,this.y - v.y, this.z - v.z, this.t, this.vx - v.vx, this.vy - v.vy);
    }

    /**
     * Scaled vectors
     * @param magnitude
     * @return Scaled vectors
     */
    public Vector scale(double magnitude){
        return new Vector(this.x*magnitude,this.y*magnitude,this.z*magnitude,this.t,this.vx*magnitude,this.vy*magnitude);
    }

    /**
     * Prints out state of Vector
     * @return State of vector as String
     */
    public String toString(){
        return "x: " + this.x + " y: " + this.y + " z: " + this.z + " vx: " + this.vx + " vy: " + this.vy;
    }

    /**
     * Checks if the distance between currentVector and a is smaller than tolerance
     * @param a
     * @param tolerance
     * @return true if distance <=tolerance
     *          false if distance>tolerance
     */
    public boolean equals(Vector a,double tolerance){
        if(Math.sqrt(Math.pow((a.getX()-this.x), 2)+Math.pow((a.getY()-this.y), 2))<=tolerance){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean doesntEqual(Vector a,double tolerance) {
        if (Math.sqrt(Math.pow((x - a.getX()), 2) + Math.pow((y - a.getY()), 2)) > tolerance) {
            return true;
        } else {
            return false;
        }
    }

}
