package it.unibo.wildenc.mvc.model.enemies;

import java.util.Set;
import org.joml.Vector2d;
import org.joml.Vector2dc;
import it.unibo.wildenc.mvc.model.weaponary.weapons.Weapon;
import it.unibo.wildenc.mvc.model.*;
import it.unibo.wildenc.mvc.model.entities.AbstractEntity;

public abstract class AbstractEnemy extends AbstractEntity implements Enemy {
    private final MapObject target;
    private final String name;
    
    /**
     * Create a General Enemy without the logic of attack.
     * @param health
     * @param weapons
     * @param name
     */
    public AbstractEnemy(
        final Vector2dc spawnPosition, 
        final double hitbox, 
        final double movementSpeedfinal,
        final int health, 
        final Set<Weapon> weapons, 
        final String name,
        final MapObject target
    ) {
        super(spawnPosition, hitbox, movementSpeedfinal, health, weapons);
        this.name = name;
        this.target = target;
    }
    
    public String getName() {
        return this.name;
    }
    
    public MapObject getTarget() {
        return this.target;
    }
    
    public Vector2d direction(final Vector2dc v1, final Vector2dc v2) {
        return new Vector2d(v1).sub(v2);
    }

    @Override
    protected boolean canTakeDamage() {
        return true;
    }
    
    @Override
    public void addWeapons(final Weapon p) {

    }
}
