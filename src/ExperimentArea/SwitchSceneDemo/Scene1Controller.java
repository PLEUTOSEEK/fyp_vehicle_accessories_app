package ExperimentArea.SwitchSceneDemo;

import javafx.event.ActionEvent;

public class Scene1Controller extends SceneController {

    public void testGlobalString(ActionEvent evt) {
        globalTestString = "Passing data to scene 2...";
        System.out.println(globalTestString);
    }
}
