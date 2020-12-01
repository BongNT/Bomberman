package uet.oop.bomberman.highscore;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class HighScoreGUI extends Application {

    private ArrayList<HighScore> listHighScore;

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        launch(args);
    }

    public void start(Stage stage) throws FileNotFoundException {

        Image image = new Image(new FileInputStream("src\\uet\\oop\\bomberman\\HighScore\\background.jpg"));
        ImageView mv = new ImageView(image);
        Group root = new Group();
        root.getChildren().addAll(mv);

        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("HIGH SCORE");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void ReadFileHighScore() {
        listHighScore = new ArrayList<HighScore>();
        try {
            FileReader file = new FileReader("src\\uet\\oop\\bomberman\\HighScore\\ScoreDB.txt");
            BufferedReader input = new BufferedReader(file);
            String line;
            while ((line = input.readLine()) != null) {
                String[] str = line.split(":");
                String name = str[0];
                int score = Integer.parseInt(str[1]);
                HighScore highScore = new HighScore(name, score);
                listHighScore.add(highScore);
            }
        } catch (FileNotFoundException e) {
            e.getMessage();
        } catch (IOException e) {
            e.getMessage();
        }
    }
}
