package it.unibo.wildenc.Weaponary;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.wildenc.Weaponary.AbstractWeapon.WeaponStats; // Make this another class.

public class TestWeapons {
    /**
     * Test for a FirstWeapon shooting basic Projectiles.
     */
    private Weapon firstWeaponTest;

    @BeforeEach
    public void initTest() {
        this.firstWeaponTest = new FirstWeapon(
            1, 1, Type.WATER,
            (sp, vel) -> new Point2D(sp.x() + vel, sp.y() + vel),
            "Disintegratore"
        );
    }

    @Test
    public void testStatsCorrect() {
        final WeaponStats currentWeaponStats = firstWeaponTest.getStats();
        assertEquals("Disintegratore", this.firstWeaponTest.getName());
        assertTrue(currentWeaponStats.projDamage() == 1.0);
        assertTrue(currentWeaponStats.projVelocity() == 1.0);
        assertTrue(currentWeaponStats.projType() == Type.WATER);
    }

    @Test
    public void testProjectileCreation() {
        final Projectile testProj = this.firstWeaponTest.attack(new Point2D(0, 0));
        assertTrue(testProj.getClass().getSimpleName().equals("ConcreteProjectile"));
        assertTrue(testProj.getPosition().isEqual(new Point2D(0, 0)));
        assertTrue(testProj.getType() == Type.WATER);
        assertTrue(testProj.getDamage() == 1.0);
    }

    @Test
    public void testProjectileMovement() {
        final Projectile testProj = this.firstWeaponTest.attack(new Point2D(0, 0));
        assertTrue(testProj.getPosition().isEqual(new Point2D(0, 0)));
        testProj.move();
        assertTrue(testProj.getPosition().isEqual(new Point2D(1, 1)));
        testProj.move();
        assertFalse(testProj.getPosition().isEqual(new Point2D(1, 1)));
        testProj.move();
        assertTrue(testProj.getPosition().isEqual(new Point2D(3, 3)));
    }
}
