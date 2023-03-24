package controllers;

import listeners.NavigateBackListener;

import javax.swing.*;

import java.awt.*;
import java.util.Stack;

public class NavigationController {
    private final JFrame jframe;
    private final Stack<JPanel> history = new Stack<JPanel>();
    private static NavigationController instance;

    private NavigationController(JFrame frame){
        this.jframe = frame;


    }

    public static NavigationController getInstance(JFrame frame) {
        if (instance == null) {
            instance = new NavigationController(frame);
        }
        return instance;
    }

    public void navigateTo(JPanel currentScene, JPanel newScene) {
        // Remove the current scene from the JFrame
        if(currentScene!=null){
            jframe.getContentPane().remove(currentScene);

        }

        // Add the new scene to the JFrame
        jframe.getContentPane().add(newScene);
        history.push(newScene);

        // Set the name of the new scene to the current scene's name
        String newSceneName = newScene.getName();
        if (newSceneName != null && !newSceneName.isEmpty()) {
            jframe.setTitle(newSceneName);
            newScene.requestFocusInWindow();
        }
        // Revalidate and repaint the JFrame to show the new scene
        jframe.revalidate();
        jframe.repaint();
    }

    public void goBack() {
        if (!history.empty()) {
            navigateTo(history.pop(), history.peek());
        }
    }



}
