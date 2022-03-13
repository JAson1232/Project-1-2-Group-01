package com.mygdx.game;

public class MathFunctions {



    float func(float x, float y)
    {
        return (x + y + x * y);
    }

    // Function for Euler formula
    void euler(float x0, float y, float h, float x)
    {
        float temp = -0;

        // Iterating till the point at which we
        // need approximation
        while (x0 < x) {
            temp = y;
            y = y + h * func(x0, y);
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
