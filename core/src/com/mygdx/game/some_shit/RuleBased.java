package com.mygdx.game.some_shit;

import com.mygdx.game.Field;
import com.mygdx.game.HillClimbing;
import com.mygdx.game.Vector;
import com.mygdx.game.accelerationX;

public class RuleBased {

    public Vector constructVector(Vector start, Vector target){
        double distanceFromStartToTarget = start.distanceTo(target);
        //System.out.println("distanceFromStartToTarget " + distanceFromStartToTarget);

        double constant = Math.sqrt(2* 9.8 * Field.frictionKinetic * distanceFromStartToTarget);
        //System.out.println("constant " + constant);

        Vector toTarget = shootInHoleDir(target.getX(), target.getY(), start.getX(), start.getY());
        //System.out.println("toTarget " + toTarget);

        Vector toTargetUnite = uniteVector(toTarget);
        //System.out.println("toTargetUnite " + toTargetUnite);

        Vector finalVector = finalVector(toTargetUnite, constant);
        //System.out.println("finalVector " + finalVector);

        return finalVector;
    }


    ////////helper methods//////
    public Vector shootInHoleDir(double holeX, double holeY, double x, double y){
        double absX = Math.abs(holeX - x);
        double absY = Math.abs(holeY - y);

        double cosO = (holeX-x)/Math.sqrt(absX*absX + absY*absY);
        double sinO = (holeY-y)/Math.sqrt(absX*absX + absY*absY);

        double xStart = x;
        double yStart = y;

        double Vx = 5 * cosO;
        double Vy = 5 * sinO;

        Vector toHole = new Vector(xStart, yStart, 0, null, Vx, Vy);
        return toHole;
    }

    public Vector uniteVector(Vector toTarget){
        double magnitude = Math.sqrt(toTarget.getVx()*toTarget.getVx() + toTarget.getVy()*toTarget.getVy());
        Vector uniteVector = new Vector(toTarget.getX(), toTarget.getY(), 0, null, toTarget.getVx()/magnitude, toTarget.getVy()/magnitude);
        return uniteVector;
    }

    public Vector finalVector(Vector uniteVector, double constant){
        Vector finalVector = new Vector(uniteVector.getX(), uniteVector.getY(), 0, null, uniteVector.getVx()*constant, uniteVector.getVy()*constant);
        return finalVector;
    }
}
