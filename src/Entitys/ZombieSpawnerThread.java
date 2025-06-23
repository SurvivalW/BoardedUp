package Entitys;

import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;
import javax.swing.Timer;
import Base.GamePanel;
import Base.GlobalStates;

public class ZombieSpawnerThread extends Thread {

    GamePanel gp;
    Random rand = new Random();
    int zomCount;
    ArrayList<Integer> xCspawn;
    ArrayList<Integer> yCspawn;
    ArrayList<Zombie> zombies;

    public ZombieSpawnerThread(int zomCount, ArrayList<Integer> xCspawn, ArrayList<Integer> yCspawn, GamePanel gp, ArrayList<Zombie> zombies)
    {
        this.zomCount = zomCount;
        this.xCspawn = xCspawn;
        this.yCspawn = yCspawn;
        this.gp = gp;
        this.zombies = zombies;
    }

    @Override
    public void run()
    {
        for (int i = 0; i < zomCount; i++)
        {
            int ranWin = rand.nextInt(xCspawn.size());
            int spawnX = xCspawn.get(ranWin) * 80;
            int spawnY = yCspawn.get(ranWin) * 80;

            synchronized (zombies) {
                zombies.add(new Zombie(gp, 0, spawnX, spawnY));
            }

            try {
                Thread.sleep(GlobalStates.spawnTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

