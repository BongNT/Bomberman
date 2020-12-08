package uet.oop.bomberman.entities.Item;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Tiles.Grass;

import java.awt.*;

import static uet.oop.bomberman.BombermanGame.bomberman;
import static uet.oop.bomberman.BombermanGame.map;
import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;

public abstract class Item extends Entity {
    public boolean isExist = true;
    protected boolean isHiding = true;

    public Item(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public Rectangle getRec() {
        return new Rectangle(x + SCALED_SIZE / 4, y + SCALED_SIZE / 4, SCALED_SIZE / 2, SCALED_SIZE / 2);
    }

    @Override
    public void update() {
        if (checkCollision(getRec(), bomberman.getRec())) {
            powerUpBomber();
            isExist = false;
            img = null;
        }
        if (map.get(getPosition()) instanceof Grass) {
            isHiding = false;
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!isHiding) {
            super.render(gc);
        }
    }

    public abstract void powerUpBomber();
}
