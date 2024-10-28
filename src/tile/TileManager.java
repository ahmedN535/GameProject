package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;


public class TileManager {
    GamePanel gp;
    BufferedImage[] tile;
    int[][] mapTileNum;
    ArrayList<BufferedImage> allTiles = new ArrayList<>();


    public TileManager (GamePanel gp){
        this.gp = gp;
        tile = new BufferedImage[50];
        mapTileNum= new int[gp.maxScreenCol][gp.maxScreenRow];
        loadMap();
        allTiles =combineTiles(allTiles,"/res/Tiles/Water_Tile.png");
        allTiles= combineTiles(allTiles,"/res/Tiles/Cliff_Tile.png");
        allTiles =combineTiles(allTiles,"/res/Tiles/Beach_Tile.png");
        allTiles=combineTiles(allTiles,"/res/Tiles/Path_Tile.png");


    }



    public BufferedImage[] tileLoader (String path,int tileWidth,int tileHeight){
        try {
            BufferedImage tileSheet = ImageIO.read(getClass().getResourceAsStream( path));

            int row = tileSheet.getHeight()/tileHeight;
            int col = tileSheet.getWidth()/tileWidth;
            tile = new BufferedImage[row * col+1];

            int tileIndex =1;
            for(int y = 0; y<row; y++){
                for (int x = 0; x<col ; x++){
                    tile[tileIndex]= tileSheet.getSubimage(x*tileWidth,y*tileHeight,tileWidth,tileHeight);
                    tileIndex ++;
                }
            }


        }catch (IOException e){
            e.printStackTrace();
        }
        return tile;
    }
    public BufferedImage getTile(BufferedImage[] tile, int index) {
        if (index >= 0 && index < tile.length) {
            return tile[index];
        } else {
            throw new IndexOutOfBoundsException("Tile index out of bounds.");
        }
    }
    public int getTileCount(BufferedImage[] tiles){
        return tiles.length;

    }
    public ArrayList<BufferedImage> combineTiles(ArrayList<BufferedImage> allTiles ,String path) {

        BufferedImage[]tile  = tileLoader(path, 16, 16);
        for (int i =0 ; i< getTileCount(tile); i++){
            allTiles.add(getTile(tile,i));
        }
        return allTiles;

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

                String[] numbers = line.split(",");

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
            if (tilenum>19) {
                g2.drawImage(allTiles.get(tilenum + 1), x, y, gp.tileSize, gp.tileSize, null);
            }else {
                g2.drawImage(allTiles.get(tilenum ), x, y, gp.tileSize, gp.tileSize, null);
            }
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
