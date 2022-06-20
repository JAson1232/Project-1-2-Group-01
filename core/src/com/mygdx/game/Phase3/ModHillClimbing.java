package com.mygdx.game.Phase3;

import com.mygdx.game.Game;
import com.mygdx.game.HillClimbing;
import com.mygdx.game.Vector;

import java.io.FileNotFoundException;

public class ModHillClimbing {

    HillClimbing hc = new HillClimbing();
    RuleBased rule = new RuleBased();
    public static int counter1 = 0;

    public Vector modHC(Vector start, Vector target) throws FileNotFoundException {

        Vector toClimb = rule.constructVector(start, target);
        Vector climb = hc.climb(toClimb, target, 0.075);

        return climb;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Game hcGame = new Game();
        Vector[][] map = hcGame.createField();
        ModHillClimbing mod = new ModHillClimbing();

        Vector pos = new Vector(-3, 0, 0, null, 0, 0);
        Vector hole = new Vector(4,1,0,null,0,0);

        System.out.println(mod.modHC(pos, hole));

    }
}
