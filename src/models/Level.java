package models;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Level {

    private int height;
    private int width;
    private char shape;
    private ArrayList<Character> configuration = new ArrayList<Character>();
    private ArrayList<Tile> tiles_config = new ArrayList<Tile>();

    public Level(int l) {
        try {
            File levelFile = new File("src/models/levels/level" + l + ".nrg");
            Scanner sc = new Scanner(levelFile);

            this.height = sc.nextInt();
            this.width = sc.nextInt();
            this.shape = sc.next().charAt(0);

            while (sc.hasNext()) {
                this.configuration.add(sc.next().charAt(0));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        int id = 0;

        for (char c : this.configuration) {
            ArrayList<Integer> tmp_edge = new ArrayList<Integer>();
            Role tmp_r;

            switch (c) {
                case 'S':
                    tmp_r = Role.SOURCE;
                    break;
                case 'L':
                    tmp_r = Role.LAMP;
                    break;
                case 'W':
                    tmp_r = Role.TERMINAL;
                    break;
                default:
                    tmp_r = Role.EMPTY;
                    break;
            }

            Tile tmp_tile = new Tile(id, 0, 0, tmp_r, tmp_edge);
            id++;
            this.tiles_config.add(tmp_tile);
        }

        int k = 0;

        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                this.tiles_config.get(k).setPositionX(j);
                this.tiles_config.get(k).setPositionY(i);
                k++;
            }
        }
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public ArrayList<Character> getConfig() {
        return this.configuration;
    }

    public ArrayList<Tile> getTileConfig() {
        return this.tiles_config;
    }

    public char getShape() {
        return this.shape;
    }
}
