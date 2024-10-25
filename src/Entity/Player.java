package Entity;

import main.GamePanel;
import main.KeyInputs;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;


public class Player extends Entity {
    GamePanel gp;
    KeyInputs keyinputs;
    public BufferedImage[][] animationsw;
    public BufferedImage[][] animationsi;


    public Player(GamePanel gp, KeyInputs keyinputs) {
        this.gp = gp;
        this.keyinputs = keyinputs;
        setDefaultPlayer();
        animationsw = new BufferedImage[4][8];
        animationsi = new BufferedImage[4][4];
        loadAnimationwalking("/res/PlayerAnimations/walk.png");
        loadAnimatidling("/res/PlayerAnimations/idle.png");



    }

    public void setDefaultPlayer() {
        x = 100;
        y = 100;
        speed = 5;
    }

    int direction;
    boolean idle=false;
    int currentframe = 0;
    long lastTime;
    long lenframe = 100;

    public void update() {
        long currenttime = System.currentTimeMillis();
        if (currenttime - lastTime > lenframe) {
            currentframe++;
            if (currentframe >= 4) {
                currentframe = 0;
            }
            lastTime = currenttime;
        }

        if (x > gp.screenWidth) { // If player goes beyond right edge
            x = -150; // Move to left side
        } else if (x < -150) { // If player goes beyond left edge
            x = gp.screenWidth; // Move to right side
        }

// Vertical boundary check
        if (y > gp.screenHeight) { // If player goes beyond bottom edge
            y = -150; // Move to top side
        } else if (y < -150) { // If player goes beyond top edge
            y = gp.screenHeight; // Move to bottom side
        }


        if (keyinputs.Reset) {
            x = 100;
            y = 100;
        }


        if (keyinputs.upPressed) {
            direction = UP;
            y -= speed;
            idle = false;
            System.out.println( y);

        } else if (keyinputs.downPressed) {
            direction = DOWN;
            y += speed;
            idle = false;
            System.out.println( y);
        } else if (keyinputs.leftPressed) {
            direction = LEFT;
            x -= speed;
            idle =false;
            System.out.println(x );

        } else if (keyinputs.rightPressed) {
            direction = RIGHT;
            x += speed;
            idle = false;
            System.out.println(x);



    }else {
            idle = true;
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image;
        if (idle) {
            image = animationsi[direction][currentframe];
        } else {
            image = animationsw[direction][currentframe];
        }
        g2.drawImage(image, x, y, gp.tileSize * 3, gp.tileSize * 3, null);
    }


    public int UP = 3;
    public int DOWN = 2;
    public int LEFT = 1;
    public int RIGHT = 0;
    public final int framewidth = 80;
    public final int frameheight = 80;


    public void loadAnimationwalking(String filename) {
        try {
            BufferedImage spritesheetwalking = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(filename)));
            if (spritesheetwalking == null) {
                System.out.println("Failed to load image: " + filename);
            }
            for (int direction = 0; direction < 4; direction++) {
                for (int frame = 0; frame < 8; frame++) {
                    int x = frame * framewidth;
                    int y = direction * frameheight;
                    assert spritesheetwalking != null;
                    animationsw[direction][frame] = spritesheetwalking.getSubimage(x, y, framewidth, frameheight);


                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadAnimatidling(String filename) {

        try {
            BufferedImage spritesheetwalking = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(filename)));
            if (spritesheetwalking == null) {
                System.out.println("Failed to load image: " + filename);
            }
            for (int direction = 0; direction < 4; direction++) {
                for (int frame = 0; frame < 4; frame++) {
                    int x = frame * framewidth;
                    int y = direction * frameheight;
                    assert spritesheetwalking != null;
                    animationsi[direction][frame] = spritesheetwalking.getSubimage(x, y, framewidth, frameheight);


                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
