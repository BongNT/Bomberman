package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;
import java.util.List;

import static uet.oop.bomberman.BombermanGame.WIDTH;
import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;

public abstract class Actor extends Entity implements Movable{
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

        switch (dir) {
            case UP:
                Rectangle actor = new Rectangle(x+1, y-1, SCALED_SIZE-1,SCALED_SIZE-1);
                if(checkCollision(actor, map.get(pos - WIDTH).getRec())) {
                    canMove = false;
                    return;
                }
                if(checkCollision(actor, map.get(pos-WIDTH-1).getRec())) {
                    canMove = false;
                    return;
                }
                canMove = true;
                return;

            case DOWN:
                if(checkCollision(this, map.get(pos + WIDTH))) {
                    canMove = false;
                    return;
                }
                if(checkCollision(this, map.get(pos+WIDTH+1))) {
                    canMove = false;
                    return;
                }
                canMove = true;
                return;

            case LEFT:
                if(checkCollision(this, map.get(pos - 1))) {
                    canMove = false;
                    return;
                }
                if(checkCollision(this, map.get(pos+WIDTH-1))) {
                    canMove = false;
                    return;
                }
                canMove = true;
                return;

            case RIGHT:
                if(checkCollision(this, map.get(pos + 1))) {
                    canMove = false;
                    return;
                }
                if(checkCollision(this, map.get(pos+WIDTH+1))) {
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


}
