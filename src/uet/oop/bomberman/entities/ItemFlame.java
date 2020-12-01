package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public class ItemFlame extends Item {
    public ItemFlame(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void powerUpBomber() {
        Bomb.increaseLength();
    }
}
