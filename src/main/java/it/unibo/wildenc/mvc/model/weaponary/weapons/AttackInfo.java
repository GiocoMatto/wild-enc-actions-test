package it.unibo.wildenc.mvc.model.weaponary.weapons;

import java.util.Optional;
import java.util.function.Supplier;

import org.joml.Vector2d;

/**
 * Record that model the informations about a specific attack.
 * These informations are formed by:
 *  - startingPos: where the Projectile will be initially generated
 *  - atkDirection: the direction which the Projectile has to follow
 *  - toFollow: an Optional containing a Supplier of a position. This
 *      is used for attacks that need to follow specific entities instead
 *      of specifing a direction.
 */
public record AttackInfo(
    Vector2d startingPos, Vector2d atkDirection, 
    Optional<Supplier<Vector2d>> toFollow
) {}