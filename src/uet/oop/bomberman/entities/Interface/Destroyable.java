package uet.oop.bomberman.entities.Interface;

import javafx.scene.image.Image;

import static uet.oop.bomberman.BombermanGame.FPS;

public interface Destroyable {
    int timeDestroy = FPS;

    void loadDestroyImg();
}
