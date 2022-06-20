package com.mygdx.game.PSO;

import java.io.FileNotFoundException;

import com.mygdx.game.Ball;
import com.mygdx.game.Game;
import com.mygdx.game.MathFunctions;
import com.mygdx.game.PartialDerivative;
import com.mygdx.game.PartialDerivative;
import com.mygdx.game.HeightFunction;
import com.mygdx.game.HillClimbing;
import com.mygdx.game.MathFunctions;
import com.mygdx.game.Vector;

public class GolfPSO {

  public MathFunctions math = new MathFunctions();
  HeightFunction fhc  = new HeightFunction();
  PartialDerivative px = new PartialDerivative(fhc);
  HillClimbing hc = new HillClimbing();

  public static int SWARM_SIZE = 30;
  Particle[] swarm  = new Particle[SWARM_SIZE];
  //weight for personal best(pBest) component
  double IND_WEIGHT = 2.0;
  //weight for swarm best component
  double  SOCIAL_WEIGHT = 2.0;
  //weight for Search component
  double SEARCH_WEIGHT = 0.8;
  //# of iterations of PSO
  int iterationsNumber = 20;
  //best swarm fitness Value
  static double swarmBestFitness;
  //position of swarmBestPrtcl
  static Position swarmBestPosition;

  Vector stateCopy = new Vector(0,0,0,null,0,0);
  Vector state = new Vector(0,0,0,null,0,0);

  //index of a bestFit particle
  //public static  Particle gBest;
  //position of target/hole for Golf
  //Position target = new Position(0.0,0.0);//(404,401);

 // fixed Position for all particles
  //double xl = 97;
  //double yl = 50;
  //initializes swarm of particles with  random values
  //sets pBest vit values equal to initial random
  public  Particle[]  initializeSwarm(){
      Particle p;
      for (int i =0; i<SWARM_SIZE;i++){
          p = new Particle();
          double vx = Math.random();
          double vy = Math.random();
          double xl = Math.random()*10-5;
          double yl = Math.random()*10-5;
          p.setVelocity(new Velocity(vx,vy));
          p.setLocation(new Position(xl,yl));
          swarm[i]=p;
          //System.out.println(p.getLocation() +"/"+ p.getVelocity());
      }

      return swarm;
  }
  /**
   * 
   * @param p Position of a Particle
   * @param v Velocity of a Particle
   * @return ball.state vector
   */
  public Vector particleToBall(Position p, Velocity v){
    Vector state = new Vector(x, y, z, t, p.getX(), p.getY());
    return state;
  }
  /**
   * 
   * @param state Ball state
   * @return Position of Particle in search space for V
   */
  public Position ballToParticle(Vector state){
    Position location = new Position(state.getVx(), state.getVy());
    return location;
  }

  //initialize prtcleBestPosition field with random positions of particle
  public void initializeParticleBestPosition(){
      for(int i=0; i<SWARM_SIZE;i++){
          swarm[i].setPrtclBestPosition(swarm[i].getLocation());
      }
  }

  //initialize bestPrtclFitness
  public void initializeBestPrtclFitness(){
      for(int i=0;i<SWARM_SIZE;i++){
          swarm[i].setBestPrtclFitness(swarm[i].getFitness(-3,0));
      }
    }
    
    
    //initialise gBest with best fit particle  
    public void initializeGBest(){
        swarmBestFitness = swarm[0].getFitness(-3,0);
        swarmBestPosition = swarm[0].getLocation();
        //System.out.println("initial fitness value for ball at (-3,0)"+swarmBestFitness);
        //System.out.println("initial swarm Best Position"+ swarmBestPosition);

      //for (int i=1; i<SWARM_SIZE;i++){
      //    if(swarmBestFitness > swarm[i].getFitness(-3,0)){
      //        swarmBestFitness= swarm[i].getFitness(-3,0);
      //        swarmBestPosition = swarm[i].getLocation();
      //    }
      //}
  }


  /**
   * 
   * @param i index in a Particle[] swarm
   * calculates new velocity for particle 
   * updates position of a particle with new velocity 
   */
  public void updatePosition(int i){
      double r1 = Math.random();
      double r2 = Math.random(); 

      double currentY =swarm[i].getLocation().getY();
      double indBestY = swarm[i].getPrtclBestPosition().getY();
      double swarmBestY = swarmBestPosition.getY();
      double newVY = SEARCH_WEIGHT*swarm[i].getLocation().getY() 
                                                                   + IND_WEIGHT*r1*(indBestY-currentY)
                                                                   + r2*(swarmBestY-currentY)*SOCIAL_WEIGHT;
      //System.out.println("5.newVY inside updatePosition -" + newVY);
      double currentX =swarm[i].getLocation().getX();
      double indBestX = swarm[i].getPrtclBestPosition().getX();
      double swarmBestX = swarmBestPosition.getX();
      double newVX = SEARCH_WEIGHT*swarm[i].getLocation().getX()
                                                                   + IND_WEIGHT*r1*(indBestX-currentX)
                                                                   + r2*(swarmBestX-currentX)*SOCIAL_WEIGHT;

      double newX = (swarm[i].getLocation().getX() + newVX);
      double newY = (swarm[i].getLocation().getY() + newVY);

      //keep velocity in bounds
      if(newX>5.0) {
          newX=5.0;  
      }else if(newX<(-5.0)){
          newX=-5.0;
      }
      if(newY>5.0){
          newY=5.0;
      }else if(newY<(-5.0)){
          newY=(-5.0);
      }

      swarm[i].setLocation(new Position(newX, newY));

  }


