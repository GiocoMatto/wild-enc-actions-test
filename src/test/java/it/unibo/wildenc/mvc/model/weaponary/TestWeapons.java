package it.unibo.wildenc.mvc.model.weaponary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.joml.Vector2d;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.wildenc.mvc.model.Weapon;
import it.unibo.wildenc.mvc.model.Weapon.WeaponStats;
import it.unibo.wildenc.mvc.model.weaponary.projectiles.Projectile;
import it.unibo.wildenc.mvc.model.weaponary.projectiles.ProjectileStats;
import it.unibo.wildenc.mvc.model.weaponary.projectiles.ProjectileStats.ProjStatType;
import it.unibo.wildenc.mvc.model.weaponary.weapons.AttackInfo;
import it.unibo.wildenc.mvc.model.weaponary.weapons.WeaponFactory;

public class TestWeapons {

    private WeaponFactory weaponMaker = new WeaponFactory();
    private Weapon currentWeapon;
    private List<Projectile> generatedProjectiles;

    @BeforeEach
    public void setup() {
        this.currentWeapon = weaponMaker.getDefaultWeapon();
        generatedProjectiles = new ArrayList<>();
    }

    @Test
    public void testWeaponCreation() {
        WeaponStats currentWeaponStats = currentWeapon.getStats();
        assertTrue(currentWeaponStats.burstSize() == 2.0);
        assertTrue(currentWeaponStats.weaponCooldown() == 1.0);
        ProjectileStats currentWeaponProjStats = currentWeaponStats.pStats();
        assertTrue("DefaultProj".equals(currentWeaponProjStats.getID()));
        assertTrue(currentWeaponProjStats.getStatValue(ProjStatType.DAMAGE) == 1.0);
        assertTrue(currentWeaponProjStats.getStatValue(ProjStatType.HITBOX) == 1.0);
        assertTrue(currentWeaponProjStats.getStatValue(ProjStatType.VELOCITY) == 1.0);
        assertTrue(currentWeaponProjStats.getTTL() == 10.0);
    }

    @Test
    public void testAttack() {
        generatedProjectiles.addAll(this.currentWeapon.attack(
            new AttackInfo(new Vector2d(0.0, 0.0), new Vector2d(1.0, 1.0), Optional.empty())
        ));
        assertTrue(!generatedProjectiles.isEmpty());
        assertTrue(generatedProjectiles.getFirst().getPosition().equals(new Vector2d(0.0, 0.0)));
        assertTrue(generatedProjectiles.getFirst().getDirection().equals(new Vector2d(1.0, 1.0)));
        assertTrue(generatedProjectiles.getFirst().isAlive());
        assertTrue("DefaultProj".equals(generatedProjectiles.getFirst().getID()));
    }

    @Test
    public void testMovement() {
        generatedProjectiles.addAll(this.currentWeapon.attack(
            new AttackInfo(new Vector2d(0.0, 0.0), new Vector2d(1.0, 1.0), Optional.empty())
        ));
        assertTrue(!generatedProjectiles.isEmpty());
        final Projectile generatedProjectile = generatedProjectiles.getFirst();
        generatedProjectile.updatePosition(1);
        assertEquals(generatedProjectile.getPosition(), (new Vector2d(1, 1)));
        generatedProjectile.updatePosition(1);
        assertEquals(generatedProjectile.getPosition(), (new Vector2d(2, 2)));
        generatedProjectile.updatePosition(1);
        assertEquals(generatedProjectile.getPosition(), (new Vector2d(3, 3)));
        generatedProjectile.updatePosition(10);
        assertEquals(generatedProjectile.getPosition(), (new Vector2d(13, 13)));
    }

    @Test
    public void testBarrage() throws InterruptedException {
        // Creating first Projectile
        Set<Projectile> generatedProj = this.currentWeapon.attack(
            new AttackInfo(new Vector2d(0.0, 0.0), new Vector2d(1.0, 1.0), Optional.empty())
        );
        // Projectile exists!
        assertTrue(!generatedProj.isEmpty());

        // Waiting 100ms and trying to shoot again.
        Thread.sleep(100);
        generatedProj = this.currentWeapon.attack(
            new AttackInfo(new Vector2d(0.0, 0.0), new Vector2d(1.0, 1.0), Optional.empty())
        );
        // Nope, this time is not present.
        assertFalse(!generatedProj.isEmpty());

        // After 200ms, the 2nd projectile of the burst appears!
        Thread.sleep(100);
        generatedProj = this.currentWeapon.attack(
            new AttackInfo(new Vector2d(0.0, 0.0), new Vector2d(1.0, 1.0), Optional.empty())
        );
        assertTrue(!generatedProj.isEmpty());

        // Waiting another 300ms for not a projectile to appear.
        Thread.sleep(300);
        generatedProj = this.currentWeapon.attack(
            new AttackInfo(new Vector2d(0.0, 0.0), new Vector2d(1.0, 1.0), Optional.empty())
        );
        assertFalse(!generatedProj.isEmpty());

        // In the end, after 1.2s (1s cd + 200ms of barrage...) a new Projectile appears!
        Thread.sleep(700);
        generatedProj = this.currentWeapon.attack(
            new AttackInfo(new Vector2d(0.0, 0.0), new Vector2d(1.0, 1.0), Optional.empty())
        );
        assertTrue(!generatedProj.isEmpty());
    }
}
