package Entitys;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import Base.Board;
import Base.GamePanel;
import Base.GlobalStates;
import Base.Keys;
import Base.PlaySound;



public class Player extends Entity {
    GamePanel gp;
    Keys keyH;
    // Collide collider = new Collide(gp);

    //Buffered Images that the player only use
    public BufferedImage dance1;
    public BufferedImage dance2;
    public BufferedImage dance3;
    public BufferedImage EatingApple1;
    public BufferedImage EatingApple2;
    public BufferedImage atk1;
    public BufferedImage atk2;
    public BufferedImage deadSprite;

    //ints the player only uses
    public int frameCounter = 0;
    public int frameCounterIdle = 0;
    public int frameCounterDance = 0;
    public int walkngSoundC = 0;
    public int spriteNum = 1;
    public int spriteNumIdle = 1;
    public int danceSN = 1;
    public int animationSpeed = 10;
    public int animationSpeedIdle = 30;
    public int animationSpeedDance = 15;
    public int walkingSoundT = 13;
    public int coolDownTimer = 0;
    public int coolDownTime = 20;
    public int damageVTime = 30;
    public int damageVTimer = 0;

    //booleans the player uses
    public static boolean dance = false;
    public static boolean attack = false;
    public boolean playSoundW = false;
    public boolean coolDown = false;
    public boolean damageV = false;
    public boolean moved = false;
    

    PlaySound ps = new PlaySound();

    public Player(GamePanel gp, Keys keyH)
    {
        this.gp = gp;
        this.keyH = keyH;
        collisionBox = new Rectangle(x + 25, y + 46, 32, 32);
        fightR = new Rectangle(x - 3, y - 5, 89, 89);

        defaultVars();
        playerAnimationFrames();
    }

    /**
     * sets default values
     */
    public void defaultVars()
    {
        x = 650;
        y = 600;
        speed = 6;
        HP = 4; //really 5
    }

