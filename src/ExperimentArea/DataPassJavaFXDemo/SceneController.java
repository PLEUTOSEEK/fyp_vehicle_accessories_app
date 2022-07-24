package ExperimentArea.DataPassJavaFXDemo;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController {

    protected Stage stage;
    protected Scene scene;
    protected Parent root;

    protected String globalTestString;

    public void switchCard(ActionEvent event) throws IOException {
        final Node source = (Node) event.getSource();
        String fxID = source.getId();
        String fxmlPath = selectFXMLPath(fxID);

        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private String selectFXMLPath(String fxID) {
        String fxmlPath = "Scene1.fxml";

        switch (fxID) {
            case "btnSwitchScene2":
                fxmlPath = "Scene2.fxml";
                break;
            case "btnSwitchScene1":
                fxmlPath = "Scene1.fxml";
                break;
            default:
                fxmlPath = "Scene1.fxml";
        }

        return fxmlPath;
    }
}
