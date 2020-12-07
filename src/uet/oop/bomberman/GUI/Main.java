package uet.oop.bomberman.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                new File("src\\uet\\oop\\bomberman\\GUI\\View.fxml").toURI().toURL());
        Parent root = loader.load();
        Scene scene = new Scene(root);

        primaryStage.setTitle("MAIN MENU");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
