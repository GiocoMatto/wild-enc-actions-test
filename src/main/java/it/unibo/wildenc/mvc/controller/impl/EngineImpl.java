package it.unibo.wildenc.mvc.controller.impl;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import org.joml.Vector2d;
import it.unibo.wildenc.mvc.controller.api.Engine;
import it.unibo.wildenc.mvc.controller.api.SavedData;
import it.unibo.wildenc.mvc.controller.api.SavedDataHandler;
import it.unibo.wildenc.mvc.controller.api.InputHandler.MovementInput;
import it.unibo.wildenc.mvc.controller.api.InputHandler;
import it.unibo.wildenc.mvc.model.Game;
import it.unibo.wildenc.mvc.model.game.GameImpl;
import it.unibo.wildenc.mvc.view.api.GameView;
import it.unibo.wildenc.mvc.view.impl.GameViewImpl;

/**
 * {@inheritDoc}.
 */
public class EngineImpl implements Engine {
    private final LinkedBlockingQueue<MovementInput> movements = new LinkedBlockingQueue<>();
    private final SavedDataHandler dataHandler = new SavedDataHandlerImpl();
    private final GameView view = new GameViewImpl();
    private final GameLoop loop = new GameLoop();
    private final Object pauseLock = new Object();
    private volatile STATUS gameStatus = STATUS.RUNNING;
    private volatile Game model;
    private Game.PlayerType playerType;
    private SavedData data;
    private final InputHandler ih = new InputHandlerImpl();

    /**
     * The status of the game loop.
     */
    public enum STATUS { RUNNING, PAUSE }

    /**
     * Create a Engine.
     */
    public EngineImpl() {
        try {
            this.data = dataHandler.loadData();
        } catch (final ClassNotFoundException | IOException e) {
            this.data = new SavedDataImpl();
        }
        view.setEngine(this);
        view.start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startGameLoop() {
        model = new GameImpl(playerType);
        this.loop.start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void processInput(final MovementInput movement) {
        this.movements.add(movement);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onLeveUpChoise(final String choise) {
        //model.aggiornastastisticheplayer;
        setPause(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPause(final boolean status) {
        synchronized (pauseLock) {
            this.gameStatus = status ? STATUS.PAUSE : STATUS.RUNNING;
            pauseLock.notifyAll();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void chosePlayerType(final Game.PlayerType pt) {
        this.playerType = pt;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pokedex() {
        view.pokedexView(data.getPokedex());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        try {
            this.dataHandler.saveData(data);
        } catch (final IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void menu() {
        //view.menu();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void shop() {
        //view.shop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerView(final GameView gv) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'registerView'");
    }

    /**
     * The game loop.
     */
    public final class GameLoop extends Thread {
        private static final long SLEEP_TIME = 10;
        private boolean running = true;

        @Override
        public void run() {
            try {
                long lastTime = System.nanoTime();
                while (running) {
                    synchronized (pauseLock) {
                        while (gameStatus == STATUS.PAUSE) {
                            pauseLock.wait();
                        }
                    }
                    final long now = System.nanoTime();
                    final long dt = now - lastTime;
                    lastTime = now;
                    final var move = movements.poll();
                    model.updateEntities(dt, (move != null) ? move.getVector() : new Vector2d(0, 0));
                    /*
                     * if (model.levelUp() {
                     *  setPause(true);
                     *  view.levelUp(model.getLevelUp());
                     * }
                    */
                    /*
                    * if (model.end()) {
                    *  view.end(model.getStatistic());
                    *  running = false;
                    * }
                    */
                    // view.updateSprites(model.getAllObjects().stream() //FIXME: and add getAllObjects().
                    //     .map(e -> new MapObjViewData(
                    //         "name", 
                    //         e.getPosition().x(), 
                    //         e.getPosition().y()
                    //     ))
                    //     .iterator()
                    // );
                    Thread.sleep(SLEEP_TIME);
                }
            } catch (final InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
