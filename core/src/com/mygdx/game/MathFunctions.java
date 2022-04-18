package com.mygdx.game;

import java.io.FileNotFoundException;
import java.lang.Thread.State;

public class MathFunctions {

    public static void main(String[] args) throws FileNotFoundException {
        MathFunctions m = new MathFunctions();
        Vector state = new Vector(0,0,0,null,1.1,0);
        Vector newPosition = m.euler(state,5);
        System.out.println(newPosition.toString());
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
        Vector k_i_2 = new Vector(StateVector.getVx() + 2*h/3,
                                StateVector.getVy() + 2*h/3,
                                StateVector.getZ(),
                                null,
                                ax.f(StateVector.getX() + 2*h/3, StateVector.getY() + 2*h/3, StateVector.getVx() + k_i_1.scale(h*2/3).getVx(), StateVector.getVy() + k_i_1.scale(h*2/3).getVy()),
                                ay.f(StateVector.getX() + 2*h/3, StateVector.getY() + 2*h/3, StateVector.getVx() + k_i_1.scale(h*2/3).getVx(), StateVector.getVy() + k_i_1.scale(h*2/3).getVy()))
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
    Vector RK4(Vector StateVector, double h) throws FileNotFoundException {
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
        Vector k_i_2 = new Vector(StateVector.getVx() + h/2,
                                StateVector.getVy() + h/2,
                                StateVector.getZ(),
                                null,
                                ax.f(StateVector.getX() + h/2, StateVector.getY() + h/2, StateVector.getVx() + k_i_1.scale(0.5).getVx(), StateVector.getVy() + k_i_1.scale(0.5).getVy()),
                                ay.f(StateVector.getX() + h/2, StateVector.getY() + h/2, StateVector.getVx() + k_i_1.scale(0.5).getVx(), StateVector.getVy() + k_i_1.scale(0.5).getVy()));
        // f(t_i + h/2, w_i + k_i_2/2)
        Vector k_i_3 = new Vector(StateVector.getVx() + h/2,
                                StateVector.getVy() + h/2,
                                StateVector.getZ(),
                                null,
                                ax.f(StateVector.getX() + h/2, StateVector.getY() + h/2, StateVector.getVx() + k_i_2.scale(0.5).getVx(), StateVector.getVy() + k_i_2.scale(0.5).getVy()),
                                ay.f(StateVector.getX() + h/2, StateVector.getY() + h/2, StateVector.getVx() + k_i_2.scale(0.5).getVx(), StateVector.getVy() + k_i_2.scale(0.5).getVy()));
        // f(t_i + h_i, w_i + k_i_3)
        Vector k_i_4 = new Vector(StateVector.getVx() + h,
                                StateVector.getVy() + h,
                                StateVector.getZ(),
                                null,
                                ax.f(StateVector.getX() + h, StateVector.getY() + h, StateVector.getVx() + k_i_3.getVx(), StateVector.getVy() + k_i_3.getVy()),
                                ay.f(StateVector.getX() + h, StateVector.getY() + h, StateVector.getVx() + k_i_3.getVx(), StateVector.getVy() + k_i_3.getVy()));
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
