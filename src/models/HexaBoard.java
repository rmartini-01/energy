package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HexaBoard extends Board {

    public HexaBoard(int c, int r, ArrayList<Tile> tl) {
        super(c, r, tl);
        this.isSquare = false;
    }

    @Override
    public boolean isSquare() {
        return false;
    }

    public boolean areConnectedHex(Tile t1, Tile t2) { // same as areConnectedSquare but on hex board
        boolean connect = false;
        if (t1.getPositionX() == t2.getPositionX() && (t2.getPositionY() == t1.getPositionY() - 1)) { // t2 top
            if (t1.getEdges().contains(0) && t2.getEdges().contains(3)) {
                connect = true;
            }
        } else if ((t2.getPositionX() == t1.getPositionX() + 1) && t1.getPositionY() == t2.getPositionY()) { // t2 top
                                                                                                             // right
            if (t1.getEdges().contains(1) && t2.getEdges().contains(4)) {
                connect = true;
            }
        } else if ((t2.getPositionX() == t1.getPositionX() + 1) && t2.getPositionY() == t1.getPositionY() + 1) { // t2
                                                                                                                 // down
                                                                                                                 // right
            if (t1.getEdges().contains(2) && t2.getEdges().contains(5)) {
                connect = true;
            }
        } else if (t1.getPositionX() == t2.getPositionX() && (t2.getPositionY() == t1.getPositionY() + 1)) { // t2 down
            if (t1.getEdges().contains(3) && t2.getEdges().contains(0)) {
                connect = true;
            }

        } else if ((t2.getPositionX() == t1.getPositionX() - 1) && (t2.getPositionY() == t1.getPositionY() + 1)) { // t2
                                                                                                                   // left
                                                                                                                   // down
            if (t1.getEdges().contains(4) && t2.getEdges().contains(1)) {
                connect = true;
            }
        } else if ((t2.getPositionX() == t1.getPositionX() - 1) && t1.getPositionY() == t2.getPositionY()) { // t2 left
                                                                                                             // top
            if (t1.getEdges().contains(5) && t2.getEdges().contains(2)) {
                connect = true;
            }
        }
        return connect; // not next to each other on the board
    }

    public void initNeighborsListH() { // initialize the adjacency list
        this.neighborsList = new ArrayList[this.board.size()];
        for (int i = 0; i < this.board.size(); i++) {
            this.neighborsList[i] = new ArrayList<>();
            Tile t1 = board.get(i);
            for (int j = 0; j < this.board.size(); j++) {
                if (i == j) {
                    continue; // skip the current tile
                }
                Tile t2 = board.get(j);
                if (areConnectedHex(t1, t2)) {
                    neighborsList[i].add(j);
                }
            }
        }
    }

    public void updateNeighborsList() {
        for (int i = 0; i < board.size(); i++) {
            Tile tile1 = board.get(i);
            neighborsList[i].clear();
            for (int j = 0; j < board.size(); j++) {
                if (i == j) {
                    continue; // skip the current tile
                }
                Tile tile2 = board.get(j);
                if (areConnectedHex(tile1, tile2)) {
                    neighborsList[i].add(j);
                }
            }
        }
    }

}
