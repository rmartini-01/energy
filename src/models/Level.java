package models;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Level {
    private int numlevel;
    private int height;
    private int width;
    private char shape;
    private ArrayList<Character> configuration = new ArrayList<Character>();
    private ArrayList<Tile> tiles_config_win = new ArrayList<Tile>();
    private ArrayList<Tile> tiles_config = new ArrayList<Tile>();

    public Level(int l) {
        this.numlevel=l;
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
        int h = this.configuration.size() - 1;
        for (int i = 0; i < this.configuration.size() - 1; i++) {
            Tile tmp_tile;
            Role tmp_r;
            ArrayList<Integer> tmp_edge = new ArrayList<Integer>();
            switch (this.configuration.get(i)) {
                case 'S' : tmp_r = Role.SOURCE;break;
                case 'L' : tmp_r = Role.LAMP;break;
                case 'W' : tmp_r = Role.WIFI;break;
                default : tmp_r = Role.EMPTY;break;
            };

            while (Character.isDigit(this.configuration.get(i + 1))) {
                tmp_edge.add(Character.getNumericValue(this.configuration.get(i + 1)));
                i++;
                if (i == h) {
                    break;
                }
            }
            tmp_tile = new Tile(id, 0, 0, this.shape, tmp_r, tmp_edge);
            id++;
            this.tiles_config.add(tmp_tile);
            this.tiles_config_win.add(tmp_tile);
        }
        char last_element = this.configuration.get(this.configuration.size() - 1);
        if (!Character.isDigit(last_element)) {
            ArrayList<Integer> tmp_edge = new ArrayList<Integer>();
            Tile tmp_tile;
            switch (last_element) {
                case 'S' : {
                    tmp_tile = new Tile(id, 0, 0, this.shape, Role.SOURCE, tmp_edge);
                    this.tiles_config.add(tmp_tile);
                    this.tiles_config_win.add(tmp_tile);
                }
                case 'L' : {
                    tmp_tile = new Tile(id, 0, 0, this.shape, Role.LAMP, tmp_edge);
                    this.tiles_config.add(tmp_tile);
                    this.tiles_config_win.add(tmp_tile);
                }
                case 'W' : {
                    tmp_tile = new Tile(id, 0, 0, this.shape, Role.WIFI, tmp_edge);
                    this.tiles_config.add(tmp_tile);
                    this.tiles_config_win.add(tmp_tile);
                }
                default : {
                    tmp_tile = new Tile(id, 0, 0, this.shape, Role.EMPTY, tmp_edge);
                    this.tiles_config.add(tmp_tile);
                    this.tiles_config_win.add(tmp_tile);
                }
            }

        }
        int k = 0;

        // correctly place the tiles
        for (int i = 0; i < this.height; i++) {// y axis
            for (int j = 0; j < this.width; j++) {// x axis
                this.tiles_config.get(k).setPositionX(j);
                this.tiles_config.get(k).setPositionY(i);
                this.tiles_config_win.get(k).setPositionX(j);
                this.tiles_config_win.get(k).setPositionY(i);
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

    public int getNum(){
        return this.numlevel;
    }
}
