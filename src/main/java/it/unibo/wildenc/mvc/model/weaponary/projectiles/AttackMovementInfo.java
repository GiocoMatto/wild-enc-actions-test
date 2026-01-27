package it.unibo.wildenc.mvc.model.weaponary.projectiles;

import org.joml.Vector2dc;

public record AttackMovementInfo(Vector2dc atkDirection, double deltaTime, double atkVelocity, double currentAngle) {
    public AttackMovementInfo setAngle(double newAngle) {
        return new AttackMovementInfo(atkDirection, deltaTime, atkVelocity, newAngle);
    }
}
