package com.mygdx.game;

public class HeightFunction implements Function{
    @Override
    public double f(double x, double y,double vx,double vy) {
        return 0;
        // return  0.5*(Math.sin((x-y)/7)+0.9);
    }
}
