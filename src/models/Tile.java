package models;

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

    public Tile(int i, int x, int y, Role r, ArrayList<Integer> e) {
        this.id = i;
        this.rotation = 0;
        this.pos_x = x;
        this.pos_y = y;
        this.role = r;
        this.edges = e;

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

}
