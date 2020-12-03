package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;

public class BombermanGame extends Application {

    public static final int FPS = 20;
    public static final int timeEachFrame = 1000 / FPS;
    public static int WIDTH;
    public static int HEIGHT;
    public static List<Enemy> enemies = new ArrayList<>();
    public static List<Entity> map = new ArrayList<>();
    public static List<Item> items = new ArrayList<>();
    public static Bomber bomberman = null;
    private GraphicsContext gc;
    private Canvas canvas;

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        createMap();

        // Create Canvas
        canvas = new Canvas(SCALED_SIZE * WIDTH, SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Create root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Create scene
        Scene scene = new Scene(root);

        // Create control
        scene.setOnKeyPressed(key -> {

            switch (key.getCode()) {
                case UP:
                    bomberman.moveUp();
                    break;
                case DOWN:
                    bomberman.moveDown();
                    break;
                case LEFT:
                    bomberman.moveLeft();
                    break;
                case RIGHT:
                    bomberman.moveRight();
                    break;
                case SPACE:
                    bomberman.setBomb();
                    break;
            }
        });
        scene.setOnKeyReleased((KeyEvent key) -> bomberman.notMoving());


        // Insert scene into stage
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long t) {
                long start = System.currentTimeMillis();

                // The update functions
                update();
                render();

                long realTime = System.currentTimeMillis() - start;
                if (realTime < timeEachFrame) {
                    try {
                        Thread.sleep(timeEachFrame - realTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        timer.start();
    }

    public void createMap() {
        try {
            Scanner sc = new Scanner(new FileReader("res\\levels\\Level1.txt"));
            int level = sc.nextInt();
            HEIGHT = sc.nextInt();
            WIDTH = sc.nextInt();

            sc.nextLine();

            // Load map
            for (int i = 0; i < HEIGHT; i++) {
                String temp = sc.nextLine();
                for (int j = 0; j < WIDTH; j++) {
                    Entity object = null;
                    Enemy enemy = null;
                    char p = temp.charAt(j);

                    switch (p) {
                        case '#':
                            object = new Wall(j, i, Sprite.wall.getFxImage());
                            break;
                        case '*':
                            object = new Brick(j, i, Sprite.brick.getFxImage());
                            break;
                        case 'p':
                            bomberman = new Bomber(j, i, Sprite.player_right.getFxImage());
                            object = new Grass(j, i, Sprite.grass.getFxImage());
                            break;
                        case '1':
                            enemy = new Balloom(j, i, Sprite.balloom_left1.getFxImage());
                            object = new Grass(j, i, Sprite.grass.getFxImage());
                            break;
                        case '2':
                            enemy = new Oneal(j, i, Sprite.oneal_left1.getFxImage());
                            object = new Grass(j, i, Sprite.grass.getFxImage());
                            break;
                        default:
                            object = new Grass(j, i, Sprite.grass.getFxImage());
                            break;
                    }
                    if (enemy != null) enemies.add(enemy);
                    map.add(object);
                }
            }

            // Load item
            int n = sc.nextInt();
            sc.nextLine();
            for (int i = 0; i < n; i++) {
                String temp = sc.nextLine();
                String[] s = temp.split(" ");
                if (s[0].equals("b")) {
                    for (int j = 1; j < s.length; j += 2) {
                        int xUnit = Integer.parseInt(s[j + 1]);
                        int yUnit = Integer.parseInt(s[j]);
                        items.add(new ItemBomb(xUnit, yUnit, Sprite.powerup_bombs.getFxImage()));
                    }
                } else if (s[0].equals("s")) {
                    for (int j = 1; j < s.length; j += 2) {
                        int xUnit = Integer.parseInt(s[j + 1]);
                        int yUnit = Integer.parseInt(s[j]);
                        items.add(new ItemSpeed(xUnit, yUnit, Sprite.powerup_speed.getFxImage()));
                    }
                } else if (s[0].equals("f")) {
                    for (int j = 1; j < s.length; j += 2) {
                        int xUnit = Integer.parseInt(s[j + 1]);
                        int yUnit = Integer.parseInt(s[j]);
                        items.add(new ItemFlame(xUnit, yUnit, Sprite.powerup_flames.getFxImage()));
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        bomberman.update();
        updateEnemy();
        items.forEach(Entity::update);
        map.forEach(Entity::update);
        updateItem();
        updateMap();
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        map.forEach(g -> g.render(gc));
        items.forEach(g -> g.render(gc));
        enemies.forEach(g -> g.render(gc));
        bomberman.render(gc);

    }

    private void updateMap() {
        int n = map.size();
        for (int j = 0; j < n; j++) {
            Entity entity = map.get(j);
            if (entity instanceof Brick && ((Brick) entity).isDestroyed) {
                Entity obj = new Grass(entity.getX() / SCALED_SIZE,
                        entity.getY() / SCALED_SIZE, Sprite.grass.getFxImage());
                map.remove(j);
                map.add(j, obj);
            }
        }
    }

    private void updateItem() {
        for (int i = 0; i < items.size(); i++) {
            if (!items.get(i).isExist) {
                items.remove(i);
                return;
            }
        }
    }

    private void updateEnemy() {
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            if(enemy !=null) {
                enemy.update();
                if(!enemy.alive) {
                    enemies.remove(enemy);
                }
            }
        }
    }
}
