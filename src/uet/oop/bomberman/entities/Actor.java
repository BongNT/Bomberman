package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;
import java.util.List;

import static uet.oop.bomberman.BombermanGame.WIDTH;
import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;

public abstract class Actor extends Entity implements Movable{
    //số lần thay đổi ảnh
    protected int timeAnimate = 6;
    protected int presentImg = 2;
    protected int speed;
    protected DIR dir;
    protected boolean canMove;
    public Actor(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);

    }

    @Override
    public void moveUp() {
        dir = DIR.UP;
    }

    @Override
    public void moveDown() {
        dir = DIR.DOWN;
    }

    @Override
    public void moveLeft() {
        dir = DIR.LEFT;
    }

    @Override
    public void moveRight() {
        dir = DIR.RIGHT;
    }

    @Override
    public void notMoving() {
        dir = DIR.DEFAULT;
    }

    protected void move(){
        if (!canMove) return;
        switch (dir) {
            case UP:
                y -= speed;
                break;
            case DOWN:
                y += speed;
                break;
            case LEFT:
                x -= speed;
                break;
            case RIGHT:
                x += speed;
                break;
            default:
                break;
        }
    }

    @Override
    public void checkMove(List<Entity> map, List<Entity> entityList) {
        if(dir == DIR.DEFAULT){
            canMove = false;
            return;
        }
        int pos = x / SCALED_SIZE + y/SCALED_SIZE * WIDTH;
        Rectangle actor = null;
        //kiem tra va cham.
        switch (dir) {
            case UP:
                actor = new Rectangle(x+2, y-speed, SCALED_SIZE-10,SCALED_SIZE);
                if(!(map.get(pos - WIDTH) instanceof Grass) && checkCollision(actor, map.get(pos - WIDTH).getRec())) {
                    canMove = false;
                    return;
                }
                if(!(map.get(pos - WIDTH + 1) instanceof Grass) && checkCollision(actor, map.get(pos - WIDTH + 1).getRec())) {
                    canMove = false;
                    return;
                }
                canMove = true;
                return;

            case DOWN:
                actor = new Rectangle(x+2, y+speed, SCALED_SIZE-10,SCALED_SIZE);
                if(!(map.get(pos + WIDTH) instanceof Grass) && checkCollision(actor, map.get(pos + WIDTH).getRec())) {
                    canMove = false;
                    return;
                }
                if(!(map.get(pos + WIDTH + 1) instanceof Grass) && checkCollision(actor, map.get(pos + WIDTH + 1).getRec())) {
                    canMove = false;
                    return;
                }
                canMove = true;
                return;

            case LEFT:
                actor = new Rectangle(x-speed, y+2, SCALED_SIZE-10,SCALED_SIZE-6);
                if(!(map.get(pos - 1) instanceof Grass) && checkCollision(actor, map.get(pos  - 1).getRec())) {
                    canMove = false;
                    return;
                }
                if(!(map.get(pos + WIDTH - 1) instanceof Grass) && checkCollision(actor, map.get(pos + WIDTH - 1).getRec())) {
                    canMove = false;
                    return;
                }
                canMove = true;
                return;

            case RIGHT:
                actor = new Rectangle(x + speed, y+2, SCALED_SIZE-10,SCALED_SIZE-6);
                if(!(map.get(pos + 1) instanceof Grass) && checkCollision(actor, map.get(pos + 1).getRec())) {
                    canMove = false;
                    return;
                }
                if(!(map.get(pos + WIDTH + 1) instanceof Grass) && checkCollision(actor, map.get(pos + WIDTH + 1).getRec())) {
                    canMove = false;
                    return;
                }
                canMove = true;
                return;

            default: {
                canMove = false;
                return;
            }
        }
    }

    protected Image animateImage(Sprite normal, Sprite x1, Sprite x2) {
        //if(presentImg == timeAnimate) presentImg = 0;
        presentImg %= timeAnimate;
        return Sprite.movingSprite(normal, x1, x2, (presentImg++) ,timeAnimate).getFxImage();
    }

    abstract protected void updateImage();

}
