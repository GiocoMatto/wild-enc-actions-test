package it.unibo.wildenc.mvc.model.weaponary.projectiles;

import java.util.Optional;
import java.util.function.Supplier;

import org.joml.Vector2d;

import it.unibo.wildenc.mvc.model.map.objects.AbstractMovable;
import it.unibo.wildenc.mvc.model.weaponary.projectiles.ProjectileStats.ProjStatType;
import it.unibo.wildenc.mvc.model.weaponary.weapons.AttackInfo;

/**
 * Implementation of a generic {@link Projectile}. This will be used 
 * as a schematic for modelling any projectile weapons can shoot.
 */
public class ConcreteProjectile extends AbstractMovable implements Projectile {

    private static final double MS_TO_S = 1000.0;

    private ProjectileStats projStats;
    private Optional<Supplier<Vector2d>> followThis;
    private long lastMovement = System.currentTimeMillis();

    /**
     * Constructor of the class.
     * @param pStats the stats of the generated Projectile, in form of a {@link ProjectileStats}
     * @param attackInfo the informations about the initial state of the projectile in form of
     *  a {@link AttackInfo}
     */
    public ConcreteProjectile(
        final ProjectileStats pStats,
        final AttackInfo atkInfo
    ) {
        super(atkInfo.startingPos(), pStats.getStatValue(ProjStatType.HITBOX), pStats.getStatValue(ProjStatType.VELOCITY));
        this.setDirection(atkInfo.atkDirection());
        this.projStats = pStats;
        this.followThis = atkInfo.toFollow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePosition(final double deltaTime) {
        if(followThis.isPresent()) {
            this.getWritablePosition().set(followThis.get().get());
        }
        this.getWritablePosition().set(
            this.projStats.getMovementFunction().apply(
                new Vector2d(this.getWritablePosition()),
                new AttackMovementInfo(this.getDirection(), deltaTime, this.projStats.getStatValue(ProjStatType.VELOCITY))
            )
        );
        lastMovement = System.currentTimeMillis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getDamage() {
        return this.projStats.getStatValue(ProjStatType.DAMAGE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getID(){
        return this.projStats.getID();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAlive() {
        return (System.currentTimeMillis() - lastMovement) / MS_TO_S < this.projStats.getTTL();
    }
}
