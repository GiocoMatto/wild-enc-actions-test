package it.unibo.wildenc.mvc.model.enemies;
import java.util.List;

import it.unibo.wildenc.mvc.model.*;
public abstract class AbstractEnemy extends AbstractEntity implements Enemy {
    private final String name;
    
    /**
     * Create a General Enemy without the logic of attack.
     * @param health
     * @param weapons
     * @param name
     */
    public AbstractEnemy(
        final int health, 
        final List<Weapon> weapons, 
        final String name
    ) {
        super(health, weapons);
        this.name = name;
    }
    
    /**
     * 
     */
    @Override
    public boolean moveTop(Point2D p) {
        if (specificMovement()) {
            super.moveTop(p);
        }
        return false;
    }
    
    public String getName() {
        return this.name;
    }

    /**
     * Specify the logic of movement of the enemy type.
     * @return If the enemy can move in the direction specified.
     */
    public abstract boolean specificMovement();
}
