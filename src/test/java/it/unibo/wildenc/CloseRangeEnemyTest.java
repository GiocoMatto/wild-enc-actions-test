package it.unibo.wildenc;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import it.unibo.wildenc.mvc.model.*;
import it.unibo.wildenc.mvc.model.enemies.*;

public class CloseRangeEnemyTest {
    @Test
    public void moveToTest() {
        Enemy pikaciu = new CloseRangeEnemy("Pikaciu");
        assertTrue(pikaciu.moveTop(null));
    }
}
