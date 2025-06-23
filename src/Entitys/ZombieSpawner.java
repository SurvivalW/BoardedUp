package Entitys;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.Timer;
import Base.GamePanel;
import Base.GlobalStates;

public class ZombieSpawner {

    GamePanel gp;
    Random rand = new Random();

    public int ZombiesLeft = 0;
    int ZombieStart = 2;
    int ZombieAmount = 0;
    int ranWin = 0;
    double roundD;

    int[][] mapC = new int[16][12];

    ArrayList<Integer> xCspawn = new ArrayList<>();
    ArrayList<Integer> yCspawn = new ArrayList<>();

    public ArrayList<Zombie> zombies = new ArrayList<>();
    public List<Zombie> toRemove = new ArrayList<>();

    public ZombieSpawner(GamePanel gp, int[][] map)
    {
        this.gp = gp;
        mapC = map;
    }

    /**
     * if theres no more zombies it runs the next round
     * updates all the zombies
     * runs died to get rid of any dead zombies
     * @throws InterruptedException 
     */
    public void update() throws InterruptedException
    {
        synchronized (zombies)
        {
            ZombiesLeft = zombies.size();
            died();
    
            if (ZombiesLeft == 0)
            {
                GlobalStates.round++;
                nextRound();
            }
    
            for (Zombie z : zombies)
            {
                z.update();
            }
        }
    }
    

    //should have set up a method to figure out all the data from the map but too late now(its the 15th lol)
    /**
     * goes through the map data and finds the windows
     * then finds the floor tiles next to it
     * increase the zombie amount by 0.2
     * then spawns the zombies
     * @throws InterruptedException 
     */
    public void nextRound() throws InterruptedException
    {
        xCspawn.clear();
        yCspawn.clear();

        for(int i = 0; i < 12; i++)
        {
            for(int j = 0; j < 16; j++)
            {
                int data = mapC[j][i];
                if (data == 9 || data == 8 || data == 7 || data == 6)
                {
                    //calculate floor tiles for each col
                    if(j == 0)
                    {
                        if(mapC[j+1][i] == 1)
                        {
                            xCspawn.add(j+1);
                            yCspawn.add(i);
                        }
                    }
                    else if(j == 15)
                    {
                        if(mapC[j-1][i] == 1)
                        {
                            xCspawn.add(j-1);
                            yCspawn.add(i);
                        }
                    }
                    else
                    {
                        if(mapC[j-1][i] == 1)
                        {
                            xCspawn.add(j-1);
                            yCspawn.add(i);
                        }
                        if(mapC[j+1][i] == 1)
                        {
                            xCspawn.add(j+1);
                            yCspawn.add(i);
                        }
                    }

                    //calculate floor tiles for each row
                    if(i == 0)
                    {
                        if(mapC[j][i+1] == 1)
                        {
                            xCspawn.add(j);
                            yCspawn.add(i+1);
                        }
                    }
                    else if(i == 11)
                    {
                        if(mapC[j][i-1] == 1)
                        {
                            xCspawn.add(j);
                            yCspawn.add(i-1);
                        }
                    }
                    else
                    {
                        if(mapC[j][i-1] == 1)
                        {
                            xCspawn.add(j);
                            yCspawn.add(i-1);
                        }
                        if(mapC[j][i+1] == 1)
                        {
                            xCspawn.add(j);
                            yCspawn.add(i+1);
                        }
                    }
                }
            }
        }

        roundD = (double)GlobalStates.round;
        ZombieAmount = (int) Math.ceil(roundD * 1.2);

        if(GlobalStates.spawnTime <= 500)
        {
            
        }
        else
        {
            GlobalStates.spawnTime = GlobalStates.spawnTime - 100;
        }

        if(GlobalStates.round == 1)
        {
            ZombieAmount += ZombieStart;
        }
        ZombiesLeft = ZombieAmount;

        spawnTheZoms(ZombieAmount, xCspawn, yCspawn);
    }
    

    /**
     * picks a random window for each zombie
     * then spawns the zombie there
     * @param zomCount
     * @throws InterruptedException 
     */
    public void spawnTheZoms(int zomCount, ArrayList<Integer> xCspawn, ArrayList<Integer> yCspawn)
    {
        ZombieSpawnerThread ZST = new ZombieSpawnerThread(zomCount, xCspawn, yCspawn, gp, zombies);
        ZST.start();
    }
    

    /**
     * takes the graphics2D and goes
     * through each zombie then runs there paint method
     * @param g2D
     */
    public void paint(Graphics2D g2D)
    {
        for (Zombie z : zombies)
        {
            z.paint(g2D);
        }
    }

    /**
     * goes through the list of zombies that need
     * to be removed and removes them as well as add
     * 10 to the score
     */
    public void died()
    {
        for (Zombie z : toRemove)
        {
            GlobalStates.score = GlobalStates.score + 10;
            zombies.remove(z);
        }
        toRemove.clear();
    }
    
}
