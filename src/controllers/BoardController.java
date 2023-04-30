package controllers;
import models.Board;
import models.Level;
import views.BoardView;
import views.TileView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoardController {
    private BoardView view;
    private Board board;
    private Level level;

    public BoardController(JFrame frame, int l) {
        this.level = new Level(l);
        this.board = new Board(level.getHeight(), level.getWidth(), level.getTileConfig(), level.getShape() == 'S');

        this.view = new BoardView(frame, l,board, this);
        view.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleTileClick(e);
            }
        });
    }
    public Board getBoard() {
        return board;
    }

    private void handleTileClick(MouseEvent e) {
        Point clickPosition = e.getPoint();
        for (TileView tileView : view.getTileViews()) {
            Point tilePosition = tileView.getPosition();
            if (view.contains(tilePosition, clickPosition)) {
                tileView.setImage(view.rotateImage(tileView.getImage(), 90));
                view.repaint();
            }
        }
    }
}
