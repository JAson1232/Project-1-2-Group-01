package com.mygdx.game;

public class MathFunctions {

    public static void main(String[] args) {
        MathFunctions m = new MathFunctions();
        m.euler(0,1,0.01f, 0);
    }




    // Function for Euler formula
    void  euler(float x0, float y, float h, float x)
    {
        float temp = -0;
        InputFunction func = new InputFunction();
        // Iterating till the point at which we
        // need approximation
        while (x0 < x) {
            temp = y;
            y = (float) (y + h * func.f(x0, y));
            x0 = x0 + h;
        }
        // Printing approximation
        System.out.println("Approximate solution at x = "
                + x + " is " + y);
    }

    public int calculateDx(){
        return 0;
    }


}
