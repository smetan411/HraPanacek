package game.object;

import game.entity.Entity;
import game.entity.Player;
import game.graphics.GameHud;
import game.core.Sound;
import game.world.WorldLocation;

public final class Door extends GameObject {

    private static final int NEXT_COLLISION_IN = 100;
    private int collisionCounter = NEXT_COLLISION_IN;
    public Door(WorldLocation position){
        super("/objects/door.png", position);
    }

    @Override
    public boolean isSolid() {
        return true;
    }

    @Override
    public void handleCollisionEvent(Entity entity, GameHud gameHud, GameObjectsManager gameObjectsManager) {
        if (!(entity instanceof Player player)) return;
        if (collisionCounter++ < NEXT_COLLISION_IN)  return;
        collisionCounter = 0;
        Sound sound = new Sound("/sound/unlock.wav");
        sound.play();
        if (player.getKeyCount() > 0) {
            player.useKey();
            gameObjectsManager.removeObject(this);
            gameHud.showMessage("You opened the door.");
        } else {
            gameHud.showMessage("You need a key!");
        }
    }
}
