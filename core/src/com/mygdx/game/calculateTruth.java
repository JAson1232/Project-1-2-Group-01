package com.mygdx.game;

import java.util.Scanner;

public class calculateTruth {
    public static void main(String[] args) {
        final double g = 9.8;
        final double uk = 0.1;

        Scanner scanner = new Scanner(System.in);
        
        double x = 0;
        double y = 0;
        double vX = 10;
        double vY = 10;
        double t = scanner.nextDouble();
        double distanceX = 0;
        double distanceY = 0;
        double aX;
        double aY;

        aX = -uk*g*(vX/(Math.sqrt((vX*vX)+(vY*vY))));
        aY = -uk*g*(vY/(Math.sqrt((vX*vX)+(vY*vY))));
        distanceX =x+ vX*t + (0.5)*aX*t*t;
        distanceY =y+ vY*t + (0.5)*aY*t*t;

        System.out.println("distanceX: " + distanceX + "; distanceY: " + distanceY);
    }
}
