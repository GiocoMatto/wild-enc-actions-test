package it.unibo.wildenc.mvc.model.enemies;

import java.util.List;

import org.joml.Vector2f;

import it.unibo.wildenc.mvc.model.Point2D;
import it.unibo.wildenc.mvc.model.Weapon;

public class RangedEnemy extends AbstractEnemy{

    public RangedEnemy(String name) {
        super(name);
        //TODO Auto-generated constructor stub
    }

    @Override
    public int getHealth() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getHealth'");
    }

    @Override
    public boolean takeDamage(int dmg) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'takeDamage'");
    }

    @Override
    public List<Weapon> getWeapons() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getWeapons'");
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

}
