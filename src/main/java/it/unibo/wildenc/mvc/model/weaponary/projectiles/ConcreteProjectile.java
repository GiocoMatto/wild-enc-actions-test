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
    private double currentAngle;

    /**
     * Constructor of the class.
     * @param pStats the stats of the generated Projectile, in form of a {@link ProjectileStats}
     * @param attackInfo the informations about the initial state of the projectile in form of
     *  a {@link AttackInfo}
     */
    public ConcreteProjectile(
        final ProjectileStats pStats,
        final AttackInfo atkInfo,
        final double baseAngle
    ) {
        super(atkInfo.startingPos(), pStats.getStatValue(ProjStatType.HITBOX), pStats.getStatValue(ProjStatType.VELOCITY));
        this.setDirection(atkInfo.atkDirection());
        this.currentAngle = baseAngle;
        this.projStats = pStats;
        this.followThis = atkInfo.toFollow();
    }

    /**
     * {@inheritDoc}
     * Specific method implementation for generalizing the movement of the Projectile.
     * When an attack is made, the Projectile is just being generated in a specific position
     * but it won't move yet. When an updatePosition is called on a Projectile, it will be moving
     * followng it's movement function, which is specified at the moment of the creation in its
     * ProjectileStats. After this, there are 2 main cases for the generation of the Projectile:
     *  - It could be moving according to physics' laws (for example, by going straight in a direction)
     *  - Else, it could lock its position to the position specified at followThis (for example giving the player an aura
     *      that moves with him)
     *  If the generated Projectile doesn't need to use angles, it's initial angle and it's angular velocity will be set to 0.
     */
    @Override
    public void updatePosition(final double deltaTime) {
        this.currentAngle = this.projStats.getStatValue(ProjStatType.ANGULAR) != 0 ?
            (this.currentAngle + this.projStats.getStatValue(ProjStatType.ANGULAR) * deltaTime) % 360 : 0;
        if(followThis.isPresent()) {
            this.getWritablePosition().set(followThis.get().get());
        }
        this.getWritablePosition().set(
            this.projStats.getMovementFunction().apply(
                new Vector2d(this.getWritablePosition()),
                new AttackMovementInfo(this.getDirection(), deltaTime, this.projStats.getStatValue(ProjStatType.VELOCITY), this.currentAngle)
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
