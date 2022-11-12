/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import Entity.Staff;
import Service.LoginService;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import net.synedra.validatorfx.Validator;

/**
 * FXML Controller class
 *
 * @author Tee Zhuo Xuan
 */
public class LoginCONTR implements Initializable {

    @FXML
    private MFXTextField txtStaffID;
    @FXML
    private MFXPasswordField txtPassword;
    @FXML
    private MFXButton btnLogin;
    @FXML
    private MFXButton btnForgetPassword;

    private Validator validator = new Validator();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        inputValidation();

    }

    @FXML
    private void login(MouseEvent event) {

        if (event.isPrimaryButtonDown() == true) {

            if (validator.containsErrors()) {
                alertDialog(AlertType.WARNING, "Warning", "Validation Message", validator.createStringBinding().getValue());
                return;
            }

            Staff loginStaff = validateUserAuthenticity();

            if (loginStaff != null) {
                //get inside the home page
                sendData(loginStaff);
            } else {
                //pop out dialog mention user id or password is wrong
                alertDialog(AlertType.ERROR, "Error", "Invalid User", validator.createStringBinding().getValue());
            }
        }
    }

    private Staff validateUserAuthenticity() {
        return LoginService.validateUserAuthenticity(txtStaffID.getText(), txtPassword.getText());
    }

    //<editor-fold defaultstate="collapsed" desc="Validation for all the neccessary fields in UI">
    private void inputValidation() {
        validator.createCheck()
                .dependsOn("userID", txtStaffID.textProperty())
                .withMethod(c -> {
                    String userName = c.get("userID");
                    if (userName == "") {
                        c.error("Required ID Field");
                    }
                })
                .decorates(txtStaffID)
                .immediate();

        validator.createCheck()
                .dependsOn("password", txtPassword.textProperty())
                .withMethod(c -> {
                    String password = c.get("password");
                    if (password == "") {
                        c.error("Required Password Field");
                    }
                })
                .decorates(txtPassword)
                .immediate();

    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Alert Dialog Creator">
    private void alertDialog(AlertType alertType, String title, String headerTxt, String contentTxt) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerTxt);
        alert.setContentText(contentTxt);

        alert.showAndWait();
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Send Data to Home Page">
    private void sendData(Object staff) {
        // Step 3
        Stage stage = (Stage) btnLogin.getScene().getWindow();
        stage.close();
        try {
            // Step 4
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("View/HomePage_UI.fxml"));
            // Step 5
            stage.setUserData(staff);
            // Step 6
            Scene scene = new Scene(root);
            stage.setScene(scene);
            // Step 7
            stage.show();
        } catch (IOException e) {
            System.err.println(String.format("Error: %s", e.getMessage()));
        }
    }
//</editor-fold>

    @FXML
    private void goToForgotPassword(MouseEvent event) {
        alertDialog(AlertType.INFORMATION, "Information", "Contact System Administrator", "Kindly inform administrator for new password login");
    }

}
