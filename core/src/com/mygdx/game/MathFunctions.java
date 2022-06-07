package com.mygdx.game;

import java.io.FileNotFoundException;


public class MathFunctions {

    public static void main(String[] args) throws FileNotFoundException {
        MathFunctions m = new MathFunctions();
        Vector state = new Vector(0,0,0,null,1.1,0);
        Vector newPosition = m.euler(state,5);
        System.out.println(newPosition.toString());
    }

    /***
     *  calculate the linear function derived from the ball trajectory and the wall segment
     * @param StartX
     * @param StartY
     * @param EndX
     * @param EndY
     * @return A linear function derived from two points
     */
    public static LinearFunction linear(double StartX, double StartY,
                          double EndX, double EndY){
        double slope = (EndY-StartY)/(EndX-StartX);
        double constant = StartY-slope*StartX;
        return new LinearFunction(slope,constant);
    }

    /***
     * calculate the intersection's x & y coordinate
     * @param f1
     * @param f2
     * @return a vector with the intersection's x & y coordinate
     */
    public static Vector calculateLinearIntersection(LinearFunction f1,LinearFunction f2){
        double x = (f2.getConstant()-f1.getConstant())/(f1.getSlope()-f2.getSlope());
        double y = f1.getSlope()*x+f1.getConstant();

        return new Vector(x ,y,0,null,0,0);
    }

    /**
     * Function for Euler's formula
     * @param StateVector
     * @param h Step size
     * @return New state
     * @throws FileNotFoundException
     */
    Vector euler(Vector StateVector, double h) throws FileNotFoundException {
        accelerationX ax = new accelerationX();
        accelerationY ay = new accelerationY();
        Vector newState = StateVector.sum((
                new Vector(StateVector.getVx(),
                        StateVector.getVy(),
                        StateVector.getZ(),
                        null,
                        ax.f(StateVector.getX(), StateVector.getY(), StateVector.getVx(), StateVector.getVy()),
                        ay.f(StateVector.getX(), StateVector.getY(), StateVector.getVx(), StateVector.getVy())))
                .scale(h));

        return newState;
    }

    /**
     * Function for second-order Runge-Kutta (Ralston's second-order method)
     * @param StateVector
     * @param h Step size
     * @return New state
     * @throws FileNotFoundException
     */
    Vector RK2(Vector StateVector, double h) throws FileNotFoundException {
        accelerationX ax = new accelerationX();
        accelerationY ay = new accelerationY();
        // f(t_i, w_i)
        Vector k_i_1 = new Vector(StateVector.getVx(),
                StateVector.getVy(),
                StateVector.getZ(),
                null,
                ax.f(StateVector.getX(), StateVector.getY(), StateVector.getVx(), StateVector.getVy()),
                ay.f(StateVector.getX(), StateVector.getY(), StateVector.getVx(), StateVector.getVy()));
        // 3f(t_i + 2h/3, w_i + f(t_i, w_i)*2h/3)
        Vector intermediate = StateVector.sum(k_i_1.scale(h*2/3));
        Vector k_i_2 = new Vector(intermediate.getVx(),
                intermediate.getVy(),
                StateVector.getZ(),
                null,
                ax.f(intermediate.getX(), intermediate.getY(), intermediate.getVx(), intermediate.getVy()),
                ay.f(intermediate.getX(), intermediate.getY(), intermediate.getVx(), intermediate.getVy()))
                .scale(3);
        return StateVector.sum(k_i_1.sum(k_i_2).scale(h/4));
    }

    /**
     * Function for fourth-order Runge-Kutta
     * @param StateVector
     * @param h Step size
     * @return New state
     * @throws FileNotFoundException
     */
    public Vector RK4(Vector StateVector, double h) throws FileNotFoundException {
        HillClimbing.counter1++;
        accelerationX ax = new accelerationX();
        accelerationY ay = new accelerationY();
        // f(t_i, w_i)
        Vector k_i_1 = new Vector(StateVector.getVx(),
                StateVector.getVy(),
                StateVector.getZ(),
                null,
                ax.f(StateVector.getX(), StateVector.getY(), StateVector.getVx(), StateVector.getVy()),
                ay.f(StateVector.getX(), StateVector.getY(), StateVector.getVx(), StateVector.getVy()));
        // f(t_i + h/2, w_i + k_i_1/2)
        Vector intermediate1 = StateVector.sum(k_i_1.scale(h/2));
        Vector k_i_2 = new Vector(intermediate1.getVx(),
                intermediate1.getVy(),
                StateVector.getZ(),
                null,
                ax.f(intermediate1.getX(), intermediate1.getY(), intermediate1.getVx(), intermediate1.getVy()),
                ay.f(intermediate1.getX(), intermediate1.getY(), intermediate1.getVx(), intermediate1.getVy()));
        // f(t_i + h/2, w_i + k_i_2/2)
        Vector intermediate2 = StateVector.sum(k_i_2.scale(h/2));
        Vector k_i_3 = new Vector(intermediate2.getVx(),
                intermediate2.getVy(),
                StateVector.getZ(),
                null,
                ax.f(intermediate2.getX(), intermediate2.getY(), intermediate2.getVx(), intermediate2.getVy()),
                ay.f(intermediate2.getX(), intermediate2.getY(), intermediate2.getVx(), intermediate2.getVy()));
        // f(t_i + h_i, w_i + k_i_3)
        Vector intermediate3 = StateVector.sum(k_i_3.scale(h));
        Vector k_i_4 = new Vector(intermediate3.getVx(),
                intermediate3.getVy(),
                StateVector.getZ(),
                null,
                ax.f(intermediate3.getX(), intermediate3.getY(), intermediate3.getVx(), intermediate3.getVy()),
                ay.f(intermediate3.getX(), intermediate3.getY(), intermediate3.getVx(), intermediate3.getVy()));
        Vector sum = k_i_1.sum(k_i_2.scale(2)).sum(k_i_3.scale(2)).sum(k_i_4);
        return StateVector.sum(sum.scale(h/6));
    }

    public double computeAngle(double angle){
        if(angle >270){
            return Math.abs(angle-360);
        }else{
            return Math.abs(angle-180);
        }
    }
}
