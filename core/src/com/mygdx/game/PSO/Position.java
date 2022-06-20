package com.mygdx.game.PSO;

public class Position {
  private double x;
  private double y;

  Position(double x,double y){
      this.x =x;
      this.y =y;
  }

  public double getX(){
      return this.x;
  }

  public void setX(double x){
      this.x=x;
  }
  public double getY(){
      return this.y;
  }

  public void setY(double y){
      this.y =y;
  }

  public String toString(){
      return this.getX()+ " , "+this.getY();
  }

}
