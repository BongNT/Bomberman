package uet.oop.bomberman.GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import uet.oop.bomberman.BombermanGame;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Button bStart;
    @FXML
    private Button bBack;
    @FXML
    private TextField nameTextField;

    public void setStartButton() {
        Stage stage = new Stage();
        BombermanGame game = new BombermanGame();
        Scene scene = game.getScene(stage);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DropShadow shadow = new DropShadow();

        // Applying the DropShadow Effect
        bStart.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> bStart.setEffect(shadow));

        //Removing the shadow when the mouse cursor is off
        bStart.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> bStart.setEffect(null));

        bBack.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> bBack.setEffect(shadow));

        bBack.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> bBack.setEffect(null));

        // Handle button START (Join game)
        bStart.setOnAction(event -> {
            setStartButton();
        });

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
