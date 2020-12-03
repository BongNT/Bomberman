package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.BombermanGame.FPS;
import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;

public class Oneal extends Enemy {
    private static final int timeToChangeDir = FPS * 3;
    private int time = timeToChangeDir;

    public Oneal(int x, int y, Image img) {
        super(x, y, img);
        speed = SCALED_SIZE / 8;
        dir = DIR.LEFT;
    }

    @Override
    public void changeDir() {
        time--;
        if (!canMove || time == 0) {
            randomDir();
        }
        if (time == 0) {
            time = timeToChangeDir;
        }
    }

    @Override
    protected void updateImage() {
        switch (dir) {
            case LEFT:
            case DOWN:
                img = animateImage(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3);
                break;
            case UP:
            case RIGHT:
                img = animateImage(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3);
                break;
            default:
                break;
        }
    }

    @Override
    public void loadDestroyImg() {
        if (timeLoadDead == 0) {
            alive = false;
        }
        img = Sprite.oneal_dead.getFxImage();
        timeLoadDead--;
    }
}
