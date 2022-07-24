package ExperimentArea.DataPassJavaFXDemo;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Scene1Controller extends SceneController {

    @FXML
    private Button btnSwitchScene2;

    @FXML
    private TextField txtField;

    @FXML
    public void goToScene2(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene2.fxml"));
        Parent root = (Parent) loader.load();

        //===============================
        //If need to pass something to other page we just use this
        Scene2Controller secController = loader.getController();

        secController.receiveData(txtField.getText());
        //===============================

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
