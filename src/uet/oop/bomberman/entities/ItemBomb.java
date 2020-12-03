package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

import static uet.oop.bomberman.BombermanGame.bomberman;

public class ItemBomb extends Item {
    public ItemBomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void powerUpBomber() {
        bomberman.increaseBomb();
        System.out.println(2);
    }
}
