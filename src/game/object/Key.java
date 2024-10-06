package game.object;

import game.entity.Entity;
import game.entity.Player;
import game.graphics.GameHud;
import game.core.Sound;
import game.world.WorldLocation;

public final class Key extends GameObject {

    public Key(WorldLocation position){
        super("/objects/key.png", position);
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public void handleCollisionEvent(Entity entity, GameHud gameHud, GameObjectsManager gameObjectsManager) {
        if (!(entity instanceof Player player)) return; //jenom hrac muze vzit klic
        Sound sound = new Sound("/sound/coin.wav");
        sound.play();
        player.pickUpKey();
        gameHud.showMessage("You got a key!");
        gameObjectsManager.removeObject(this);
    }
}
