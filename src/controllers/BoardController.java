package controllers;
import models.Board;
import models.Level;
import models.Role;
import models.Tile;
import views.BoardView;
import views.TileView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoardController extends Controller {
    private BoardView view;
    protected Board board;

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
        this.view.btnDialog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dialog.dispose();
                NavigationController.getInstance(view.frame).goBack();
            }
        });

        this.view.nextLevelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dialog.dispose();
                try {
                    Level level = new Level(view.getLevel().getNum()+1);
                    Board b = new Board(level.getHeight(), level.getWidth(), level.getTileConfig(), level.getShape() == 'S');
                    BoardView boardView = new BoardView(view.frame,  level, b);
                    BoardController bc = new BoardController(boardView, b, navigationController);
                    navigationController.navigateTo(view,boardView);

                    board.notifyObservers();

                }catch (Exception exception){
                    view.showAlert(view, "Level not found");
                }
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
                board.lightsUp();
            }
        }
        if(board.isBoardWinningConfig()){
            view.showWinningDialog(view.getPanel());
        }
    }
}
