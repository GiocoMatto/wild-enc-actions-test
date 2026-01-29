package it.unibo.wildenc.mvc.model.map.objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import static it.unibo.wildenc.mvc.model.map.MapTestingVariables.MapObjectTest;
import static it.unibo.wildenc.mvc.model.map.MapTestingVariables.MovableObjectTest;
import static it.unibo.wildenc.mvc.model.map.MapTestingVariables.TEST_TIME_SECONDS;

import it.unibo.wildenc.mvc.model.map.MapTestingVariables.TestDirections;
import it.unibo.wildenc.mvc.model.map.MapTestingVariables.TestObject;

public class TestMapObjects {

    @Test
    void mapObjectShouldBeCreatedWithCorrectCoordinates() {
        final TestObject to = TestObject.StaticObject;
        var obj = new MapObjectTest(to.pos, to.hitbox);
        assertEquals(to.pos.x(), obj.getPosition().x());
        assertEquals(to.pos.y(), obj.getPosition().y());
        assertEquals(to.hitbox, obj.getHitbox());
    }

    @Test
    void movableObjectWithNoDirectionShouldNotMove() {
        final TestObject to = TestObject.MovableObject;
        final TestDirections td = TestDirections.RIGHT;
        var obj = new MovableObjectTest(to.pos, to.hitbox, to.speed);
        obj.updatePosition(TEST_TIME_SECONDS);
        assertEquals(to.pos.x(), obj.getPosition().x());
        assertEquals(to.pos.y(), obj.getPosition().y());
    }

    @Test
    void movableObjectWithDirectionShouldMoveCorrectly() {
        final TestObject to = TestObject.MovableObject;
        final TestDirections td = TestDirections.RIGHT;
        var obj = new MovableObjectTest(to.pos, to.hitbox, to.speed);
        // Test movement with a direction.
        obj.setDirection(td.vect);
        obj.updatePosition(TEST_TIME_SECONDS);
        assertEquals(to.pos.x() + td.vect.x() * to.speed * TEST_TIME_SECONDS, obj.getPosition().x());
        assertEquals(to.pos.y() + td.vect.y() * to.speed * TEST_TIME_SECONDS, obj.getPosition().y());
    }
}
