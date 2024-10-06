package game.object;

import game.entity.Entity;
import game.entity.Player;
import game.graphics.GameHud;
import game.core.Sound;
import game.world.WorldLocation;

public final class Chest extends GameObject {

    public Chest(WorldLocation position){
        super("/objects/chest.png", position);
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public void handleCollisionEvent(Entity entity, GameHud gameHud, GameObjectsManager gameObjectsManager) {
        if (!(entity instanceof Player)) return;
        Sound sound = new Sound("/sound/fanfare.wav");
        sound.play();
        gameHud.stopGame();
    }
}
