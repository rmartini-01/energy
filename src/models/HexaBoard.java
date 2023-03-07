package models;

public class HexaBoard extends Board {

    public HexaBoard() {
        super();
        this.isSquare= false;
    }

    @Override
    public boolean isSquare() {
        return false;
    }
}
