package Base;

public class GlobalStates {

    public static boolean hitBoxb = false;
    public static boolean playerDied = false;
    public static boolean movedPub = false;

    public static int mapNum = 0;
    public static int score = 0;
    public static int round = 0;
    public static int FPS = 0;

    public static int playerY = 0;
    public static int playerX = 0;

    public static int currZomIndex = 0;

    public static volatile long spawnTime = 2000;

    public GlobalStates()
    {

    }

    public static boolean getHitBoxb()
    {
        return hitBoxb;
    }
    public static void setHitBoxb(boolean newhitBoxb)
    {
        hitBoxb = newhitBoxb;
    }
}
