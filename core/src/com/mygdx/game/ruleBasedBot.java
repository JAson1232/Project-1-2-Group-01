package com.mygdx.game;

import java.util.ArrayList;
import java.util.Random;

public class ruleBasedBot {
    private Vector ball;

    private double holeX;
    private double holeY;
    private final double velocity = 5;
    private double initAngle;
    private double curAngle;

    double curvX = 0;
    double curvY = 0;

    boolean inHole;
    boolean inWater;


    ArrayList<Double> distances = new ArrayList<Double>();
    ArrayList<Vector> vectors = new ArrayList<Vector>();

    public ruleBasedBot(){

    }
    public Vector directlyShooting(Vector start, Vector target){

        double ballX = start.getX();
        double ballY = start.getY();
        double holeX = target.getX();
        double holeY = target.getY();

        double dx = holeX - ballX;
        double dy = holeY - ballY;
        double distance =  Math.sqrt(dx*dx+dy*dy);



        double cosA = dx/distance;
        double sinA = dy/distance;

        curvX = velocity*cosA;
        curvY = velocity*sinA;

        changeAnglesStepByStep();

        return new Vector(start.getX(),start.getY(),start.getZ(),null,curvX,curvY);
    }
    public void changeAnglesStepByStep(){
        curAngle = curAngle - 0.01;
    }
    public void distanceCalculation(){
        double dx = holeX-ball.getX();
        double dy = holeY-ball.getY();
        double distance = Math.sqrt(dx*dx+dy*dy);

        distances.add(distance);
        vectors.add(new Vector(ball.getX(),ball.getY(),ball.getZ(),null,ball.getVx(),ball.getVy()));
    }

    public void running(){

        //moveball
        if(curAngle+1==initAngle+360){
            esaySort(distances);

        }
        if(inHole){
            //end
        }else if(inWater){
            //respawn
        }else{
            //ball stoped
            distanceCalculation();
            changeAnglesStepByStep();
        }
        if(distances.get(distances.size()-1)<=0.17){
            //TODO：ball distances to hole less than 0.17
            inHole = true;
        }
    }

//    public static void main(String[] args) {
//
//        Random rand = new Random();
//        ArrayList<Double> sort = new ArrayList<Double>();
//        for (int i = 0; i < 10; i++) {
//            sort.add(rand.nextDouble()*100);
//        }
//        System.out.println("Previous:");
//        for (int i = 0; i < sort.size(); i++) {
//
//            System.out.println(" "+sort.get(i));
//        }
//        esaySort(sort);
//        System.out.println("Later");
//        for (int i = 0; i < sort.size(); i++) {
//
//            System.out.println(" "+sort.get(i));
//        }
//
//    }

    public static void esaySort(ArrayList<Double> distances){
        double cur = 0;
        now: while (true) {
            for (int i = 1; i < distances.size(); i++) {
                if(distances.get(i-1)>distances.get(i)){
                    cur = distances.get(i-1);
                    distances.set(i-1,distances.get(i));
                    distances.set(i,cur);
                    continue now;
                }
            }
            return;
        }

    }

    public ArrayList<Double> getDistances() {
        return distances;
    }

    public double getCurAngle() {
        return curAngle;
    }

    public double getInitAngle() {
        return initAngle;
    }

    public ArrayList<Vector> getVectors() {
        return vectors;
    }

    /****
     *
     * @param vX current vx of the ball
     * @param vY current vy of the ball
     * @param angle the angle u want to plus or minus
     * @return an array with element 0 is new vx and element 1 is new vy
     */
    public double[] changeAngle(double vX,double vY, double angle){
        double vxX = 0;
        double vyY = 0;

        double cosBig = vX/(Math.sqrt(vX*vX+vY*vY));
        double sinBig = vY/(Math.sqrt(vX*vX+vY*vY));
        double pi_Big = Math.acos(cosBig);
        double big = Math.toDegrees(pi_Big);

        double newAngle = big-angle;
        double newRadian = Math.toRadians(newAngle);

        vxX = 5 * Math.cos(newRadian);
        vyY = 5 * Math.sin(newRadian);


        double [] output;
        return output = new double[]{vxX, vyY};
    }
}
