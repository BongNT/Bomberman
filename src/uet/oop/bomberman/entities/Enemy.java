package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

import static uet.oop.bomberman.BombermanGame.enemies;

public abstract class Enemy extends Actor {
    protected boolean flag = false;

    public Enemy(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public void changeDir() {
        dir = DIR.random();
        while (dir == DIR.DEFAULT) {
            dir = DIR.random();
        }
    }

    protected void checkCollisionEnemies() {
        for (int i = 0; i < enemies.size(); i++) {
            Enemy temp = (Enemy) enemies.get(i);
            if (temp != this) {
                if (checkCollision(this.getRec(), temp.getRec())) {
                    flag = true;
                    this.reverseDIR();
                    temp.reverseDIR();
                }
            }
            flag = false;
        }
    }

    public void reverseDIR() {
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
}
