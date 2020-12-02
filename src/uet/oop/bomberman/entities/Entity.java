package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.*;

import static uet.oop.bomberman.BombermanGame.WIDTH;
import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;

public abstract class Entity {
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected int x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected int y;

    //protected boolean alive = true;

    protected Image img;

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity(int xUnit, int yUnit, Image img) {
        this.x = xUnit * SCALED_SIZE;
        this.y = yUnit * SCALED_SIZE;
        this.img = img;
    }

    protected boolean checkCollision(Rectangle a1, Rectangle a2) {
        return a1.intersects(a2);
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    public abstract void update();

    public Rectangle getRec() {
        return new Rectangle(x, y, SCALED_SIZE, SCALED_SIZE);
    }

    public int getPosition() {
        return x / SCALED_SIZE + y / SCALED_SIZE * WIDTH;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
