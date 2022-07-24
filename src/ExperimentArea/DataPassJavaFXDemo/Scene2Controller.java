package ExperimentArea.DataPassJavaFXDemo;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Scene2Controller extends SceneController {

    private String passedInformation;

    @FXML
    private Label lblScene2;

    public void testPassedFromJavaFX() {
        System.out.println(passedInformation);
    }

    public void receiveData(String passedInformation) {
        this.passedInformation = passedInformation;
    }

    public void goToScene1(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene1.fxml"));
        Parent root = (Parent) loader.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
