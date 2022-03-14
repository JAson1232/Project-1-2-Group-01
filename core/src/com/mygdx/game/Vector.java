package com.mygdx.game;

public class Vector {
    private double x,y,z;
    private double vx;
    private double vy;
    Tile t;

    public Vector(double x, double y, double z, Tile t, double vx, double vy){
        this.x = x;
        this.y = y;
        this.z = z;
        this.t = t;
        this.vx = vx;
        this.vy = vy;
    }
    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }
    public double distanceTo(Vector v){
        return Math.sqrt(((v.x-this.x)*(v.x-this.x))-((v.y-this.y)*(v.y-this.y)) - ((v.z - this.z)*(v.z - this.z)));
    }
    public double getVx() {
        return vx;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public double getVy() {
        return vy;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }



    @Override
    public Vector clone(){
        return new Vector(this.x,this.y,this.z,this.t,this.vx,this.vy);
    }

    public Vector sum(Vector v){
        return new Vector(this.x + v.x,this.y+v.y,this.z+v.z,this.t,this.vx+v.vx,this.vy+v.vy);
    }

    public Vector scale(double magnitude){
        return new Vector(this.x*magnitude,this.y*magnitude,this.z*magnitude,this.t,this.vx*magnitude,this.vy*magnitude);
    }

    public String toString(){
        return "x: " + this.x + " y: " + this.y + " z: " + this.z + " vx: " + this.vx + " vy: " + this.vy;
    }

}
