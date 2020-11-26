package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

import uet.oop.bomberman.graphics.Sprite;
import static uet.oop.bomberman.BombermanGame.*;
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
    public void checkMove(List<Entity> bombs) {
        if(dir == DIR.DEFAULT){
            canMove = false;
            return;
        }
        int pos = getPosition();
        Rectangle actor = null;
        Entity temp1 = null, temp2 = null;
        //kiem tra va cham vs map.
        switch (dir) {
            case UP:
                actor = new Rectangle(x+2, y-speed, SCALED_SIZE-10,SCALED_SIZE);
                temp1 = map.get(pos - WIDTH);
                temp2 = map.get(pos - WIDTH + 1);
                if(!(temp1 instanceof Grass) && checkCollision(actor, temp1.getRec())) {
                    canMove = false;
                    return;
                }
                if(!(temp2 instanceof Grass) && checkCollision(actor, temp2.getRec())) {
                    canMove = false;
                    return;
                }
                break;

            case DOWN:
                actor = new Rectangle(x+2, y+speed, SCALED_SIZE-10,SCALED_SIZE);
                temp1 = map.get(pos + WIDTH);
                temp2 = map.get(pos + WIDTH + 1);
                if(!(temp1 instanceof Grass) && checkCollision(actor, temp2.getRec())) {
                    canMove = false;
                    return;
                }
                if(!(temp2 instanceof Grass) && checkCollision(actor, temp2.getRec())) {
                    canMove = false;
                    return;
                }
                break;

            case LEFT:
                actor = new Rectangle(x-speed, y+2, SCALED_SIZE-10,SCALED_SIZE-6);
                temp1 = map.get(pos - 1);
                temp2 = map.get(pos + WIDTH - 1);
                if(!(temp1 instanceof Grass) && checkCollision(actor, temp1.getRec())) {
                    canMove = false;
                    return;
                }
                if(!(temp2 instanceof Grass) && checkCollision(actor, temp2.getRec())) {
                    canMove = false;
                    return;
                }
                break;

            case RIGHT:
                actor = new Rectangle(x + speed, y+2, SCALED_SIZE-10,SCALED_SIZE-6);
                temp1 = map.get(pos + 1);
                temp2 = map.get(pos + WIDTH + 1);
                if(!(temp1 instanceof Grass) && checkCollision(actor, temp1.getRec())) {
                    canMove = false;
                    return;
                }
                if(!(temp2 instanceof Grass) && checkCollision(actor, temp2.getRec())) {
                    canMove = false;
                    return;
                }
                break;

        }
        //kiem tra va cham vs bomb.
        actor = new Rectangle(x , y, SCALED_SIZE ,SCALED_SIZE);
        for (int i = 0; i < bombs.size(); i++) {
            if(!checkCollision(actor, bombs.get(i).getRec())) {
                Rectangle tempActor = null;
                switch (dir) {
                    case UP:
                        tempActor = new Rectangle(x, y-speed, SCALED_SIZE,SCALED_SIZE);
                        if(checkCollision(tempActor, bombs.get(i).getRec())) {
                            canMove = false;
                            return;
                        }
                        break;

                    case DOWN:
                        tempActor = new Rectangle(x, y+speed, SCALED_SIZE,SCALED_SIZE);
                        if(checkCollision(tempActor, bombs.get(i).getRec())) {
                            canMove = false;
                            return;
                        }
                        break;

                    case LEFT:
                        tempActor = new Rectangle(x-speed, y, SCALED_SIZE,SCALED_SIZE);
                        if(checkCollision(tempActor, bombs.get(i).getRec())) {
                            canMove = false;
                            return;
                        }
                        break;

                    case RIGHT:
                        tempActor = new Rectangle(x + speed, y, SCALED_SIZE,SCALED_SIZE);
                        if(checkCollision(tempActor, bombs.get(i).getRec())) {
                            canMove = false;
                            return;
                        }
                        break;

                }
            }
        }
        canMove = true;

    }

    protected Image animateImage(Sprite normal, Sprite x1, Sprite x2) {
        //if(presentImg == timeAnimate) presentImg = 0;
        presentImg %= timeAnimate;
        return Sprite.movingSprite(normal, x1, x2, (presentImg++) ,timeAnimate).getFxImage();
    }

    abstract protected void updateImage();

}
