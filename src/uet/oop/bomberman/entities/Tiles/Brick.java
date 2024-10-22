package uet.oop.bomberman.entities.Tiles;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.BombermanGame.FPS;


public class Brick extends Entity {
    public boolean isDestroyed = false;
    private boolean isDestroying = false;
    private int timeDestroy = FPS;
    private final Image imgBackGround = Sprite.grass.getFxImage();


    public Brick(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        if (isDestroying) {
            img = Sprite.movingSprite(Sprite.brick_exploded,
                    Sprite.brick_exploded1,
                    Sprite.brick_exploded2,
                    timeDestroy--, FPS).getFxImage();
        }
        if (timeDestroy == 0) {
            isDestroyed = true;
            isDestroying = false;
            timeDestroy--;
        }

    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(imgBackGround, x, y);
        super.render(gc);
    }

    public void destroy() {
        isDestroying = true;
    }
}
