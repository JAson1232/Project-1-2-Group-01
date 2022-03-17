package com.mygdx.game;

import java.io.FileNotFoundException;

public class Experiments {
    static MathFunctions math = new MathFunctions();
    static double h = 0.0001;
    static double time = 10.0;
    static Vector state =new Vector(-1, -0.5, 0, null, 3, 0);

    public static void main(String[] args) throws FileNotFoundException {
        long startTime = 0;
        startTime= System.nanoTime();

        while(!((state.getVx() < 0.01 && state.getVx() > -0.01) && ((state.getVy() < 0.01 && state.getVy() > -0.01)))) {
            state = math.euler(state, h);
            System.out.println(state);
        }
        long stopTime = System.nanoTime();
        System.out.println("Time: " + (stopTime - startTime)/1000000000.00000000000000);
    }
}
