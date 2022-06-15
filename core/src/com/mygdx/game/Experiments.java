package com.mygdx.game;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.FileNotFoundException;

public class Experiments {
    static MathFunctions math = new MathFunctions();
    static double[] stepSizes = {0.1, 0.01, 0.001};
    static double h = 0.01;
    static double time = 3;
    static double epsilon = 0.0;
    static Vector state = new Vector(0, 0, 0, null, 3, 3);
//IMPORTANT : When running experiments make sure that both in 
//the Field class and the input file the coefficients of friction are 
//what you want, also set height function to 0

    public static void main(String[] args) throws FileNotFoundException {
        for(double stepSize : stepSizes) {
            h = stepSize;
            Vector stateCopy = state.clone();
            long startTime = 0;
            startTime = System.nanoTime();
            for (double j = 0.0; j <= time; j += h) {
                //stateCopy = math.BD2(stateCopy, h); // Off values
                //stateCopy = math.AM2(stateCopy, h);
                //stateCopy = math.AB3(stateCopy, h);
                //stateCopy = math.AB2(stateCopy, h);
                //stateCopy = math.RK4(stateCopy, h);
                //stateCopy = math.RK2(stateCopy, h);
                stateCopy = math.euler(stateCopy, h);
                //System.out.println(stateCopy);
                /*
                if ((epsilon - 0.000001) <= i && i <= (epsilon + 0.00001)) {
                    System.out.println(state.getX());
                    epsilon += 0.1;
                }
                */
            }
            long stopTime = System.nanoTime();
            // System.out.println("Time: " + (stopTime - startTime)/1000000000.000000000);
            System.out.println("Time: " + (stopTime - startTime) / 1000000000.000000000 + " Step Size: " + h + " Final Position: " + stateCopy);
        }
    }
}
