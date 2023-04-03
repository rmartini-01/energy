package models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

public abstract class Board {
    protected int rows;
    protected int columns;
    protected int score;
    protected ArrayList<Tile> board;
    protected ArrayList<Integer>[] neighborsList; // storing the neighbors of each vertex in the graph. connected
    protected boolean isSquare; // 0 = square, 1 = Hexagone

    public Board(int r, int c, ArrayList<Tile> tl) {
        rows = r;
        columns = c;
        score = 0;
        board = tl;
        neighborsList = (ArrayList<Integer>[]) new ArrayList[tl.size()];
        for (int i = 0; i < tl.size(); i++) {
            neighborsList[i] = new ArrayList<Integer>();
        }
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

    public ArrayList<Integer>[] getneighborsList() {
        return neighborsList;
    }

    public abstract boolean isSquare();

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

    public void LightON(Tile a, Tile b) {
        if (DFS(a.getId(), b.getId())) {
            if (a.getRole() == Role.LAMP
                    && (b.getRole() == Role.SOURCE || (b.getRole() == Role.TERMINAL && b.getLit()))) {
                a.setLit(true);
            }
            if (b.getRole() == Role.LAMP
                    && (a.getRole() == Role.SOURCE || (a.getRole() == Role.TERMINAL && a.getLit()))) {
                b.setLit(true);
            }
        }
    }

}
