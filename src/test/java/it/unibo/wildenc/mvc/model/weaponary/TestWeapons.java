package it.unibo.wildenc.mvc.model.weaponary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.joml.Vector2d;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.wildenc.mvc.model.weaponary.projectiles.Projectile;
import it.unibo.wildenc.mvc.model.weaponary.projectiles.ProjectileStats;
import it.unibo.wildenc.mvc.model.weaponary.projectiles.ProjectileStats.ProjStatType;
import it.unibo.wildenc.mvc.model.weaponary.weapons.AttackInfo;
import it.unibo.wildenc.mvc.model.weaponary.weapons.Weapon;
import it.unibo.wildenc.mvc.model.weaponary.weapons.Weapon.WeaponStats;
import it.unibo.wildenc.mvc.model.weaponary.weapons.WeaponFactory;

public class TestWeapons {

    private WeaponFactory weaponMaker = new WeaponFactory();
    private Weapon currentWeapon;

    @BeforeEach
    public void setup() {
        this.currentWeapon = weaponMaker.getDefaultWeapon();
    }

    @Test
    public void testWeaponCreation() {
        WeaponStats currentWeaponStats = currentWeapon.getStats();
        assertTrue(currentWeaponStats.burstSize() == 1.0);
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
        Optional<Projectile> generatedProj = this.currentWeapon.attack(
            new AttackInfo(new Vector2d(0.0, 0.0), new Vector2d(1.0, 1.0), Optional.empty())
        );
        assertTrue(generatedProj.isPresent());
        assertTrue(generatedProj.get().getPosition().equals(new Vector2d(0.0, 0.0)));
        assertTrue(generatedProj.get().getDirection().equals(new Vector2d(1.0, 1.0)));
        assertTrue(generatedProj.get().isAlive());
        assertTrue("DefaultProj".equals(generatedProj.get().getID()));
    }

    @Test
    public void testMovement() {
        Optional<Projectile> generatedProj = this.currentWeapon.attack(
            new AttackInfo(new Vector2d(0.0, 0.0), new Vector2d(1.0, 1.0), Optional.empty())
        );
        generatedProj.get().updatePosition(1);
        assertEquals(generatedProj.get().getPosition(), (new Vector2d(1, 1)));
        generatedProj.get().updatePosition(1);
        assertEquals(generatedProj.get().getPosition(), (new Vector2d(2, 2)));
        generatedProj.get().updatePosition(1);
        assertEquals(generatedProj.get().getPosition(), (new Vector2d(3, 3)));
        generatedProj.get().updatePosition(10);
        assertEquals(generatedProj.get().getPosition(), (new Vector2d(13, 13)));
    }

    @Test
    public void testBarrage() throws InterruptedException {
        // Creating first Projectile
        Optional<Projectile> generatedProj = this.currentWeapon.attack(
            new AttackInfo(new Vector2d(0.0, 0.0), new Vector2d(1.0, 1.0), Optional.empty())
        );
        // Projectile exists!
        assertTrue(generatedProj.isPresent());

        // Waiting 100ms and trying to shoot again.
        Thread.sleep(100);
        generatedProj = this.currentWeapon.attack(
            new AttackInfo(new Vector2d(0.0, 0.0), new Vector2d(1.0, 1.0), Optional.empty())
        );
        // Nope, this time is not present.
        assertFalse(generatedProj.isPresent());

        // After 200ms, the 2nd projectile of the burst appears!
        Thread.sleep(100);
        generatedProj = this.currentWeapon.attack(
            new AttackInfo(new Vector2d(0.0, 0.0), new Vector2d(1.0, 1.0), Optional.empty())
        );
        assertTrue(generatedProj.isPresent());

        // Waiting another 300ms for not a projectile to appear.
        Thread.sleep(300);
        generatedProj = this.currentWeapon.attack(
            new AttackInfo(new Vector2d(0.0, 0.0), new Vector2d(1.0, 1.0), Optional.empty())
        );
        assertFalse(generatedProj.isPresent());

        // In the end, after 1.2s (1s cd + 200ms of barrage...) a new Projectile appears!
        Thread.sleep(700);
        generatedProj = this.currentWeapon.attack(
            new AttackInfo(new Vector2d(0.0, 0.0), new Vector2d(1.0, 1.0), Optional.empty())
        );
        assertTrue(generatedProj.isPresent());
    }
}
