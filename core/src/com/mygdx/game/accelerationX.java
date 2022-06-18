package com.mygdx.game;

import java.io.FileNotFoundException;

public class accelerationX implements Function{
    double g = 9.81;
    double m = 0.0459; // kg
    
    @Override
    /**
     * Calculates acceleration in x direction
     * @param x Current x position
     * @param y Current y position
     * @param vx Velocity in x direction
     * @param vy Velocity in y direction
     * @return Acceleration in x direction
     */
    public double f(double x, double y,double vx,double vy) throws FileNotFoundException {
        HeightFunction f = new HeightFunction();
        PartialDerivative px = new PartialDerivative(f);
        double pdx = px.getX(x, y, vx, vy);
        double pdy = px.getY(x, y, vx, vy);
        double denom = 1 + pdx*pdx + pdy*pdy;
        // Original
        /*
//        if(Math.sqrt(Math.pow(vx, 2) + Math.pow(vy, 2)) < Experiments.h * 5
//        && pdx < Field.frictionStatic && pdy < Field.frictionStatic) {
        if ((Math.sqrt(((vx) * (vx)) + ((vy) * (vy)))) < Experiments.h * 5) {
            return ((-g*pdx)-(Field.frictionKinetic*g*(pdx/Math.sqrt(pdx*pdx+pdy*pdy))));
        }
        return  (-g*pdx)-(Field.frictionKinetic*g*((vx))/(Math.sqrt(((vx)*(vx))+((vy)*(vy)))));
        */
        // Sophisticated
        return -m*g*pdx/denom - Field.frictionKinetic*m*g / Math.sqrt(denom) * (vx / Math.sqrt(vx * vx + vy * vy + Math.pow(pdx*vx + pdy*vy, 2)))/m;
    }
}
