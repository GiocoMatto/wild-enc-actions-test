package it.unibo.wildenc.mvc.model;

import java.util.List;

import it.unibo.wildenc.mvc.model.weaponary.weapons.Weapon;

public interface Entity extends Movable {

    int getHealth();

    boolean takeDamage(int dmg);

    List<Weapon> getWeapons();

}
