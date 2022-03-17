package com.mygdx.game;

import java.util.Scanner;

public class calculateTruth {
    public static void main(String[] args) {
        final double g = 9.8;
        final double uk = 0.1;

        Scanner scanner = new Scanner(System.in);
        
        //System.out.println("plz enter x");
        double x = 0;
        //System.out.println("plz enter y");
        double y = 0;
        //System.out.println("plz enter Vx");
        double vX = 10;
        //System.out.println("plz enter Vy");
        double vY = 10;
        System.out.println("plz enter step");
        double t = scanner.nextDouble();

        double tSum = 0;

        double distanceX = 0;
        double distanceY = 0;

        double aX;
        double aY;
        
        //aX = (-g*(Math.cos((x-y)/7)/14)-uk*g*(vX/(Math.sqrt((vX*vX)+(vY*vY)))));
        //aY = (-g*(Math.cos((x-y)/7)/14)-uk*g*(vX/(Math.sqrt((vX*vX)+(vY*vY)))));

        aX = -uk*g*(vX/(Math.sqrt((vX*vX)+(vY*vY))));
        aY = -uk*g*(vY/(Math.sqrt((vX*vX)+(vY*vY))));
        
        System.out.println(aX+" "+aY);

        System.out.println("(1/2)*aX: "+(0.5)*aX);

            

        distanceX =x+ vX*t + (0.5)*aX*t*t;
        distanceY =y+ vY*t + (0.5)*aY*t*t;

          
        

        System.out.println("the distanceX would be: "+distanceX+" the distanceY would be: "+distanceY);


    }
}
