package it.unibo.wildenc.mvc.model.map;

import org.joml.Vector2d;
import org.joml.Vector2dc;

import it.unibo.wildenc.mvc.model.map.objects.AbstractMapObject;
import it.unibo.wildenc.mvc.model.map.objects.AbstractMovable;

public class MapTestingVariables {
    // Objects
    public enum TestObject {
        StaticObject(0, 10, 5, 0),
        MovableObject(0, 10, 5, 10),
        PlayerObject(0, 0, 5, 10),
        EnemyObject(10, 20, 5, 7);

        public final Vector2dc pos;
        public final double hitbox;
        public final double speed;

        private TestObject(int x, int y, double hitbox, double speed) {
            this.pos = new Vector2d(x, y);
            this.hitbox = hitbox;
            this.speed = speed;
        }
    }
    // Directions
    public enum TestDirections {
        STILL(0,0),
        RIGHT(1, 0),
        LEFT(-1, 0),
        UP(0, 1),
        DOWN(0, -1);

        public final Vector2dc vect;

        TestDirections(int x, int y) {
            this.vect = new Vector2d(x, y);
        }
    }
    // Ticks
    public static final int TEST_TIME_NANOSECONDS = 1_000_000_000;
    public static final double TEST_TIME_SECONDS = 1.0;
    public static final int TEST_SIMULATION_TICKS = 20;
    
    public static class MapObjectTest extends AbstractMapObject {

        public MapObjectTest(Vector2dc spawnPosition, double hitbox) {
            super(spawnPosition, hitbox);
        }

    }

    public static class MovableObjectTest extends AbstractMovable {

        public MovableObjectTest(Vector2dc spawnPosition, double hitbox, double movementSpeed) {
            super(spawnPosition, hitbox, movementSpeed);
        }

        @Override
        public void setDirection(Vector2dc direction) {
            super.setDirection(direction);
        }

    }

    private MapTestingVariables() { }
}