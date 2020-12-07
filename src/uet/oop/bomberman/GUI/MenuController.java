package uet.oop.bomberman.GUI;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    @FXML
    private Button bPlay;
    @FXML
    private Button bHighScore;
    @FXML
    private Button bInstruction;
    @FXML
    private Button bExit;

    public void setPlayButton() throws IOException {
        Stage stage = (Stage) bPlay.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(
                new File("src\\uet\\oop\\bomberman\\GUI\\Login.fxml").toURI().toURL());
        Parent root = loader.load();
        Scene scene = new Scene(root);
        LoginController controller = loader.getController();
        stage.setScene(scene);
    }

    public void setInstructionButton() {
        Stage stageIns = new Stage();
        StackPane stIns = new StackPane();
        Image img = null;
        try {
            img = new Image(new FileInputStream("res\\img\\menu\\tutorial.png"));
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        ImageView view = new ImageView(img);
        stIns.getChildren().add(view);
        stageIns.setTitle("INSTRUCTION");
        stageIns.setScene(new Scene(stIns, 800, 600));
        stageIns.show();
    }

    public void setExitButton() {
        Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION);
        exitAlert.setTitle("EXIT GAME");
        exitAlert.setHeaderText("DO YOU WANT TO QUIT ?");
        exitAlert.setContentText(" Choose your option");

        ButtonType btYes = new ButtonType("YES", ButtonBar.ButtonData.YES);
        ButtonType btCancel = new ButtonType("CANCEL", ButtonBar.ButtonData.CANCEL_CLOSE);

        exitAlert.getButtonTypes().setAll(btYes, btCancel);

        Optional<ButtonType> result = exitAlert.showAndWait();   // Return users' choice

        if (result.get() == btYes) {
            Platform.exit();
            System.out.println("EXIT GAME SUCCESSFULLY");
        } else if (result.get() == btCancel) {
            System.out.println("RETURN MAIN MENU");
            exitAlert.close();
        }
    }

    public void setShadow() {
        DropShadow shadow = new DropShadow();

        // Applying the DropShadow Effect
        bPlay.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> bPlay.setEffect(shadow));

        //Removing the shadow when the mouse cursor is off
        bPlay.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> bPlay.setEffect(null));

        bHighScore.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> bHighScore.setEffect(shadow));

        bHighScore.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> bHighScore.setEffect(null));

        bInstruction.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> bInstruction.setEffect(shadow));

        bInstruction.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> bInstruction.setEffect(null));

        bExit.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> bExit.setEffect(shadow));

        bExit.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> bExit.setEffect(null));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set shadow for buttons
        setShadow();

        // Handle button EXIT
        bExit.setOnAction(event -> {
            setExitButton();
        });

        // Handle button INSTRUCTION
        bInstruction.setOnAction(event -> {
            System.out.println("Show the instruction");
            setInstructionButton();
        });

        // Handle button PLAY GAME
        bPlay.setOnAction(event -> {
            try {
                setPlayButton();
            } catch (IOException ioException) {
                System.out.println("Unchecked");
                ioException.getMessage();
            }
        });

        // Handle button HIGH SCORE
        bHighScore.setOnAction(event -> {
        });
    }
}
