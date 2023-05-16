package controllers;

import models.Board;
import models.Level;
import views.BoardView;
import views.EditGameView;
import views.HomepageView;

import javax.swing.*;

public class HomepageController extends  Controller{
    private final HomepageView view;
    private JFrame frame;


    public HomepageController(HomepageView view) {
        this.view = view;
        this.view.getNewGameBtn().addActionListener(e -> {
            Level level = new Level(view.getSelectedLevel());
            Board board = new Board(level.getHeight(), level.getWidth(), level.getTileConfig(), level.getShape() == 'S');
            BoardView boardView = new BoardView(frame,  level, board);
            BoardController bc = new BoardController(boardView, board, navigationController);
            navigationController.navigateTo(view,boardView);
        });
        this.view.getEditGameBtn().addActionListener(e->{
            Level level = new Level(view.getSelectedLevel());
            Board board = new Board(level.getHeight(), level.getWidth(), level.getTileConfig(), level.getShape() == 'S');
            board.setEditmode(true);
            EditGameView editView = new EditGameView(frame,level,board,this.getView());
            EditmodeController editController = new EditmodeController(editView,board,navigationController);
            navigationController.navigateTo(view, editView);

        });

        this.view.getSettings().addActionListener(e->{
            System.out.println("settings btn clicked ");
        });
    }

    public HomepageView getView() {
        return view;
    }
}

