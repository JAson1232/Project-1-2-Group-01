package com.mygdx.game;

public class Field {
    int width,height,length;
    int holeX,holeY,holeZ;
    static double  frictionStatic,frictionKinetic;

    Vector[][][] vectors = new Vector[width][length][height];

    public double getFrictionKinetic() {
        return frictionKinetic;
    }

    public  double getFrictionStatic() {
        return frictionStatic;
    }

    public int getHeight() {
        return height;
    }

    public int getHoleX() {
        return holeX;
    }

    public int getHoleY() {
        return holeY;
    }

    public int getHoleZ() {
        return holeZ;
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

    public void setHeight(int height) {
        this.height = height;
    }

    public void setHoleX(int holeX) {
        this.holeX = holeX;
    }

    public void setHoleY(int holeY) {
        this.holeY = holeY;
    }

    public void setHoleZ(int holeZ) {
        this.holeZ = holeZ;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
