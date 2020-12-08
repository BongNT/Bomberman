package uet.oop.bomberman.entities.Item;

import javafx.scene.image.Image;
import static uet.oop.bomberman.BombermanGame.bomberman;
import static uet.oop.bomberman.Sound.Sound.eatItemSound;
import static uet.oop.bomberman.Sound.Sound.playMedia;

public class ItemSpeed extends Item {
    public ItemSpeed(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void powerUpBomber() {
        playMedia(eatItemSound);
        bomberman.increaseSpeed();
    }
}
