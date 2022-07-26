/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class VehicleAccessoriesSalesSystem extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        String targetFXMLPath = "View/Login_UI.fxml";

        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(targetFXMLPath));
            Scene scene = new Scene(root);
            //primaryStage.initStyle(StageStyle.TRANSPARENT);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
