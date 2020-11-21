package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.BombermanGame.FPS;

public class Bomb extends Entity{
    public boolean exploded;
    private boolean isExploding;
    private int timeExsist = FPS * 3;
    private int timeExplode = FPS;
    //độ dài max hiện tại nếu không vướng tường
    public static int presentFlameLength = 2;
    private final Flame flameUp ;
    private final Flame flameDown;
    private final Flame flameLeft;
    private final Flame flameRight;
    public Bomb(int x, int y, Image img) {
        super(x, y, img);
        exploded = false;
        isExploding = false;
        flameUp = new Flame(x, y - 1, Sprite.explosion_vertical_top_last.getFxImage(), 2, DIR.UP);
        flameDown = new Flame(x, y + 1, Sprite.explosion_vertical_down_last.getFxImage(), 2, DIR.DOWN);
        flameLeft = new Flame(x - 1, y  , Sprite.explosion_horizontal_left_last.getFxImage(), 2, DIR.LEFT);
        flameRight = new Flame(x + 1, y , Sprite.explosion_horizontal_right_last.getFxImage(), 2, DIR.RIGHT);
    }

    public void increaseLength() {
        presentFlameLength ++;
        if(presentFlameLength > Flame.maxLength) presentFlameLength = Flame.maxLength;
    }

    @Override
    public void update() {
        if (!exploded)
            img = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, timeExsist--, FPS/4).getFxImage();
        //trước khi nổ
        if (timeExsist == 0) {
            isExploding = true;
            timeExsist = FPS * 3;
        }
        //bomb nổ
        if (isExploding) {
            img = Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1,
                    Sprite.bomb_exploded2, timeExplode--, FPS).getFxImage();
            flameUp.update();
            flameDown.update();
            flameLeft.update();
            flameRight.update();
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
            flameUp.render(gc);
            flameDown.render(gc);
            flameLeft.render(gc);
            flameRight.render(gc);
        }
    }

}
