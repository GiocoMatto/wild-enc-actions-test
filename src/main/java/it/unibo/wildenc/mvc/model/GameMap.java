package it.unibo.wildenc.mvc.model;

import java.util.List;
import java.util.function.BiConsumer;

import org.joml.Vector2d;
import org.joml.Vector2dc;

import it.unibo.wildenc.mvc.model.weaponary.weapons.WeaponFactory;

/**
 * Map of the game, it includes all core logic to update all the entities on it.
 */
public interface GameMap {

    /**
     * Get the player.
     * 
     * @return the {@link Player}.
     */
    Player getPlayer();

    /**
     * Get all objects on this Map.
     * 
     * @return A {@link List} of all {@link MapObject}s on this Map.
     */
    List<MapObject> getAllObjects();

    /**
     * Update every living object on this Map.
     * 
     * @param deltaTime how much to update in time;
     * @param playerDirection the player-chosen direction as a {@link Vector2dc}.
     */
    void updateEntities(long deltaTime, Vector2dc playerDirection);

    /**
     * Spawn enemies on the map.
     */
    void spawnEnemies();

    /**
     * Set the enemy spawn logic.
     * 
     * @param spawnLogic a {@link EnemySpawner} logic.
     */
    void setEnemySpawnLogic(EnemySpawner spawnLogic);

    /**
     * Whether the game is ended.
     * 
     * @return true if the game ended, false otherwise.
     */
    boolean gameEnded();

    /**
     * Constant default player types.
     */
    enum PlayerType {
        Charmender(10, 5, 100, (wf, p) -> {
            // FIXME: understand how to pass the Vector2d Supplier. It should be the mouse position.
            p.addWeapon(wf.getDefaultWeapon(
                10, 
                10, 
                2,
                2,
                100,
                1,
                p,
                () -> new Vector2d(0, 0))); 
        }),
        Bulbasaur(20, 30, 200, (wf, p) -> {
            // p.addWeapon(wf.getMeleeWeapon(7, 5, p));
        }),
        Squirtle(10, 5, 90, (wf, p) -> {
            // p.addWeapon(wf.getMeleeWeapon(8,4, p));
        });

        private final PlayerStats playerStats;

        PlayerType(final int speed, final double hitbox, final int health, 
            final BiConsumer<WeaponFactory, Player> defaultWeapon) {
            playerStats = new PlayerStats(speed, hitbox, health, defaultWeapon);
        }

        /**
         * Get the player stats.
         * 
         * @return a {@link PlayerStats} instance with the current player stats.
         */
        public PlayerStats getPlayerStats() {
            return playerStats;
        }

        /**
         * Player Stats.
         * 
         * @param speed The player's speed (pixel-per-second);
         * @param hitbox The player's hitbox radius;
         * @param health The player's max health;
         * @param addDefaultWeapon A BiConsumer which takes a WeaponFactory 
         */
        public record PlayerStats(int speed, double hitbox, int health,
            BiConsumer<WeaponFactory, Player> addDefaultWeapon) { }
    }

}
