package com.mygdx.game;

import java.io.FileNotFoundException;

public class PartialDerivative{
    private final double Delta_X = 0.0000001;
    private Function function;

    /**
     * Constructor method
     * @param function
     */
    PartialDerivative(Function function){
        this.function = function;
    }

    /**
     * Getter method
     * @param x
     * @param y
     * @param vx
     * @param vy
     * @return New x position
     * @throws FileNotFoundException
     */
    public double getX(double x,double y,double vx,double vy) throws FileNotFoundException {
        return (function.f(x+Delta_X,y,0,0)-function.f(x,y,0,0))/Delta_X;
    }

    /**
     * Getter method
     * @param x
     * @param y
     * @param vx
     * @param vy
     * @return New y position
     * @throws FileNotFoundException
     */
    public double getY(double x,double y,double vx, double vy) throws FileNotFoundException {
        return (function.f(x,y+Delta_X,0,0)-function.f(x,y,0,0))/Delta_X;
    }
}