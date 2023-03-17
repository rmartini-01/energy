package models;

import java.util.ArrayList;

public class SquareBoard extends Board {

    public SquareBoard(ArrayList<Tile> tl) {
        super(tl);
        this.isSquare = true;
    }

    @Override
    public boolean isSquare() {
        return true;
    }

    public boolean areConnectedSquare(Tile t1, Tile t2) { // check if two tiles are next to each other and connected on
        // the board with their edges
        boolean is_next = false;
        boolean is_connected = false;
        if (t1.getPositionX() == t2.getPositionX() && t1.getPositionY() == t2.getPositionY() + 1) { // t2 top
            is_next = true;
        } else if (t1.getPositionX() == t2.getPositionX() + 1 && t1.getPositionY() == t2.getPositionY()) { // t2 right
            is_next = true;
        } else if (t1.getPositionX() == t2.getPositionX() && t1.getPositionY() == t2.getPositionY() - 1) { // t2 down
            is_next = true;
        } else if (t1.getPositionX() == t2.getPositionX() - 1 && t1.getPositionY() == t2.getPositionY()) { // t2 left
            is_next = true;
        } else {
            return false; // not next to each other on the board
        }
        // if they're next to each other, check if their edges are connected
        if (is_next) {
            if ((t1.getEdges().contains(0) && t2.getEdges().contains(2))
                    || (t1.getEdges().contains(2) && t2.getEdges().contains(0))) {
                is_connected = true;
            } else if ((t1.getEdges().contains(1) && t2.getEdges().contains(3))
                    || (t1.getEdges().contains(3) && t2.getEdges().contains(1))) {
                is_connected = true;
            }
        }
        return is_connected;
    }

    public void setNeighborsSquare() {
        for (int i = 0; i < this.board.size(); i++) {
            ArrayList<Tile> tmp2 = new ArrayList<Tile>();
            for (int j = 0; j < this.board.size(); j++) {
                if (areConnectedSquare(board.get(i), board.get(j)) && i != j) {
                    tmp2.add(board.get(j));
                }
            }
            this.board.get(i).setNeighbors(tmp2);
        }
    }

}
