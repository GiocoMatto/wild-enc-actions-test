package it.unibo.wildenc.mvc.model.enemies;

import org.joml.Vector2d;

import it.unibo.wildenc.mvc.model.Enemy;
import it.unibo.wildenc.mvc.model.EnemyFactory;
import it.unibo.wildenc.mvc.model.MapObject;
import it.unibo.wildenc.mvc.model.weaponary.weapons.WeaponFactory;

public class EnemyFactoryImpl implements EnemyFactory {
    private static final double HITBOX = 1;
    private static final double MOVEMET_SPEED_FINAL = 1;
    private static final int HEALTH = 100;
    private final MapObject target;
    private final WeaponFactory wf;

    public EnemyFactoryImpl(final MapObject taget) {
        this.target = taget;
        this.wf = new WeaponFactory();
    }

    @Override
    public Enemy CloseRangeEnemy(Vector2d spawnPosition, String name) {
        return new CloseRangeEnemy(spawnPosition, HITBOX, MOVEMET_SPEED_FINAL, HEALTH, wf., name, null)
    }

    @Override
    public Enemy CloseRangeFastEnemy(Vector2d spawnPosition, String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'CloseRangeFastEnemy'");
    }

    @Override
    public Enemy RangedEnemy(Vector2d spawnPosition, String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'RangedEnemy'");
    }

    @Override
    public Enemy RangedDoubleShotEnemy(Vector2d spawnPosition, String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'RangedDoubleShotEnemy'");
    }

    @Override
    public Enemy RoamingEnemy(Vector2d spawnPosition, String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'RoamingEnemy'");
    }

    @Override
    public Enemy RoamingLongLifeEnemy(Vector2d spawnPosition, String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'RoamingLongLifeEnemy'");
    }

}
