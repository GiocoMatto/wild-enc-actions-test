package it.unibo.wildenc.mvc.model.enemies;

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

public class EnemySpawnerImpl implements EnemySpawner{
    private static final int DS_X = 30;
    private static final int DS_Y = 13;
    private static final int BASE_ENEMY = 10;
    private static final double LOAD_FACTOR = 1.3;
    private static final int PROBABILITY_DISTRIBUTION = 16;
    private final Random rand;
    private final EnemyFactory ef;

    //TODO: enum dei nomi dei pokemon.

    public EnemySpawnerImpl(final MapObject target) {
        this.rand = new Random();
        this.ef = new EnemyFactoryImpl(target);
    }

    private final int pickRandom(final int... values) {
        return values[this.rand.nextInt((values.length))];
    }

    @Override
    public Set<Enemy> spawn(Player p, int enemyCount) {
        // final int target = BASE_ENEMY + Math.pow(p.getExp(), LOAD_FACTOR); //TODO: kleo devi farmi queto.
        // final int n = Math.max(0, target - enemycount);        
        final int n = 10;
        return IntStream.range(0, n)
            .mapToObj(i -> {
                final Vector2d origin = new Vector2d(p.getPosition()).add( switch(rand.nextInt(2)) {
                    case 1 -> new Vector2d(pickRandom(-DS_X, DS_X), rand.nextInt(-DS_Y, DS_Y));
                    default -> new Vector2d(rand.nextInt(-DS_X, DS_X), pickRandom(-DS_Y, DS_Y));
                });
                return switch (i % PROBABILITY_DISTRIBUTION) {
                    case 1, 2, 3, 14, 15 -> this.ef.CloseRangeEnemy(origin, "Pippo");
                    case 4, 5, 6 -> this.ef.CloseRangeFastEnemy(origin, "PaperoniDePaperoni");
                    case 7, 8, 13 -> this.ef.RangedEnemy(origin, "Topolino");
                    case 9, 10 -> this.ef.RangedDoubleShotEnemy(origin, "Topolina");
                    case 11, 16 -> this.ef.RoamingEnemy(origin, "Pluto");
                    case 12 -> this.ef.RoamingLongLifeEnemy(origin, "Chip");
                    default -> this.ef.CloseRangeEnemy(origin, "Paperino");
                };
            }).collect(Collectors.toSet());
    }

}
