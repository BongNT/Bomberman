package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;

public class Bomber extends Actor {
    private final int maxBomb = 5;
    private int presentBomb = 1;
    private final int maxSpeed = SCALED_SIZE / 8 * 2;

    public static List<Entity> bombs = new ArrayList<>();

    public Bomber(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        dir = DIR.DEFAULT;
        speed = SCALED_SIZE / 8;
    }

    @Override
    public void update() {
        checkMove(bombs);
        move();
        updateImage();
        //load bomb nổ
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb =(Bomb) bombs.get(i);
            bomb.update();
            if (bomb.exploded) {
                bombs.remove(i);
            }
            for(int j = i; j < bombs.size(); j++) {
                Bomb bomb2 =(Bomb) bombs.get(j);
                collisionBomb(bomb, bomb2);
            }
            if (bomb.isExploding && bomb.collisionWithActor(this)) {
                System.out.println("bomber : die");
                alive = false;
                //nhan vat chet
            }
        }
    }

    @Override
    protected void updateImage() {

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

    public void setBomb() {
        if (bombs.size() >= presentBomb) return;
        int xUnit = (x + SCALED_SIZE / 3) / SCALED_SIZE;
        int yUnit = (y + SCALED_SIZE / 3) / SCALED_SIZE;
        for (int i = 0; i < bombs.size(); i++) {
            if (bombs.get(i).x == xUnit * SCALED_SIZE && bombs.get(i).y == yUnit * SCALED_SIZE) {
                return;
            }
        }
        bombs.add(new Bomb(xUnit, yUnit, Sprite.bomb.getFxImage()));

    }

    public void increaseBomb() {
        if (presentBomb < maxBomb) {
            presentBomb++;
        }
    }

    public void increaseSpeed() {
        if (speed < maxSpeed) {
            speed += SCALED_SIZE / 16;
        }
    }


    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
        bombs.forEach(g -> g.render(gc));
    }

    private void collisionBomb(Bomb bomb1, Bomb bomb2) {
        bomb1.collisionWithBomb(bomb2);
    }
}
