package uet.oop.bomberman.GUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import uet.oop.bomberman.BombermanGame;

import java.net.URL;
import java.util.ResourceBundle;

import static uet.oop.bomberman.GUI.Main.menuScene;

public class LoginController extends MenuController implements Initializable {
    @FXML
    private Button bStart;
    @FXML
    private Button bBack;
    @FXML
    private TextField nameTextField;
    public static BombermanGame game = new BombermanGame();
    public void setStartButton() {
        Stage stage = (Stage) bStart.getScene().getWindow();
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
            stage.setTitle("MAIN MENU");
            stage.setScene(menuScene);
        });
    }
}
