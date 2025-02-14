package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import uet.oop.bomberman.GUI.Main;
import uet.oop.bomberman.entities.Character.Bomber;
import uet.oop.bomberman.entities.Character.Enemy.Balloom;
import uet.oop.bomberman.entities.Character.Enemy.Doll;
import uet.oop.bomberman.entities.Character.Enemy.Enemy;
import uet.oop.bomberman.entities.Character.Enemy.Oneal;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Enum.STATUS;
import uet.oop.bomberman.entities.HighScore;
import uet.oop.bomberman.entities.Item.*;
import uet.oop.bomberman.entities.Tiles.Brick;
import uet.oop.bomberman.entities.Tiles.Grass;
import uet.oop.bomberman.entities.Tiles.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static uet.oop.bomberman.Sound.Sound.*;
import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;

public class BombermanGame {
    private GraphicsContext gc;
    private Canvas canvas;
    private int timeLoadImg;
    public static STATUS status = STATUS.NEXTLEVEL;
    public static final int FPS = 20;
    public static final int timeEachFrame = 1000 / FPS;
    public static int WIDTH;
    public static int HEIGHT;
    public static List<Enemy> enemies ;
    public static List<Entity> map ;
    public static List<Item> items ;
    public static Portal portal ;
    public static Bomber bomberman ;
    public static int level ;
    public static int maxLevel ;
    public static int gameScore ;



    public Scene getScene (Stage stage) {
        stage.setTitle("BOMBERMAN");
        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
        System.out.println("reset");
        level = 1;
        maxLevel = 3;
        gameScore = 0;
        portal = null;
        if(bomberman != null)bomberman.clearBomb();
        bomberman = null;
        status = STATUS.NEXTLEVEL;
        timeLoadImg = FPS * 3;
        enemies = new ArrayList<>();
        map = new ArrayList<>();
        items = new ArrayList<>();
        Bomber.resetLife();
        createMap();
        playLoopMedia(gameSound);
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long t) {
                long start = System.currentTimeMillis();
                // The update functions
                loadStatusImg();
                update();
                render();
                System.out.println(1);
                if ((status == STATUS.WIN || status == STATUS.LOSE) && timeLoadImg == 0) {
                    System.out.println("end game");
                    try {
                        gameSound.stop();
                        //stage.setScene(null);
                        stop();
                        Main.loadMenuStage(stage);
                    } catch (IOException ioException) {
                        ioException.getMessage();
                    }
                }

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
                case Z:
                    enemies.clear();
                    playMedia(BomberDie);
                    break;
                case ESCAPE:
                    timer.stop();
                    gameSound.stop();
                    try {
                        Main.loadMenuStage(stage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        });
        scene.setOnKeyReleased((KeyEvent key) -> bomberman.notMoving());
        stage.setResizable(false);

        timer.start();
        return scene;
    }

    public void createMap() {
        try {
            String path = "res\\levels\\Level" + level + ".txt";
            FileReader fileReader = new FileReader(path);
            Scanner sc = new Scanner(fileReader);
            sc.nextInt();
            HEIGHT = sc.nextInt();
            WIDTH = sc.nextInt();
            int I = sc.nextInt();
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
                        case '3':
                            enemy = new Doll(j, i, Sprite.oneal_left1.getFxImage());
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
            for (int i = 0; i < I; i++) {
                String temp = sc.nextLine();
                String[] s = temp.split(" ");
                switch (s[0]) {
                    case "b":
                        for (int j = 1; j < s.length; j += 2) {
                            int xUnit = Integer.parseInt(s[j + 1]);
                            int yUnit = Integer.parseInt(s[j]);
                            items.add(new ItemBomb(xUnit, yUnit, Sprite.powerup_bombs.getFxImage()));
                        }
                        break;
                    case "s":
                        for (int j = 1; j < s.length; j += 2) {
                            int xUnit = Integer.parseInt(s[j + 1]);
                            int yUnit = Integer.parseInt(s[j]);
                            items.add(new ItemSpeed(xUnit, yUnit, Sprite.powerup_speed.getFxImage()));
                        }
                        break;
                    case "f":
                        for (int j = 1; j < s.length; j += 2) {
                            int xUnit = Integer.parseInt(s[j + 1]);
                            int yUnit = Integer.parseInt(s[j]);
                            items.add(new ItemFlame(xUnit, yUnit, Sprite.powerup_flames.getFxImage()));
                        }
                        break;
                    case "x":
                        for (int j = 1; j < s.length; j += 2) {
                            int xUnit = Integer.parseInt(s[j + 1]);
                            int yUnit = Integer.parseInt(s[j]);
                            portal = new Portal(xUnit, yUnit, Sprite.portal.getFxImage());
                        }
                        break;
                }
            }
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public void update() {
        if (status == STATUS.PLAYING) {
            bomberman.update();
            updateEnemy();
            items.forEach(Entity::update);
            map.forEach(Entity::update);
            updateItem();
            if (portal != null) portal.update();
            updateMap();
            updateStatus();
        }
    }

    public void render() {
        if (status == STATUS.PLAYING) {
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            map.forEach(g -> g.render(gc));
            items.forEach(g -> g.render(gc));
            if (portal != null) portal.render(gc);
            enemies.forEach(g -> g.render(gc));
            bomberman.render(gc);
        }
    }

    private void loadStatusImg() {
        Image image = null;
        if (status == STATUS.WIN) {
            if (timeLoadImg >= 0) timeLoadImg--;
            try {
                image = new Image(new FileInputStream("res/img/status/win.png"));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (timeLoadImg >= 0) gc.drawImage(image, 0, 0);
        } else if (status == STATUS.LOSE) {
            if (timeLoadImg >= 0) timeLoadImg--;
            try {
                image = new Image(new FileInputStream("res/img/status/lose.png"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (timeLoadImg > 0) gc.drawImage(image, 0, 0);
        } else if (status == STATUS.NEXTLEVEL) {
            if (timeLoadImg >= 0) timeLoadImg--;
            String path = "res/img/level/lv" + level + ".png";
            try {
                image = new Image(new FileInputStream(path));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (timeLoadImg > 0) gc.drawImage(image, 0, 0);
            if (timeLoadImg == 0) status = STATUS.PLAYING;
        }
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
            if (enemy != null) {
                enemy.update();
                if (!enemy.alive) {
                    gameScore += enemy.getScore();
                    System.out.println(gameScore);
                    enemies.remove(enemy);
                }
            }
        }
    }

    private void updateStatus() {
        if (enemies.size() == 0 && portal == null) {
            level++;
            //System.out.println(level);
            gameScore += (500 * level);
            timeLoadImg = FPS * 3;
            status = STATUS.NEXTLEVEL;
            if (level > maxLevel) {
                timeLoadImg = FPS * 3;
                status = STATUS.WIN;
                playMedia(winSound);
                gameScore += 2000;
                System.out.println(gameScore);
                return;
            }
            playMedia(levelUpSound);
            bomberman.clearBomb();
            items.clear();
            map.clear();
            createMap();
            System.out.println("CONGRATS ! YOU WIN");
        } else if (Bomber.life == 0) {
            timeLoadImg = FPS * 3;
            status = STATUS.LOSE;
            System.out.println(gameScore);
            System.out.println("YOU LOSE");
            playMedia(loseSound);
        }
    }
}
