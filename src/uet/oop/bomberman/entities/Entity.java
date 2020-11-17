package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;

import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;

public abstract class Entity {
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected int x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected int y;

    protected Image img;

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity( int xUnit, int yUnit, Image img) {
        this.x = xUnit * SCALED_SIZE;
        this.y = yUnit * SCALED_SIZE;
        this.img = img;
    }
    protected boolean checkCollision (Entity entity1, Entity entity2) {
        /*  A(x,y)----B(a,y)
		       |	     |
	        C(x,b)----D(a,b)
	    */
        if (entity1 instanceof Grass || entity2 instanceof Grass) {
            return false;
        }
        int x1 = entity1.x;
        int y1 = entity1.y;
        int S = SCALED_SIZE-1;
        int x2 = entity2.x;
        int y2 = entity2.y;

        return x1 + S > x2 && x2 + S > x1 && y1 + S > y2 && y2 + S > y1;
    }
    protected boolean checkCollision (Rectangle a1, Rectangle a2) {
        return a1.intersects(a2);
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }
    public abstract void update();
    public Rectangle getRec() {
        return new Rectangle(x,y,SCALED_SIZE,SCALED_SIZE);
    }
}
