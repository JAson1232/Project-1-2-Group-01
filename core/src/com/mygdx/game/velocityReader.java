package com.mygdx.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class velocityReader {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("/Users/mymac/Documents/GitHub/Project-1-2-Group-01/core/src/com/mygdx/game/VelocityTestTXT.txt");
        //File file = new File("C:\\Users\\jason\\OneDrive\\Documents\\GitHub\\Project-1-2-Group-01\\core\\src\\com\\mygdx\\game\\VelocityTestTXT.txt");
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
