package it.unibo.wildenc.mvc.model.enemies;

import it.unibo.wildenc.mvc.model.Enemy;

public abstract class AbstractEnemy extends AbstractEntity implements Enemy {
    private final String name;

    public AbstractEnemy(final String name) {
        this.name = name;
    }


    public String getName() {
        return this.name;
    }
}
