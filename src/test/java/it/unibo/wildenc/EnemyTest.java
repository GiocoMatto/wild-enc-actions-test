package it.unibo.wildenc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import it.unibo.wildenc.mvc.model.*;
import it.unibo.wildenc.mvc.model.enemies.*;

public class EnemyTest {
    private Enemy enemy;

    @Test
    public void getNameTest() {
        this.enemy = new CloseRangeEnemy("Pikaciu");
        assertEquals("pikaciu", enemy.getName());
    }

    @Test
    public void CloseRangeEnemyTest() {
        this.enemy = new CloseRangeEnemy("Pikaciu");
        assertTrue(enemy.moveTop(new Point2D(0, 0)));
        assertTrue(enemy.moveTop(new Point2D(1, 1)));
        assertTrue(enemy.moveTop(new Point2D(2, 2)));
        assertTrue(enemy.moveTop(new Point2D(2, 3)));
        assertTrue(enemy.moveTop(new Point2D(3, 3)));
    }

    @Test
    public void RangedEnemyTest() {
        this.enemy = new RangedEnemy("Pikaciu");
        assertTrue(this.enemy.moveTop(new Point2D(0, 0)));
    }

    @Test
    public void RunAwayEnemyTest() {

    }
}
