package models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

public abstract class Board {
    protected int rows;
    protected int columns;
    protected int score;
    protected ArrayList<Tile> board;
    protected ArrayList<Integer>[] edgeList; // storing the neighbors of each vertex in the graph.
    protected boolean isSquare; // 0 = square, 1 = Hexagone

    public Board(ArrayList<Tile> tl) {
        rows = 4;
        columns = 4;
        score = 0;
        board = tl;
        edgeList = (ArrayList<Integer>[]) new ArrayList[tl.size()];
        for (int i = 0; i < 3; i++) {
            edgeList[i] = new ArrayList<Integer>();
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

    public ArrayList<Integer>[] getEdgeList() {
        return edgeList;
    }

    public void updateEdgelist(Tile t) { // update edgeList when move Tile t, t's id is the position as
                                         // edgeList[position] to change
    }

    public abstract boolean isSquare();

    public boolean DFS(int t1, int t2) { // arguments are tiles' id
        boolean[] visited = new boolean[board.size()];
        Stack<Integer> stack = new Stack<Integer>();
        visited[t1] = true;
        stack.push(t1);

        while (!stack.isEmpty()) {
            int currentVertix = stack.pop();
            if (currentVertix == t2) {
                return true;
            }
            Iterator<Integer> i = edgeList[currentVertix].listIterator();
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

    public void setEdgeList() {
        for (int i = 0; i < edgeList.length; i++) {
            if (!board.get(i).getNeighbors().isEmpty()) {
                for (Tile elem : board.get(i).getNeighbors()) {
                    edgeList[i].add(elem.getId());
                }
            }
        }
    }
}
