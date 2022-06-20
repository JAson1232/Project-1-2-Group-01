package com.mygdx.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class velocityReader {
    public static void main(String[] args) throws FileNotFoundException {
    }

    public static ArrayList<Float> compute() throws FileNotFoundException {
         File file = new File("/Users/mymac/Documents/GitHub/Project-1-2-Group-01/core/src/com/mygdx/game/VelocityTestTXT.txt");
        // File file = new File("core/src/com/mygdx/game/VelocityTestTXT.txt");
        //File file = new File("C:\\Users\\maria\\OneDrive\\Documents\\GitHub\\Project-1-2-Group-01\\core\\src\\com\\mygdx\\game\\VelocityTestTXT.txt");

        Scanner scanner = new Scanner(file);

        int count = 0;
        ArrayList<Float> result = new ArrayList<>();

        while (scanner.hasNext()) {
            count++;
            String word = scanner.next();

            if (count % 3 == 0) {
                result.add(Float.parseFloat(word));
            }
        }
        return result;
    }
}
