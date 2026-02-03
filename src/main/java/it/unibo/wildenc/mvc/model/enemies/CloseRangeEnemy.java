package it.unibo.wildenc.mvc.model.enemies;

import java.util.Optional;
import org.joml.Vector2d;
import org.joml.Vector2dc;
import it.unibo.wildenc.mvc.model.MapObject;

/**
 * A enemy that move to the palyer until their hitbox collaps.
 */
public class CloseRangeEnemy extends AbstractEnemy {

    /**
     * Create a new close range Enemey.
     * 
     * @param spawnPosition The position of spawn.
     * @param hitbox The area of map where the player can hit the nemey.
     * @param movementSpeedfinal the speed of movement of the enemy.
     * @param health The health of the enemy.
     * @param name The name of the enemy.
     * @param target The Optional Position of the player to hit.
     */
    public CloseRangeEnemy(
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
        final var movement = direction(getTarget().get().getPosition(), this.getPosition());
        if (movement.lengthSquared() > 0) {
            return movement.normalize();
        }
        return new Vector2d(0, 0);
    }

}
