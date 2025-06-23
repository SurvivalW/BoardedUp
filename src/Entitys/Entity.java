package Entitys;

import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import Base.GamePanel;

public class Entity {
    public int speed;
    public int x;
    public int y;
    public int HP;
    public int damage;

    public String direction;
    public Rectangle fightR;
    public Rectangle collisionBox;
    public boolean collisionON;
    
    public BufferedImage up1;
    public BufferedImage up2;
    public BufferedImage down1;
    public BufferedImage down2;
    public BufferedImage right1;
    public BufferedImage right2;
    public BufferedImage left1;
    public BufferedImage left2;
    public BufferedImage idle1;
    public BufferedImage idle2;
}
