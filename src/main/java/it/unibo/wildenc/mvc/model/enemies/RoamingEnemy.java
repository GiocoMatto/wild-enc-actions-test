package it.unibo.wildenc.mvc.model.enemies;

import java.util.Optional;
import java.util.Random;
import org.joml.Vector2d;
import org.joml.Vector2dc;

/**
 * A enemy that run in random direction in the map and 
 * is immortal for some times by the spawn.
 */
public class RoamingEnemy extends AbstractEnemy {
    public static final int STEP_FOR_CHANGE_DIRECTION = 11;
    public static final long TIME_SAFE = 5000;
    private int steps;
    private long startTime;
    private final Random rand;
    private Vector2d actualTarget;

    /**
     * Create a new roaming Enemey.
     * 
     * @param spawnPosition The position of spawn.
     * @param hitbox The area of map where the player can hit the nemey.
     * @param movementSpeedfinal the speed of movement of the enemy.
     * @param health The health of the enemy.
     * @param name The name of the enemy.
     */
    public RoamingEnemy(
        final Vector2dc spawnPosition, 
        final double hitbox, 
        final double movementSpeedfinal, 
        final int health,
        final String name
    ) {
        super(
            spawnPosition, 
            hitbox, 
            movementSpeedfinal, 
            health, 
            name,
            Optional.empty()
        );
        this.rand = new Random();
        updateDirection();
        this.steps = 0;
        this.startTime = System.currentTimeMillis();
    }

    private void updateDirection() {
        this.actualTarget = new Vector2d(
            rand.nextInt() * STEP_FOR_CHANGE_DIRECTION + this.getPosition().x(), 
            rand.nextInt() * STEP_FOR_CHANGE_DIRECTION + this.getPosition().y()
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector2dc alterDirection() {
        if (this.steps == STEP_FOR_CHANGE_DIRECTION) {
            updateDirection();
            this.steps = 0;
        }
        this.steps++;
        return direction(actualTarget, this.getPosition()).normalize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canTakeDamage() {
        final long now = System.currentTimeMillis();
        if (now - this.startTime >= TIME_SAFE) {
            return true;
        }
        return false;
    }

}
