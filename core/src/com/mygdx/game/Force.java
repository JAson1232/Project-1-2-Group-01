package com.mygdx.game;

public class Force {
  private String name;
  private double power;

  /**
   * Getter method
   * @return name
   */
  public String getName(){
    return name;
  }

  /**
   * Getter method
   * @return power
   */
  public double getPower(){
    return power;
  }

  /**
   * Setter method
   * @param name
   */
  public void setName(String name){
    this.name = name;
  }

  /**
   * Setter method
   * @param power
   */
  public void setPower(double p){
    power = p;
  }

}
