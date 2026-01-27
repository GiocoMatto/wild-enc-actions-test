package it.unibo.wildenc.mvc.model;

import org.joml.Vector2d;

public interface Player extends Entity {

    void levelUp();
    
    void getExp();
    
    void setDirection(Vector2d dir);

}
