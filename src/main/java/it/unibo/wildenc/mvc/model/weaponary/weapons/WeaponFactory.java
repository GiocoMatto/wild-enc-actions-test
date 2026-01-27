package it.unibo.wildenc.mvc.model.weaponary.weapons;

import java.util.Set;

import org.joml.Vector2d;

import it.unibo.wildenc.mvc.model.Weapon;
import it.unibo.wildenc.mvc.model.weaponary.projectiles.ConcreteProjectile;
import it.unibo.wildenc.mvc.model.weaponary.projectiles.ProjectileStats;
import it.unibo.wildenc.mvc.model.weaponary.projectiles.ProjectileStats.ProjStatType;

public class WeaponFactory {
    public Weapon getDefaultWeapon() {
        return new GenericWeapon(
            1,
            new ProjectileStats(
                1.0,
                1.0,
                1.0,
                1.0, 
                10.0,
                "DefaultProj", 
                (start, moveInfo) -> {
                    return new Vector2d(
                        start.x + moveInfo.atkDirection().x() * moveInfo.atkVelocity() * moveInfo.deltaTime(),
                        start.y + moveInfo.atkDirection().y() * moveInfo.atkVelocity() * moveInfo.deltaTime()
                    );
                }
            ),
            (lvl, weaponStats) -> {
                weaponStats.pStats().setMultiplier(ProjStatType.DAMAGE, 1.5 * lvl);
            },
            (info, projStats) -> Set.of(new ConcreteProjectile(projStats, info, 0.0)),
            2,
            "BasicWeapon"
        );
    }

    public Weapon getDefaultOrbiting() {
        return new GenericWeapon(
            1,
            new ProjectileStats(
                1.0, 
                1.0, 
                2.0,
                1.0,
                10.0, 
                "DefaultOrbitingProj", 
                (start, moveInfo) -> {
                    return new Vector2d(
                        start.x + 5 * Math.cos(Math.toRadians(moveInfo.currentAngle())),
                        start.y + 5 * Math.sin(Math.toRadians(moveInfo.currentAngle()))
                    );
                }
            ), 
            (lvl, weaponStats) -> {
               weaponStats.pStats().setMultiplier(ProjStatType.VELOCITY, lvl); 
            },
            (info, projStats) -> Set.of(new ConcreteProjectile(projStats, info, 0)),
            1,
            "OrbitingWeapon"
        );
    }
}

