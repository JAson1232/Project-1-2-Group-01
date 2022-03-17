package com.mygdx.game;
import java.util.*;

public class WallLong implements Obstacle{
    private String name = "longWall";
    private Vector startPosition;
    private Vector endPosition;
    private double coefa;
    private double coefb;
    private boolean normal;
    private double radius;
    private List<Wall> obstacles = new ArrayList<Wall>();



    public WallLong(double x1, double y1,double x2,double y2,double radius){
        this.radius = radius;
        startPosition = new Vector(x1,y1,0,null,0,0);
        endPosition = new Vector(x2,y2,0,null,0,0);
        obstacles.add(new Wall(startPosition, radius));
        if (Math.abs(x1-x2)>=Math.abs(y1-y2)){
            normal = true;
            this.coefa = (y2 - y1)/(x2 - x1);
            this.coefb = y1 - coefa*x1;
            for (double i = x1;i <x2;i+=0.1){
                obstacles.add(new Wall(i,((i*coefa)+coefb), radius));
            }
        }
        else{
            normal = false;
            this.coefa = (x2 - x1)/(y2 - y1);
            this.coefb = x1 - coefa*y1;
            for (double i = y1;i <y2;i+=0.1){
                obstacles.add(new Wall((i*coefa+coefb),i, radius));
            }
        }

        obstacles.add(new Wall(endPosition, radius));
    }

    public boolean collision(Vector currentPos) {
        for (int i=0;i<obstacles.size();i++){
            if (obstacles.get(i).collision(currentPos)){
                return true;
            }
        }
        return false;
    }

    public void effect(Vector currentVelocity,Vector currentPos){
        double angle=0;
        double pow = getV(currentVelocity);
        if (pow>0.13) {
            for (int i = 0; i < 360; i += 1) {
                if (currentVelocity.equals(new Vector(adj(pow, i), op(pow, i),0,null,0,0), 1)) {
                    angle = i;
                    break;
                }
            }
            if (normal) {
                pow *= Wall.getAbsorption() * -1;
            } else {
                pow *= Wall.getAbsorption();
            }
            if (coefa!=0){
                if (normal){
                    System.out.println(angle);
                    double angle2 =((Math.asin(Math.abs((1/coefa)/180)))*180)-45;
                    angle = angle2+((angle));
                    System.out.println(angle);
                }
                if (!normal){
                    System.out.println(angle);
                    double angle2 =45-((Math.asin(Math.abs((1/coefa)/180)))*180);
                    angle = angle2+((angle));
                    System.out.println(angle);
                }
            }

            angle *= -1;



            currentVelocity.setX(adj(pow, angle));
            currentVelocity.setY(op(pow, angle));
        }
        else{
            currentVelocity.setX(0.0001);
            currentVelocity.setY(0.0001);
        }

    }

    public String getName() {
        return name;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public Vector getPosition() {
        return null;
    }

    public List<Wall> getObstacles() {
        return obstacles;
    }

    private double getV(Vector a){
        double x = a.getX();
        double y = a.getY();
        return Math.sqrt((Math.pow(x, 2)+Math.pow(y, 2)));
    }
    private double op (double power, double angle){
        return (Math.sin(Math.toRadians(angle))*power);
    }
    private double adj (double power, double angle){
        return (Math.cos(Math.toRadians(angle))*power);
    }
}
