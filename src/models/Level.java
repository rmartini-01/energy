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
            File levelFile = new File("levels/level" + l + ".nrg");
            Scanner sc = new Scanner(levelFile);
            int i = 0;
            while (sc.hasNext()) {
                String line = sc.next();
                switch (i) {
                    case 0:
                        this.height = Integer.parseInt(line);
                        break;
                    case 1:
                        this.width = Integer.parseInt(line);
                        break;
                    case 2:
                        this.shape = line.charAt(0);
                        break;
                    default:
                        this.configuration.add(line.charAt(0));
                        break;
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int id = 0; // tmp_tile id
        // init tiles_config
        int h = this.configuration.size() - 1;
        for (int i = 0; i < this.configuration.size() - 1; i++) {
            Tile tmp_tile;
            Role tmp_r;
            ArrayList<Integer> tmp_edge = new ArrayList<Integer>();
            switch (this.configuration.get(i)) {
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

            while (Character.isDigit(this.configuration.get(i + 1))) {
                tmp_edge.add(Character.getNumericValue(this.configuration.get(i + 1)));
                i++;
                if (i == h) {
                    break;
                }
            }
            tmp_tile = new Tile(id, 0, 0, tmp_r, tmp_edge);
            id++;
            this.tiles_config.add(tmp_tile);
        }
        char last_element = this.configuration.get(this.configuration.size() - 1);
        if (!Character.isDigit(last_element)) {
            ArrayList<Integer> tmp_edge = new ArrayList<Integer>();
            Tile tmp_tile;
            switch (last_element) {
                case 'S':
                    tmp_tile = new Tile(id, 0, 0, Role.SOURCE, tmp_edge);
                    this.tiles_config.add(tmp_tile);
                    break;
                case 'L':
                    tmp_tile = new Tile(id, 0, 0, Role.LAMP, tmp_edge);
                    this.tiles_config.add(tmp_tile);
                    break;
                case 'W':
                    tmp_tile = new Tile(id, 0, 0, Role.TERMINAL, tmp_edge);
                    this.tiles_config.add(tmp_tile);
                    break;
                default:
                    tmp_tile = new Tile(id, 0, 0, Role.EMPTY, tmp_edge);
                    this.tiles_config.add(tmp_tile);
                    break;
            }

        }
        int k = 0;

        // correctly place the tiles
        for (int i = 0; i < this.height; i++) {// y axis
            for (int j = 0; j < this.width; j++) {// x axis
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
