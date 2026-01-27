package it.unibo.wildenc.mvc.model.weaponary.weapons;

import java.util.Optional;
import java.util.function.Supplier;

import org.joml.Vector2d;

public record AttackInfo(
    Vector2d startingPos, Vector2d atkDirection, 
    Optional<Supplier<Vector2d>> toFollow
) {}