package com.mygdx.game;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.FileNotFoundException;

public class Experiments {
    static MathFunctions math = new MathFunctions();
    static double h = 0.0001 ;
    static double time = 3;
    static Vector state = new Vector(0, 0, 0, null, 3, 3);
//IMPORTANT : When running experiments make sure that both in 
//the Field class and the input file the coefficients of friction are 
//what you want, also set height function to 0

    public static void main(String[] args) throws FileNotFoundException {

        
        try {
        FileWriter myWriter = new FileWriter("EulerComputationTimesVsStepSize.txt");

        
        long startTime = 0;
        startTime = System.nanoTime();
        for(double i = 0.0; i < time+h; i+=h) {
            // state = math.BD2(state, h); // Off values
            //state = math.AM2(state, h);
            //state = math.AB3(state, h);
            //state = math.AB2(state, h);
            state = math.RK4(state,h);
            //state = math.RK2(state, h);
            //state = math.euler(state, h);
            //System.out.println(state);
        }
        long stopTime = System.nanoTime();
        // System.out.println("Time: " + (stopTime - startTime)/1000000000.000000000);
        System.out.println("Time: " + (stopTime - startTime)/1000000000.000000000 + " Step Size: "+ h + " Final Position: " + state);

        myWriter.close();
       
            
            
            
           
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }        
}
