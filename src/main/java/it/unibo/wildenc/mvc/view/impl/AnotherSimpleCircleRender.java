package it.unibo.wildenc.mvc.view.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import it.unibo.wildenc.mvc.controller.api.MapObjViewData;
import it.unibo.wildenc.mvc.view.api.SpriteRender;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class AnotherSimpleCircleRender implements SpriteRender {

    private static final int TOTAL_FRAMES = 3;
    private static final int PLAYER_SPRITE_SIZE = 32;
    private static final int ENEMY_SPRITE_SIZE = 40;
    private static final int GRASS_TILE_SIZE = 64;
    private static final List<Integer> SPRITE_MAP = List.of(2, 1, 0, 7, 6, 5, 4, 3);

    private Canvas canvas;
    private Image playerSheet;
    private Image grassTile;
    private Image enemySheet;
    private DrawInfos drawInfos;
    private int frameCount;
    private int rotationCounter;
    private double cameraX;
    private double cameraY;

    public AnotherSimpleCircleRender() {
        var spriteLoad = getClass().getResource("/sprites/testidle.png");
        var grassLoad = getClass().getResource("/sprites/grasstile.png");
        var enemyLoad = getClass().getResource("/sprites/testenemy.png");
        if (spriteLoad == null || grassLoad == null || enemyLoad == null) {
            System.err.println("Errore nell'apertura del file! :(");
        } else {
            this.playerSheet = new Image(spriteLoad.toExternalForm());
            this.grassTile = new Image(grassLoad.toExternalForm());
            this.enemySheet = new Image(enemyLoad.toExternalForm());
        }
    }

    @Override
    public void renderAll(Collection<MapObjViewData> objectDatas) {
        final GraphicsContext draw = canvas.getGraphicsContext2D();
        drawGrassTiles(draw);
        final int currentFrame = ((frameCount / 24) % TOTAL_FRAMES) * PLAYER_SPRITE_SIZE;
        final int currentRotation = (rotationCounter / 3) % 8;
        updateCamera(
            objectDatas.stream()
                .filter(e -> e.name().contains("player"))
                .findFirst()
                .orElse(null)
        );
        objectDatas.stream()
            .forEach(objectData -> {
                if(objectData.name().contains("player")) {
                    draw.drawImage(
                        playerSheet, currentFrame, 40 * convertVersorToDominant(objectData.directionX().get(), objectData.directionY().get()), 
                        PLAYER_SPRITE_SIZE, PLAYER_SPRITE_SIZE,
                        objectData.x() - this.cameraX - (PLAYER_SPRITE_SIZE), 
                        objectData.y() - this.cameraY - (PLAYER_SPRITE_SIZE), 
                        64, 64
                    );
                } else if (objectData.name().contains("enemy")) {
                    draw.drawImage(
                        playerSheet, currentFrame, 40 * convertVersorToDominant(objectData.directionX().get(), objectData.directionY().get()), 
                        PLAYER_SPRITE_SIZE, PLAYER_SPRITE_SIZE,
                        objectData.x() - this.cameraX - (PLAYER_SPRITE_SIZE), 
                        objectData.y() - this.cameraY - (PLAYER_SPRITE_SIZE), 
                        64, 64
                    );
                } else {
                    draw.setFill(getColor(objectData));
                    draw.fillOval(objectData.x() - this.cameraX, objectData.y() - this.cameraY, 10, 10);
                }
        });

        frameCount++;
        rotationCounter++;
    }

    @Override
    public void setCanvas(Canvas c) {
        canvas = c;
    }

    @Override
    public void clean() {
        final var draw = canvas.getGraphicsContext2D();
        draw.clearRect(0, 0, canvas.widthProperty().get(), canvas.heightProperty().get());
    }

    public void setRenderInfos(DrawInfos draw) {
        drawInfos = draw;
    }

    private Color getColor(MapObjViewData objd) {
        return Stream.of(DrawInfos.values())
            .filter(i -> {
                return objd.name().split(":")[0].equals(i.name().toLowerCase());
            })
            .map(i -> i.getColor())
            .findFirst()
            .orElse(Color.BLACK);
    }
     
    public enum DrawInfos {
        PLAYER("player", Color.GREEN),
        ENEMY("enemy", Color.RED),
        PROJECTILE("projectile", Color.BLACK);

        private final Color color;
        
        DrawInfos(String id, Color c) {
            color = c;
        }

        public Color getColor() {
            return color;
        }

    }

    @Override
    public void updateCamera(MapObjViewData playerObj) {
        this.cameraX = playerObj.x() - canvas.widthProperty().get() / 2;
        this.cameraY = playerObj.y() - canvas.heightProperty().get() / 2;
    }

    private void drawGrassTiles(GraphicsContext draw) {
        final double offsetX = -cameraX % GRASS_TILE_SIZE;
        final double offsetY = -cameraY % GRASS_TILE_SIZE;
    
        for (double x = offsetX - GRASS_TILE_SIZE; x < canvas.getWidth(); x += GRASS_TILE_SIZE) {
            for (double y = offsetY - GRASS_TILE_SIZE; y < canvas.getHeight(); y += GRASS_TILE_SIZE) {
                draw.drawImage(grassTile, x, y, GRASS_TILE_SIZE, GRASS_TILE_SIZE);
            }
        }
    }

    private int convertVersorToDominant(final double dx, final double dy) {
        if (Math.abs(dx) < 0.01 && Math.abs(dy) < 0.01) {
            return 7;
        }
        double effectiveAngle = Math.toDegrees(Math.atan2(dy, dx));
        if (effectiveAngle < 0) {
            effectiveAngle += 360;
        };
        int slice = (int) Math.floor((effectiveAngle + 22.5) / 45) % 8;
        return SPRITE_MAP.get(slice);
    }   
}
