
package uet.oop.bomberman.entities;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.BombermanGame.FPS;
import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;

public class Flame extends Entity {
    public static final int maxLength = 5;
    private int length = 0;
    public boolean exploded;
    private int timeExplode = FPS;

    //các hình ảnh ở giữa
    private Image bodyImg;
    private final DIR dir;

    public Flame(int xUnit, int yUnit, Image img, int length, DIR dir) {
        super(xUnit, yUnit, img);
        exploded = false;
        if ((length < maxLength)) {
            this.length = length;
        } else {
            this.length = maxLength;
        }
        this.dir = dir;
    }



    @Override
    public void update() {
        if (timeExplode == 0) {
            exploded = true;
            return;
        }
        if(length == 0) return;
        if (length >= 1) {
            switch (dir) {
                case UP :
                    img = Sprite.movingSprite(Sprite.explosion_vertical_top_last, Sprite.explosion_vertical_top_last1,
                            Sprite.explosion_vertical_top_last2, --timeExplode, FPS ).getFxImage();
                    break;
                case DOWN:
                    img = Sprite.movingSprite(Sprite.explosion_vertical_down_last, Sprite.explosion_vertical_down_last1,
                            Sprite.explosion_vertical_down_last2, --timeExplode, FPS ).getFxImage();
                    break;
                case LEFT:
                    img = Sprite.movingSprite(Sprite.explosion_horizontal_left_last, Sprite.explosion_horizontal_left_last1,
                            Sprite.explosion_horizontal_left_last2, --timeExplode, FPS ).getFxImage();
                    break;
                case RIGHT:
                    img = Sprite.movingSprite(Sprite.explosion_horizontal_right_last, Sprite.explosion_horizontal_right_last1,
                            Sprite.explosion_horizontal_right_last2, --timeExplode, FPS ).getFxImage();
                    break;
                default:
                    break;
            }
        }

        if (length >= 2) {
            switch (dir) {
                case UP :
                case DOWN:
                    bodyImg = Sprite.movingSprite(Sprite.explosion_vertical, Sprite.explosion_vertical1,
                                Sprite.explosion_vertical2, --timeExplode, FPS ).getFxImage();
                    break;
                case LEFT:
                case RIGHT:
                     bodyImg = Sprite.movingSprite(Sprite.explosion_horizontal, Sprite.explosion_horizontal1,
                                Sprite.explosion_horizontal2, --timeExplode, FPS ).getFxImage();
                    break;
                default:
                    break;
            }

        }
    }


    public void render(GraphicsContext gc, int flameLength) {
        /*for (Image image : images) {
            gc.drawImage(image, x, y);
        }*/
        if (length == 1) {
            super.render(gc);
        } else if (length > 0) {
            switch (dir) {
                case UP:
                    for (int i = 0; i < length - 1; i++) {
                        gc.drawImage(bodyImg, x, y - SCALED_SIZE * i);
                    }
                    if (length <= flameLength) gc.drawImage(img, x, y - SCALED_SIZE * (length - 1));
                    break;
                case DOWN:
                    for (int i = 0; i < length - 1; i++) {
                        gc.drawImage(bodyImg, x,y + SCALED_SIZE * i);
                    }
                    if (length <= flameLength) gc.drawImage(img, x, y + SCALED_SIZE * (length - 1));
                    break;
                case LEFT:
                    for (int i = 0; i < length - 1; i++) {
                        gc.drawImage(bodyImg, x- SCALED_SIZE * i, y );
                    }
                    if (length <= flameLength) gc.drawImage(img, x - SCALED_SIZE * (length - 1), y);
                    break;
                case RIGHT:
                    for (int i = 0; i < length - 1; i++) {
                        gc.drawImage(bodyImg, x + SCALED_SIZE * i, y );
                    }
                    if (length <= flameLength) gc.drawImage(img, x + SCALED_SIZE * (length - 1), y);
                    break;
                default:
                    break;
            }

        }

    }
}

