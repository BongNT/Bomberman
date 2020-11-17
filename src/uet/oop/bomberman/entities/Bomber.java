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
}
