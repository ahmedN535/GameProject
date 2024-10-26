package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {
    GamePanel gp;
    Tiles[] tile;
    int[][] mapTileNum;

    public TileManager (GamePanel gp){
        this.gp = gp;
        tile = new Tiles[10];
        mapTileNum= new int[gp.maxScreenCol][gp.maxScreenRow];
        loadMap();
        getTileImage();

    }

    public void getTileImage (){
        try {
            tile[0] = new Tiles();
            tile[0].image= ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/Tiles/Grass_Middle.png")));
            tile[1] = new Tiles();
            tile[1].image= ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/Tiles/Water_Middle.png")));
            tile[2] = new Tiles();
            tile[2].image= ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/Tiles/Path_Middle.png")));



        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void loadMap (){
        try {
            InputStream is = getClass().getResourceAsStream("/res/maps/map.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int col = 0;
            int row = 0;
            while (row < gp.maxScreenRow) {
                String line = br.readLine();
                if (line == null) break;// Break if there are no more lines
                System.out.println(line);

                String[] numbers = line.split(" ");

                for (col = 0; col < gp.maxScreenCol; col++) {
                    mapTileNum[col][row] = Integer.parseInt(numbers[col]);
                    System.out.println(mapTileNum[col][row]);
                }

                row++;
            }
            br.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }






    }

    public void draw (Graphics2D g2) {
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;
        while (col < gp.maxScreenCol && row < gp.maxScreenRow) {

            int tilenum = mapTileNum[col][row];
            g2.drawImage(tile[tilenum].image,x,y,gp.tileSize,gp.tileSize,null );
            col ++;
            x += gp.tileSize;
            if (col == gp.maxScreenCol){
                col =0;
                x=0;
                row ++;
                y += gp.tileSize;
            }
        }

        }


    }
