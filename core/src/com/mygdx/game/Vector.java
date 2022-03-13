package com.mygdx.game;

public class Vector {
    private int x,y,z;
    Tile t;

    public Vector(int x, int y, int z, Tile t){
        this.x = x;
        this.y = y;
        this.z = z;
        this.t = t;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }
    public double distanceTo(Vector v){
        return Math.sqrt(((v.x-this.x)^2)-((v.y-this.y)^2) - ((v.z - this.z)^2));
    }

    @Override
    public Vector clone(){
        return new Vector(this.x,this.y,this.z,this.t);
    }

    public Vector sum(Vector v){
        return new Vector(this.x + v.x,this.y+v.y,this.z+v.z,this.t);
    }

    public Vector scale(int magnitude){
        return new Vector(this.x*magnitude,this.y*magnitude,this.z*magnitude,this.t);
    }

}
