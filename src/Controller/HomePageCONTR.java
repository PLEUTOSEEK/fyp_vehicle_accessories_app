/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import Entity.Staff;
import io.github.palexdev.materialfx.controls.MFXCircleToggleNode;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Tee Zhuo Xuan
 */
public class HomePageCONTR implements Initializable {

    private Staff loginStaff;

    @FXML
    private MFXCircleToggleNode btnSetting;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                receiveData();
            }
        });
    }

    private void receiveData() {
        // Step 1
        Stage stage = (Stage) btnSetting.getScene().getWindow();
        // Step 2
        this.loginStaff = (Staff) stage.getUserData();
    }
}
