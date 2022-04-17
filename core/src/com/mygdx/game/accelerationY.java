package com.mygdx.game;

import java.io.FileNotFoundException;

public class accelerationY implements Function{
    double g = 9.8;
    
    @Override
    /**
     * Calculates acceleration in y direction
     * @param x Current x position
     * @param y Current y position
     * @param vx Velocity in x direction
     * @param vy Velocity in y direction
     * @return Acceleration in y direction
     */
    public double f(double x, double y,double vx,double vy) throws FileNotFoundException {
        HeightFunction f  = new HeightFunction();
        PartialDerivative px = new PartialDerivative(f);
        return  -g*px.getY(x,y,0,0)-Field.frictionKinetic*g*((vy))/(Math.sqrt(((vx)*(vx))+(vy)*(vy)));
    }
}
