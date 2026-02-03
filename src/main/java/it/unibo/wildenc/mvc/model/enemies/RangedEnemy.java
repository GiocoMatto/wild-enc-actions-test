package it.unibo.wildenc.mvc.model.enemies;

import java.util.Optional;
import org.joml.Vector2d;
import org.joml.Vector2dc;
import it.unibo.wildenc.mvc.model.MapObject;
import it.unibo.wildenc.mvc.model.map.CollisionLogic;

/**
 * A enemy that attach to a specific distance by the player.
 */
public class RangedEnemy extends AbstractEnemy {
    public static final int MAX_DISTANCE = 100;
    public static final int MIN_DISTANCE = 80;

    /**
     * Create a new ranged Enemey.
     * 
     * @param spawnPosition The position of spawn.
     * @param hitbox The area of map where the player can hit the nemey.
     * @param movementSpeedfinal the speed of movement of the enemy.
     * @param health The health of the enemy.
     * @param name The name of the enemy.
     * @param target The Optional Position of the player to hit.
     */
    public RangedEnemy(
        final Vector2dc spawnPosition, 
        final double hitbox, 
        final double movementSpeedfinal, 
        final int health,
        final String name,
        final Optional<MapObject> target
    ) {
        super(
            spawnPosition, 
            hitbox, 
            movementSpeedfinal, 
            health, 
            name,
            target
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector2dc alterDirection() {
        if (!CollisionLogic.areInRange(this, getTarget().get(), MAX_DISTANCE)) {
            return direction(getTarget().get().getPosition(), this.getPosition()).normalize();
        } else if (CollisionLogic.areInRange(this, getTarget().get(), MIN_DISTANCE)) {
            return direction(this.getPosition(), getTarget().get().getPosition()).normalize();
        }
        return new Vector2d(0, 0);
    }

}
