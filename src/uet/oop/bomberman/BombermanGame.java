package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BombermanGame extends Application {

    public static int WIDTH ;
    public static int HEIGHT ;

    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();
    private Actor bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());

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
                    //set bom
                    break;
            }
        });
        scene.setOnKeyReleased((KeyEvent key) -> bomberman.notMoving());


        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            /*private final long[] frameTimes = new long[100];
            private int frameTimeIndex = 0 ;
            private boolean arrayFilled = false ;*/

            @Override
            public void handle(long now) {

                /*long oldFrameTime = frameTimes[frameTimeIndex] ;
                frameTimes[frameTimeIndex] = now ;
                frameTimeIndex = (frameTimeIndex + 1) % frameTimes.length ;
                if (frameTimeIndex == 0) {
                    arrayFilled = true ;
                }
                if (arrayFilled) {
                    long elapsedNanos = now - oldFrameTime ;
                    long elapsedNanosPerFrame = elapsedNanos / frameTimes.length ;
                    double frameRate = 1_000_000_000.0 / elapsedNanosPerFrame ;
                    System.out.println(String.format("Current frame rate: %.3f", frameRate));
                    //label.setText(String.format("Current frame rate: %.3f", frameRate));
                }*/
                render();
                update();

            }
        };
        timer.start();




        entities.add(bomberman);
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
                    Entity object;
                    char p = temp.charAt(j);
                    switch (p) {
                        case '#':
                            object = new Wall(j, i, Sprite.wall.getFxImage());
                            break;
                        /*case '*':
                            object = new Wall(j, j, Sprite.brick.getFxImage());
                            break;*/
                        /*case 'x':
                            object = new Wall(i, j, Sprite.portal.getFxImage());
                            break;
                        case '*':
                            object = new Wall(i, j, Sprite.wall.getFxImage());
                            break;*/
                        default:
                            object = new Grass(j, i, Sprite.grass.getFxImage());
                            break;
                    }
                    stillObjects.add(object);
                    /*if (j == 0 || j == HEIGHT - 1 || i == 0 || i == WIDTH - 1) {
                        object = new Wall(i, j, Sprite.wall.getFxImage());
                    }
                    else {
                        object = new Grass(i, j, Sprite.grass.getFxImage());
                    }*/
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void update() {
        bomberman.checkMove(stillObjects, entities);
        entities.forEach(Entity::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }
}
