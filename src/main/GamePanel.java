package main;

import Entity.Player;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {


    int fps= 60;

    final int originalTileSize = 16;
    final int scale = 5;
    public final int tileSize = scale * originalTileSize;
    final int maxScreenCol= 16;
    final int maxScreenRow=12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    Thread gameThread;
    KeyInputs keyinputs = new KeyInputs();
    Player player = new Player(this,keyinputs);


    public GamePanel (){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyinputs);
        this.setFocusable(true);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        long frameTime = 1000000000 / fps;
        long lastTime = System.nanoTime();
        long delta = 0;
        long currentTime;
        while (gameThread != null) {
            currentTime= System.nanoTime();
            delta += (currentTime-lastTime) /frameTime;

            if (delta >= 1){
                update();
                repaint();
                lastTime = currentTime;
                delta --;
            }

        }
    }
    public void update (){
        player.update();

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        player.draw(g2);
        g2.dispose();
    }
}
