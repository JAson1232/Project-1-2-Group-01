package com.mygdx.game;

public class Field {
    int width,height,length;
    int holeX,holeY,holeZ;
    static double  frictionStatic = 0.2,frictionKinetic = 0.1;
    static Vector[][] vectors;

    /**
     * Creates new field (2D array) of Vectors
     * @return Created field
     */
    public Vector[][] createField() {
        return new Vector[length][width];
    }

    /**
     * Getter method
     * @return Kinetic friction
     */
    public double getFrictionKinetic() {
        return frictionKinetic;
    }

    /**
     * Getter method
     * @return Static friction
     */
    public double getFrictionStatic() {
        return frictionStatic;
    }

    /**
     * Getter method
     * @return x position of hole
     */
    public int getHoleX() {
        return holeX;
    }

    /**
     * Getter method
     * @return y position of hole
     */
    public int getHoleY() {
        return holeY;
    }

    /**
     * Getter method
     * @return Length of field
     */
    public int getLength() {
        return length;
    }

    /**
     * Getter method
     * @return Width of field
     */
    public int getWidth() {
        return width;
    }

    /**
     * Setter method
     * @param frictionKinetic
     */
    public void setFrictionKinetic(double frictionKinetic) {
        this.frictionKinetic = frictionKinetic;
    }

    /**
     * Setter method
     * @param frictionStatic
     */
    public void setFrictionStatic(double frictionStatic) {
        this.frictionStatic = frictionStatic;
    }

    /**
     * Setter method
     * @param holeX
     */
    public void setHoleX(int holeX) {
        this.holeX = holeX;
    }

    /**
     * Setter method
     * @param holeY
     */
    public void setHoleY(int holeY) {
        this.holeY = holeY;
    }

    /**
     * Setter method
     * @param length
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * Setter method
     * @param width
     */
    public void setWidth(int width) {
        this.width = width;
    }
}
