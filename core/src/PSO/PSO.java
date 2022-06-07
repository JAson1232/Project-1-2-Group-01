package PSO;

import java.io.FileNotFoundException;

import com.mygdx.game.Game;
import com.mygdx.game.HeightFunction;
import com.mygdx.game.HillClimbing;
import com.mygdx.game.MathFunctions;
import com.mygdx.game.PartialDerivative;
import com.mygdx.game.Reader;
import com.mygdx.game.Vector;
import com.mygdx.*;

public class PSO {
    HeightFunction fhc  = new HeightFunction();
    PartialDerivative px = new PartialDerivative(fhc);
    MathFunctions matFun = new MathFunctions();
    
    Particle[] swarm  = new Particle[SWARM_SIZE];
    //swarm = initializeSwarm();
    
    public static int SWARM_SIZE = 100;
    //weight for personal best(pBest) component
    double IND_WEIGHT = 0.2;
    //weight for swarm best component
    double  SOCIAL_WEIGHT = 0.5;
    //weight for Search component
    double SEARCH_WEIGHT = 0.2;
    //# of iteartions of PSO
    int iterationsNumber = 10;

    //index of a bestFit particle
    int bestIndex = 0;
    //position of target/hole for Golf
    Position target = new Position(404,401);

    //array of pBest particles
    Particle[] pBest = new Particle[SWARM_SIZE];
   // fixed Position for all particles
    double xl = 97;
    double yl = 50;
    //updates vx and vy with initial random values
    public  Particle[]  initializeSwarm(){
        Particle p;
        for (int i =0; i<SWARM_SIZE;i++){
            p = new Particle();
            double vx = Math.random();//has to have constraint < |5| for Golf
            double vy = Math.random();//has to have constraint < |5| for Golf
            p.setVelocity(new Velocity(vx,vy));
            p.setLocation(new Position(xl,yl));
            swarm[i]=p;
            pBest[i]=p;
        }
        return swarm;
    }

    /**
     *
     * @param iw individualComponent weight
     * @param sw swarmComponent weight
     * @param i index of Particle
     * @param r1 random1
     * @param r2 random2
     * @return calculated VelocityX for next iteration
     */
    public double calcNewVX(int i,double r1, double r2){
        //individual best component
        double ivx = pBest[i].getVelocity().getVx();
        //Swarm best component
        double svx = swarm[bestIndex].getVelocity().getVx();
        double newVX = SEARCH_WEIGHT*swarm[i].getVelocity().getVx() + IND_WEIGHT*r1*ivx + r2*svx*SOCIAL_WEIGHT;

        return newVX;
    }
    public double calcNewVY(int i, double r1, double r2) {
        double ivy = pBest[i].getVelocity().getVy();
        double svy = swarm[bestIndex].getVelocity().getVy();
        double newVY = SEARCH_WEIGHT*swarm[i].getVelocity().getVy() + IND_WEIGHT*r1*ivy + r2*svy*SOCIAL_WEIGHT;

        return newVY;
    }
 
    public Vector holeInState(Particle[] swarm) throws FileNotFoundException {
        swarm = initializeSwarm();
        Game game = new Game();
        Vector hole = new Vector(404,401,0,null,0,0);
        Vector best = new Vector(0,0,0,null,0,0);
        for(int n=0;n<iterationsNumber;n++){
            for(int i=0;i<SWARM_SIZE;i++){
                Vector state =  new Vector(97, 50, 0,null,swarm[i].getVelocity().getVx(), swarm[i].getVelocity().getVx());
                if(state.distanceTo(hole) < 0.1) {return state;}
                      //copmute new x,y untill ball stops
                while (HillClimbing.hasNotStopped(state, px)) {
                    state = matFun.RK4(state, 0.1);
                   //normalize Vx,Vy
                   if (Math.sqrt(state.getVx() * state.getVx() + state.getVy() * state.getVy()) > 5) {
                     double v = Math.sqrt(state.getVx() * state.getVx() + state.getVy() * state.getVy());
                     state.setVx(5 * state.getVx() / v);
                     state.setVy(5 * state.getVy() / v);
                   }
                   //update personal best
                    if (swarm[i].getFitness(target) > swarm[i].getFitness(target)) {
                       pBest[i] = swarm[i];
                    }
                   //update global best
                    if(swarm[bestIndex].getFitness(target)>swarm[i].getFitness(target)){
                       bestIndex=i;
                    }
                }
                double r1 = Math.random();
                double r2 = Math.random();  
                double vx = calcNewVX(i,r1, r2);
                double vy = calcNewVY(i,r1,r2);
                swarm[i].setVelocity( new Velocity(vx,vy));
                //best.setVy(pBest[i].getVelocity().getVx());
                //best.setVx(pBest[i].getVelocity().getVx());
            }
        }

       return best;
    }
}



