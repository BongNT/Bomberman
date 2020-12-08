package uet.oop.bomberman.entities.Character;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import uet.oop.bomberman.entities.Character.Enemy.Enemy;
import uet.oop.bomberman.entities.Bomb.Bomb;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Enum.DIR;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

import static uet.oop.bomberman.BombermanGame.FPS;
import static uet.oop.bomberman.BombermanGame.enemies;
import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;

public class Bomber extends Actor {
    private final int maxBomb = 5;
    private int presentBomb = 2;
    private final int maxSpeed = SCALED_SIZE / 8 * 2;
    public static int life = 3;
    //tọa độ để reset nhân vật nếu chết và còn mạng
    private int initX, initY;
    public static List<Entity> bombs = new ArrayList<>();

    public Bomber(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        dir = DIR.DEFAULT;
        speed = SCALED_SIZE / 8;
        initX = x;
        initY = y;
    }

    @Override
    public void update() {
        checkMove(bombs);
        move();
        updateImage();
        updateBomb();

        if(loadDead) {
            loadDestroyImg();
        } else {
            //bomber vs enemies
            for(int i = 0; i < enemies.size(); i++) {
                if(checkCollision(this.getRec(), enemies.get(i).getRec())){
                    loadDead = true;
                }
            }
        }

        //chết vẫn còn mạng
        if(!alive) {
            resetBomber();
        }
    }

    private void resetBomber(){
        life--;
        System.out.println("life : " + life);
        alive = true;
        x = initX;
        y = initY;
        bombs.clear();
        loadDead = false;
        img = Sprite.player_down_1.getFxImage();
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
            if (bombs.get(i).getX() == xUnit * SCALED_SIZE && bombs.get(i).getY() == yUnit * SCALED_SIZE) {
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

    @Override
    public void loadDestroyImg() {
        if (timeLoadDead == 0) {
            alive = false;
            timeLoadDead = FPS;
        }
        img = Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3
                ,timeLoadDead-- ,FPS).getFxImage();
    }
    public void clearBomb(){
        bombs.clear();
    }
    private void updateBomb(){
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb =(Bomb) bombs.get(i);
            if (bomb.exploded) {
                bombs.remove(i);
            }
        }
        for (int i = 0; i < bombs.size(); i++) {
            //bomb vs bomb
            Bomb bomb =(Bomb) bombs.get(i);
            bomb.update();
            for(int j = i+1; j < bombs.size(); j++) {
                Bomb bomb2 =(Bomb) bombs.get(j);
                collisionBomb(bomb, bomb2);
            }
            //bomb vs actor
            if(bomb.isExploding) {
                if (!loadDead && bomb.collisionWithActor(this)) {
                    // Actor died
                    System.out.println("bomber : die");
                    loadDead = true;
                }
                int n = enemies.size();
                for(int j = 0; j < n; j++) {
                    Enemy enemy = (Enemy) enemies.get(j);
                    if(!enemy.loadDead && bomb.collisionWithActor(enemy)) {
                        enemy.loadDead = true;
                        System.out.println("enemydie");
                    }
                }
            }
        }
    }
}
