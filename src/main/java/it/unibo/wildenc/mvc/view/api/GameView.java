package it.unibo.wildenc.mvc.view.api;

import java.util.Collection;
import it.unibo.wildenc.mvc.controller.api.Engine;
import it.unibo.wildenc.mvc.model.MapObject;

public interface GameView {

    /**
     * Start the view.
     */
    void start();

    /**
     * Set an Engine (Controller) for this view.
     * 
     * @param e the Engine that will control the view.
     */
    void setEngine(Engine e);

    /**
     * Update all the sprites on the screen.
     * TODO: MapObject is a Model class, should not be used here in the view. Needs to be processed by the Controller.
     * 
     * @param mObjs a {@link Collection} of {@link MapObject}s that will have the infos necessary to be rendered.
     */
    void updateSprites(Collection<MapObject> mObjs);

    /**
     * Display the win screen.
     */
    void won();

    /**
     * Display the loss screen.
     */
    void lost();

    /**
     * Display the pause screen.
     */
    void pause();

}
