package com.mygdx.game;

import java.io.FileNotFoundException;


public class MathFunctions {
    static Vector w_prev;
    static Vector w_prev2;
    static Vector w_prev3;
    static Vector w_next;
    static Vector w_current;

    public static void main(String[] args) throws FileNotFoundException {
        MathFunctions m = new MathFunctions();
        Vector state = new Vector(0,0,0,null,1,1);
        // Vector newPosition = m.euler2(state, 0.1, 0.0, 0.1);
        // Vector newPosition = m.euler2(state, 0.1, 0.0, 0.2);
        // Vector newPosition = m.euler2(state, 0.01, 0.0, 0.1);
        Vector newPosition = m.RK2_2(state,0.1, 0.0, 0.2);
        System.out.println(newPosition);
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
     * Finds derivative of vector given input
     * @param input
     * @return Vector derivative of given Vector input
     */
    Vector derivFinder(Vector input) throws FileNotFoundException {
        accelerationX ax = new accelerationX();
        accelerationY ay = new accelerationY();
        return new Vector (input.getVx(),
                            input.getVy(),
                            input.getZ(),
                            null,
                            ax.f(input.getX(), input.getY(), input.getVx(), input.getVy()),
                            ay.f(input.getX(), input.getY(), input.getVx(), input.getVy()));
    }

    /**
     * Function for Euler's formula
     * @param StateVector
     * @param h Step size
     * @return New state
     * @throws FileNotFoundException
     */
    Vector euler(Vector StateVector, double h) throws FileNotFoundException {
        return StateVector.sum((derivFinder(StateVector).scale(h)));
    }

    /**
     * Euler's method but for experiments
     * @param StateVector
     * @param h Step size
     * @return New state
     * @throws FileNotFoundException
     */
    Vector euler2(Vector StateVector, double h, double t0, double tf) throws FileNotFoundException {
        int N = (int) (tf/h);
        for(int i = 0; i < N; i++) {
        //for (double j = t0; j <= tf - h; j += h) {
            StateVector = StateVector.sum((derivFinder(StateVector).scale(h)));
        }
        return StateVector;
    }

    /**
     * Function for second-order Runge-Kutta (Ralston's second-order method)
     * @param StateVector
     * @param h Step size
     * @return New state
     * @throws FileNotFoundException
     */
    Vector RK2(Vector StateVector, double h) throws FileNotFoundException {
        // f(t_i, w_i)
        Vector k_i_1 = derivFinder(StateVector);
        // System.out.println(k_i_1);
        // 3f(t_i + 2h/3, w_i + f(t_i, w_i)*2h/3)
        Vector intermediate = StateVector.sum(k_i_1.scale(2/3.0));
        // System.out.println(intermediate);
        Vector k_i_2 = derivFinder(intermediate);
        // System.out.println(k_i_2);
        return StateVector.sum((k_i_1.sum(k_i_2.scale(3))).scale(h/4.0));
    }

    /**
     * RK2 but for experiments
     * @param StateVector
     * @param h Step size
     * @return New state
     * @throws FileNotFoundException
     */
    Vector RK2_2(Vector StateVector, double h, double t0, double tf) throws FileNotFoundException {
        int N = (int) (tf / h);
        for (int i = 0; i < N; i++) {
            Vector k_i_1 = derivFinder(StateVector).scale(h);
            Vector intermediate = StateVector.sum(k_i_1.scale(2.0/3.0));
            Vector k_i_2 = derivFinder(intermediate).scale(h);
            StateVector = StateVector.sum((k_i_1.sum(k_i_2.scale(3.0))).scale(0.25));
        }
        return StateVector;
    }

    /**
     * Function for fourth-order Runge-Kutta
     * @param StateVector
     * @param h Step size
     * @return New state
     * @throws FileNotFoundException
     */
    Vector RK4(Vector StateVector, double h) throws FileNotFoundException {
        HillClimbing.counter1++;
        // f(t_i, w_i)
        Vector k_i_1 = derivFinder(StateVector).scale(h);
        // System.out.println(k_i_1);
        // f(t_i + h/2, w_i + k_i_1/2)
        Vector intermediate1 = StateVector.sum(k_i_1.scale(0.5));
        // System.out.println(intermediate1);
        Vector k_i_2 = derivFinder(intermediate1).scale(h);
        // System.out.println(k_i_2);
        // f(t_i + h/2, w_i + k_i_2/2)
        Vector intermediate2 = StateVector.sum(k_i_2.scale(0.5));
        // System.out.println(intermediate2);
        Vector k_i_3 = derivFinder(intermediate2).scale(h);
        // System.out.println(k_i_3);
        // f(t_i + h_i, w_i + k_i_3)
        Vector intermediate3 = StateVector.sum(k_i_3);
        // System.out.println(intermediate4);
        Vector k_i_4 = derivFinder(intermediate3).scale(h);
        // System.out.println(k_i_4);
        Vector sum = k_i_1.sum(k_i_2.scale(2)).sum(k_i_3.scale(2)).sum(k_i_4);
        // System.out.println(sum);
        return StateVector.sum(sum.scale(1/6.0));
    }

    /**
     * RK4 but for experiments
     * @param StateVector
     * @param h Step size
     * @return New state
     * @throws FileNotFoundException
     */
    Vector RK4_2(Vector StateVector, double h, double t0, double tf) throws FileNotFoundException {
        int N = (int) (tf / h);
        for (int i = 0; i < N; i++) {
            Vector k_i_1 = derivFinder(StateVector).scale(h);
            Vector intermediate1 = StateVector.sum(k_i_1.scale(0.5));
            Vector k_i_2 = derivFinder(intermediate1).scale(h);
            Vector intermediate2 = StateVector.sum(k_i_2.scale(0.5));
            Vector k_i_3 = derivFinder(intermediate2).scale(h);
            Vector intermediate3 = StateVector.sum(k_i_3);
            Vector k_i_4 = derivFinder(intermediate3).scale(h);
            Vector sum = k_i_1.sum(k_i_2.scale(2.0)).sum(k_i_3.scale(2.0)).sum(k_i_4);
            StateVector = StateVector.sum(sum.scale(1.0/6.0));
        }
        return StateVector;
    }

    /**
     * Function for twp-stage Adams-Bashforth
     * @param StateVector
     * @param h Step size
     * @return New state
     * @throws FileNotFoundException
     */
    Vector AB2(Vector StateVector, double h) throws FileNotFoundException {
        HillClimbing.counter1++;
        accelerationX ax = new accelerationX();
        accelerationY ay = new accelerationY();
        // Using RK to bootstrap w_(i - 1) and w_(i - 2)
        if(w_prev2 == null && w_prev == null) {
            // Bootstrap w_(i - 2)
            w_prev2 = StateVector;
            w_prev = RK4(StateVector, h);
            return w_prev;
        }

        Vector first = new Vector(w_prev.getVx(),
                                    w_prev.getVy(),
                                    w_prev.getZ(),
                                    null,
                                    ax.f(w_prev.getX(), w_prev.getY(), w_prev.getVx(), w_prev.getVy()),
                                    ay.f(w_prev.getX(), w_prev.getY(), w_prev.getVx(), w_prev.getVy()))
                                    .scale(3);

        Vector second = new Vector(w_prev2.getVx(),
                                    w_prev2.getVy(),
                                    w_prev2.getZ(),
                                    null,
                                    ax.f(w_prev2.getX(), w_prev2.getY(), w_prev2.getVx(), w_prev2.getVy()),
                                    ay.f(w_prev2.getX(), w_prev2.getY(), w_prev2.getVx(), w_prev2.getVy()));

        Vector sum = first.subtract(second).scale(h/2);
        w_prev2 = w_prev;
        w_prev = w_prev.sum(sum);
        return w_prev;
    }

    /**
     * Function for three-stage Adams-Bashforth
     * @param StateVector
     * @param h Step size
     * @return New state
     * @throws FileNotFoundException
     */
    Vector AB3(Vector StateVector, double h) throws FileNotFoundException {
        HillClimbing.counter1++;
        accelerationX ax = new accelerationX();
        accelerationY ay = new accelerationY();
        // Using RK to bootstrap w_(i - 1), w_(i - 2), and w_(i - 3)
        if(w_prev3 == null && w_prev2 == null && w_prev == null) {
            // Bootstrap w_(i - 2)
            w_prev3 = StateVector;
            w_prev2 = RK4(w_prev3, h);
            return w_prev2;
        } else if(w_prev == null) {
            w_prev = RK4(w_prev2, h);
            return w_prev;
        }

        Vector first = new Vector(w_prev.getVx(),
                                    w_prev.getVy(),
                                    w_prev.getZ(),
                                    null,
                                    ax.f(w_prev.getX(), w_prev.getY(), w_prev.getVx(), w_prev.getVy()),
                                    ay.f(w_prev.getX(), w_prev.getY(), w_prev.getVx(), w_prev.getVy()))
                                    .scale(23);

        Vector second = new Vector(w_prev2.getVx(),
                                    w_prev2.getVy(),
                                    w_prev2.getZ(),
                                    null,
                                    ax.f(w_prev2.getX(), w_prev2.getY(), w_prev2.getVx(), w_prev2.getVy()),
                                    ay.f(w_prev2.getX(), w_prev2.getY(), w_prev2.getVx(), w_prev2.getVy()))
                                    .scale(16);

        Vector third = new Vector(w_prev3.getVx(),
                                    w_prev3.getVy(),
                                    w_prev3.getZ(),
                                    null,
                                    ax.f(w_prev3.getX(), w_prev3.getY(), w_prev3.getVx(), w_prev3.getVy()),
                                    ay.f(w_prev3.getX(), w_prev3.getY(), w_prev3.getVx(), w_prev3.getVy()))
                                    .scale(5);

        Vector sum = (first.subtract(second).sum(third)).scale(h/12);
        w_prev3 = w_prev2;
        w_prev2 = w_prev;
        w_prev = w_prev.sum(sum);
        return w_prev;
    }

    /**
     * Function for two-stage Adams-Moulton
     * @param StateVector
     * @param h Step size
     * @return New state
     * @throws FileNotFoundException
     */
    Vector AM2(Vector StateVector, double h) throws FileNotFoundException {
        HillClimbing.counter1++;
        accelerationX ax = new accelerationX();
        accelerationY ay = new accelerationY();
        // Using RK to bootstrap w_(i - 1)
        if(w_prev == null && w_current == null) {
            // Bootstrap w_(i - 1), w_i
            w_prev = StateVector;
            w_current = RK4(w_prev, h);  
            return w_current;
        }

        w_next = RK4(w_current, h);
        
        Vector first = new Vector(w_next.getVx(),
                                    w_next.getVy(),
                                    w_next.getZ(),
                                    null,
                                    ax.f(w_next.getX(), w_next.getY(), w_next.getVx(), w_next.getVy()),
                                    ay.f(w_next.getX(), w_next.getY(), w_next.getVx(), w_next.getVy()))
                                    .scale(5);

        Vector second = new Vector(w_current.getVx(),
                                    w_current.getVy(),
                                    w_current.getZ(),
                                    null,
                                    ax.f(w_current.getX(), w_current.getY(), w_current.getVx(), w_current.getVy()),
                                    ay.f(w_current.getX(), w_current.getY(), w_current.getVx(), w_current.getVy()))
                                    .scale(8);

        Vector third = new Vector(w_prev.getVx(),
                                    w_prev.getVy(),
                                    w_prev.getZ(),
                                    null,
                                    ax.f(w_prev.getX(), w_prev.getY(), w_prev.getVx(), w_prev.getVy()),
                                    ay.f(w_prev.getX(), w_prev.getY(), w_prev.getVx(), w_prev.getVy()));

        Vector sum = first.sum(second).subtract(third).scale(h/12);
        w_prev = w_current;
        w_current = StateVector.sum(sum);
        return w_current;
    }

    /**
     * Function for two-stage backward-difference
     * @param StateVector
     * @param h Step size
     * @return New state
     * @throws FileNotFoundException
     */
    Vector BD2(Vector StateVector, double h) throws FileNotFoundException {
        HillClimbing.counter1++;
        accelerationX ax = new accelerationX();
        accelerationY ay = new accelerationY();
        // Using RK to bootstrap w_(i - 1)
        if(w_prev == null && w_current == null) {
            // Bootstrap w_(i - 1), w_i
            w_prev = StateVector;
            w_current = RK4(StateVector, h);
            return w_current;
        }

        // Bootstrap w_(i + 1)
        w_next = RK4(w_current, h);
        
        Vector first = new Vector(w_next.getVx(),
                                    w_next.getVy(),
                                    w_next.getZ(),
                                    null,
                                    ax.f(w_next.getX(), w_next.getY(), w_next.getVx(), w_next.getVy()),
                                    ay.f(w_next.getX(), w_next.getY(), w_next.getVx(), w_next.getVy()))
                                    .scale(2*h/3);

        w_next = w_current.scale(4/3).subtract(w_prev.scale(1/3)).sum(first);
        w_prev = w_current;
        w_current = w_next;
        return w_next;
    }

    public double computeAngle(double angle){
        if(angle >270){
            return Math.abs(angle-360);
        }else{
            return Math.abs(angle-180);
        }
    }
}
