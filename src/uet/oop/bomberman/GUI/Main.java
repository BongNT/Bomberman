package uet.oop.bomberman.GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uet.oop.bomberman.BombermanGame;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class Main extends Application {
    public static Scene menuScene = createScene("src\\uet\\oop\\bomberman\\GUI\\View.fxml");
    public static Scene highScoreScene = createScene("src\\uet\\oop\\bomberman\\GUI\\HighScore.fxml");
    public static Scene loginScene = createScene("src\\uet\\oop\\bomberman\\GUI\\Login.fxml");
    //private static Stage stage;
    //public static Scene gameScene = new BombermanGame().getScene(stage);

    public static void main(String[] args) {
        Platform.setImplicitExit(false);
        Application.launch(Main.class);
    }

    public static void loadMenuStage(Stage stage) throws IOException {
        stage.setTitle("MAIN MENU");
        stage.setScene(menuScene);
        stage.show();
    }
    public static Scene createScene(String path) {
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(
                    new File(path).toURI().toURL());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        return scene;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        //stage = primaryStage;
        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
        loadMenuStage(primaryStage);
    }
}
