package uet.oop.bomberman.entities.Interface;

import uet.oop.bomberman.entities.Entity;

import java.util.List;

public interface Movable {
    void moveUp();

    void moveDown();

    void moveLeft();

    void moveRight();

    void notMoving();

    void checkMove(List<Entity> entityList);
}
