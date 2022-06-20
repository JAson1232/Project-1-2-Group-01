package com.mygdx.game.PSO;

public class Particle {
  private Velocity velocity;
  private Position location;
  private double fitness;
  private Position prtclBestPosition;
  private double bestPrtclFitness;
  

  


  // returns value of objective function at Position (x,y)
  public double  getFitness(double x, double y){
      //double x = this.location.getX();
      //double y = this.location.getY();
      fitness = eucledianDistance(x, y);
      return fitness;
  }

  //math function from Tin
  public double dropWave(double x, double y){
      double upper = 1 + Math.cos(12 * Math.sqrt(x*x + y*y));
      double lower = 0.5 * (x*x + y*y) + 2;
      return -upper / lower;
  }
  //Eucledian distance to hole
  public double eucledianDistance(double x, double y){
    return Math.sqrt(Math.pow(x-(4),2)+Math.pow(y-(1),2));
  }

  public Position getLocation(){
      return location;
  }
  public void setLocation(Position location){
      this.location = location;
  }

  public Velocity getVelocity(){
      return this.velocity;
  }

  public void setVelocity(Velocity v){
      this.velocity=v;
  }

  public Position getPrtclBestPosition(){
      return this.prtclBestPosition;
  }

  public void setPrtclBestPosition(Position best){
      this.prtclBestPosition = best;
  }

  public double getBestPrtcleFitness(){
      return this.bestPrtclFitness;
  }

  public void setBestPrtclFitness(double best){
      this.bestPrtclFitness = best;
  }
  
}
