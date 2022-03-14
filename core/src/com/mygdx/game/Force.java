package com.mygdx.game;

public class Force {
  private String name;
  private double power;

  private int[] directions = new int[2];

  public String getName(){
    return name;
  }

  public double getPower(){
    return power;
  }

  public void setName(String name){
    this.name = name;
  }

  public void setPower(double p){
    power = p;
  }

}
