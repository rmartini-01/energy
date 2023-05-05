package controllers;
import models.Board;
import models.Level;
import views.BoardView;
import views.TileView;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoardController extends Controller {
    private BoardView view;
    private Board board;

    public BoardController(BoardView view, Board board, NavigationController nc) {
        this.board =board;
        this.navigationController = nc;
        this.view = view;

        this.view.getPanel(). addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleTileClick(e);
                System.out.println("clicked");
            }
        });
    }
    public void handleTileClick(MouseEvent e) {
        Point clickPosition = e.getPoint();
        System.out.println(
                "ici"
        );
        System.out.println(view.getTileViews());
        view.repaint();
        for (TileView tileView : view.getTileViews()) {
            Point tilePosition = tileView.getPosition();
            if (view.contains(tilePosition, clickPosition , tileView.getImage().getWidth(), tileView.getImage().getHeight())) {
                tileView.setImage(view.rotateImage(tileView.getImage(), 90));

            }
        }
    }
}
