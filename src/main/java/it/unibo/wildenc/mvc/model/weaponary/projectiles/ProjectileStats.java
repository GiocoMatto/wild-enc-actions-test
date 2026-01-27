package it.unibo.wildenc.mvc.model.weaponary.projectiles;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;

import org.joml.Vector2d;

public class ProjectileStats {

    public enum ProjStatType {
        DAMAGE("Damage"),
        VELOCITY("Velocity"),
        HITBOX("Hitbox Radius");

        private final String statName;

        ProjStatType(final String name) {
            this.statName = name;
        }

        public String toString() {
            return this.statName;
        }
    }

    private class ProjStat {
        private final ProjStatType statType;
        private final double baseValue;
        private double currentMultiplier = 1.0;

        ProjStat(final ProjStatType type, final double val) {
            this.statType = type;
            this.baseValue = val;
        }

        private ProjStatType getType() {
            return this.statType;
        }

        private void setMult(final double newMult) {
            this.currentMultiplier = newMult;
        }

        private double getValue() {
            return this.baseValue * currentMultiplier;
        }
    }

    private final List<ProjStat> projStats = new ArrayList<>();
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
        projStats.add(new ProjStat(ProjStatType.DAMAGE, baseDamage));
        projStats.add(new ProjStat(ProjStatType.HITBOX, baseRadius));
        projStats.add(new ProjStat(ProjStatType.VELOCITY, baseVelocity));
        this.timeToLive = ttl;
        this.projID = id;
        this.projMovementFunction = moveFunc;
    }

    public double getStatValue(final ProjStatType statType) {
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

    public void setMultiplier(final ProjStatType statType, final double newMult) {
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
