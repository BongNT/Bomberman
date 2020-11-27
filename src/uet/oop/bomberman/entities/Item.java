package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import static uet.oop.bomberman.BombermanGame.bomberman;
import static uet.oop.bomberman.BombermanGame.map;

public abstract class Item extends Entity {
    protected boolean isHiding = true;
    public boolean isExsist = true;

    public Item(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        if (checkCollision(getRec(), bomberman.getRec())) {
            powerUpBomber();
            isExsist = false;
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
