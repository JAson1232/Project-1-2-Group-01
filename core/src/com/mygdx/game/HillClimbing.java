package com.mygdx.game;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Random;

public class HillClimbing {
   HeightFunction fhc  = new HeightFunction();
   PartialDerivative px = new PartialDerivative(fhc);
   MathFunctions matFun = new MathFunctions();
   static double stepSize = 0.1;
   static int counter = 0;
   static int counter1 = 0;

   // perform random shoots and if one stopped in threshold start climbing
   public Vector HillClimbing(Vector position, Vector[][] fieldToVisit, int n, double holeX, double holeY) throws FileNotFoundException {
      double trashHold = 10;
      Vector hole = new Vector(holeX,holeY,0,null,0,0);

      for(int i=0; i< n; i++) {
         //System.out.println("Shot : " + n  );
         double initialVx = randDouble(-5, 5);
         double initialVy = randDouble(-5, 5);

         Vector state =  new Vector(position.getX(), position.getY(), 0,null,initialVx*20, initialVy*20);
         Vector stateCopy = state;
        // System.out.println("state " + state);
         while (hasNotStopped(state, px)) {
           
            if (Math.sqrt(state.getVx() * state.getVx() + state.getVy() * state.getVy()) > 5*20) {
               double v = Math.sqrt(state.getVx() * state.getVx() + state.getVy() * state.getVy());
               state.setVx(5*20 * state.getVx() / v);
               state.setVy(5*20 * state.getVy() / v);
            }
            
            Game game = new Game();
            Ball ball = new Ball(state);

            ball = game.moveBall(ball);
            state = ball.state;
            
            
          //  System.out.println(state);
         // System.out.println(state.distanceTo(hole));
            if (state.distanceTo(hole) <= 10) {
//               System.out.println("state " + state);
//               System.out.println("distance to hole " + state.distanceTo(hole));
               System.out.println("start climb");
//               System.out.println("vectorToClimb " + stateCopy);
               return climb(stateCopy, hole, 0.075);
            }
         }
      }
      return null;
   }

   // hill climbing itself
   public Vector climb(Vector state, Vector hole, double n) throws FileNotFoundException {
      System.out.println("VECTOR BEFORE: " + state);
//      Game game = new Game();
//      game.createField();
      Vector stateCopy = state;
      
      //Ball ball = new Ball(stateCopy);

      while (hasNotStopped(stateCopy, px)) {
         if (Math.sqrt(stateCopy.getVx() * stateCopy.getVx() + stateCopy.getVy() * stateCopy.getVy()) > 5) {
            double v = Math.sqrt(stateCopy.getVx() * stateCopy.getVx() + stateCopy.getVy() * stateCopy.getVy());
            stateCopy.setVx(5 * stateCopy.getVx() / v);
            stateCopy.setVy(5 * stateCopy.getVy() / v);
         }

         //if (Math.sqrt(stateCopy.getVx() * stateCopy.getVx() + stateCopy.getVy() * stateCopy.getVy()) > 5*20) {
         //            double v = Math.sqrt(stateCopy.getVx() * stateCopy.getVx() + stateCopy.getVy() * stateCopy.getVy());
         //            stateCopy.setVx(5*20 * stateCopy.getVx() / v);
         //            stateCopy.setVy(5*20 * stateCopy.getVy() / v);
         //         }
        

//         ball = game.moveBall(ball);
//         stateCopy = ball.state;
         stateCopy = matFun.RK2(stateCopy, 0.1);
         //stateCopy = matFun.euler(stateCopy, 0.1);
        // System.out.println(stateCopy.distanceTo(hole));
      }
      System.out.println("VECTOR AFTER: " + stateCopy);
      //System.out.println(stateCopy.distanceTo(hole));
      //TODO changed from 15 to 0.15 (for game)
      if((int)stateCopy.distanceTo(hole) <= 0.15){
         System.out.println("is in hole 1");
         return state;
      }
      else if (stateCopy.getX() == hole.getX() && stateCopy.getY() == hole.getY() || Math.abs(stateCopy.getX() - hole.getX()) < 0.17 && Math.abs(stateCopy.getY() - hole.getY()) < 0.17) {
         System.out.println("is in hole 2");
         return state;
      }

      double[] neighVx = new double[2];
      neighVx[0] = state.getVx() + n;
      neighVx[1] = state.getVx() - n;

      double[] neighVy = new double[2];
      neighVy[0] = state.getVy() + n;
      neighVy[1] = state.getVy() - n;

      Vector state00 = new Vector(state.getX(), state.getY(), state.getZ(), null, neighVx[0], neighVy[0]);
      //System.out.println("state00 " + state00);
      Vector state00Copy = state00;

      Vector state01 = new Vector(state.getX(), state.getY(), state.getZ(), null, neighVx[0], neighVy[1]);
      //System.out.println("state01 " + state01);
      Vector state01Copy = state01;

      Vector state10 = new Vector(state.getX(), state.getY(), state.getZ(), null, neighVx[1], neighVy[0]);
      //System.out.println("state10 " + state10);
      Vector state10Copy = state10;

      Vector state11 = new Vector(state.getX(), state.getY(), state.getZ(), null, neighVx[1], neighVy[1]);
      //System.out.println("state11 " + state11);
      Vector state11Copy = state11;

      double dist00 = simulate(state00Copy, 0.1, hole);
      //System.out.println("dist00 is " + dist00);
      double dist01 = simulate(state01Copy, 0.1, hole);
      //System.out.println("dist01 is " + dist01);
      double dist10 = simulate(state10Copy, 0.1, hole);
      //System.out.println("dist10 is " + dist10);
      double dist11 = simulate(state11Copy, 0.1, hole);
      //System.out.println("dist11 is " + dist11);

      if (dist00 < dist01 && dist00 < dist10 && dist00 < dist11) {
         //System.out.println("dist00 is smallest");
         return climb(state00, hole, n);
      }
      if (dist01 < dist00 && dist01 < dist10 && dist01 < dist11) {
         //System.out.println("dist01 is smallest");
         return climb(state01, hole, n);
      }
      if (dist10 < dist00 && dist10 < dist01 && dist10 < dist11) {
         //System.out.println("dist10 is smallest");
         return climb(state10, hole, n);
      }
      if (dist11 < dist00 && dist11 < dist01 && dist11 < dist10) {
         //System.out.println("dist11 is smallest");
         return climb(state11, hole, n);
      }
      return state;
   }

