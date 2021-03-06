package com.mygdx.game;

public class Tile {
  private boolean hittable = false;
  private String name;

  /**
   * Getter method
   * @return name
   */
  public String getName(){
    return name;
  }

  /**
   * Whether tile is obstacle that can be hit
   * @return T/F
   */
  public boolean getHittable(){
    return hittable;
  }

  /**
   * Setter method
   * @param name
   */
  public void setName(String name){
    this.name = name;
  }
  

}
