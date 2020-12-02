package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.BombermanGame.FPS;
import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;

public class Oneal extends Enemy {
    private static final int timeToChangeDir = FPS * 4;
    private int time = timeToChangeDir;

    public Oneal(int x, int y, Image img) {
        super(x, y, img);
        dir = DIR.LEFT;
        canMove = true;
        speed = SCALED_SIZE / 8;
    }

    @Override
    public void changeDir() {
        time--;
        if (!canMove || time == 0) {
            randomDir();
        }
        if (time == 0) time = timeToChangeDir;
    }

    @Override
    protected void updateImage() {
        switch (dir) {
            case LEFT:
                img = animateImage(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3);
                break;
            case DOWN:
                img = animateImage(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3);
                break;
            case UP:
                img = animateImage(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3);
                break;
            case RIGHT:
                img = animateImage(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3);
                break;
            default:
                break;
        }
    }

    @Override
    public void update() {
        checkMove(Bomber.bombs);
        changeDir();
        checkCollisionEnemies();
        move();
        updateImage();
    }
}
