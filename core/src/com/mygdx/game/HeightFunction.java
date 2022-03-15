package com.mygdx.game;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class HeightFunction implements Function{
    @Override
    public double f(double x, double y,double vx,double vy) throws FileNotFoundException {
        Expression expression = new ExpressionBuilder(reader())
                .variables("x", "y")
                .build()
                .setVariable("x", x)
                .setVariable("y", y);

        double result = expression.evaluate();

        return result;
    }

    public static String reader() throws FileNotFoundException {
        File file = new File("/Users/mymac/Documents/GitHub/Project-1-2-Group-01/core/src/com/mygdx/game/example_inputfile.txt");
        Scanner scaner = new Scanner(file);
        int count = 0;
        ArrayList<String> result = new ArrayList<>();

        while(scaner.hasNext()) {
            count++;
            String word = scaner.next();

            if(count%3 == 0){
                result.add(word);
            }
        }
        String formula = result.get(7);
        String newFormula = formula.replace("Math.", "");
        return newFormula;
    }
}
