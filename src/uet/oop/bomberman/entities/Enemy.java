package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

import java.awt.*;

import static uet.oop.bomberman.BombermanGame.enemies;
import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;

public abstract class Enemy extends Actor {

    public Enemy(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        dir = DIR.DEFAULT;
    }

    public abstract void changeDir();

    protected void checkCollisionEnemies() {

        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = (Enemy) enemies.get(i);
            if (enemy.canMove != false && enemy != this) {
                if (checkCollision(this.getRecNextStep(), enemy.getRecNextStep())) {
                    this.canMove = false;
                    enemy.canMove = false;
                    this.reverseDir();
                    enemy.reverseDir();
                }
            }
        }
    }


    public void reverseDir() {
        switch (dir) {
            case UP:
                dir = DIR.DOWN;
                break;
            case DOWN:
                dir = DIR.UP;
                break;
            case LEFT:
                dir = DIR.RIGHT;
                break;
            case RIGHT:
                dir = DIR.LEFT;
                break;
        }
    }

    public void randomDir() {
        dir = DIR.random();
        while (dir == DIR.DEFAULT) {
            dir = DIR.random();
        }

    }
}