package uet.oop.bomberman.entities.Item;

import javafx.scene.image.Image;

import static uet.oop.bomberman.BombermanGame.bomberman;
import static uet.oop.bomberman.Sound.Sound.*;

public class ItemBomb extends Item {
    public ItemBomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void powerUpBomber() {
        bomberman.increaseBomb();
        playMedia(eatItemSound);
    }
}
