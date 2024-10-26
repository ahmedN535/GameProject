package main;

import Entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {


    int fps= 60;

    final int originalTileSize = 16;
    final int scale = 2;
    public final int tileSize = scale * originalTileSize;
    public final int maxScreenCol= 32;
    public final int maxScreenRow=24;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    Thread gameThread;
    KeyInputs keyinputs = new KeyInputs();
    Player player = new Player(this,keyinputs);
    TileManager tileM = new TileManager(this);


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
        tileM.draw(g2);
        player.draw(g2);
        g2.dispose();
    }
}
