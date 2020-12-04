package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

import static uet.oop.bomberman.BombermanGame.enemies;

public abstract class Enemy extends Actor {

    public Enemy(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public abstract void changeDir();

    protected void checkCollisionEnemies() {
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = (Enemy) enemies.get(i);
            if (enemy != this && enemy.canMove) {
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
        if(!alive || loadDead) return;
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
        if(!alive || loadDead) return;
        dir = DIR.random();
        while (dir == DIR.DEFAULT) {
            dir = DIR.random();
        }
    }

    @Override
    public void update() {
        if(loadDead) {
            loadDestroyImg();
            return;
        }
        changeDir();
        checkCollisionEnemies();
        checkMove(Bomber.bombs);
        move();
        updateImage();
    }
}
