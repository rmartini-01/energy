package models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

public class Board implements Observable {
    protected int rows;
    protected int columns;
    protected int score;
    protected ArrayList<Tile> board;
    protected ArrayList<Integer>[] neighborsList; // storing the neighbors of each vertex in the graph. connected
    protected boolean isSquare; // 0 = square, 1 = Hexagone
    private ArrayList<Observer> observers;
    public Board(int r, int c, ArrayList<Tile> tl,boolean isSquare) {
        rows = r;
        columns = c;
        score = 0;
        board = tl;
        neighborsList = (ArrayList<Integer>[]) new ArrayList[tl.size()];
        observers = new ArrayList<>();
        for (int i = 0; i < tl.size(); i++) {
            neighborsList[i] = new ArrayList<Integer>();
        }
        this.isSquare = isSquare;
       // initNeighborsList();

        //TODO fix this
        for (Tile currentTile : board) {
            System.out.println("tile + " + currentTile.getRole() + " x = " + currentTile.getPositionX() + "y = "+ currentTile.getPositionY());
            for (int row = 0; row <= rows+2; row++) {
                for (int col = 0; col <= columns; col++) {
                    Tile nextTile = getTileByPosition(row, col);
                    if(nextTile!= null){
                        if(nextTile.equals(currentTile)){
                                // Check and add the neighbors: up, down, left, and right
                                if (row > 0) {
                                    Tile topNeighbor = getTileByPosition(row - 1, col);
                                    if(topNeighbor!=null){
                                        currentTile.addNeighbor(topNeighbor);
                                    }
                                }
                                if (row < rows - 1) {
                                    Tile bottomNeighbor = getTileByPosition(row + 1, col);
                                    if(bottomNeighbor!=null) {
                                        currentTile.addNeighbor(bottomNeighbor);
                                    }
                                }
                                if (col > 0) {
                                    Tile leftNeighbor = getTileByPosition(row, col - 1);
                                    if(leftNeighbor!=null){
                                        currentTile.addNeighbor(leftNeighbor);
                                    }
                                }
                                if (col < columns - 1) {
                                    Tile rightNeighbor = getTileByPosition(row, col + 1);
                                    if(rightNeighbor!= null){
                                        currentTile.addNeighbor(rightNeighbor);
                                    }
                                }


                        }
                    }

                }
            }
        }

    }

    public int getRows() {
        return rows;
    }
    public void setRows(int r){this.rows=r;}

    public int getColumns() {
        return columns;
    }
    public void setColumns(int c){this.columns=c;}

    public int getScore() {
        return score;
    }

    public ArrayList<Tile> getBoard() {
        return board;
    }

    public Tile getTileByPosition(int x, int y){
        for(Tile t : board){
            if(t.getPositionX()==x && t.getPositionY()==y){
                System.out.println("return tile at position " + x + " " + y);
                return t;
            }
        }
        return null;
    }

