package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

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
