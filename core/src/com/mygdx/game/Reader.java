package com.mygdx.game;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Reader {
    public static ArrayList<String> resultOfCord;
    public Reader(){}

    /**
     * Reads input file text
     * @return String result of read file
     * @throws FileNotFoundException
     */
    public ArrayList<String> compute() throws FileNotFoundException {
        // File file = new File("/Users/mymac/Documents/GitHub/Project-1-2-Group-01/core/src/com/mygdx/game/example_inputfile.txt");
         File file = new File("C:\\Users\\jason\\OneDrive\\Documents\\GitHub\\Project-1-2-Group-01\\core\\src\\com\\mygdx\\game\\example_inputfile.txt");
       // File file = new File("C:\\Users\\maria\\OneDrive\\Documents\\GitHub\\Project-1-2-Group-01\\core\\src\\com\\mygdx\\game\\example_inputfile.txt");
        Scanner scanner = new Scanner(file);

        int count = 0;
        resultOfCord = new ArrayList<>();

        while(scanner.hasNext()) {
            count++;
            String word = scanner.next();

            if(count%3 == 0){
                resultOfCord.add(word);
            }
        }
        return resultOfCord;
    }
}