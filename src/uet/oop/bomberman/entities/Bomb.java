package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.BombermanGame.FPS;
import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;

public class Bomb extends Entity {
    public boolean exploded;
    private boolean isExploding;
    private int timeExsist = FPS * 3;
    private int timeExplode = FPS;
    private Flame flameTop = null;

    public Bomb(int x, int y, Image img) {
        super(x, y, img);
        exploded = false;
        isExploding = false;
        flameTop = new Flame(x, y - 1, Sprite.explosion_vertical_top_last.getFxImage());
    }

    @Override
    public void update() {
        if (!exploded)
            img = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, timeExsist--, 5).getFxImage();
        //trước khi nổ
        if (timeExsist == 0) {
            isExploding = true;
            timeExsist = FPS * 3;
        }
        //bomb nổ
        if (isExploding) {
            img = Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1,
                    Sprite.bomb_exploded2, timeExplode--, FPS).getFxImage();
            flameTop.update();
        }
        //sau khi nổ
        if (timeExplode == 0) {
            exploded = true;
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
        if (isExploding) {
            flameTop.render(gc);
        }
    }

}
