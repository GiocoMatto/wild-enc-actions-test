package it.unibo.wildenc.mvc.model;

import java.util.Collections;
import java.util.List;

public abstract class AbstractEntity extends AbstractMovable implements Entity {

    private  final int health;
    private  List<Weapon> weapons;

    public AbstractEntity(final int health, final List<Weapon> weapons) {
        this.health = health;
        this.weapons = weapons;
    }


    @Override
    public int getHealth() {
        return this.health;
    }

    @Override
    public List<Weapon> getWeapons() {
        return Collections.unmodifiableList(weapons);
    }

    @Override
    public boolean takeDamage(int dmg) {
        // TODO Auto-generated method stub
        return false;
    }

}
