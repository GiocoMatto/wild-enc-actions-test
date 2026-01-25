package it.unibo.wildenc.mvc.model.enemies;

import java.util.Set;
import org.joml.Vector2d;
import org.joml.Vector2dc;
import it.unibo.wildenc.mvc.model.MapObject;
import it.unibo.wildenc.mvc.model.weaponary.weapons.Weapon;

public class CloseRangeEnemy extends AbstractEnemy{

    public CloseRangeEnemy(
        final Vector2dc spawnPosition, 
        final double hitbox, 
        final double movementSpeedfinal, 
        final int health,
        final Set<Weapon> weapons, 
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
    public Vector2dc alterDirection() {
        final var movement = direction(getTarget().getPosition(), this.getPosition());
        if (movement.lengthSquared() > 0) {
            return movement.normalize();
        }
        return new Vector2d(0 ,0);
    }
    
}
