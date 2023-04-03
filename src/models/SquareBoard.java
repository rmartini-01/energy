package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SquareBoard extends Board {

    public SquareBoard(int c, int r, ArrayList<Tile> tl) {
        super(c, r, tl);
        this.isSquare = true;
    }

    @Override
    public boolean isSquare() {
        return true;
    }

    public boolean areConnectedSquare(Tile t1, Tile t2) { // check if two tiles are next to each other and connected on
        // the board with their edges
        boolean connect = false;
        if (t1.getPositionX() == t2.getPositionX() && (t2.getPositionY() == t1.getPositionY() - 1)) { // t2 top
            if (t1.getEdges().contains(0) && t2.getEdges().contains(2)) {
                connect = true;
            }
        } else if ((t2.getPositionX() == t1.getPositionX() + 1) && t1.getPositionY() == t2.getPositionY()) { // t2 right
            if (t1.getEdges().contains(1) && t2.getEdges().contains(3)) {
                connect = true;
            }
        } else if (t1.getPositionX() == t2.getPositionX() && (t2.getPositionY() == t1.getPositionY() + 1)) { // t2 down
            if (t1.getEdges().contains(2) && t2.getEdges().contains(0)) {
                connect = true;
            }

        } else if ((t2.getPositionX() == t1.getPositionX() - 1) && t1.getPositionY() == t2.getPositionY()) { // t2 left
            if (t1.getEdges().contains(3) && t2.getEdges().contains(1)) {
                connect = true;
            }

        }
        return connect; // not next to each other on the board

    }

    public void setNeighborsSquare() { // sets the neighbors of each square tile
        for (int i = 0; i < this.board.size(); i++) {
            ArrayList<Tile> tmp2 = new ArrayList<Tile>();
            for (int j = 0; j < this.board.size(); j++) {
                if (areConnectedSquare(board.get(i), board.get(j)) && i != j) {
                    if (!tmp2.contains(board.get(j))) {
                        tmp2.add(board.get(j));
                    }

                }
            }
            this.board.get(i).setNeighbors(tmp2);
        }
    }

    public void initNeighborsList() { // initialize the adjacency list
        this.neighborsList = new ArrayList[this.board.size()];
        for (int i = 0; i < this.board.size(); i++) {
            this.neighborsList[i] = new ArrayList<>();
            Tile t1 = board.get(i);
            for (int j = 0; j < this.board.size(); j++) {
                if (i == j) {
                    continue; // skip the current tile
                }
                Tile t2 = board.get(j);
                if (areConnectedSquare(t1, t2)) {
                    neighborsList[i].add(j);
                }
            }
        }
    }

    public void updateNeighborsList() {
        for (int i = 0; i < board.size(); i++) {
            Tile tile1 = board.get(i);
            neighborsList[i].clear(); // clear the list of neighbors for the i-th tile
            for (int j = 0; j < board.size(); j++) {
                if (i == j) {
                    continue; // skip the current tile
                }
                Tile tile2 = board.get(j);
                if (areConnectedSquare(tile1, tile2)) {
                    // if the j-th tile is a neighbor of the i-th tile, add it to the list of
                    // neighbors
                    neighborsList[i].add(j);
                }
            }
        }
    }

}
