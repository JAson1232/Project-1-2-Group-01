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
        // Original
        /*
//        if(Math.sqrt(Math.pow(vx, 2) + Math.pow(vy, 2)) < Experiments.h * 5
//        && px.getX(x,y,vx,vy) < Field.frictionStatic && px.getY(x,y,vx,vy) < Field.frictionStatic) {
        if ((Math.sqrt(((vx) * (vx)) + ((vy) * (vy)))) < Experiments.h * 5) {
            return ((-g*px.getX(x,y,0,0))-(Field.frictionKinetic*g*(px.getX(x,y,0,0)/Math.sqrt(px.getX(x,y,0,0)*px.getX(x,y,0,0)+px.getY(x,y,0,0)*px.getY(x,y,0,0)))));
        }
        return  (-g*px.getX(x,y,vx,vy))-(Field.frictionKinetic*g*((vx))/(Math.sqrt(((vx)*(vx))+((vy)*(vy)))));
        */
        // Sophisticated
        return -m * g * px.getX(x, y, vx, vy) / (1 + px.getX(x, y, vx, vy) * px.getX(x, y, vx, vy) + px.getY(x, y, vx, vy) * px.getY(x, y, vx, vy)) - Field.frictionKinetic * m * g / Math.sqrt(1 + px.getX(x, y, vx, vy) * px.getX(x, y, vx, vy) + px.getY(x, y, vx, vy) * px.getY(x, y, vx, vy)) * (vx / Math.sqrt(vx * vx + vy * vy + Math.pow(px.getX(x, y, vx, vy) * vx + px.getY(x, y, vx, vy) * vy, 2)))/m;
    }
}
