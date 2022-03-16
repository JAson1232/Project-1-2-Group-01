package com.mygdx.game;

import java.io.FileNotFoundException;

public class Experiments {
    static MathFunctions math = new MathFunctions();


    static double h = 0.01;
    static double time = 10.0;

    static Vector state =new Vector(0, 0, 0, null, 10, 10);

    public static void main(String[] args) throws FileNotFoundException {

        for(double i = 0.0;i < time+h;i+=h) {
            state = math.euler(state,h);
            System.out.println(state);
        }
    }
}
