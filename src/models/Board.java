package models;

import views.TilesFactory;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Board implements Observable {
    private int rows;
    private int columns;
    private ArrayList<Tile> board;
    private boolean isSquare; // 0 = square, 1 = Hexagone
    private ArrayList<Observer> observers;

    public Board(int r, int c, ArrayList<Tile> tl,boolean isSquare) {
        rows = r;
        columns = c;
        board = tl;
        observers = new ArrayList<>();

        this.isSquare = isSquare;

        for (Tile currentTile : board) {
            int row = currentTile.getPositionY();
            int col = currentTile.getPositionX();
            if(currentTile.getEdges().size()!=0){
                // Check and add the neighbors: up, down, left, and right
                if(isSquare){
                    connectSquareTiles(currentTile, row, col);
                }else{
                    connectHexagonalTiles(currentTile, row, col);
                }
            }

        }
        shuffle();
        lightsUp();
        Collections.sort(this.board, Comparator.comparingInt(Tile::getId));
    }
    public void shuffle() {
        int max = isSquare ? 4 : 6;
        for (var tile : board) {
            int randint = ThreadLocalRandom.current().nextInt(max);
            for (int i = 0; i < randint; i++) {
                tile.rotateTile();
            }
        }

    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    private void connectSquareTiles(Tile currentTile, int row, int col){
        if (row > 0) {
            Tile topNeighbor = getTileByPosition(row - 1, col);
            if (topNeighbor != null) {
                if(topNeighbor.getEdges().size()!=0){
                    currentTile.addNeighbor(topNeighbor);
                }
            }
        }
        if (row < rows - 1) {
            Tile bottomNeighbor = getTileByPosition(row + 1, col);
            if (bottomNeighbor != null) {
                if(bottomNeighbor.getEdges().size()!=0) {
                    currentTile.addNeighbor(bottomNeighbor);
                }
            }
        }
        if (col > 0) {
            Tile leftNeighbor = getTileByPosition(row, col - 1);
            if (leftNeighbor != null) {
                if(leftNeighbor.getEdges().size()!=0) {
                    currentTile.addNeighbor(leftNeighbor);
                }
            }
        }
        if (col < columns - 1) {
            Tile rightNeighbor = getTileByPosition(row, col + 1);
            if (rightNeighbor != null) {
                if(rightNeighbor.getEdges().size()!=0) {
                    currentTile.addNeighbor(rightNeighbor);
                }
            }
        }

    }

    public void updateNeighbors(){
        for (Tile currentTile : board) {
            int row = currentTile.getPositionY();
            int col = currentTile.getPositionX();
            if(currentTile.getEdges().size()!=0){
                // Check and add the neighbors: up, down, left, and right
                if(isSquare){
                    connectSquareTiles(currentTile, row, col);
                }else{
                    connectHexagonalTiles(currentTile, row, col);
                }
            }

        }
        lightsUp();
    }

    private void connectHexagonalTiles(Tile currentTile, int row, int col) {
        int[][] directions;

        if (col % 2 == 0) {
            directions = new int[][]{
                    {-1, -1}, {-1, 0}, {0, -1},
                    {1, 0}, {0, 1}, {-1, 1}
            };
        } else {
            directions = new int[][]{
                    {-1, 0}, {1, 0}, {1, 1},
                    {0, 1}, {1, -1}, {0, -1}
            };
        }

        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < columns) {
                Tile neighbor = getTileByPosition(newRow, newCol);
                if (neighbor != null && neighbor.getEdges().size() != 0) {
                    currentTile.addNeighbor(neighbor);
                }
            }
        }
    }

    public ArrayList<Tile> getBoard() {
        return board;
    }

    public Tile getTileByPosition(int row, int col){
        for(Tile t : board){
            if(t.getPositionX()==col && t.getPositionY()==row){
                return t;
            }
        }
        return null;
    }

    public boolean isSquare(){
        return this.isSquare;
    }

    public void rotateTile(Tile t ){
        for(Tile tile : board){
            if (tile.equals(t)){
                t.rotateTile();
            }
        }
        notifyObservers();
    }

    public boolean areConnectedHex(Tile t1, Tile t2) {
        int t1Direction = neighborHexDirection(t1, t2);
        int t2Direction = (t1Direction + 3)%6;
        return t1.getEdges().contains(t1Direction) && t2.getEdges().contains(t2Direction);
    }

    /**
     * récuperer la direction d'une tuile hexagonale par rapport à une autre (voisine)
     * @param h1 tuile 1
     * @param h2 tuile 2 le voisin
     * @return côté connecté de la tuile 1
     */
    public int neighborHexDirection(Tile h1, Tile h2) {
        int h1X = h1.getPositionX();
        int h1Y = h1.getPositionY();
        int h2X = h2.getPositionX();
        int h2Y = h2.getPositionY();
        int xDifference = h1X-h2X;
        int yDifference = h1Y-h2Y;
        if (h1X%2 == 0) {
            if (xDifference == 0 && yDifference == 1) return 0;
            if (xDifference == -1 && yDifference == 1) return 1;
            if (xDifference == -1 && yDifference == 0) return 2;
            if (xDifference == 0 && yDifference == -1) return 3;
            if (xDifference == 1 && yDifference == 0) return 4;
            if (xDifference == 1 && yDifference == 1) return 5;
        }
        else {
            if (xDifference == 0 && yDifference == 1) return 0;
            if (xDifference == -1 && yDifference == 0) return 1;
            if (xDifference == -1 && yDifference == -1) return 2;
            if (xDifference == 0 && yDifference == -1) return 3;
            if (xDifference == 1 && yDifference == -1) return 4;
            if (xDifference == 1 && yDifference == 0) return 5;
        }
        throw new IllegalArgumentException("Tiles are not neighbors");
    }

    public boolean areConnectedSquare(Tile t1, Tile t2) {
        // check if two tiles are next to each other and connected on
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

    public void modifGeoBoard(){
        this.clearBoard();
        if (this.isSquare) {
            this.isSquare=false;
        }
        else{
            this.isSquare=true;
        }
        notifyObservers();
    }

    public boolean isEmptyBoard(){
        return this.board.isEmpty();
    }

    public void clearBoard(){
        this.board.clear();
        this.columns=0;
        this.rows=0;
        notifyObservers();
    }

    public void lightsUp(){
        for(Tile t: board){
            if(t.getRole()!=Role.SOURCE){
                t.setLit(false);
            }
        }
        for(Tile t : board){
            if(t.getRole()==Role.SOURCE){
                lightUpNeighbors(t);
            }
        }
        lightUpWifi();
    }

    public void lightUpWifi(){
        boolean isWifiLit= false;
        for(Tile t : board){
            if (t.getRole() == Role.WIFI && t.getLit()) {
                isWifiLit = true;
                break;
            }
        }
        if(isWifiLit){
            for (Tile t : board){
                if(t.getRole()==Role.WIFI){
                    t.setLit(true);
                    lightUpNeighbors(t);
                }
            }
        }
    }

    public void lightUpNeighbors(Tile tile){
        for(Tile neighbor : tile.getNeighbors()){
            boolean connected;
            if(isSquare){
                connected = areConnectedSquare(tile, neighbor);
            }else{
                connected = areConnectedHex(tile, neighbor);
            }
            if(connected){
                if(!neighbor.getLit()){
                    neighbor.setLit(true);
                    lightUpNeighbors(neighbor);
                }
            }
        }
    }
    public boolean isBoardWinningConfig() {
        for (Tile tile : board) {
            if (tile.getEdges().size() != 0) {
                if (!tile.getLit()) {
                    return false;
                }
            }
        }
        return true;
    }

    public int lastTileAdded(){
        if (this.isEmptyBoard()){
            return 0;
        }
        int id_tmp = 0;
        for (Tile t : this.board){ // to find the last tile in the board
            if (id_tmp<t.getId()) {
                id_tmp = t.getId();
            }
        }
        return  id_tmp;
    }

    public int [] getPosToAddRight(){ // to add tile on the bottom of the board
        if (this.board.isEmpty()){
            int [] pos ={0,0};
            this.columns++;
            this.rows++;
            return pos;
        }
        int[] position = new int[2];
        int lastX =this.columns-1;
        int lastY =0;
        Tile last_tile = this.board.get(0);
        for (Tile t : this.board){
            if (t.getPositionY()>lastY && t.getPositionX()==lastX){
                lastY = t.getPositionY();
                last_tile=t;
            }
        }
        if (last_tile.getPositionX()==((this.columns)-1) && last_tile.getPositionY()==((this.rows)-1)){ // case when adding to a new column
            position[0] = this.columns;
            position[1]=0;
            this.columns+=1;
            for(int i = this.rows+1; i < this.board.size()-1;i++){
                int tmp =this.board.get(i).getId();
                this.board.get(i).setId(tmp+1);
            }
            this.board.get(this.board.size()-1).setId(this.rows+1);
            Collections.sort(this.board, Comparator.comparingInt(Tile::getId));
        }else{ // case when adding to an existing row
            position[0] =this.columns-1;
            position[1] = last_tile.getPositionY()+1;
        }
        return position;
    }

     public int [] getPosToAddBottom(){ // to add tile at the right of the board
        if (this.board.isEmpty()){
            int [] pos ={0,0};
            this.columns++;
            this.rows++;
            return pos;
        }
        int[] position = new int[2];
        int lastX =0;
        int lastY = this.rows-1;
        Tile last_tile = this.board.get(0);
        for (Tile t : this.board){
            if (t.getPositionX()>lastX && t.getPositionY()==lastY){
                lastX = t.getPositionX();
                last_tile=t;
            }
        }
        if (last_tile.getPositionX()==((this.columns)-1) && last_tile.getPositionY()==((this.rows)-1)){ // case when adding to a new row
            position[0] = 0;
            position[1]=this.rows;
            this.rows+=1;
        }else{ // case when adding to an existing row
            position[0] = last_tile.getPositionX()+1;
            position[1] = this.rows-1;
        }
        return position;
    }

    public char getShape(){
        return this.isSquare()? 'S':'H';
    }


    public void addTile(Tile t) {
        this.board.add(t);
        Collections.sort(this.board, Comparator.comparingInt(Tile::getId));
        notifyObservers();
    }


    public String getLevelTxtFromBoard(){
        int nextRow = 0;
        String txt = "";
        txt+=this.rows+ " ";
        txt+=this.columns+ " ";
        txt+=this.getShape()+"\n";
        for (Tile t : this.board){
            if (nextRow == this.columns){
                txt+="\n";
                nextRow=0;
            }
            txt+=t.getRoleChar()+" ";
            nextRow++;
            for(Integer i : t.getEdges()){
                txt+=i+" ";
            }
        }
        return txt;
    }

    public void modifyTile (int id,Role new_role,ArrayList<Integer> new_edges){
        for (Tile t : this.board){
            if (t.getId()==id){
                t.setEdges(new_edges);
                t.setRole(new_role);
            }
        }
        Collections.sort(this.board, Comparator.comparingInt(Tile::getId));
        notifyObservers();
    }

    public void removeTile (int id){
        Tile tmp =this.board.get(id);
        if (tmp.getPositionX()==this.columns-1){
            this.columns-=1;
        }
        if (tmp.getPositionY()==this.rows-1) {
            this.rows-=1;
        }

        this.board.remove(id);
        for (int i = id+1 ; i< this.board.size();i++){
            this.board.get(i).setId(this.board.get(i).getId()-1);
        }
        notifyObservers();
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }
}
