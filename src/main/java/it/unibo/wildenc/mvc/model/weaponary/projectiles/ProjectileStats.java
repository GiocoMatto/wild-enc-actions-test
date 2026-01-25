package it.unibo.wildenc.mvc.model.weaponary.projectiles;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;

import org.joml.Vector2d;

public class ProjectileStats {

    enum StatType {
        DAMAGE("Damage"),
        VELOCITY("Velocity"),
        HITBOX("Hitbox Radius");

        private final String statName;

        StatType(final String name) {
            this.statName = name;
        }

        public String toString() {
            return this.statName;
        }
    }

    private class Stat {
        private final StatType statType;
        private final double baseValue;
        private double currentMultiplier = 1.0;

        Stat(final StatType type, final double val) {
            this.statType = type;
            this.baseValue = val;
        }

        private StatType getType() {
            return this.statType;
        }

        private void setMult(final double newMult) {
            this.currentMultiplier = newMult;
        }

        private double getValue() {
            return this.baseValue * currentMultiplier;
        }
    }

    private final List<Stat> projStats = new ArrayList<>();
    private final double timeToLive;
    private final String projID;
    private final BiFunction<Vector2d, AttackMovementInfo, Vector2d> projMovementFunction;

    public ProjectileStats(
        final double baseDamage,
        final double baseRadius,
        final String id,
        final double baseVelocity,
        final double ttl,
        final BiFunction<Vector2d, AttackMovementInfo, Vector2d> moveFunc
    ) {
        projStats.add(new Stat(StatType.DAMAGE, baseDamage));
        projStats.add(new Stat(StatType.HITBOX, baseRadius));
        projStats.add(new Stat(StatType.VELOCITY, baseVelocity));
        this.timeToLive = ttl;
        this.projID = id;
        this.projMovementFunction = moveFunc;
    }

    public double getStatValue(final StatType statType) {
        try {
            return projStats.stream()
            .filter(e -> e.getType().equals(statType))
            .findFirst().get().getValue();
        } catch (final NoSuchElementException statNotFound) {
            // TODO: Remove this System.out.println.
            System.out.println("The stats was not found." + statNotFound);
            return 0.0;
        }
    }

    public BiFunction<Vector2d, AttackMovementInfo, Vector2d> getMovementFunction() {
        return this.projMovementFunction;
    }

    public String getID() {
        return this.projID;
    }

    public double getTTL() {
        return this.timeToLive;
    }

    public void setMultiplier(final StatType statType, final double newMult) {
        try {
            projStats.stream()
            .filter(e -> e.getType().equals(statType))
            .findFirst().get().setMult(newMult);
        } catch (final NoSuchElementException statNotFound){
            // TODO: Remove this System.out.println.
            System.out.println("The stat was not found." + statNotFound);
        }
    }
}
