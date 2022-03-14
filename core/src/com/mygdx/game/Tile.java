package com.mygdx.game;

public class Tile {
  //private texture t;
  private boolean hitable = false;
  private String name;

  public String getNmae(){
    return name;
  }
  public boolean getHitable(){
    return hitable;
  }

  public void setName(String name){
    this.name = name;
  }
  

}
