package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.graphics.Sprite;

public class Bomber extends Actor {

    public Bomber(int x, int y, Image img) {
        super( x, y, img);
        dir = DIR.DEFAULT;
        speed = Sprite.SCALED_SIZE / 32;
    }

    @Override
    public void update() {
        move();
    }

    @Override
    public void moveRight() {
        super.moveRight();
        img = Sprite.player_right.getFxImage();
    }

    @Override
    public void moveLeft() {
        super.moveLeft();
        img = Sprite.player_left.getFxImage();
    }

    @Override
    public void moveUp() {
        super.moveUp();
        img = Sprite.player_up.getFxImage();
    }

    @Override
    public void moveDown() {
        super.moveDown();
        img = Sprite.player_down.getFxImage();
    }
}
