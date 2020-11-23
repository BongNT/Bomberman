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

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BombermanGame extends Application {

    public static int WIDTH ;
    public static int HEIGHT ;
    public static final int FPS = 20;
    public static final int timeEachFrame = 1000 / FPS;

    private GraphicsContext gc;
    private Canvas canvas;

    //private List<Entity> entities = new ArrayList<>();
    //map
    public static List<Entity> map = new ArrayList<>();
    private Bomber bomberman = null;

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        createMap();
        // Tao Canvas

        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        //tao control
        scene.setOnKeyPressed(key ->{

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


        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {


            @Override
            public void handle(long t) {
                long start = System.currentTimeMillis();
                //cac ham cap nhat.
                render();
                update();

                long realTime = System.currentTimeMillis() - start;
                if(realTime < timeEachFrame) {
                    try{
                        Thread.sleep(timeEachFrame - realTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        timer.start();
        //entities.add(new Bomb(32,32,Sprite.bomb.getFxImage()));
        //entities.add(bomberman);
    }

    public void createMap() {
        try{
            Scanner sc =  new Scanner(new FileReader("res\\levels\\Level1.txt"));
            int level = sc.nextInt();
            HEIGHT = sc.nextInt();
            WIDTH = sc.nextInt();

            sc.nextLine();
            for (int i = 0; i < HEIGHT; i++) {
                String temp = sc.nextLine();
                for (int j = 0; j < WIDTH; j++) {
                    Entity object = null;
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
                        /*case '*':
                            object = new Wall(i, j, Sprite.wall.getFxImage());
                            break;*/
                        default:
                            object = new Grass(j, i, Sprite.grass.getFxImage());
                            break;
                    }
                    map.add(object);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        bomberman.checkMove(map, bomberman.getBombs());
        bomberman.update();
        //entities.forEach(Entity::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        map.forEach(g -> g.render(gc));
        //entities.forEach(g -> g.render(gc));
        bomberman.render(gc);

    }
}
