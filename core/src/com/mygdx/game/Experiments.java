package com.mygdx.game;

import java.io.FileNotFoundException;

public class Experiments {
    static MathFunctions math = new MathFunctions();


    static double h = 0.1;
    static double time = 1.0;

    public static void main(String[] args) throws FileNotFoundException {

        for(double i = 0.0;i < time;i+=h) {
            System.out.println(math.euler(new Vector(0, 0, 0, null, 100, 100), i));
        }
    }
}
