package main;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("GAME");
        frame.setResizable(false);
        GamePanel gamePanel = new GamePanel();

        frame.add(gamePanel);
        frame.pack();
        gamePanel.startGameThread();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }
}