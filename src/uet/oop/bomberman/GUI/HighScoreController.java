package uet.oop.bomberman.GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class HighScoreController implements Initializable {
    @FXML private Button bBack;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DropShadow shadow = new DropShadow();

        bBack.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> bBack.setEffect(shadow));

        bBack.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> bBack.setEffect(null));

        bBack.setOnAction(event -> {
            Stage stage = (Stage) bBack.getScene().getWindow();
            FXMLLoader loader = null;
            try {
                loader = new FXMLLoader(
                        new File("src\\uet\\oop\\bomberman\\GUI\\View.fxml").toURI().toURL());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            Scene scene = new Scene(root);
            stage.setTitle("MAIN MENU");
            stage.setScene(scene);
        });
    }
}
