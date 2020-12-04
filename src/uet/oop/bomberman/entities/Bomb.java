package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;

import static uet.oop.bomberman.BombermanGame.*;
import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;

public class Bomb extends Entity implements Destroyable {
    public boolean exploded;
    public boolean isExploding;
    private int timeExist = FPS * 3;
    private int timeExplode = FPS;

    // Current max length if not stuck with in wall
    private int flameLength = 2;
    public static int presentFlameLength = 2;
    private final Flame flameUp;
    private final Flame flameDown;
    private final Flame flameLeft;
    private final Flame flameRight;

    // Actual length
    private int upLength;
    private int downLength;
    private int leftLength;
    private int rightLength;

    public Bomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        exploded = false;
        isExploding = false;
        flameLength = presentFlameLength;
        upLength = setLengthFlame(DIR.UP);
        downLength = setLengthFlame(DIR.DOWN);
        leftLength = setLengthFlame(DIR.LEFT);
        rightLength = setLengthFlame(DIR.RIGHT);
        flameUp = new Flame(xUnit, yUnit - 1, Sprite.explosion_vertical_top_last.getFxImage(), upLength, DIR.UP);
        flameDown = new Flame(xUnit, yUnit + 1, Sprite.explosion_vertical_down_last.getFxImage(), downLength, DIR.DOWN);
        flameLeft = new Flame(xUnit - 1, yUnit, Sprite.explosion_horizontal_left_last.getFxImage(), leftLength, DIR.LEFT);
        flameRight = new Flame(xUnit + 1, yUnit, Sprite.explosion_horizontal_right_last.getFxImage(), rightLength, DIR.RIGHT);
    }

    public static void increaseLength() {
        if (presentFlameLength <= Flame.maxLength) presentFlameLength++;
    }

    @Override
    public void update() {

        if (!exploded) {
            img = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, timeExist--, FPS / 4).getFxImage();
        }
        //trước khi nổ
        if (timeExist == 0) {
            isExploding = true;
            //timeExsist = FPS * 3;
        }
        //bomb nổ
        if (isExploding) {
            loadDestroyImg();
            destroyObject();
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
            flameUp.render(gc, flameLength);
            flameDown.render(gc, flameLength);
            flameLeft.render(gc, flameLength);
            flameRight.render(gc, flameLength);
        }
    }

    private int setLengthFlame(DIR dir){
        int pos = getPosition();
        int length = 0;
        switch (dir) {
            case UP:
                for (int i = 1; i <= flameLength; i++) {
                    if (!(map.get(pos - i * WIDTH) instanceof Grass)) {
                        break;
                    }
                    length++;
                }
                break;
            case DOWN:
                for (int i = 1; i <= flameLength; i++) {
                    if (!(map.get(pos + i * WIDTH) instanceof Grass)) {
                        break;
                    }
                    length++;
                }
                break;
            case LEFT:
                for (int i = 1; i <= flameLength; i++) {
                    if (!(map.get(pos - i) instanceof Grass)) {
                        break;
                    }
                    length++;
                }
                break;
            case RIGHT:
                for (int i = 1; i <= flameLength; i++) {
                    if (!(map.get(pos + i) instanceof Grass)) {
                        break;
                    }
                    length++;
                }
                break;
        }
        return length;
    }

    private void destroyObject() {
        int pos = getPosition();
        if (upLength < flameLength) {
            upLength++;
        }
        for (int i = 1; i <= upLength; i++) {
            int j = pos - i * WIDTH;
            if(collisionWithMap(j)){
                upLength = i;
            }
        }
        if (downLength < flameLength) {
            downLength++;
        }
        for (int i = 1; i <= downLength; i++) {
            int j = pos + i * WIDTH;
            if(collisionWithMap(j)){
                downLength = i;
            }
        }
        if (leftLength < flameLength) {
            leftLength++;
        }
        for (int i = 1; i <= leftLength; i++) {
            int j = pos - i;
            if(collisionWithMap(j)){
                leftLength = i;
            }
        }
        if (rightLength < flameLength) {
            rightLength++;
        }
        for (int i = 1; i <= rightLength; i++) {
            int j = pos + i;
            if(collisionWithMap(j)){
                rightLength = i;
            }
        }
    }


    public boolean collisionWithActor(Actor actor) {
        if(actor instanceof Bomber) {
            Rectangle a = new Rectangle(actor.getX() + 5, actor.getY()+ 5,SCALED_SIZE - 15, SCALED_SIZE - 15);
            //Rectangle a = actor.getRec();
            return checkCollision(a, getVerticalRec()) || checkCollision(a, getHorizontalRec());
        }
        Rectangle a = new Rectangle(actor.getX(), actor.getY(), SCALED_SIZE - 10, SCALED_SIZE);
        return checkCollision(a, getVerticalRec()) || checkCollision(a, getHorizontalRec());
    }

    /**
     * check collision between flame and map.
     * @param pos position of entity in map
     * @return return true if flame impact wall, brick.
     */
    private boolean collisionWithMap(int pos){
        if (pos < 0 || pos > map.size()) {
            return true;
        }
        Entity temp = map.get(pos);
        if (temp instanceof Wall) {
            return true;
        }
        if (temp instanceof Brick) {
            ((Brick) temp).destroy();
            return true;
        }
        return  false;
    }

    private void setBombExplode(int timeExplode) {
        timeExist = 0;
        this.timeExplode = timeExplode;
        isExploding = true;
        timeExist = 0;
        flameUp.setTimeExplode(timeExplode);
        flameRight.setTimeExplode(timeExplode);
        flameLeft.setTimeExplode(timeExplode);
        flameDown.setTimeExplode(timeExplode);
    }

    public int getTimeExplode() {
        return timeExplode;
    }

    private int getVerticalLength() {
        return downLength + upLength + 1;
    }
    private int getHorizontalLength() {
        return leftLength + rightLength + 1;
    }
    private java.awt.Rectangle getHorizontalRec() {
        return new java.awt.Rectangle(x - leftLength * SCALED_SIZE,y,getHorizontalLength() * SCALED_SIZE, SCALED_SIZE);
    }
    private java.awt.Rectangle getVerticalRec() {
        return new java.awt.Rectangle(x , y - upLength * SCALED_SIZE, SCALED_SIZE, getVerticalLength() * SCALED_SIZE);
    }


    public void collisionWithBomb(Bomb bomb) {
        if(checkCollision(getRec(), bomb.getHorizontalRec()) ||
                checkCollision(getRec(), bomb.getVerticalRec()) ||
                checkCollision(getVerticalRec(), bomb.getRec()) ||
                checkCollision(getHorizontalRec(), bomb.getRec()))
        {
            if((this.isExploding &&!bomb.isExploding)) {
                //int min = Integer.min(bomb.getTimeExplode(),timeExplode);
                bomb.setBombExplode(timeExplode);
            }
        }
    }

    @Override
    public void loadDestroyImg() {
        img = Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1,
                Sprite.bomb_exploded2, timeExplode--, FPS).getFxImage();

    }

}

