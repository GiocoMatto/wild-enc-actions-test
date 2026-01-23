package it.unibo.wildenc.mvc.model.enemies;

import java.util.List;

import org.joml.Vector2f;
import it.unibo.wildenc.mvc.model.Point2D;
import it.unibo.wildenc.mvc.model.Weapon;

public class RangedEnemy extends AbstractEnemy {

    public RangedEnemy(
        final int health, 
        final List<Weapon> weapons,
        final String name
    ) {
        super(health, weapons, name);
        //TODO Auto-generated constructor stub
    }

    @Override
    public boolean moveTop(Point2D p) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'moveTop'");
    }

    @Override
    public Vector2f getPosition() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPosition'");
    }

    @Override
    public boolean specificMovement() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'specificMovement'");
    }

}
