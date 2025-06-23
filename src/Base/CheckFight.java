package Base;

import java.awt.Rectangle;
import java.util.ArrayList;

import Entitys.Entity;

public class CheckFight {
    
    GamePanel gp;

    public CheckFight(GamePanel gp)
    {
        this.gp = gp;
    }

    /**
     * checks if the player can attack
     * checks every zombies fightR(the box of there attack range)
     * and takes the players fightR then checks if they intersect
     * using .intersects if so then we set that zombies is dead boolean to true
     * adds 10 to the score and we use a global state currZomIndex to make sure
     * when we kill the zombie we have the correct zombie
     * @param entity the player
     */
    public void checkP(Entity entity)
    {
        for (int i = 0; i < gp.z.zombies.size(); i++)
        {
            if (entity.fightR.intersects(gp.z.zombies.get(i).fightR))
            {
                gp.z.zombies.get(i).zombieDED = true;
                GlobalStates.currZomIndex = i;
                return;
            }
        }
    }


    /**
     * checks if the zombie can attack
     * gets the zombie fightR and checks if it intersects
     * the players fightR if so we make the player take the
     * amount of damage that the zombie can give
     * then checks if the players HP is less then or equal to zero
     * if so then we set playerDied to true
     * @param entity the zombie
     */
    public void checkZ(Entity entity)
    {
        if(entity.fightR.intersects(gp.p.fightR))
        {
            if(gp.p.HP <= 0)
            {
                GlobalStates.playerDied = true;
            }
            gp.p.takeDam(entity.damage);
        }
    }
}
