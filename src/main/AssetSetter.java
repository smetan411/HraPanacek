package main;

import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {

        // klíč 1
        gp.obj[0] = new OBJ_Key();
        gp.obj[0].worldX = 37 * gp.tileSize;
        gp.obj[0].worldY = 6 * gp.tileSize;
        // klíč 2
        gp.obj[1] = new OBJ_Key();
        gp.obj[1].worldX = 19 * gp.tileSize;
        gp.obj[1].worldY = 22 * gp.tileSize;
        // klíč 3
        gp.obj[2] = new OBJ_Key();
        gp.obj[2].worldX = 34 * gp.tileSize;
        gp.obj[2].worldY = 40 * gp.tileSize;
        // dveře 1
        gp.obj[3] = new OBJ_Door();
        gp.obj[3].worldX = 19 * gp.tileSize;
        gp.obj[3].worldY = 18 * gp.tileSize;
        // dveře 2
        gp.obj[4] = new OBJ_Door();
        gp.obj[4].worldX = 15 * gp.tileSize;
        gp.obj[4].worldY = 24 * gp.tileSize;
        // dveře 3
        gp.obj[5] = new OBJ_Door();
        gp.obj[5].worldX = 8 * gp.tileSize;
        gp.obj[5].worldY = 18 * gp.tileSize;
        // truhla
        gp.obj[6] = new OBJ_Chest();
        gp.obj[6].worldX = 11 * gp.tileSize;
        gp.obj[6].worldY = 16 * gp.tileSize;
        // boty
        gp.obj[7] = new OBJ_Boots();
        gp.obj[7].worldX = 18 * gp.tileSize;
        gp.obj[7].worldY = 32 * gp.tileSize;
    }
}
