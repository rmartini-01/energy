package models;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class Tile {
    private ArrayList<Tile> neighbors; // exists an edge between "this" and the tiles in "neighbors"
    private ArrayList<Integer> edges; // ex : [1,4,3] -> edges at position 1 4 3 of the tile
    private int id;
    private int rotation; // useful ? IG?
    private int pos_x;
    private int pos_y;
    private Role role;
    private char shape;
    private boolean lit = false;
    private BufferedImage image ;

    public Tile(int i, int x, int y, char s, Role r, ArrayList<Integer> e) {
        this.id = i;
        this.pos_x = x;
        this.pos_y = y;
        this.role = r;
        this.edges = e;
        this.shape = s;
        if(e.size()!= 0 && this.shape == 'S'){
            switch (e.get(0)) {
                case 0 -> {
                    if(e.size() ==2){
                        if(e.get(1) == 3)
                            this.rotation = 270;
                        else this.rotation = 0;
                    }else {
                        this.rotation = 0;
                    }
                }
                case 1 -> {
                    this.rotation = 90;
                }
                case 2 -> {
                    this.rotation = 180;
                }
                case 3 -> {
                    this.rotation = 270;
                }
            }
        }else if(e.size() != 0 && shape == 'H'){
            switch (e.get(0)) {
                case 0 -> {
                    if(e.size() >=2){
                        if(e.get(1) > 2) {
                            System.out.println("ici");
                            this.rotation = e.get(1) * 60;
                        }
                        else this.rotation = 0;
                    }else {
                        this.rotation = 0;
                    }
                }
                case 1 -> {
                    if(e.size() >=2){
                        if(e.get(1) > 3)
                            this.rotation = e.get(1) * 60;
                        else this.rotation = 60;
                    }else {
                        this.rotation = 60;
                    }
                }
                case 2 -> {
                    if(e.size() >=2){
                        if(e.get(1) > 4)
                            this.rotation = e.get(1) * 60;
                        else this.rotation = 120;
                    }else {
                        this.rotation = 120;
                    }
                }
                case 3 -> {
                        this.rotation = 180;
                }
                case 4 -> {
                    this.rotation = 240;
                }
                case 5 -> {
                    this.rotation = 300;
                }
            }
        }
        image = null;

    }

    public void setId(int i) {
        this.id = i;
    }

    public void setRotation(int r) {
        this.rotation = r;
    }

    public void setPositionX(int x) {
        this.pos_x = x;
    }

    public void setPositionY(int y) {
        this.pos_y = y;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
    public void setRole(Role r) {
        this.role = r;
    }

    public ArrayList<Tile> getNeighbors() {
        return this.neighbors;
    }

    public int getId() {
        return this.id;
    }

    public int getRotation() {
        return this.rotation;
    }

    public int getPositionX() {
        return this.pos_x;
    }

    public int getPositionY() {
        return this.pos_y;
    }

    public Role getRole() {
        return this.role;
    }

    public char getShape() {
        return this.shape;
    }

    public ArrayList<Integer> getEdges() {
        return this.edges;
    }

    public void setNeighbors(ArrayList<Tile> n) {
        this.neighbors = n;
    }

    public void setLit(boolean b) {
        this.lit = b;
    }

    public boolean getLit() {
        return this.lit;
    }

    public void rotateTile() { // update neighbors/edges and when this function is used, need to change the
                               // board's edgeList too
        if (this.shape == 'S') { // square
            for (int i = 0; i < this.edges.size(); i++) {
                int tmp = (edges.get(i) + 1) % 4;
                edges.set(i, tmp);
            }
        } else {// hexagonal
            for (int i = 0; i < this.edges.size(); i++) {
                int tmp = (edges.get(i) + 1) % 6;
                edges.set(i, tmp);
            }
        }
    }

    public void printTile() {
        System.out.println(
                "id : " + this.id + " |x : " + this.pos_x + " y : " + this.pos_y + "| Role : " + this.role.toString());
        for (Integer r : this.edges) {
            System.out.print(r + " ");
        }
        System.out.println();

    }

    public void printNeighbors() {
        if (!this.neighbors.isEmpty()) {
            for (Tile p : this.neighbors) {
                p.printTile();
            }
        }

    }

}
