package it.unibo.wildenc;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import it.unibo.wildenc.mvc.model.*;
import it.unibo.wildenc.mvc.model.enemies.*;

class AbstractEnemiesTest {
    @Test
    public void nameTest() {
        Enemy pikaciu = new CloseRangeEnemy("pikaciu");
        assertEquals("pikaciu", pikaciu.getName());
    }
}
