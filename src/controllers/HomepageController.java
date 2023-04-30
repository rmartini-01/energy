package controllers;

import views.BoardView;
import views.EditGameView;
import views.HomepageView;

import javax.swing.*;

public class HomepageController extends  Controller{
    private final HomepageView view;
    private JFrame frame;


    public HomepageController(JFrame frame) {
        this.frame = frame;
        this.view = new HomepageView(frame, this);
    }

    public void newGameAction() {
        BoardController bc = new BoardController(frame, getView().getSelectedLevel(), navigationController);
        navigationController.navigateTo(view, new BoardView(frame,  view.getSelectedLevel(), bc.getBoard(), bc));
    }

    public void settingsAction() {
        // Add logic for settings action
        //TODO
    }

    public void editGameAction() {
        // Add logic for edit game action
        NavigationController.getInstance(frame).navigateTo(view, new EditGameView(frame,  view.getSelectedLevel(),null));

    }

    public HomepageView getView() {
        return view;
    }
}

