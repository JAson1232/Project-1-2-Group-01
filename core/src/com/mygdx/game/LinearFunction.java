package com.mygdx.game;

import com.mygdx.game.Vector;

public class LinearFunction {
    private double slope;
    private double constant;

    /***
     * constructor method
     * @param slope
     * @param constant
     */
    public LinearFunction(double slope, double constant){
        this.slope = slope;
        this.constant = constant;
    }

    /***
     * setter method
     * @param constance
     */
    public void setConstance(double constance) {
        this.constant = constance;
    }

    /***
     * setter method
     * @param slope
     */
    public void setSlope(double slope) {
        this.slope = slope;
    }

    /***
     * getter method
     * @return
     */
    public double getConstant() {
        return constant;
    }

    /***
     * getter method
     * @return
     */
    public double getSlope() {
        return slope;
    }
}
