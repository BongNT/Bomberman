package uet.oop.bomberman.entities.Character.Enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Enum.DIR;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;

public class Balloom extends Enemy {

    public Balloom(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        dir = DIR.LEFT;
        canMove = true;
        speed = SCALED_SIZE / 16;
        score = 100;
    }

    @Override
    public void changeDir() {
        if (!canMove) {
            randomDir();
        }
    }

    @Override
    protected void updateImage() {
        switch (dir) {
            case LEFT:
            case DOWN:
                img = animateImage(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3);
                break;
            case UP:
            case RIGHT:
                img = animateImage(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3);
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
        img = Sprite.balloom_dead.getFxImage();
        timeLoadDead--;
    }
}
