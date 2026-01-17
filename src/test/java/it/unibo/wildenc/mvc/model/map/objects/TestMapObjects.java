package it.unibo.wildenc.mvc.model.map.objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import static it.unibo.wildenc.mvc.model.map.MapTestingCommons.TEST_TIME_SECONDS;
import static it.unibo.wildenc.mvc.model.map.MapTestingCommons.calculateMovement;

import it.unibo.wildenc.mvc.model.map.MapTestingCommons.TestDirections;
import it.unibo.wildenc.mvc.model.map.MapTestingCommons.TestObject;

public class TestMapObjects {

    @Test
    void mapObjectShouldBeCreatedWithCorrectCoordinates() {
        final TestObject to = TestObject.StaticObject;
        var obj = to.getAsStaticObj();
        assertEquals(to.pos.x(), obj.getPosition().x());
        assertEquals(to.pos.y(), obj.getPosition().y());
        assertEquals(to.hitbox, obj.getHitbox());
    }

    @Test
    void movableObjectWithNoDirectionShouldNotMove() {
        final TestObject to = TestObject.MovableObject;
        var obj = to.getAsMovableObj();
        obj.updatePosition(TEST_TIME_SECONDS);
        assertEquals(to.pos.x(), obj.getPosition().x());
        assertEquals(to.pos.y(), obj.getPosition().y());
    }

    @Test
    void movableObjectWithDirectionShouldMoveCorrectly() {
        final TestObject to = TestObject.MovableObject;
        final TestDirections td = TestDirections.RIGHT;
        var obj = to.getAsMovableObj();
        // Test movement with a direction.
        obj.setDirection(td.vect);
        obj.updatePosition(TEST_TIME_SECONDS);
        assertEquals(calculateMovement(to.pos, td.vect, to.speed, TEST_TIME_SECONDS), obj.getPosition());
    }
}
