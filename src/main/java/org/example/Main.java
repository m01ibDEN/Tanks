package org.example;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setCursor(null);
        frame.setVisible(false);
        frame.setTitle("Game");
        Game game = new Game();
        game.setup();
        frame.add(game);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        game.startGameTread();
    }
}