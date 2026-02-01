package it.unibo.wildenc.mvc.controller.api;

import java.util.Map;

/**
 * Responsible of handling all the data that needs to be stored between every game.
 */
public interface DataHandler {
    
    /**
     * Update player coins.
     * 
     * FIXME: Method signature will be changed.
     */
    void updateCoins();

    /**
     * Update infos about killed enemies. 
     * 
     * FIXME: Method signature will be changed.
     */
    void updatePokedex();

    /**
     * Get coins the player has earned between every game.
     * 
     * @return the number of coins.
     */
    int getCoins();

    /**
     * Get information about every enemy in the game and how many times they have been killed.
     * 
     * @return A {@link Map} with enemy id as key and how many times it has been killed as value.
     */
    Map<String, Integer> getPokedex();

}
