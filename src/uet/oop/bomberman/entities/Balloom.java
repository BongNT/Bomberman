package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;

public class Balloom extends Actor {

    public Balloom(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        dir =DIR.LEFT;
        canMove =true;
        speed = SCALED_SIZE / 8;
    }


    @Override
    protected void updateImage() {
        switch (dir) {
            case LEFT:
                img = animateImage(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3);
                break;
            case DOWN:
                img = animateImage(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3);
                break;
            case UP:
                img = animateImage(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3);
                break;
            case RIGHT:
                img = animateImage(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3);
                break;
            default:
                break;
        }
    }

    @Override
    public void update() {
        move();
        updateImage();
        checkMove(Bomber.bombs);
    }
}
