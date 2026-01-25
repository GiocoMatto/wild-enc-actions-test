package it.unibo.wildenc.mvc.model.enemies;

import java.util.List;
import org.joml.Vector2dc;

import it.unibo.wildenc.mvc.model.MapObject;
import it.unibo.wildenc.mvc.model.weaponary.weapons.Weapon;

public class RangedEnemy extends AbstractEnemy {
    private static final int MIN_DISTANCE = 100;

    public RangedEnemy(
        final Vector2dc spawnPosition, 
        final double hitbox, 
        final double movementSpeedfinal, 
        final int health,
        final List<Weapon> weapons, 
        final String name,
        final MapObject target
    ) {
        super(
            spawnPosition, 
            hitbox, 
            movementSpeedfinal, 
            health, 
            weapons, 
            name,
            target
        );
    }

    @Override
    public Vector2dc specificMovement() {
        throw new IllegalStateException();
    }

}
