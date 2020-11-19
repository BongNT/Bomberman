package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;

public class Bomber extends Actor {

    public Bomber(int x, int y, Image img) {
        super( x, y, img);
        dir = DIR.DEFAULT;
        speed = SCALED_SIZE / 8;
    }

    @Override
    public void update() {
        move();
        updateImage();
    }

    @Override
    protected void updateImage() {
        if (!canMove) return;
        switch (dir) {
            case UP:
                img = animateImage(Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2);
                break;
            case DOWN:
                img = animateImage(Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2);
                break;
            case LEFT:
                img = animateImage(Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2);
                break;
            case RIGHT:
                img = animateImage(Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2);
                break;
            default:
                break;
        }
    }

    @Override
    public void moveRight() {
        super.moveRight();

    }

    @Override
    public void moveLeft() {
        super.moveLeft();

    }

    @Override
    public void moveUp() {
        super.moveUp();

    }

    @Override
    public void moveDown() {
        super.moveDown();

    }
    public Bomb setBomb() {
        return new Bomb(x/SCALED_SIZE, y/SCALED_SIZE, Sprite.bomb.getFxImage());
    }
}
