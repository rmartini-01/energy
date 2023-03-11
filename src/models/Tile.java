package models;

import java.util.HashMap;

public class Tile {
    private HashMap<Integer, Boolean> neighboors; // <graph's every tile's id, connected to it?> or <tile's neighboors,
                                                  // (is connected to it)>
    private int id;
    private int rotation;
    private int pos_x;
    private int pos_y;
    private boolean flag;
    private Role role;

    public enum Role {
        SOURCE, LAMP, TERMINAL
    };

    public Tile(int i, int x, int y, Role r) {
        this.id = i;
        this.rotation = 0;
        this.pos_x = x;
        this.pos_y = y;
        this.role = r;
        this.flag = false;

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

    public void setFlag(boolean f) {
        this.flag = f;
    }

    public void setRole(Role r) {
        this.role = r;
    }

    public HashMap getNeighboors() {
        return this.neighboors;
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

    public boolean getFlag() {
        return this.flag;
    }

    public Role getRole() {
        return this.role;
    }
}
