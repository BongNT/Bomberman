package uet.oop.bomberman.entities.Item;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Item.Item;

import static uet.oop.bomberman.BombermanGame.bomberman;

public class ItemSpeed extends Item {
    public ItemSpeed(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void powerUpBomber() {
        bomberman.increaseSpeed();
    }
}
