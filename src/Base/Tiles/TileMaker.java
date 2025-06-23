package Base.Tiles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import Base.GamePanel;
import Base.GlobalStates;

public class TileMaker {
    GamePanel gp;
    public Tile[] tile;


    public TileMaker(GamePanel gp)
    {
        this.gp = gp;//get the game panel
        tile = new Tile[14];// array of tiles [the number of tile types]

        getTileSprites();//load all the tile sprites
    }


    /**
     * loads tile sprites
     */
    public void getTileSprites()
    {
        try
        {
            tile[0] = new Tile();
            tile[0].sprite = ImageIO.read(getClass().getResource("/Sprites/Tiles/Nothing.png"));

            tile[1] = new Tile();
            tile[1].sprite = ImageIO.read(getClass().getResourceAsStream("/Sprites/Tiles/Floor.png"));

            tile[2] = new Tile();
            tile[2].collision = true;
            tile[2].sprite = ImageIO.read(getClass().getResource("/Sprites/Tiles/WallN.png"));

            tile[3] = new Tile();
            tile[3].collision = true;
            tile[3].sprite = ImageIO.read(getClass().getResource("/Sprites/Tiles/WallS.png"));

            tile[4] = new Tile();
            tile[4].collision = true;
            tile[4].sprite = ImageIO.read(getClass().getResource("/Sprites/Tiles/WallW.png"));

            tile[5] = new Tile();
            tile[5].collision = true;
            tile[5].sprite = ImageIO.read(getClass().getResource("/Sprites/Tiles/WallE.png"));

            tile[6] = new Tile();
            tile[6].collision = true;
            tile[6].sprite = ImageIO.read(getClass().getResource("/Sprites/Tiles/OpenWindowN.png"));

            tile[7] = new Tile();
            tile[7].collision = true;
            tile[7].sprite = ImageIO.read(getClass().getResource("/Sprites/Tiles/OpenWindowS.png"));

            tile[8] = new Tile();
            tile[8].collision = true;
            tile[8].sprite = ImageIO.read(getClass().getResource("/Sprites/Tiles/OpenWindowW.png"));

            tile[9] = new Tile();
            tile[9].collision = true;
            tile[9].sprite = ImageIO.read(getClass().getResource("/Sprites/Tiles/OpenWindowE.png"));

            tile[10] = new Tile();
            tile[10].collision = true;
            tile[10].sprite = ImageIO.read(getClass().getResource("/Sprites/Tiles/BoardedWindowN.png"));

            tile[11] = new Tile();
            tile[11].collision = true;
            tile[11].sprite = ImageIO.read(getClass().getResource("/Sprites/Tiles/BoardedWindowS.png"));

            tile[12] = new Tile();
            tile[12].collision = true;
            tile[12].sprite = ImageIO.read(getClass().getResource("/Sprites/Tiles/BoardedWindowW.png"));

            tile[13] = new Tile();
            tile[13].collision = true;
            tile[13].sprite = ImageIO.read(getClass().getResource("/Sprites/Tiles/BoardedWindowE.png"));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * reads the map that is made in a txt file
     * then paints the tiles in the corresponding rows and cols
     * @param g2 the graphics
     * @param map the 2D int array of the map
     */
    public void paint(Graphics2D g2, int[][] map)
    {
        int currTileType = 0;
        int Xcurr = 0;
        int Ycurr = 0;
        final int pxMove = 80;

        for(int i = 0; i < 12; i++)
        {
            for(int j = 0; j < 16; j++)
            {
                currTileType = map[j][i];
                Ycurr = (i * pxMove);
                Xcurr = (j * pxMove);
                g2.drawImage(tile[currTileType].sprite, Xcurr, Ycurr, gp.tileSize, gp.tileSize, null);
                
                if(GlobalStates.getHitBoxb())// If the user turned on hit box then it will show where the hit box is on the tiles that have collsion
                {
                    if(tile[currTileType].collision)
                    {
                        g2.setColor(Color.RED);
                        g2.drawRect(Xcurr, Ycurr, gp.tileSize, gp.tileSize);
                    }
                }
            }
        }
    }
}
