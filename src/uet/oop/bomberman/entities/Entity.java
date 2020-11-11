package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.graphics.Sprite;

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
        int x1 = entity1.x;
        int y1 = entity1.y;
        int a1 = x1 + SCALED_SIZE;
        int b1 = y1 + SCALED_SIZE;
        int S = SCALED_SIZE;
        int x2 = entity2.x;
        int y2 = entity2.y;
        int a2 = x2 + SCALED_SIZE;
        int b2 = y2 + SCALED_SIZE;
        if (a1 < a2 && a1 > x2) {
            if (b1 > y2 && b1 < b2) {//1
                return true;
            }
            if (y1 > y2 && y1 < b2) {//3
                return true;
            }
        }
        if (x1 > x2 && x1 < a2) {
            if (b1 > y2 && b1 < b2) {//2
                return true;
            }
            if (y1 < b2 && y1 > y2) {
                return true;
            }
        }
        //TH:rec1 > rec2
        if (a2 < a1 && a2 > x1) {
            if (b2 > y1 && b2 < b1) {//1
                return true;
            }
            if (y2 > y1 && y2 < b1) {//3
                return true;
            }
        }
        if (x2 > x1 && x2 < a1) {
            if (b2 > y1 && b2 < b1) {//2
                return true;
            }
            if (y2 < b1 && y2 > y1) {
                return true;
            }
        }
        if (x1 == x2 && a1 == a2 && y1 == y2)return true;
        if (x1 == x2 && a1 == a2) {
            if (y1 < y2 && S + S > b2 - y1)return true;
            if (y1 > y2 && S + S - 40 > b1 - y2)return true;
        }
        if (y1 == y2 && b1 == b2) {
            if (x1<x2 && S + S>a2 - x1)return true;
            if (x1>x2 && S + S>a1 - x2)return true;
        }
        return false;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }
    public abstract void update();
}
