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
                "DefaultProj", 
                1.0, 
                10.0,
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
            (info, projStats) -> Set.of(new ConcreteProjectile(projStats, info)),
            2,
            "BasicWeapon"
        );
    }
}

