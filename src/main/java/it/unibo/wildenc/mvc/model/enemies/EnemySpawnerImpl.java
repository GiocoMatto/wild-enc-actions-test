package it.unibo.wildenc.mvc.model.enemies;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.joml.Vector2d;
import it.unibo.wildenc.mvc.model.Enemy;
import it.unibo.wildenc.mvc.model.EnemyFactory;
import it.unibo.wildenc.mvc.model.EnemySpawner;
import it.unibo.wildenc.mvc.model.MapObject;
import it.unibo.wildenc.mvc.model.Player;

/**
 * Spawn enemy.
 */
public class EnemySpawnerImpl implements EnemySpawner {
    private static final int DS_X = 300;
    private static final int DS_Y = 130;
    private static final int BASE_ENEMY = 10;
    private static final double ENEMY_LOAD_FACTOR = 1.3;
    private static final int PROBABILITY_DISTRIBUTION = 16;
    private static final int START_LIFE = 100;
    private static final double LIFE_LOAD_FACTOR = 1.5;
    private final Random rand;
    private final EnemyFactory ef;
    private long totalTime;
    private final List<String> closeRangeEnemyNames = List.of("Koffing", "Zubat", "Haunter", "Croagunk");
    private final List<String> rangedEnemyNames = List.of("Marowak", "Shiftry", "Banette");
    private final List<String> roamingEnemyNames = List.of("Exeggcute", "Wingull");

    /**
     * The gorup of enemy for calc probability.
     */
    public enum Group {
        COMMON(List.of(1, 2, 3, 4, 5, 6)),
        RARE(List.of(7, 8, 9)),
        REAL_RARE(List.of(10, 11, 12)),
        LEGEND(List.of(13, 14)),
        REAL_LEGEND(List.of(15)),
        SUPER_LEGEND(List.of(16));

        private final List<Integer> probability;

        Group(final List<Integer> probability) {
            this.probability = probability;
        }

        /**
         * Check if the group contain the value.
         * 
         * @param value Value to check.
         * @return True if is contained.
         */
        public boolean contains(final int value) {
            return this.probability.contains(value);
        }
    }

    /**
     * Spawn different enemy.
     * 
     * @param target Comont target followed by all enemy.
     */
    public EnemySpawnerImpl(final MapObject target) {
        this.rand = new Random();
        this.ef = new EnemyFactoryImpl(target);
        this.totalTime = 0;
    }

    private <T> T pickRandom(final List<T> values) {
        return values.get(this.rand.nextInt(values.size()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Enemy> spawn(final Player p, final int enemyCount, final double tick) {
        final int target = BASE_ENEMY + (int) Math.pow(p.getExp(), ENEMY_LOAD_FACTOR);
        final int n = Math.max(0, target - enemyCount);
        this.totalTime = (int) (this.totalTime + tick);
        final int life = START_LIFE + (int) (totalTime * LIFE_LOAD_FACTOR);
        return IntStream.range(0, n)
            .mapToObj(num -> {
                final Vector2d origin = new Vector2d(p.getPosition()).add(switch (rand.nextInt(2)) {
                    case 1 -> new Vector2d(pickRandom(List.of(-DS_X, DS_X)), rand.nextInt(-DS_Y, DS_Y));
                    default -> new Vector2d(rand.nextInt(-DS_X, DS_X), pickRandom(List.of(-DS_Y, DS_Y)));
                });
                final int i = (int) System.currentTimeMillis() % PROBABILITY_DISTRIBUTION;
                if (Group.COMMON.contains(i)) {
                    return this.ef.closeRangeEnemy(origin, life, pickRandom(this.closeRangeEnemyNames));
                } else if (Group.RARE.contains(i)) {
                    return this.ef.closeRangeFastEnemy(origin, life, pickRandom(this.closeRangeEnemyNames));
                } else if (Group.REAL_RARE.contains(i)) {
                    return this.ef.rangedEnemy(origin, life, pickRandom(this.rangedEnemyNames));
                } else if (Group.LEGEND.contains(i)) {
                    return this.ef.rangedDoubleShotEnemy(origin, life, pickRandom(this.rangedEnemyNames));
                } else if (Group.REAL_LEGEND.contains(i)) {
                    return this.ef.roamingEnemy(origin, life, pickRandom(this.roamingEnemyNames));
                } else if (Group.SUPER_LEGEND.contains(i)) {
                    return this.ef.roamingLongLifeEnemy(origin, life, pickRandom(this.roamingEnemyNames));
                } 
                return this.ef.closeRangeEnemy(origin, life, pickRandom(this.closeRangeEnemyNames));
            }).collect(Collectors.toSet()
        );
    }

}
