package uet.oop.bomberman.entities.Item;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Tiles.Grass;

import static uet.oop.bomberman.BombermanGame.*;

public class Portal extends Item {

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
