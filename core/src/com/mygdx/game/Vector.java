package com.mygdx.game;

public class Vector {
    private int x,y;
    private double z;
    private double vx;
    private double vy;
    Tile t;

    public Vector(int x, int y, double z, Tile t, double vx, double vy){
        this.x = x;
        this.y = y;
        this.z = z;
        this.t = t;
        this.vx = vx;
        this.vy = vy;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }
    public double distanceTo(Vector v){
        return Math.sqrt(((v.x-this.x)^2)-((v.y-this.y)^2) -Math.pow ((v.z - this.z), 2));
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
        return new Vector(this.x + v.x,this.y+v.y,this.z+v.z,this.t,this.vx,this.vy);
    }

    public Vector scale(int magnitude){
        return new Vector(this.x*magnitude,this.y*magnitude,this.z*magnitude,this.t,this.vx,this.vy);
    }

}
