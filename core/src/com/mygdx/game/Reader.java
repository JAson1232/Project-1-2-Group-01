package com.mygdx.game;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Reader {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("/Users/mymac/Documents/GitHub/Project-1-2-Group-01/core/src/com/mygdx/game/example_inputfile.txt");
        //File file = new File("C:\\Users\\jason\\OneDrive\\Documents\\GitHub\\Project-1-2-Group-01\\core\\src\\com\\mygdx\\game\\example_inputfile.txt");
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
        System.out.println(result);
    }
}