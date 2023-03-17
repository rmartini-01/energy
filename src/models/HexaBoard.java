package models;

import java.util.ArrayList;

public class HexaBoard extends Board {

    public HexaBoard(ArrayList<Tile> tl) {
        super(tl);
        this.isSquare = false;
    }

    @Override
    public boolean isSquare() {
        return false;
    }

    public boolean areConnectedHex(Tile t1, Tile t2) { // same as areConnectedSquare but on hex board
        boolean is_next = false;
        boolean is_connected = false;
        if (t1.getPositionX() == t2.getPositionX() - 1 && t1.getPositionY() == t2.getPositionY()) { // t2 top
            is_next = true;
        } else if (t1.getPositionX() == t2.getPositionX() && t1.getPositionY() == t2.getPositionY() + 1) { // t2 right
                                                                                                           // top
            is_next = true;
        } else if (t1.getPositionX() == t2.getPositionX() + 1 && t1.getPositionY() == t2.getPositionY() + 1) { // t2
                                                                                                               // right
                                                                                                               // down
            is_next = true;
        } else if (t1.getPositionX() == t2.getPositionX() + 1 && t1.getPositionY() == t2.getPositionY()) { // t2 down
            is_next = true;
        } else if (t1.getPositionX() == t2.getPositionX() + 1 && t1.getPositionY() == t2.getPositionY() - 1) { // t2
                                                                                                               // left
                                                                                                               // down
            is_next = true;
        } else if (t1.getPositionX() == t2.getPositionX() && t1.getPositionY() == t2.getPositionY() - 1) { // t2 left
                                                                                                           // top
            is_next = true;
        } else {
            return false;
        }

        if (is_next) {
            if ((t1.getEdges().contains(0) && t2.getEdges().contains(3))
                    || (t1.getEdges().contains(3) && t2.getEdges().contains(0))) {
                is_connected = true;
            } else if ((t1.getEdges().contains(1) && t2.getEdges().contains(4))
                    || (t1.getEdges().contains(4) && t2.getEdges().contains(1))) {
                is_connected = true;
            } else if ((t1.getEdges().contains(2) && t2.getEdges().contains(5))
                    || (t1.getEdges().contains(5) && t2.getEdges().contains(2))) {
                is_connected = true;
            }
        }
        return is_connected;
    }

    public void setNeighborsHexa() {
        for (int i = 0; i < this.board.size(); i++) {
            ArrayList<Tile> tmp2 = new ArrayList<Tile>();
            for (int j = 0; j < this.board.size(); j++) {
                if (areConnectedHex(board.get(i), board.get(j)) && i != j) {
                    tmp2.add(board.get(j));
                }
            }
            this.board.get(i).setNeighbors(tmp2);
        }

    }
}
