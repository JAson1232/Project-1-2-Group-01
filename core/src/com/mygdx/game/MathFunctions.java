package com.mygdx.game;

public class MathFunctions {

    public static void main(String[] args) {
        MathFunctions m = new MathFunctions();
        Vector state = new Vector(0,0,0,null,1.1,0);
        Vector newPosition = m.euler(state,0.3);
        System.out.println(newPosition.toString());
    }




    // Function for Euler formula
    Vector euler(Vector StateVector, double h)
    {
        accelerationX ax = new accelerationX();
        accelerationY ay = new accelerationY();
        HeightFunction heightFunction = new HeightFunction();
        Vector newState = StateVector.sum((
                new Vector(StateVector.getVx()*h,
                        StateVector.getVy()*h,
                        StateVector.getZ()*h,
                        null,
                        ax.f(StateVector.getX(), StateVector.getY(), StateVector.getVx(), StateVector.getVy()),
                        ay.f(StateVector.getX(), StateVector.getY(), StateVector.getVx(), StateVector.getVy())))
                        .scale(h));
            //System.out.println(newState.getVx());

        return newState;
    }

    public int calculateDx(){
        return 0;
    }

    public double computeAngle(double angle){
        if(angle >270){
            return Math.abs(angle-360);
        }else{
            return Math.abs(angle-180);
        }

    }


}