  //updates individual best Position and Fitness value for particle 
  public void updateIndBest(int i,Vector s){
      if (swarm[i].getBestPrtcleFitness() > swarm[i].getFitness(s.getX(),s.getY())) {
          swarm[i].setBestPrtclFitness(swarm[i].getFitness(s.getX(),s.getY()));
          swarm[i].setPrtclBestPosition(swarm[i].getLocation());
          
      }
  }


  //updates global best for swarm
  public void updateSwarmBest(int i,Vector st){
      if(swarmBestFitness > swarm[i].getFitness(st.getX(),st.getY())){
          swarmBestFitness = swarm[i].getFitness(st.getX(),st.getY());
          swarmBestPosition = swarm[i].getLocation();
         
      }
  }

  

  /**
   * @param
   * 
   * @return
  */
    public Position run(){
        swarm = initializeSwarm();
        initializeGBest();
        initializeBestPrtclFitness();
        initializeParticleBestPosition();
    
        for(int l=0;l<iterationsNumber;l++){
            for(int k=0;k<SWARM_SIZE;k++){
                //calculate new Position of particle
                updatePosition(k);
                //update ind best
                updateIndBest(k);
                //update global best
                updateSwarmBest( k);
            }
            System.out.println(swarmBestFitness);
            System.out.println(swarmBestPosition);
            System.out.println("---");
        }
        return swarmBestPosition;
    }


  /**
   * 
   * @param ball 
   * @return best found Vector state of a ball hole-in-one 
   * @throws FileNotFoundException
   */
  public Vector runPSO(Ball ball) throws FileNotFoundException{
    //state = ball.getState();
    //1.initialize swarm of particles
    swarm = initializeSwarm();

    //2. initialize indBest, swarmBest
    initializeBestPrtclFitness();
    initializeParticleBestPosition();
    initializeGBest();

    for(int n=0;n<iterationsNumber;n++){
        //System.out.println("new iteration ----------");
        //3. iterate through swarm of balls
        for(int i=0;i<SWARM_SIZE;i++){
            //create state of a Ball1 
            state = ball.getState();
            state.setVx(swarm[i].getLocation().getX()); 
            state.setVy(swarm[i].getLocation().getY()); 
            stateCopy=state;
            
            
            //4. wile loop with stopCondition for ball
            //System.out.println(state);
            
            //while (ball.ballIsMoving()){
                while (hc.hasNotStopped(state,px)){
                    
                    if (Math.sqrt(state.getVx() * state.getVx() + state.getVy() * state.getVy()) > 5) {
                        double v = Math.sqrt(state.getVx() * state.getVx() + state.getVy() * state.getVy());
                        state.setVx(5 * state.getVx() / v);
                        state.setVy(5 * state.getVy() / v);
                    }
                    //5.state = RK4(state, h)
                    state = math.RK4(state,0.1);
                    //System.out.println(swarm[i].getFitness(state.getX(),state.getY()));
                }
                //System.out.println("current particle fitness  -"+swarm[i].getFitness(state.getX(),state.getY()));
            // calculate Particle fitness
            
            //swarm[i].getFitness(state.getX(),state.getY()); 
            //6.updatePosition
            //7. updateIndBest, updateSwarmBest
            updateIndBest(i,state);
            updateSwarmBest(i,state); 
            updatePosition(i); 
            //System.out.println("------------"); 
            //System.out.println(swarmBestFitness);
        }
    }
    //set state of a ball to a starting Position and vx,vy to found hole-in-one-shot values 
    state.setVx(swarmBestPosition.getX());
    state.setVy(swarmBestPosition.getY());
    state.setX(-3);
    state.setY(0);
    System.out.println(swarmBestFitness + "///" + swarmBestPosition);  
    System.out.println(state);
    return state;
  }

  public static void main(String[] args) throws FileNotFoundException {
      GolfPSO pso = new GolfPSO();
      Vector state = new Vector(-3.0,0,0,null,0,0);
      Ball ball = new Ball(state);
      pso.runPSO(ball);
      //pso.run();
      //Particle p = new Particle();
      //p.setLocation(new Position(01.7152964891014881 , -0.2774191279487441
      //System.out.println(p.dropWave(1.7152964891014881 , -0.2774191279487441));
      //System.out.println(p.getFitness());

  }
}

