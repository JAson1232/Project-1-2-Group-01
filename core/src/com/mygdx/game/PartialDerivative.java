package com.mygdx.game;

public class PartialDerivative{
    private final double Delta_X = 0.0000001;
    private Function function;
    PartialDerivative(Function function){
        this.function = function;
    }
    public double getX(double x,double y){
        return (function.f(x+Delta_X,y)-function.f(x,y))/Delta_X;
    }
    public double getY(double x,double y){
        return (function.f(x,y+Delta_X)-function.f(x,y))/Delta_X;
    }
}