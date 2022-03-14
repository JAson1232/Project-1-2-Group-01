package com.mygdx.game;

public class InputFunction implements Function{
    double g = 9.8;
    @Override
    public double f(double x, double y,double vx,double vy) {
        HeightFunction f  = new HeightFunction();
        PartialDerivative px = new PartialDerivative(f);
        return  -g*px.getX(x,y,0,0)-Field.frictionKinetic*g*((vx))/(Math.sqrt(((vx)*(vx))+(vy)*(vy)));
    }
}
