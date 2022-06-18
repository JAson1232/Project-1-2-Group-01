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

//        if(Math.sqrt(Math.pow(vx, 2) + Math.pow(vy, 2)) < Experiments.h * 5
//                && px.getX(x,y,vx,vy) < Field.frictionStatic && px.getY(x,y,vx,vy) < Field.frictionStatic) {
        if((Math.sqrt(((vx)*(vx))+((vy)*(vy)))) < Experiments.h * 5){
            // Original
            return (-g*px.getY(x,y,0,0))-(Field.frictionKinetic*g*(px.getY(x,y,0,0)/Math.sqrt(px.getX(x,y,0,0)*px.getX(x,y,0,0)+px.getY(x,y,0,0)*px.getY(x,y,0,0))));
            // Sophisticated
            //return -m*g*px.getY(x,y,vx,vy)/(1 + px.getX(x,y,vx,vy)*px.getX(x,y,vx,vy) + px.getY(x,y,vx,vy)*px.getY(x,y,vx,vy)) - Field.frictionKinetic*m*g/Math.sqrt(1 + px.getX(x,y,vx,vy)*px.getX(x,y,vx,vy) + px.getY(x,y,vx,vy)*px.getY(x,y,vx,vy))*(vy/Math.sqrt(vx*vx + vy*vy + Math.pow(px.getX(x,y,vx,vy)*vx + px.getY(x,y,vx,vy)*vy, 2)));
        }
        // Original
        return (-g*px.getY(x,y,vx,vy))-(Field.frictionKinetic*g*((vy))/(Math.sqrt(((vx)*(vx))+((vy)*(vy)))));
        // Sophisticated
        //return -m*g*px.getY(x,y,vx,vy)/(1 + px.getX(x,y,vx,vy)*px.getX(x,y,vx,vy) + px.getY(x,y,vx,vy)*px.getY(x,y,vx,vy)) - Field.frictionKinetic*m*g/Math.sqrt(1 + px.getX(x,y,vx,vy)*px.getX(x,y,vx,vy) + px.getY(x,y,vx,vy)*px.getY(x,y,vx,vy))*(vy/Math.sqrt(vx*vx + vy*vy + Math.pow(px.getX(x,y,vx,vy)*vx + px.getY(x,y,vx,vy)*vy, 2)));
    }
}
