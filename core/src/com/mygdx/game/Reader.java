package com.mygdx.game;

import java.io.*;
import java.util.Scanner;

public class Reader {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("/Users/mymac/Desktop/Project_1-2/src/example_inputfile.txt");
        Scanner scaner = new Scanner(file);
        int count = 0;

        while(scaner.hasNext()) {
            count++;
            String word = scaner.next();

            if(count%3 == 0){
                System.out.println(word);
            }
        }
    }
}