    /**
     * loads all player sprites
     */
    public void playerAnimationFrames()
    {
        try
        {
            up1 = ImageIO.read(getClass().getResourceAsStream("/Sprites/PlayerSprites/up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/Sprites/PlayerSprites/up2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/Sprites/PlayerSprites/down1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/Sprites/PlayerSprites/down2.png"));
            idle1 = ImageIO.read(getClass().getResourceAsStream("/Sprites/PlayerSprites/idle1.png"));
            idle2 = ImageIO.read(getClass().getResourceAsStream("/Sprites/PlayerSprites/idle2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/Sprites/PlayerSprites/left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/Sprites/PlayerSprites/left2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/Sprites/PlayerSprites/right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/Sprites/PlayerSprites/right2.png"));

            atk1 = ImageIO.read(getClass().getResourceAsStream("/Sprites/PlayerSprites/ATK_1.png"));
            atk2 = ImageIO.read(getClass().getResourceAsStream("/Sprites/PlayerSprites/ATK_2.png"));
            
            EatingApple1 = ImageIO.read(getClass().getResourceAsStream("/Sprites/PlayerSprites/eatingApple1.png"));
            EatingApple2 = ImageIO.read(getClass().getResourceAsStream("/Sprites/PlayerSprites/eatingApple2.png"));

            dance1 = ImageIO.read(getClass().getResourceAsStream("/Sprites/PlayerSprites/dance1.png"));
            dance2 = ImageIO.read(getClass().getResourceAsStream("/Sprites/PlayerSprites/dance2.png"));
            dance3 = ImageIO.read(getClass().getResourceAsStream("/Sprites/PlayerSprites/dance3.png"));

            deadSprite = ImageIO.read(getClass().getResourceAsStream("/Sprites/PlayerSprites/DeadPlayer.png"));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Makes sure the player is dead if not checks keys pressed then runs the collision check
     * then updates movement then alternates the sprite animations.
     */
    public void update()
    {
        if(GlobalStates.playerDied)
        {
            
        }
        else
        {
            fightR.setBounds(x - 3, y - 5, 89, 89);

            damageVTimer++;

            if(damageVTime == damageVTimer)
            {
                damageV = false;
                damageVTimer = 0;
            }

            if(coolDownTime == coolDownTimer)
            {
                coolDown = false;
                coolDownTimer = 0;
            }
    
            if(coolDown)
            {
                coolDownTimer++;
            }
    
            if(attack)
            {
                if(!coolDown)
                {
                    attack = false;
                }
            }

    
            if(keyH.lPressed == true && !coolDown)
            {
                attack = true;
                coolDown = true;
                gp.fightChecker.checkP(this);
            }

            Board board = new Board(gp);

            if(keyH.SPACEPressed == true)
            {
                //board.BoardWindow(this);
            }
    
            GlobalStates.movedPub = moved;
            if(keyH.upPressed == true)
            {
                moved = true;
                direction = "up";
                dance = false;
            }
            else if(keyH.downPressed == true)
            {
                moved = true;
                direction = "down";
                dance = false;
            }
            else if(keyH.leftPressed == true)
            {
                moved = true;
                direction = "left";
                dance = false;
            }
            else if(keyH.rightPressed == true)
            {
                moved = true;
                direction = "right";
                dance = false;
            }
            else if(keyH.upPressed == false && keyH.downPressed == false && keyH.leftPressed == false && keyH.rightPressed == false)
            {
                direction = "idle";
                dance = false;
            }
    
            collisionON = false;
            gp.collider.checkTileCollision(this);
    
            if(!collisionON)//if there was no collision
            {
                //we need to check the direction to make sure to
                //apply force going against the players movement so they
                //"stop moving" but really the forces cancel out
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
    
            GlobalStates.playerX = x;
            GlobalStates.playerY = y;
    
            if(direction == "idle" && keyH.ePressed)
            {
                dance = true;
            }
    
            //this is for up, down, left, right.
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
    
            //this one is for idle only
            frameCounterIdle++;
            if(frameCounterIdle > animationSpeedIdle)
            {
                if(spriteNumIdle == 1)
                {
                    spriteNumIdle = 2;
                }
                else if(spriteNumIdle == 2)
                {
                    spriteNumIdle = 1;
                }
                frameCounterIdle = 0;
            }
    
            //this one is for dancing
            frameCounterDance++;
            if(frameCounterDance > animationSpeedDance)
            {
                if(danceSN == 1)
                {
                    danceSN = 2;
                }
                else if(danceSN == 2)
                {
                    danceSN = 3;
                }
                else if(danceSN == 3)
                {
                    danceSN = 1;
                }
                frameCounterDance = 0;
            }
    
    
            //this one is for walking sound
            playSoundW = false;
            walkngSoundC++;
            if(walkngSoundC > walkingSoundT)
            {
                playSoundW = true;
                walkngSoundC = 0;
            }
        }
    }


    /**
     * draws everything
     * @param g2D
     */
    public void paint(Graphics2D g2D)
    {
        BufferedImage image = idle1;

        if(GlobalStates.playerDied)
        {
            //player
            image = deadSprite;
            g2D.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);


            //background for stats to sit on
            g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            g2D.setColor(Color.BLACK);
            g2D.fillRect(500, 300, 350, 350);

            //score
            g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            g2D.setColor(Color.RED);
            g2D.setFont(new Font("Arial", Font.BOLD, 30));
            g2D.drawString("Score: " + GlobalStates.score, 600, 400);
                
            //round
            g2D.setColor(Color.RED);
            g2D.setFont(new Font("Arial", Font.BOLD, 30));
            if(GlobalStates.round <= 5)
            {
                g2D.drawString("Round: " + generateLString(GlobalStates.round), 600, 500);
            }
            else
            {
                g2D.drawString("Round: " + GlobalStates.round, 600, 500);
            }
        }
        else// Players not dead
        {
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
                case "idle":
                    if(spriteNumIdle == 1)
                    {
                        image = idle1;
                    }
                    if(spriteNumIdle == 2)
                    {
                        image = idle2;
                    }
                    break;
            }
    
            if(dance)
            {
                if(danceSN == 1)
                {
                    image = dance1;
                }
                if(danceSN == 2)
                {
                    image = dance2;
                }
                if(danceSN == 3)
                {
                    image = dance3;
                }
                g2D.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
            }
            else if(attack)
            {
                if(spriteNum == 1)
                {
                    image = atk1;
                }
                if(spriteNum == 2)
                {
                    image = atk2;
                }
                g2D.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
            }
            else
            {
                if((direction == "up" || direction == "down" || direction == "left" || direction == "right") && (playSoundW))
                {
                    walking();//plays walking sounds
                }
                g2D.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
                
            }
    
            if(!moved)
            {
                g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
                g2D.setColor(Color.BLACK);
                g2D.fillRect(500, 300, 350, 350);
                
                g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                g2D.setColor(Color.RED);
                g2D.setFont(new Font("Arial", Font.BOLD, 30));
                g2D.drawString("Use 'W, A, S, D' for", 520, 400);
                g2D.drawString("movement", 520, 450);
                g2D.drawString("and 'L' for attack", 520, 550);
            }

            if(GlobalStates.getHitBoxb())
            {
                g2D.setColor(Color.RED);
                g2D.drawRect(x + 25, y + 46, 32, 32);//players hit box
    
                g2D.setColor(Color.GREEN);
                g2D.drawRect(x - 3, y - 5, 89, 89);//players fight box

                g2D.setColor(Color.WHITE);
                g2D.setFont(new Font("Arial", Font.BOLD, 15));
                g2D.drawString("Px: " + GlobalStates.playerX, 1100, 50);//players x

                g2D.setColor(Color.WHITE);
                g2D.setFont(new Font("Arial", Font.BOLD, 15));
                g2D.drawString("Py: " + GlobalStates.playerY, 1100, 100);//players y

                g2D.setColor(Color.WHITE);
                g2D.setFont(new Font("Arial", Font.BOLD, 15));
                g2D.drawString("FPS: " + GlobalStates.FPS, 1100, 150);//FPS
            }

            if(damageV)
            {
                //makes screen red when hit
                g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
                g2D.setColor(Color.RED);
                g2D.fillRect(0, 0, 1280, 960);
            }
    
            //score
            g2D.setColor(Color.RED);
            g2D.setFont(new Font("Arial", Font.BOLD, 30));
            g2D.drawString("Score: " + GlobalStates.score, 20, 50);
    
            //round
            g2D.setColor(Color.RED);
            g2D.setFont(new Font("Arial", Font.BOLD, 30));
            if(GlobalStates.round <= 5)
            {
                g2D.drawString("Round: " + generateLString(GlobalStates.round), 20, 100);
            }
            else
            {
                g2D.drawString("Round: " + GlobalStates.round, 20, 100);
            }
    
            int HPP = HP + 1;//to print correct HP level
            g2D.setColor(Color.RED);
            g2D.setFont(new Font("Arial", Font.BOLD, 30));
            g2D.drawString("HP: " + HPP, 20, 150);//HP
        }
    }

    /**
     * turns the number in the amount of l's
     * @param num the number
     * @return the amount of l's
     */
    public static String generateLString(int num)
    {
        if (num <= 0) {
            return "";
        }
        return "l ".repeat(num).trim();
    }

    /**
     * plays walking sounds
     */
    public void walking()
    {
        try
        {
            Clip clip;
            File soundFile = new File("src/AllAudio/SoundFX/walk.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        }
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * takes damage
     * @param damage the damage taken
     */
    public void takeDam(int damage)
    {
        HP = HP - damage;
        damageV = true;//to make the screen red
    }
}

