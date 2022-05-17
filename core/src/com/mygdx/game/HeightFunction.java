package com.mygdx.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class HeightFunction implements Function{
    @Override
    /**
     * Returns calculated height value
     * @param x
     * @param y
     * @param vx
     * @param vy
     * @throws FileNotFoundException
     */
    public double f(double x, double y,double vx,double vy) throws FileNotFoundException {
        Expression expression = new ExpressionBuilder(reader())
                .variables("x", "y", "e")
                .build()
                .setVariable("x", x)
                .setVariable("y", y)
                .setVariable("e", Math.E);
        double result = expression.evaluate();
        return 0;

    }

    /**
     * Throw exception if file could not be found
     * @return New String formula
     * @throws FileNotFoundException
     */
    public static String reader() throws FileNotFoundException {
        // File file = new File("/Users/mymac/Documents/GitHub/Project-1-2-Group-01/core/src/com/mygdx/game/example_inputfile.txt");
        // File file = new File("C:\\Users\\jason\\OneDrive\\Documents\\GitHub\\Project-1-2-Group-01\\core\\src\\com\\mygdx\\game\\example_inputfile.txt");
        File file = new File("C:\\Users\\maria\\OneDrive\\Documents\\GitHub\\Project-1-2-Group-01\\core\\src\\com\\mygdx\\game\\example_inputfile.txt");
        Scanner scanner = new Scanner(file);
        int count = 0;
        ArrayList<String> result = new ArrayList<>();

        while(scanner.hasNext()) {
            count++;
            String word = scanner.next();

            if(count%3 == 0){
                result.add(word);
            }
        }
        String formula = result.get(7);
        String newFormula = formula.replace("Math.", "");
        return newFormula;
    }
}
