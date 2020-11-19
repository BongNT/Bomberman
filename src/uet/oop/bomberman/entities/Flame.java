
package uet.oop.bomberman.entities;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

import static uet.oop.bomberman.BombermanGame.FPS;

public class Flame extends Entity {
    public static int maxLength = 3;
    public boolean exploded;
    private int timeExplode = FPS;
    //private List<Image> images = new ArrayList<>();
    private DIR dir;

    public Flame(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        exploded = false;
    }

    public void increaseLength() {

    }

    @Override
    public void update() {
        if (timeExplode == 0) {
            exploded = true;
            return;
        }
        if (timeExplode >= (FPS / 2)) {
            //System.out.println("1");
            img = Sprite.movingSprite(Sprite.explosion_vertical_top_last2, Sprite.explosion_vertical_top_last1
                    , Sprite.explosion_vertical_top_last, --timeExplode, FPS / 2).getFxImage();

            System.out.println(timeExplode);
        } else if (timeExplode >= 0) {
            //System.out.println("2");
            img = Sprite.movingSprite(Sprite.explosion_vertical_top_last, Sprite.explosion_vertical_top_last1,
                    Sprite.explosion_vertical_top_last2, --timeExplode, FPS / 2).getFxImage();
            System.out.println(timeExplode);
        }

    }

    @Override
    public void render(GraphicsContext gc) {
        /*for (Image image : images) {
            gc.drawImage(image, x, y);
        }*/
        super.render(gc);
    }
}

