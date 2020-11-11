package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.graphics.Sprite;

import java.util.List;

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
        for (int i = 0; i < map.size();i++) {

            System.out.print(dir.toString());
            switch (dir) {
                case UP:
                    if (!checkCollision(map.get(i),this )){
                    //if (map.get(i).y - SCALED_SIZE >= y - speed) {
                        canMove = true;
                        return;
                    } else {
                        canMove = false;
                        return;
                    }

                case DOWN:
                    if (map.get(i).y <= y + speed) {
                        canMove = true;
                        return;
                    } else {
                        canMove = false;
                        return;
                    }

                case LEFT:
                    if (map.get(i).x + SCALED_SIZE <= x - speed) {
                        canMove = true;
                        return;
                    } else {
                        canMove = false;
                        return;
                    }

                case RIGHT:
                    if (map.get(i).x >= x + speed) {
                        canMove = true;
                        return;
                    } else {
                        canMove = false;
                        return;
                    }

                default: {
                    canMove = false;
                    return;
                }
            }
        }
    }


}
