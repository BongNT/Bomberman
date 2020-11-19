package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.BombermanGame.FPS;

public class Bomb extends Entity{
    public boolean exploded;
    private int timeExsist = FPS * 3;
    public Bomb(int x, int y, Image img) {
        super(x, y, img);
        exploded = false;
    }

    @Override
    public void update() {
        if(!exploded)img = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2,timeExsist--,5 ).getFxImage();
        if(timeExsist == 0){
            exploded = true;
            timeExsist = FPS * 3;
        }
    }
}
