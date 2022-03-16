package com.mygdx.game;

import java.io.FileNotFoundException;

public class Experiments {
    static MathFunctions math = new MathFunctions();


    static double h = 1;
    static double time = 10.0;

    static Vector state =new Vector(0, 0, 0, null, -5, 1);

    public static void main(String[] args) throws FileNotFoundException {

        while((state.getVx() < 0.1 && state.getVx() > -0.1) && (state.getVy() < 0.1 && state.getVy() > -0.1)) {
            state = math.euler(state, h);
            System.out.println(state);
        }

    }
}
