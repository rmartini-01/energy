import com.formdev.flatlaf.FlatDarculaLaf;
import controllers.NavigationController;
import listeners.NavigateBackListener;

import views.HomepageView;
import views.MenuBarView;
import javax.swing.*;
import java.awt.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Main {

    public static void main(String[] args){
        FlatDarculaLaf.setup();
        JFrame frame = new JFrame(); // contains the main frame
        frame.setSize(600, 600);
        frame.setLayout(new BorderLayout());


        NavigationController.getInstance(frame).navigateTo(null , new HomepageView(frame));


        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setJMenuBar(new MenuBarView(frame));
        frame.setVisible(true);
    }


}
/*
 * import java.io.File;
 * import java.io.FileInputStream;
 * import java.io.FileNotFoundException;
 * import java.util.ArrayList;
 * import java.util.Iterator;
 * import java.util.Scanner;
 * import java.util.Stack;
 * 
 * public class test {
 * 
 * public static void main(String[] args) {
 * Level l = new Level(1);
 *      System.out.println(l.getHeight());
 *     System.out.println(l.getWidth());
 *       System.out.println(l.getShape());
 *      System.out.println(l.getConfig());
 *     if (l.getTileConfig().isEmpty()) {
 *           System.out.println("empty");
 *
 *      } else {
 *           for (Tile e : l.getTileConfig()) {
 *              e.printTile();
 *           }
 *      }
 * ArrayList<Integer> tab1 = new ArrayList<Integer>();
 * tab1.add(0);
 * ArrayList<Integer> tab2 = new ArrayList<Integer>();
 * tab2.add(2);
 * ArrayList<Integer> tab3 = new ArrayList<Integer>();
 * tab3.add(1);
 * Tile t1 = new Tile(0, 0, 0, Role.SOURCE, tab1);
 * Tile t2 = new Tile(1, 1, 0, Role.LAMP, tab2);
 * Tile t3 = new Tile(2, 0, 1, Role.LAMP, tab3);
 * ArrayList<Tile> tl = new ArrayList<Tile>(3);
 * tl.add(t1);
 * tl.add(t2);
 * tl.add(t3);
 * SquareBoard b = new SquareBoard(tl);
 * 
 * b.getBoard().add(t1);
 * b.getBoard().add(t2);
 * b.getBoard().add(t3);
 * 
 * // to test functions :
 * 
 * System.out.println(b.areConnectedSquare(t1, t2));
 * System.out.println(b.areConnectedSquare(t1, t3));
 * System.out.println(b.areConnectedSquare(t3, t2));
 * b.setNeighborsSquare();
 * b.setEdgeList();
 * for (int i = 0; i < b.edgeList.length; i++) {
 * System.out.println(b.edgeList[i]);
 * }
 * for (int i = 0; i < b.board.size(); i++) {
 * System.out.println("Pour le tile n°" + b.board.get(i).getId() + " : ");
 * for (Tile elem : b.board.get(i).getNeighbors()) {
 * System.out.println(elem.getId() + " ");
 * }
 * }
 * System.out.println(b.DFS(t1.getId(), t3.getId()));
 * System.out.println(b.DFS(t1.getId(), t2.getId()));
 * 
 * t1.rotateTile();
 * b.setNeighborsSquare();
 * b.setEdgeList();
 * System.out.println(b.DFS(t1.getId(), t2.getId()));
 * 
 * // TO TEST ON BIGGER SCALE
 * }}
 */