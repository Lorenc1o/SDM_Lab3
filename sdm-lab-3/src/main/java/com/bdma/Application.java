package com.bdma;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.bdma.ABOX.createABOX;
import static com.bdma.PreProcessing.preProcessData;
import static com.bdma.TBOX.createTBOX;

public class Application {

    public static void main(String[] args) throws IOException {

        printInstructions();
        // Enter data using BufferReader
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        // Reading data using readLine
        int serviceNumber = Integer.parseInt(reader.readLine());

        switch (serviceNumber) {
            case 1:
                preProcessData();
                break;
            case 2:
                createTBOX();
                break;
            case 3:
                createABOX();
                break;
        }
        System.out.println("Good Bye");

    }

    private static void printInstructions() {
        System.out.println("Please enter the number according to the application you want to use");
        System.out.println("1 - PreProcessing");
        System.out.println("2 - Create TBOX");
        System.out.println("3 - Create ABOX");
        System.out.println("else - exit");
        System.out.print("\nPlease enter the number here: ");
    }
}
