package it.unibo.wildenc.mvc.model.enemies;

import java.util.Optional;

import org.joml.Vector2d;

import it.unibo.wildenc.mvc.model.Enemy;
import it.unibo.wildenc.mvc.model.EnemyFactory;
import it.unibo.wildenc.mvc.model.MapObject;
import it.unibo.wildenc.mvc.model.weaponary.weapons.WeaponFactory;

/**
 * {@inheritDoc}.
 */
public class EnemyFactoryImpl implements EnemyFactory {
    /* Enemy */
    private static final double BASE_HITBOX_ENEMY = 5;
    private static final double BASE_VELOCITY_ENEMY = 1;
    /* Projectile */
    private static final double BASE_COOLDOWN_PROJECTILE = 3;
    private static final double BASE_DAMAGE_PROJECTILE = 25;
    private static final double BASE_HITBOX_PROJECTILE = 2;
    private static final double BASE_VELOCITY_PROJECTILE = 3;
    private static final double BASE_TIME_TO_LIVE_PROJECTILE = 15;
    private static final int BASE_BURST_PROJECTILE = 5;

    private final WeaponFactory wf;
    private final MapObject target;

    /**
     * Create a Factory that associate the same target to all enemys.
     * 
     * @param target MapObject to attack.
     */
    public EnemyFactoryImpl(final MapObject target) {
        this.target = target;
        this.wf = new WeaponFactory();
    }

    private void addDefaultWeaponTo(final Enemy e) {
        e.addWeapons(wf.getDefaultWeapon(
            BASE_COOLDOWN_PROJECTILE,
            BASE_DAMAGE_PROJECTILE, 
            BASE_HITBOX_PROJECTILE, 
            BASE_VELOCITY_PROJECTILE, 
            BASE_TIME_TO_LIVE_PROJECTILE, 
            BASE_BURST_PROJECTILE, 
            e
        ));
    }

    private void addMeleeWeaponTo(final Enemy e) {
        e.addWeapons(wf.getMeleeWeapon(
            BASE_HITBOX_PROJECTILE, 
            BASE_DAMAGE_PROJECTILE, 
            e
        ));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Enemy closeRangeEnemy(final Vector2d spawnPosition, final int healt, final String name) {
        final Enemy e = new CloseRangeEnemy(
            spawnPosition, 
            BASE_HITBOX_ENEMY, 
            BASE_VELOCITY_ENEMY, 
            healt, 
            name, 
            Optional.of(target)
        );
        addMeleeWeaponTo(e);
        return e;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Enemy closeRangeFastEnemy(final Vector2d spawnPosition, final int healt, final String name) {
        final Enemy e = new CloseRangeEnemy(
            spawnPosition, 
            BASE_HITBOX_ENEMY, 
            2 * BASE_VELOCITY_ENEMY, 
            healt, 
            name, 
            Optional.of(target)
        );
        addMeleeWeaponTo(e);
        return e;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Enemy rangedEnemy(final Vector2d spawnPosition, final int healt, final String name) {
        final Enemy e = new RangedEnemy(
            spawnPosition, 
            BASE_HITBOX_ENEMY, 
            BASE_VELOCITY_ENEMY, 
            healt, 
            name, 
            Optional.of(target)
        );
        addMeleeWeaponTo(e);
        return e;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Enemy rangedDoubleShotEnemy(final Vector2d spawnPosition, final int healt, final String name) {
        final Enemy e = new RangedEnemy(
            spawnPosition, 
            BASE_HITBOX_ENEMY, 
            BASE_VELOCITY_ENEMY, 
            healt, 
            name, 
            Optional.of(target)
        );
        addDefaultWeaponTo(e);
        addDefaultWeaponTo(e);
        return e;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Enemy roamingEnemy(final Vector2d spawnPosition, final int healt, final String name) {
        final Enemy e = new RoamingEnemy(
            spawnPosition, 
            BASE_HITBOX_ENEMY, 
            BASE_VELOCITY_ENEMY, 
            healt, 
            name
        );
        addMeleeWeaponTo(e);
        return e;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Enemy roamingLongLifeEnemy(final Vector2d spawnPosition, final int healt, final String name) {
        final Enemy e = new RoamingEnemy(
            spawnPosition, 
            BASE_HITBOX_ENEMY, 
            BASE_VELOCITY_ENEMY, 
            healt + healt / 2, 
            name
        );
        addMeleeWeaponTo(e);
        return e;
    }

}