    public ArrayList<Integer>[] getneighborsList() {
        return neighborsList;
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
    public boolean DFS(int t1, int t2) { // arguments are tiles' id, checks if there's a way between two tiles
        boolean[] visited = new boolean[board.size()];
        Stack<Integer> stack = new Stack<Integer>();
        visited[t1] = true;
        stack.push(t1);

        while (!stack.isEmpty()) {
            int currentVertix = stack.pop();
            if (currentVertix == t2) {
                return true;
            }
            Iterator<Integer> i = neighborsList[currentVertix].listIterator();
            while (i.hasNext()) {
                int m = i.next();
                if (!visited[m]) {
                    visited[m] = true;
                    stack.push(m);
                }
            }
        }
        return false;
    }

    public void printNeighborsList() {
        for (int i = 0; i < neighborsList.length; i++) {
            System.out.print("Tile id " + i + " : ");
            for (Integer k : this.neighborsList[i]) {
                System.out.print(k + " ");
            }
            System.out.println();
        }
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
    public void setNeighborsHexa() { // sets the neighbors of each square tile
        for (int i = 0; i < this.board.size(); i++) {
            ArrayList<Tile> tmp2 = new ArrayList<Tile>();
            for (int j = 0; j < this.board.size(); j++) {
                if (areConnectedHex(board.get(i), board.get(j)) && i != j) {
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
                if (isSquare){
                    if (areConnectedSquare(t1, t2)) {
                        neighborsList[i].add(j);
                    }
                }else{
                    if (areConnectedHex(t1, t2)) {
                        neighborsList[i].add(j);
                    }
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

                if (isSquare){
                    if (areConnectedSquare(tile1, tile2)) {
                        // if the j-th tile is a neighbor of the i-th tile, add it to the list of
                        // neighbors
                        neighborsList[i].add(j);
                    }
                }else{
                    if (areConnectedHex(tile1, tile2)) {
                        neighborsList[i].add(j);
                    }
                }
            }
        }
    }

    public void LightON(Tile a, Tile b) {
        if (DFS(a.getId(), b.getId())) {
            if (a.getRole() == Role.LAMP
                    && (b.getRole() == Role.SOURCE || (b.getRole() == Role.WIFI && b.getLit()))) {
                a.setLit(true);
            }
            if (b.getRole() == Role.LAMP
                    && (a.getRole() == Role.SOURCE || (a.getRole() == Role.WIFI && a.getLit()))) {
                b.setLit(true);
            }
        }
    }

    public boolean isEmptyBoard(){
        return this.board.isEmpty();
    }

    public void clearBoard(){
        this.board.clear();
        for (int i = 0 ; i<this.neighborsList.length ;i++){
            neighborsList[i].clear();
        }
    }




    public boolean areTilesConnected(Tile tile1, Tile tile2) {
        for (int edge1 : tile1.getEdges()) {
            for (int edge2 : tile2.getEdges()) {
                if (tile1.getNeighbors().contains(tile2) && tile2.getNeighbors().contains(tile1)) {
                    if (tile1.getOppositeEdge(edge1) == edge2) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public void connectNeighborTiles(Tile previous, Tile tile) {
        if (tile.getLit()) {
            for (Tile neighbor : tile.getNeighbors()) {
                System.out.println("tile : " + tile.getRole() + " neighbors " + neighbor.getRole());

                if(( previous==null || previous!= neighbor) && neighbor.getEdges().size()!=0 ){
                    if (areConnectedSquare(tile, neighbor)) {
                      //  System.out.println("Tile " + tile.getId() + " is connected to Tile " + neighbor.getId());
                        neighbor.setLit(true);
                        connectNeighborTiles(tile, neighbor);
                    }else{
                        //System.out.println("Tile " + tile.getId() + " is NOT connected to Tile " + neighbor.getId());

                    }
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
        Tile tile_tmp= this.board.get(0);
        for (Tile t : this.board){ // to find the last tile in the board
            if (id_tmp<t.getId()) {
                id_tmp = t.getId();
            }
        }
        return  id_tmp;
    }


    public int [] getPosToAddColumn(){ // to add tile on the right of the board
        if (this.board.isEmpty()){
            int [] pos ={0,0};
            this.columns++;
            return pos;
        }
        int[] position = new int[2];
        int last = this.lastTileAdded();
        Tile last_tile = this.board.get(0);
        for (Tile t : this.board){
            if (t.getId()==last){
                last_tile=t;
            }
        }
        if (last_tile.getPositionX()==((this.columns)-1) && last_tile.getPositionY()==((this.rows)-1)){ // case when adding to a new column
            position[0] = this.columns;
            position[1]=0;
            this.columns+=1;
        }else{ // case when adding to an existing column
            position[0] = this.columns-1;
            position[1] = last_tile.getPositionY()+1;
        }
        return position;
    }

    public int [] getPosToAddRow(){ // to add tile at the bottom of the board
        if (this.board.isEmpty()){
            int [] pos ={0,0};
            this.rows++;
            return pos;
        }
        int[] position = new int[2];
        int last = this.lastTileAdded();
        Tile last_tile = this.board.get(0);
        for (Tile t : this.board){
            if (t.getId()==last){
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



    public void changeTile(Tile t1, Tile t2){ // change t1 into t2

    }

    public void addTileHexa(Tile t){
        this.board.add(t);
        updateNeighborsList();
    }

    public void addTileSquare(Tile t){
        this.board.add(t);
        updateNeighborsList();
    }


    public char getShape(){
        return this.isSquare()? 'S':'H';
    }


    public void addTile(Tile t) {
        this.board.add(t);
        initNeighborsList();
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
