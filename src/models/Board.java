package models;

import java.util.ArrayList;

public abstract class Board {
    protected int rows;
    protected int columns;
    protected int score;
    protected ArrayList<Tile> board;

    protected boolean isSquare; // 0 = square, 1 = Hexagone

    public Board() {
        rows = 4;
        columns = 4;
        score = 0;
        board = new ArrayList<Tile>(16);
        isSquare = true;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getScore() {
        return score;
    }

    public ArrayList<Tile> getBoard() {
        return board;
    }
    public abstract boolean isSquare();
}
