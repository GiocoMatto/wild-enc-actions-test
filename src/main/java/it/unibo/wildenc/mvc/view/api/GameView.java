package it.unibo.wildenc.mvc.view.api;

import java.util.Iterator;
import it.unibo.wildenc.mvc.controller.api.Engine;
import it.unibo.wildenc.mvc.controller.api.MapObjViewData;

public interface GameView {

    void start();

    void setEngine(Engine e);

    void updateSprites(Iterator<MapObjViewData> mObj);

    void won();

    void lost();

    void pause();

}
