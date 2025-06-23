package Base;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JPanel;
import Base.Tiles.TileMaker;
import Entitys.Player;
import Entitys.ZombieSpawner;

public class GamePanel extends JPanel implements Runnable {

    final int TileSize = 16;
    final int size = 5;
    public final int tileSize = TileSize * size;//80px
    final int maxScreenCol = 16;
    final int maxScreenrow = 12;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHight = tileSize * maxScreenrow;

    public int[][] map;
    public boolean madeMap;

    int FPS = 60;

    public Collide collider = new Collide(this);
    public CheckFight fightChecker = new CheckFight(this);
    Keys keyH = new Keys();
    TileMaker tileMAK = new TileMaker(this);
    Thread mainThread;
    public Player p = new Player(this, keyH);
    public ZombieSpawner z = new ZombieSpawner(this, null);

    public GamePanel()
    {
        this.setPreferredSize(new Dimension(screenWidth, screenHight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        map = getMap();
        z = new ZombieSpawner(this, map);
    }

    /**
     * gets the map
     * @return
     */
    public int[][] getterMap()
    {
        return getMap();
    }

    /**
     * starts the mainThread
     */
    public void mainThread()
    {
        mainThread = new Thread(this);
        mainThread.start();
    }

    /**
     * calls all the paints
     */
    public void paintComponent(Graphics g)
    {
        map = getMap();
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)(g);
        tileMAK.paint(g2, map);//tiles below
        z.paint(g2);//zombies
        p.paint(g2);//player on top
        g2.dispose();
    }

    /**
     * makes the map
     * 
     * gets the name of the txt file from calling getMapDIR()
     * scans through the txt file taking each num and index of
     * x and y sets the map value of every index to the number
     * type of the tile
     * @return
     */
    public int[][] getMap()
    {
        String mapDir = getMapDIR();
        int[][] map = new int[16][12];

        try
        {
            File mapF = new File(mapDir);
            Scanner reader = new Scanner(mapF);

            for(int row = 0; row < 12; row++)
            {
                for(int col = 0; col < 16; col++)
                {
                    int num = reader.nextInt();
                    map[col][row] = num;
                }
            }
            reader.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return map;
    }

    /**
     * switch case checking what map the Global state mapNum is
     * then gives the corresponding map dir
     * @return the map dir
     */
    public String getMapDIR()
    {
        String map = "";

        switch (GlobalStates.mapNum) {
            case 0:
                map = "src/Maps/OGmap.txt";
                break;
            case 1:
                map = "src/Maps/ModdedMap1.txt";
                break;
            case 2:
                map = "src/Maps/ModdedMap2.txt";
                break;
            case 3:
                map = "src/Maps/ModdedMap3.txt";
                break;
        }
        return map;
    }

    /**
     * calls all the updates
     * @throws InterruptedException 
     */
    public void update() throws InterruptedException
    {
        p.update();
        z.update();
    }



    // the code below was a mix of AI, looking online and my own work.
    //id say 80-90 percent not mine.

    /**
     * overrides run
     * 
     * calculates draw intervals using our FPS var
     * in here we call update and repaint
     */
    @Override
    public void run()
    {
        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(mainThread != null)
        {
            currentTime = System.nanoTime();

            timer += (currentTime - lastTime);
            lastTime = currentTime;

            //update
            try {
                update();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //draw
            repaint();
            drawCount++;

            if(timer>= 1000000000)
            {
                GlobalStates.FPS = drawCount;
                drawCount = 0;
                timer = 0;
            }

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;

                if(remainingTime < 0)
                {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;
            } 
            catch (InterruptedException e) 
            {
                e.getStackTrace();
            }
        }
    }
}
