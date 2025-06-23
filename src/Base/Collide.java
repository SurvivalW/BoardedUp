package Base;

import Entitys.Entity;
import Entitys.Zombie;

public class Collide {
    GamePanel gp;

    public Collide(GamePanel gp)
    {
        this.gp = gp;
    }

    /**
     * checks for tile collision the way i set it up its so that any entity can be used
     * 
     * depending on the direction of the entity
     * it uses the rightcol, leftcol, toprow, bottomrow. to check if its about to hit
     * a tile that has collision if so then we set collision to true
     * @param entity the player or zombie
     */
    public void checkTileCollision(Entity entity)
    {
        //for the math i had chatgpt help me.
        //I also used BroCode on yt and some random sites i forgot to get the link of
        //(i found this part kinda fun ngl)
        int LeftX = entity.x + entity.collisionBox.x;
        int RightX = entity.x + entity.collisionBox.x + entity.collisionBox.width;
        int TopY = entity.y + entity.collisionBox.y;
        int BottomY = entity.y + entity.collisionBox.y + entity.collisionBox.height;

        int LeftCol = LeftX/gp.tileSize;
        int RightCol = RightX/gp.tileSize;
        int TopRow = TopY/gp.tileSize;
        int BottomRow = BottomY/gp.tileSize;

        int checkTile1;
        int checkTile2;

        //we need to figure out which way the
        //character is moving so were checking
        //the tiles using the two points of the collision box
        switch (entity.direction) {
            case "up":
                TopRow = (TopY - entity.speed)/gp.tileSize;
                checkTile1 = gp.getterMap()[LeftCol][TopRow];
                checkTile2 = gp.getterMap()[RightCol][TopRow];

                if(gp.tileMAK.tile[checkTile1].collision || gp.tileMAK.tile[checkTile2].collision)
                {
                    entity.collisionON = true;
                }
                break;

            case "down":
                BottomRow = (BottomY + entity.speed)/gp.tileSize;
                checkTile1 = gp.getterMap()[LeftCol][BottomRow];
                checkTile2 = gp.getterMap()[RightCol][BottomRow];

                if(gp.tileMAK.tile[checkTile1].collision || gp.tileMAK.tile[checkTile2].collision)
                {
                    entity.collisionON = true;
                }
                break;

            case "right":
                RightCol = (RightX + entity.speed)/gp.tileSize;
                checkTile1 = gp.getterMap()[RightCol][TopRow];
                checkTile2 = gp.getterMap()[RightCol][BottomRow];

                if(gp.tileMAK.tile[checkTile1].collision || gp.tileMAK.tile[checkTile2].collision)
                {
                    entity.collisionON = true;
                }
                break;

            case "left":
                LeftCol = (LeftX - entity.speed)/gp.tileSize;
                checkTile1 = gp.getterMap()[LeftCol][TopRow];
                checkTile2 = gp.getterMap()[LeftCol][BottomRow];
                
                if(gp.tileMAK.tile[checkTile1].collision || gp.tileMAK.tile[checkTile2].collision)
                {
                    entity.collisionON = true;
                }
                break;
        }
    }


    //it said no trash code but im in the middle of implimenting this
    // public void checkZombieCollision(Entity entity)
    // {
    //     for (Zombie other : gp.z.zombies)
    //     {
    //         if (other != entity && entity.collisionBox.intersects(other.collisionBox))
    //         {

    //             int dx = entity.x - other.x;
    //             int dy = entity.y - other.y;
        
    //             if (Math.abs(dx) > Math.abs(dy))
    //             {
    //                 entity.x += dx > 0 ? 1 : -1; // Move right if dx is positive, left if negative
    //             } else {
    //                 entity.y += dy > 0 ? 1 : -1; // Move down if dy is positive, up if negative
    //             }

    //             entity.collisionON = true;
    //         }
    //     }
    // }
}
