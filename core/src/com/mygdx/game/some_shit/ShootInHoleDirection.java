package com.mygdx.game.some_shit;

import com.mygdx.game.Vector;

public class ShootInHoleDirection {

    public static Vector shootInHoleDir(double holeX, double holeY, double x, double y){
        double absX = Math.abs(holeX - x);
        double absY = Math.abs(holeY - y);
        double cosO = absX/Math.sqrt(absX*absX + absY*absY);
        double sinO = absY/Math.sqrt(absX*absX + absY*absY);

        double xStart = x;
        double yStart = y;

        double Vx = 5 * cosO;
        double Vy = 5 * sinO;

        Vector toHole = new Vector(xStart, yStart, 0, null, Vx, Vy);
        return toHole;
    }
}
