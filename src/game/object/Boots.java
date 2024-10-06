package game.object;

import game.entity.Entity;
import game.entity.Player;
import game.graphics.GameHud;
import game.core.Sound;
import game.world.WorldLocation;

public final class Boots extends GameObject {

    public Boots(WorldLocation position) {
        super("/objects/boots.png", position);
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public void handleCollisionEvent(Entity entity, GameHud gameHud, GameObjectsManager gameObjectsManager) {
        if (!(entity instanceof Player player)) return;
        Sound sound = new Sound("/sound/powerup.wav");
        sound.play();
        gameHud.showMessage("Speed up!");
        gameObjectsManager.removeObject(this);
        player.speedUp(2);
    }
}
