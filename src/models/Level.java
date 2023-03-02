package models;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Level {

    private int height;
    private int width;
    private char shape;
    private ArrayList<Character> configuration = new ArrayList<Character>();

    public Level(int l) {

        try {
            File levelFile = new File("levels/level" + l + ".nrg");
            Scanner sc = new Scanner(levelFile);
            int i = 0;
            while (sc.hasNext()) {
                String line = sc.next();
                switch (i) {
                    case 0:
                        this.height = Integer.parseInt(line);
                    case 1:
                        this.width = Integer.parseInt(line);
                    case 2:
                        this.shape = line.charAt(0);
                    default:
                        this.configuration.add(line.charAt(0));
                        i++;
                }
            }
            /*
             * System.out.println(height);
             * System.out.println(width);
             * System.out.println(shape);
             * System.out.println(configuration);
             */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public ArrayList getConfig() {
        return this.configuration;
    }
}
