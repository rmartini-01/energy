package models;

public class SquareBoard extends Board {


    public SquareBoard() {
        super();
        this.isSquare= true;
    }

    @Override
    public boolean isSquare() {
        return true;
    }
}
