package ExperimentArea;

import java.io.IOException;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class PrimaryController {

    @FXML
    private Button showTempButton;

    @FXML
    private void showTemp() throws IOException {

        Scene currentScene = showTempButton.getScene();
        Parent currentRoot = currentScene.getRoot();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLfiles/temp.fxml"));
        Parent tempRoot = loader.load();

        currentScene.setRoot(tempRoot);

        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(e -> currentScene.setRoot(currentRoot));
        pause.play();
    }
}
