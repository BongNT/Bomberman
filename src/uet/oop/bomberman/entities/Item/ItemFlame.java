package uet.oop.bomberman.entities.Item;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Bomb.Bomb;
import static uet.oop.bomberman.Sound.Sound.eatItemSound;
import static uet.oop.bomberman.Sound.Sound.playMedia;

public class ItemFlame extends Item {
    public ItemFlame(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void powerUpBomber() {
        playMedia(eatItemSound);
        Bomb.increaseLength();

    }
}
