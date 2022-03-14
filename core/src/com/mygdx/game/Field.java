package com.mygdx.game;

public class Field {
    int width,height,length;
    int holeX,holeY,holeZ;
    static double  frictionStatic = 0.2,frictionKinetic = 0.1;
    Vector[][] vectors;

    public Vector[][] createField() {
        vectors = new Vector[length][width];
        return vectors;
    }

    public double getFrictionKinetic() {
        return frictionKinetic;
    }

    public  double getFrictionStatic() {
        return frictionStatic;
    }

    public int getHoleX() {
        return holeX;
    }

    public int getHoleY() {
        return holeY;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public void setFrictionKinetic(double frictionKinetic) {
        this.frictionKinetic = frictionKinetic;
    }

    public void setFrictionStatic(double frictionStatic) {
        this.frictionStatic = frictionStatic;
    }

    public void setHoleX(int holeX) {
        this.holeX = holeX;
    }

    public void setHoleY(int holeY) {
        this.holeY = holeY;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
