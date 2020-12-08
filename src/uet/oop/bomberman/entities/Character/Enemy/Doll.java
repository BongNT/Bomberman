package uet.oop.bomberman.entities.Character.Enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Enum.DIR;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.BombermanGame.FPS;
import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;

public class Doll extends Enemy {
    private final int speed1 = SCALED_SIZE / 16;
    private final int speed2 = SCALED_SIZE / 8;
    private final int speed3 = SCALED_SIZE / 4;
    private final int timeToChangeSpeed = FPS * 3;
    private int time = timeToChangeSpeed;

    public Doll(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        dir = DIR.LEFT;
        canMove = true;
        speed = SCALED_SIZE / 16;
        score = 200;
    }

    @Override
    public void changeDir() {
        if (!canMove) {
            randomDir();
        }
    }

    public void changeSpeed() {
        time--;
        if (time == 0) {
            speed = randomSpeed();
            time = timeToChangeSpeed;
        }
    }

    @Override
    public void update() {
        changeSpeed();
        super.update();
    }

    @Override
    protected void updateImage() {

        switch (dir) {
            case LEFT:
            case DOWN:
                img = animateImage(Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left3);
                break;
            case UP:
            case RIGHT:
                img = animateImage(Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right3);
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
        img = Sprite.doll_dead.getFxImage();
        timeLoadDead--;
    }

    public int randomSpeed() {
        int random = (int) (Math.random() * 3 + 1);
        switch (random) {
            case 2:
                return speed2;
            case 3:
                return speed3;
        }
        return speed1;
    }
}
