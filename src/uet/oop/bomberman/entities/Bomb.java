package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;

import static uet.oop.bomberman.BombermanGame.*;
import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;

public class Bomb extends Entity{
    public boolean exploded;
    public boolean isExploding;
    private int timeExsist = FPS * 3;
    private int timeExplode = FPS;
    //độ dài max hiện tại nếu không vướng tường
    public int presentFlameLength = 3;
    private final Flame flameUp;
    private final Flame flameDown;
    private final Flame flameLeft;
    private final Flame flameRight;
    //độ dài thực tế
    private int upLength = setLengthFlame(DIR.UP);
    private int downLength = setLengthFlame(DIR.DOWN);
    private int leftLength = setLengthFlame(DIR.LEFT);
    private int rightLength = setLengthFlame(DIR.RIGHT);

    public Bomb(int x, int y, Image img) {
        super(x, y, img);
        exploded = false;
        isExploding = false;
        flameUp = new Flame(x, y - 1, Sprite.explosion_vertical_top_last.getFxImage(), upLength, DIR.UP);
        flameDown = new Flame(x, y + 1, Sprite.explosion_vertical_down_last.getFxImage(), downLength, DIR.DOWN);
        flameLeft = new Flame(x - 1, y, Sprite.explosion_horizontal_left_last.getFxImage(), leftLength, DIR.LEFT);
        flameRight = new Flame(x + 1, y, Sprite.explosion_horizontal_right_last.getFxImage(), rightLength, DIR.RIGHT);
    }

    public void increaseLength() {
        presentFlameLength ++;
        if(presentFlameLength > Flame.maxLength) presentFlameLength = Flame.maxLength;
    }

    @Override
    public void update() {
        if (!exploded)
            img = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, timeExsist--, FPS/4).getFxImage();
        //trước khi nổ
        if (timeExsist == 0) {
            isExploding = true;
            timeExsist = FPS * 3;
        }
        //bomb nổ
        if (isExploding) {
            img = Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1,
                    Sprite.bomb_exploded2, timeExplode--, FPS).getFxImage();
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
            flameUp.render(gc, presentFlameLength);
            flameDown.render(gc, presentFlameLength);
            flameLeft.render(gc, presentFlameLength);
            flameRight.render(gc, presentFlameLength);
        }
    }

    private int setLengthFlame(DIR dir){
        int pos = getPosition();
        int length = 0;
        switch (dir) {
            case UP:
                for(int i = 1; i <= presentFlameLength; i++) {
                    if(! (map.get(pos - i * WIDTH) instanceof Grass)) {
                        break;
                    }
                    length++;
                }
                break;
            case DOWN:
                for(int i = 1; i <= presentFlameLength; i++) {
                    if(! (map.get(pos + i * WIDTH) instanceof Grass)) {
                        break;
                    }
                    length++;
                }
                break;
            case LEFT:
                for(int i = 1; i <= presentFlameLength; i++) {
                    if(! (map.get(pos - i) instanceof Grass)) {
                        break;
                    }
                    length++;
                }
                break;
            case RIGHT:
                for(int i = 1; i <= presentFlameLength; i++) {
                    if(! (map.get(pos + i ) instanceof Grass)) {
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
        if (upLength < presentFlameLength) {
            upLength++;
        }
        for (int i = 1; i <= upLength; i++) {
            int j = pos - i * WIDTH;
            if(collisionWithMap(j)){
                upLength = i;
            }
        }
        if (downLength < presentFlameLength) {
            downLength++;
        }
        for (int i = 1; i <= downLength; i++) {
            int j = pos + i * WIDTH;
            if(collisionWithMap(j)){
                downLength = i;
            }
        }
        if (leftLength < presentFlameLength) {
            leftLength++;
        }
        for (int i = 1; i <= leftLength; i++) {
            int j = pos - i;
            if(collisionWithMap(j)){
                leftLength = i;
            }
        }
        if (rightLength < presentFlameLength) {
            rightLength++;
        }
        for (int i = 1; i <= rightLength; i++) {
            int j = pos + i;
            if(collisionWithMap(j)){
                rightLength = i;
            }
        }
        //kiểm tra vs vị trí qua bomb(vs người và enemies)

    }

    public boolean collisionWithActor(Actor actor) {
        Rectangle a = actor.getRec();
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
    private void setTime(int timeExplode) {
        timeExsist = 0;
        this.timeExplode = timeExplode;
    }

    public int getTimeExplode() {
        return timeExplode;
    }

    private int getVerticalLength() {
        return downLength + upLength;
    }
    private int getHorizontalLength() {
        return leftLength + rightLength;
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
            if((this.isExploding &&!bomb.isExploding) || (!this.isExploding && bomb.isExploding)) {
                int min = Integer.min(bomb.getTimeExplode(),timeExplode);
                setTime(min);
                isExploding =true;
                bomb.setTime(min);
                bomb.isExploding = true;

            }
        }

    }
}

