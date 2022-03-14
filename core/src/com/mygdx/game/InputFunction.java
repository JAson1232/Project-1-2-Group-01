package com.mygdx.game;

public class InputFunction implements Function{
    @Override
    public double f(double x, double y) {
        HeightFunction f  = new HeightFunction();
        PartialDerivative px = new PartialDerivative(f);
        return  -g*px.getX(x,y)-Field.frictionKinetic*g*()
    }
}
