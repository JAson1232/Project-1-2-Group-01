package com.mygdx.game;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.FileNotFoundException;

public class Experiments {
    static MathFunctions math = new MathFunctions();
    static double[] stepSizes = {0.01};
    static double h;
    static double time = 0.1;
    static double epsilon = 0.0;
//IMPORTANT : When running experiments make sure that both in 
//the Field class and the input file the coefficients of friction are 
//what you want, also set height function to 0

    public static void main(String[] args) throws FileNotFoundException {
        for(double stepSize : stepSizes) {
            Vector state = new Vector(0.0, 0.0, 0.0, null, 1.0, 1.0);
            h = stepSize;
            long startTime = 0;
            startTime = System.nanoTime();

            state = math.euler2(state, h, 0, time);

            for (double j = 0.0; j <= time - h; j += h) {
                //state = math.BD2(state, h); // Off values
                //state = math.AM2(state, h);
                //state = math.AB3(state, h);
                //state = math.AB2(state, h);
                //state = math.RK4(state, h);
                //state = math.RK2(state, h);
                //state = math.euler(state, h);
                //System.out.println(j);
                /*
                if ((epsilon - 0.000001) <= i && i <= (epsilon + 0.00001)) {
                    System.out.println(state.getX());
                    epsilon += 0.1;
                }
                */
            }
            long stopTime = System.nanoTime();
            // System.out.println("Time: " + (stopTime - startTime)/1000000000.000000000);
            System.out.println("Time: " + (stopTime - startTime) / 1000000000.000000000 + " Step Size: " + h + " Final Position: " + state);
        }
    }
}
