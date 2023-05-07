package controllers;
import models.Board;
import models.Tile;
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
        this.board.addObserver(this.view);

        this.view.getPanel(). addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleTileClick(e);
            }
        });
    }
    public void handleTileClick(MouseEvent e) {
        Point clickPosition = e.getPoint();
        for (TileView tileView : view.getTileViews()) {
            Point tilePosition = tileView.getPosition();
            if (view.contains(tilePosition, clickPosition , tileView.getImage().getWidth(), tileView.getImage().getHeight())) {
                Tile t = tileView.getTile();
                board.rotateTile(t);
                board.connectNeighborTiles(null, t);
                if(!board.isBoardWinningConfig()){
                  // System.out.println("pas connecté");
                }else{
                  // System.out.println("tout est lié ");

                }
            }
        }
    }
}
