package com.mygdx.game.Phase3;

import com.mygdx.game.HillClimbing;
import com.mygdx.game.MathFunctions;
import com.mygdx.game.PSO.GolfPSO;
import com.mygdx.game.Vector;
import java.io.FileNotFoundException;

public class ExperimentsPhase3 {

    //Vector to test
    static Vector start = new Vector(-3,0,0,null,0,0);
    static Vector target = new Vector(4,1,0,null,0,0);

    //bots to test
    static HillClimbing hc = new HillClimbing();
    static ModHillClimbing modHC = new ModHillClimbing();
    static RuleBased ruleMag = new RuleBased();
    static GraphGolf mazeBot;
    static GolfPSO pso = new GolfPSO();

    static {
        try {
            mazeBot = new GraphGolf();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws FileNotFoundException {

        long allTime = 0;
        for (int i = 0; i < 200; i++) {
            long startTime = System.nanoTime();
            startTime = System.nanoTime();
            //System.out.println("startTime" + startTime);

            //ruleMag.constructVector(start, target);
            Vector n = modHC.modHC(start, target);
            //pso.runPSO(start, target);
            //Vector h = hc.HillClimbing(start, target);

            long stopTime = System.nanoTime();
            //System.out.println("stopTime" + stopTime);

            allTime = allTime + (stopTime - startTime);
            Vector after = hc.shoot(n, 0.1);
            System.out.println("AFTER SHOOT " + after);
        }


        System.out.println("counter " + ModHillClimbing.counter1/200);
        System.out.println("FINAL in nano " + allTime);
         System.out.println("FINAL in  sec " + (double) (allTime/1000000000)/200);
         System.out.println("FINAL in  mill " + (double) (allTime/1000000)/200);

    }
}
