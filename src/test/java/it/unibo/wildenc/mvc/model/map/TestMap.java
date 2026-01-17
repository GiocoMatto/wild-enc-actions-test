package it.unibo.wildenc.mvc.model.map;

import it.unibo.wildenc.mvc.model.GameMap;
import it.unibo.wildenc.mvc.model.Player;
import it.unibo.wildenc.mvc.model.map.MapTestingCommons.MapObjectTest;
import it.unibo.wildenc.mvc.model.map.MapTestingCommons.MovableObjectTest;
import it.unibo.wildenc.mvc.model.map.MapTestingCommons.TestDirections;
import it.unibo.wildenc.mvc.model.map.MapTestingCommons.TestObject;

import static it.unibo.wildenc.mvc.model.map.MapTestingCommons.TEST_TIME_NANOSECONDS;
import static it.unibo.wildenc.mvc.model.map.MapTestingCommons.TEST_TIME_SECONDS;
import static it.unibo.wildenc.mvc.model.map.MapTestingCommons.calculateMovement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestMap {
    
    GameMap map;
    Player player;

    @BeforeEach
    void setup() {
        player = TestObject.PlayerObject.getAsPlayer();
        map = new GameMapImpl(player);
    }

    @Test
    void objectsShouldBeAddedToMap() {
        final TestObject objConf = TestObject.StaticObject;
        final MapObjectTest obj = objConf.getAsStaticObj();

        map.addObject(obj);
        assertTrue(map.getAllObjects().contains(obj));
    }

    @Test
    void staticObjectsShouldNotMove() {
        final TestObject objConf = TestObject.StaticObject;
        final MapObjectTest obj = objConf.getAsStaticObj();

        map.addObject(obj);
        map.updateEntities(TEST_TIME_NANOSECONDS);
        assertEquals(obj.getPosition(), objConf.pos);
    }

    @Test
    void movableObjWithNoDirectionShouldNotMove() {
        final TestObject objConf = TestObject.MovableObject;
        final MovableObjectTest obj = objConf.getAsMovableObj();

        map.addObject(obj);
        map.updateEntities(TEST_TIME_NANOSECONDS);
        assertEquals(obj.getPosition(), objConf.pos);
    }

    @Test
    void movableObjWithDirectionShouldMoveCorrectly() {
        final TestObject objConf = TestObject.MovableObject;
        final TestDirections direction = TestDirections.RIGHT;
        final MovableObjectTest obj = objConf.getAsMovableObj();

        map.addObject(obj);
        obj.setDirection(direction.vect);
        map.updateEntities(TEST_TIME_NANOSECONDS);
        assertNotEquals(objConf.pos, obj.getPosition(), "Object did not move");
        assertEquals(calculateMovement(objConf.pos, direction.vect, objConf.speed, TEST_TIME_SECONDS), obj.getPosition(), "Object moved wrong");
    }

}
