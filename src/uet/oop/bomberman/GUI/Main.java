package uet.oop.bomberman.GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        Platform.setImplicitExit(false);
        Application.launch(Main.class);
    }

    public static void loadMenuStage(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                new File("src\\uet\\oop\\bomberman\\GUI\\View.fxml").toURI().toURL());
        Parent root = loader.load();
        Scene scene = new Scene(root);

        stage.setTitle("MAIN MENU");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
        loadMenuStage(primaryStage);
    }
}
