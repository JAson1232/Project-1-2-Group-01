package com.mygdx.game;

import java.io.FileNotFoundException;

public class accelerationY implements Function{
    double g = 9.8;
    double m = 0.0459; // kg
    
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
        double pdx = px.getX(x, y, vx, vy);
        double pdy = px.getY(x, y, vx, vy);
        double denom = 1 + pdx*pdx + pdy*pdy;
        // Original
        /*
        if(Math.sqrt(Math.pow(vx, 2) + Math.pow(vy, 2)) < Experiments.h * 5
                && px.getX(x,y,vx,vy) < Field.frictionStatic && px.getY(x,y,vx,vy) < Field.frictionStatic) {
        if((Math.sqrt(((vx)*(vx))+((vy)*(vy)))) < Experiments.h * 5){
            //return (-g*px.getY(x,y,0,0))-(Field.frictionKinetic*g*(px.getY(x,y,0,0)/Math.sqrt(px.getX(x,y,0,0)*px.getX(x,y,0,0)+px.getY(x,y,0,0)*px.getY(x,y,0,0))));
        }
        return (-g*px.getY(x,y,vx,vy))-(Field.frictionKinetic*g*((vy))/(Math.sqrt(((vx)*(vx))+((vy)*(vy)))));
        */
        // Sophisticated
        return -m*g*pdy/denom - Field.frictionKinetic*m*g / Math.sqrt(denom) * (vy / Math.sqrt(vx * vx + vy * vy + Math.pow(pdx*vx + pdy*vy, 2)))/m;
    }
}
