/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import Entity.Staff;
import PassObjs.BasicObjs;
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
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import net.synedra.validatorfx.Validator;

/**
 * FXML Controller class
 *
 * @author Tee Zhuo Xuan
 */
public class LoginCONTR implements Initializable, BasicCONTRFunc {

    private BasicObjs passObj = new BasicObjs();
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
        receiveData();

    }

    @FXML
    private void login(MouseEvent event) {

        if (event.isPrimaryButtonDown() == true) {

            if (!validator.validate()) {
                return;
            }

            Staff loginStaff = validateUserAuthenticity();

            if (loginStaff != null) {
                //get inside the home page

                if (loginStaff.getIsFrozen() == true) {
                    alertDialog(AlertType.ERROR, "Error", "Staff Account frozen", "Please contact system administrator to unlock the account");
                    return;
                }

                passObj.setLoginStaff(loginStaff);
                switchScene("View/HomePage_UI.fxml", passObj, BasicObjs.forward);
            } else {
                //pop out dialog mention user id or password is wrong
                alertDialog(AlertType.ERROR, "Error", "Invalid User", "ID or password incorrect");
            }
        }
    }

    private Staff validateUserAuthenticity() {
        return LoginService.validateUserAuthenticity(txtStaffID.getText(), txtPassword.getText());
    }

    //<editor-fold defaultstate="collapsed" desc="Validation for all the neccessary fields in UI">
    public void inputValidation() {
        validator.createCheck()
                .dependsOn("userID", txtStaffID.textProperty())
                .withMethod(c -> {
                    String userName = c.get("userID");
                    userName = userName.trim();

                    if (userName.isEmpty()) {
                        c.error("Required ID Field");
                        return;
                    }
                })
                .decorates(txtStaffID);

        validator.createCheck()
                .dependsOn("password", txtPassword.textProperty())
                .withMethod(c -> {
                    String password = c.get("password");
                    password = password.trim();
                    if (password.isEmpty()) {
                        c.error("Required Password Field");
                        return;
                    }
                })
                .decorates(txtPassword);

    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Alert Dialog Creator">
    @Override
    public ButtonType alertDialog(Alert.AlertType alertType, String title, String headerTxt, String contentTxt) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerTxt);
        alert.setContentText(contentTxt);

        alert.showAndWait();
        return alert.getResult();
    }
//</editor-fold>

    @FXML
    private void goToForgotPassword(MouseEvent event) {
        alertDialog(AlertType.INFORMATION, "Information", "Contact System Administrator", "Kindly inform administrator for new password login");
    }

    @Override
    public boolean clearAllFieldsValue() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void switchScene(String fxmlPath, BasicObjs passObj, String direction) {
        // Step 3
        Stage stage = (Stage) btnLogin.getScene().getWindow();
        stage.close();
        try {
            // Step 4
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(fxmlPath)); // Example: "View/HomePage_UI.fxml"
            // Step 5
            stage.setUserData(sendData(passObj, direction));
            // Step 6
            Scene scene = new Scene(root);

            stage.setScene(scene);
            // Step 7
            stage.show();

        } catch (IOException e) {
            System.err.println(String.format("Error: %s", e.getMessage()));
        }
    }

    @Override
    public BasicObjs sendData(BasicObjs passObj, String direction) {
        String currentPath = "View/Login_UI.fxml";
        passObj.getFxmlPaths().clear();
        passObj.getFxmlPaths().addLast(currentPath);
        System.out.println("Here is lgin" + passObj.getLoginStaff().getStaffID());

        passObj.setPassDirection(direction);
        return passObj;
    }

    @Override
    public void receiveData() {
        if (btnLogin.getScene() != null) {

            // Step 1
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            // Step 2
            if (stage.getUserData() != null) {

                passObj = (BasicObjs) stage.getUserData();
                if (passObj.getLoginStaff() == null) {
                    // do nothing
                } else {
                    // session timeout banner show up
                }
                switch (passObj.getPassDirection()) {
                    //receive data from after scene;
                    case BasicObjs.back:
                        if (passObj.getFxmlPaths().getLength() != 0) {
                            passObj.getFxmlPaths().delLast();
                        }
                        break;
                }
            } else {
                passObj = new BasicObjs();
            }
        }
    }

    @Override
    public void quitWindow(String title, String headerTxt, String contentTxt) {
//Unused
    }

}
