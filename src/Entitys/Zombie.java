package Entitys;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Base.GamePanel;
import Base.GlobalStates;

public class Zombie extends Entity {
    
    public BufferedImage dead;

    public int frameCounter = 0;
    public int spriteNum = 1;
    public int animationSpeed = 5;
    public int Dwait = 120;
    public int attackSpeed;
    public int atkT = 0;

    public boolean zombieDED = false;

    GamePanel gp;
    int lvlZ;
    int moveTimer = 0;
    int moveLockDuration = 30;
    boolean movingHorizontally = true;
    final int threshold = 5;

    public Zombie(GamePanel gp, int lvlZ, int spawnX, int spawnY)
    {
        this.gp = gp;
        this.lvlZ = lvlZ;

        collisionBox = new Rectangle(x + 25, y + 46, 32, 32);
        fightR = new Rectangle(x + 3, y + 5, 89, 89);

        defaultVars(spawnX, spawnY);
        loadAnims();
    }

    /**
     * sets the players x to the spawnX and the same to y
     * sets some default values
     * @param spawnX players start x
     * @param spawnY players start y
     */
    public void defaultVars(int spawnX, int spawnY)
    {
        x = spawnX;
        y = spawnY;
        speed = 2;
        HP = 1;
        damage = 1;
        attackSpeed = 80;
        direction = "down";
    }

    /**
     * loads all the sprites for the zombie
     */
    public void loadAnims()
    {
        try
        {
            up1 = ImageIO.read(getClass().getResourceAsStream("/Sprites/ZombieSprites/zombie-U-1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/Sprites/ZombieSprites/zombie-U-2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/Sprites/ZombieSprites/zombie-D-1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/Sprites/ZombieSprites/zombie-D-2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/Sprites/ZombieSprites/zombie-L-1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/Sprites/ZombieSprites/zombie-L-2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/Sprites/ZombieSprites/zombie-R-1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/Sprites/ZombieSprites/zombie-R-2.png"));

            dead = ImageIO.read(getClass().getResourceAsStream("/Sprites/ZombieSprites/zombie-death.png"));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * makes it so if the player is dancing then the zombie cant move
     * has a timer for when the zombie dies and waits before despawning the zombie
     * runs the movement and attacks
     * and sets the animation speeds and directions
     */
    public void update()
    {
        if(!GlobalStates.movedPub)
        {

        }
        else
        {
            if(gp.p.dance)
            {
                
            }
            else
            {
                if (zombieDED)
                {
                    Dwait--;
                    if (Dwait <= 0)
                    {
                        gp.z.toRemove.add(this);
                    }
                }
                else
                {
                    fightR.setBounds(x + 20, y + 25, 49, 49);
        
                    if(attackSpeed == atkT)
                    {
                        atkT = 0;
                        gp.fightChecker.checkZ(this);
                    }
                    atkT++;
            
                    if(lvlZ == 0)
                    {
                        speed = 2;
                        HP = 1;
                    }
                    if(lvlZ == 1)
                    {
                        speed = 3;
                        HP = 5;
                    }
                
                    //looked this up
                    //link: https://stackoverflow.com/questions/25128545/java-enemy-follow-player
                
                
                    //calculate the difference in position
                    int diffX = GlobalStates.playerX - x;
                    int diffY = GlobalStates.playerY - y;
                
                    if(Math.abs(diffX) < threshold && Math.abs(diffY) > threshold)
                    {
                        movingHorizontally = false;
                    }
                    //If the difference in y is within the threshold and the difference in x is larger. switch to x
                    else if(Math.abs(diffY) < threshold && Math.abs(diffX) > threshold)
                    {
                        movingHorizontally = true;
                    }
                    
                    if(moveTimer >= moveLockDuration)
                    {
                        moveTimer = 0;//reset counter
                        movingHorizontally = !movingHorizontally;//Switch direction
                    }
                
                    //move in the locked direction
                    if (movingHorizontally)
                    {
                        if (diffX > 0)
                        {
                            direction = "right";
                        }
                        else
                        {
                            direction = "left";
                        }
                    }
                    else
                    {
                        if (diffY > 0)
                        {
                            direction = "down";
                        }
                        else
                        {
                            direction = "up";
                        }
                    }
                
                    collisionON = false;
                    gp.collider.checkTileCollision(this);
                    //gp.collider.checkZombieCollision(this);
                
                    if(!collisionON)//if there was no collision
                    {
                        switch (direction)
                        {
                            case "up":
                                y -= speed;
                                break;
                            case "down":
                                y += speed;
                                break;
                            case "right":
                                x += speed;
                                break;
                            case "left":
                                x -= speed;
                                break;
                        }
                    }
                frameCounter++;
                if(frameCounter > animationSpeed)
                {
                    if(spriteNum == 1)
                        {
                            spriteNum = 2;
                        }
                        else if(spriteNum == 2)
                        {
                            spriteNum = 1;
                        }
                        frameCounter = 0;
                    }
                
                    moveTimer++;//increment frame counter
                    
                    //somehow this works, took so long ):
                }
            }
        }
    }
    

    /**
     * draws the direction
     * draws the hit box if the user turned that on
     * @param g2D
     */
    public void paint(Graphics2D g2D)
    {
        BufferedImage image = null;

        switch (direction)
        {
            case "up":
                if(spriteNum == 1)
                {
                    image = up1;
                }
                if(spriteNum == 2)
                {
                    image = up2;
                }
                break;
            case "down":
                if(spriteNum == 1)
                {
                    image = down1;
                }
                if(spriteNum == 2)
                {
                    image = down2;
                }
                break;
            case "left":
                if(spriteNum == 1)
                {
                    image = left1;
                }
                if(spriteNum == 2)
                {
                    image = left2;
                }
                break;
            case "right":
                if(spriteNum == 1)
                {
                    image = right1;
                }
                if(spriteNum == 2)
                {
                    image = right2;
                }
                break;
        }

        if(zombieDED)
        {
            image = dead;
        }

        g2D.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);

        if(GlobalStates.hitBoxb)
        {
            g2D.setColor(Color.RED);
            g2D.drawRect(x + 25, y + 46, 32, 32);

            g2D.setColor(Color.GREEN);
            g2D.drawRect(x + 20, y + 25, 49, 49);
        }
    }
}
