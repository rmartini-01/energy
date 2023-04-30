package controllers;

import views.BoardView;
import views.EditGameView;
import views.HomepageView;

import javax.swing.*;

public class HomepageController{
    private final HomepageView view;
    private JFrame frame;

    public HomepageController(JFrame frame) {
        this.frame = frame;
        this.view = new HomepageView(frame, this);
    }

    public void newGameAction() {
        NavigationController.getInstance(frame).navigateTo(view, new BoardView(frame,  view.getSelectedLevel()));
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

