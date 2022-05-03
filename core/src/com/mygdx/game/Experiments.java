package com.mygdx.game;

import java.io.FileNotFoundException;

public class Experiments {
    static MathFunctions math = new MathFunctions();
    static double h = 0.01;
    static double time = 10;
  static Vector state = new Vector(0, 0, 0, null, 10, 10);
    public static void main(String[] args) throws FileNotFoundException {
        long startTime = 0;
        startTime = System.nanoTime();
        for(double i = 0.0; i < time+h; i+=h) {
            state = math.RK4(state,h);
            System.out.println(state);
        }
        long stopTime = System.nanoTime();
        System.out.println("Time: " + (stopTime - startTime)/1000000000.000000000);
    }        
}