   // simulate a shoot and calculate the distance to the hole after
   public double simulate(Vector state, double h, Vector hole) throws FileNotFoundException {
      //counter++;
      Vector stateCopy = state;
      while (hasNotStopped(stateCopy, px)) {
         if (Math.sqrt(stateCopy.getVx() * stateCopy.getVx() + stateCopy.getVy() * stateCopy.getVy()) > 5) {
            double v = Math.sqrt(stateCopy.getVx() * stateCopy.getVx() + stateCopy.getVy() * stateCopy.getVy());
            stateCopy.setVx(5 * stateCopy.getVx() / v);
            stateCopy.setVy(5 * stateCopy.getVy() / v);
         }
         stateCopy = matFun.RK4(stateCopy, h);
      }
      //System.out.println("stateStop " + stateCopy);
      double dist = stateCopy.distanceTo(hole);
      return dist;
   }

   public Vector shoot(Vector state, double h, Vector hole) throws FileNotFoundException {

      while (hasNotStopped(state, px)) {
         if (Math.sqrt(state.getVx() * state.getVx() + state.getVy() * state.getVy()) > 5) {
            double v = Math.sqrt(state.getVx() * state.getVx() + state.getVy() * state.getVy());
            state.setVx(5 * state.getVx() / v);
            state.setVy(5 * state.getVy() / v);
         }
         state = matFun.RK4(state, h);
      }
      //System.out.println("stateStop " + stateCopy);
      return state;
   }


   // stopping condition
   public boolean hasNotStopped(Vector state, PartialDerivative px) throws FileNotFoundException {
      if (!((state.getVx() < stepSize * 5 && state.getVx() > stepSize * -5) && ((state.getVy() < stepSize * 5 && state.getVy() > stepSize * -5)))) {
         return true;
      }
      double x = px.getX(state.getX(), state.getY(), state.getVx(), state.getVy());
      double y = px.getY(state.getX(), state.getY(), state.getVx(), state.getVy());
      double value = x * x + y * y;
      return Field.frictionStatic <= Math.sqrt(value);
   }

   // random double generator
   public static double randDouble(double min, double max) {
      double random = new Random().nextDouble();
      double result = min + (random * (max - min));
      return result;
   }
   public static void main(String[] args) throws FileNotFoundException {
      HillClimbing hc = new HillClimbing();
      Game hcGame = new Game();
      Vector[][] map = hcGame.createField();
      double holeX = hcGame.holeX;
      double holeY = hcGame.holeY;
      Vector pos = new Vector(hcGame.ballX, hcGame.ballY, 0, null, 0, 0);

      long start = System.currentTimeMillis();
      System.out.println(holeX);
      System.out.println(hc.HillClimbing(pos, map, 10000, holeX, holeY));
      System.out.println("here2");
      long finish = System.currentTimeMillis();
      long timeElapsed = finish - start;
      System.out.println("time: " + timeElapsed + " ms");
      System.out.println("number of climbing simulations " + counter);
      System.out.println("number of total simulations " + counter1);

   }


}
