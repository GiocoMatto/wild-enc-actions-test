package it.unibo.wildenc.mvc.model.enemies;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import org.joml.Vector2d;
import org.joml.Vector2dc;
import it.unibo.wildenc.mvc.model.Collectible;
import it.unibo.wildenc.mvc.model.Enemy;
import it.unibo.wildenc.mvc.model.EnemyFactory;
import it.unibo.wildenc.mvc.model.MapObject;
import it.unibo.wildenc.mvc.model.dataloaders.StatLoader;
import it.unibo.wildenc.mvc.model.dataloaders.StatLoader.LoadedEntityStats;
import it.unibo.wildenc.mvc.model.enemies.AbstractEnemy.AbstractEnemyField;
import it.unibo.wildenc.mvc.model.map.objects.ExperienceGem;

/**
 * {@inheritDoc}.
 */
public class EnemyFactoryImpl implements EnemyFactory {
    private static final int VALUE_COLLECTIBLE = 34;

    private final MapObject target;
    private final StatLoader statLoader = StatLoader.getInstance();

    /**
     * Create a Factory that associate the same target to all enemys.
     * 
     * @param target MapObject to attack.
     */
    public EnemyFactoryImpl(final MapObject target) {
        this.target = target;
    }

    private void addDefaultWeaponTo(final Enemy e) {
        /*e.addWeapon(wf.getDefaultStaticPointWeapon(
            BASE_COOLDOWN_PROJECTILE,
            BASE_DAMAGE_PROJECTILE, 
            BASE_HITBOX_PROJECTILE, 
            BASE_VELOCITY_PROJECTILE, 
            BASE_TIME_TO_LIVE_PROJECTILE,
            BASE_PROJ_AT_ONCE, 
            BASE_BURST_PROJECTILE, 
            e,
            () -> new Vector2d(target.getPosition())
        ));
        */
    }

    private void addMeleeWeaponTo(final Enemy e) {
        // e.addWeapon(wf.getMeleeWeapon(
        //     BASE_HITBOX_PROJECTILE, 
        //     BASE_DAMAGE_PROJECTILE, 
        //     e
        // ));
    }

    private Function<MapObject, Collectible> experienceLoot(final Vector2dc pos) {
        return e -> new ExperienceGem(e.getPosition(), VALUE_COLLECTIBLE);
    };

    /**
     * {@inheritDoc}
     */
    @Override
    public Enemy closeRangeEnemy(final Vector2d spawnPosition, final int healt, final String name) {
        final LoadedEntityStats loadedEntityStats = loadEntityFromName(name);
        final Enemy e = new CloseRangeEnemy(new AbstractEnemyField(
            spawnPosition, 
            loadedEntityStats.hbRadius(), 
            loadedEntityStats.velocity(), 
            loadedEntityStats.maxHealth(), 
            name, 
            Optional.of(target),
            Set.of(experienceLoot(spawnPosition))
        ));
        addMeleeWeaponTo(e);
        return e;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Enemy closeRangeFastEnemy(final Vector2d spawnPosition, final int healt, final String name) {
        final LoadedEntityStats loadedEntityStats = loadEntityFromName(name);
        final Enemy e = new CloseRangeEnemy(new AbstractEnemyField(
            spawnPosition, 
            loadedEntityStats.hbRadius(), 
            2 * loadedEntityStats.velocity(), 
            loadedEntityStats.maxHealth(), 
            name, 
            Optional.of(target),
            Set.of(experienceLoot(spawnPosition))
        ));
        addMeleeWeaponTo(e);
        return e;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Enemy rangedEnemy(final Vector2d spawnPosition, final int healt, final String name) {
        final LoadedEntityStats loadedEntityStats = loadEntityFromName(name);
        final Enemy e = new RangedEnemy(new AbstractEnemyField(
            spawnPosition, 
            loadedEntityStats.hbRadius(), 
            loadedEntityStats.velocity(), 
            loadedEntityStats.maxHealth(), 
            name, 
            Optional.of(target),
            Set.of(experienceLoot(spawnPosition))
        ));
        addMeleeWeaponTo(e);
        return e;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Enemy rangedDoubleShotEnemy(final Vector2d spawnPosition, final int healt, final String name) {
        final LoadedEntityStats loadedEntityStats = loadEntityFromName(name);
        final Enemy e = new RangedEnemy(new AbstractEnemyField(
            spawnPosition, 
            loadedEntityStats.hbRadius(), 
            loadedEntityStats.velocity(), 
            loadedEntityStats.maxHealth(), 
            name, 
            Optional.of(target),
            Set.of(experienceLoot(spawnPosition))
        ));
        addDefaultWeaponTo(e);
        addDefaultWeaponTo(e);
        return e;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Enemy roamingEnemy(final Vector2d spawnPosition, final int healt, final String name) {
        final LoadedEntityStats loadedEntityStats = loadEntityFromName(name);
        final Enemy e = new RoamingEnemy(new AbstractEnemyField(
            spawnPosition, 
            loadedEntityStats.hbRadius(), 
            loadedEntityStats.velocity(), 
            loadedEntityStats.maxHealth(), 
            name, 
            Optional.empty(), 
            Set.of(experienceLoot(spawnPosition))
        ));
        addMeleeWeaponTo(e);
        return e;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Enemy roamingLongLifeEnemy(final Vector2d spawnPosition, final int healt, final String name) {
        final LoadedEntityStats loadedEntityStats = loadEntityFromName(name);
        final Enemy e = new RoamingEnemy(new AbstractEnemyField(
            spawnPosition, 
            loadedEntityStats.hbRadius(), 
            loadedEntityStats.velocity(), 
            loadedEntityStats.maxHealth() + loadedEntityStats.maxHealth() / 2, 
            name, 
            Optional.empty(), 
            Set.of(experienceLoot(spawnPosition))
        ));
        addMeleeWeaponTo(e);
        return e;
    }

    private LoadedEntityStats loadEntityFromName(final String name) {
        return statLoader.getLoadedEnemyStats(name);
    }
}
