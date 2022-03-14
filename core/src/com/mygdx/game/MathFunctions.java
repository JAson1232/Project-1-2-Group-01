package com.mygdx.game;

public class MathFunctions {

    public static void main(String[] args) {
        MathFunctions m = new MathFunctions();
        
    }




    // Function for Euler formula
    Vector euler(Vector StateVector, int h)
    {
        accelerationX ax = new accelerationX();
        accelerationY ay = new accelerationY();
        HeightFunction heightFunction = new HeightFunction();
        Vector newState = StateVector.sum((
                new Vector(StateVector.getX()*h,
                        StateVector.getY()*h,
                        StateVector.getZ()*h,
                        null,
                        ax.f(StateVector.getX(), StateVector.getY(), StateVector.getVx(), StateVector.getVy()),
                        ay.f(StateVector.getX(), StateVector.getY(), StateVector.getVx(), StateVector.getVy()))).scale(h));

        return newState;
    }

    public int calculateDx(){
        return 0;
    }


}
