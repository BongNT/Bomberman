package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;

import java.awt.*;

import static uet.oop.bomberman.BombermanGame.*;
import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;

public class Portal extends Item{

    public Portal(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }



    @Override
    public void update() {
        if (!isHiding && checkCollision(getRec(), bomberman.getRec())) {
            portal = null;
            isExist = false;
            img = null;
        }
        if(enemies.size() == 0 && map.get(getPosition()) instanceof Grass) {
            isHiding = false;
        }
    }

    @Override
    public void powerUpBomber() {

    }
}
