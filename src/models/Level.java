package models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Level {

    private int height;
    private int width;
    private char shape;

    public Level(int l) {
        try {
            File levelFile = new File("../../levels/level" + l + ".nrg");
            // File levelFile = new File("/src/ReadMe.txt");
            FileInputStream f = new FileInputStream(levelFile);
            int r = 0;
            while ((r = f.read()) != -1) {
                System.out.print((char) r); // prints the content of the file
            }
        }

        catch (Exception e) {
            e.printStackTrace();

        }
    }
}
