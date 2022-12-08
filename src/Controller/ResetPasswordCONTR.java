/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Entity.Staff;
import PassObjs.BasicObjs;
import Service.GeneralRulesService;
import Service.StaffService;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.synedra.validatorfx.Check;
import net.synedra.validatorfx.Validator;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class ResetPasswordCONTR implements Initializable {

    @FXML
    private MFXButton btnConfirm;
    @FXML
    private MFXPasswordField txtNewPass;
    @FXML
    private MFXPasswordField txtCurrentPass;
    @FXML
    private MFXPasswordField txtRepeatPass;

    BasicObjs passObj = new BasicObjs();

    private Validator validator = new Validator();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                receiveData();
                inputValidation();
                autoClose();
            }
        });
    }

    public void inputValidation() {
        Check validatorCheck = (new Validator()).createCheck();

        /*
        No need include:
         */
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Current Password", this.txtCurrentPass.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Current Password");
                    textVal = textVal.trim();
                    /*
                     1. Not allow null
                     2. Must match with current password
                     */
                    // allow null
                    if (textVal.isEmpty()) {
                        c.error("Current Password - Required Field");
                        return;
                    }

                    if (!textVal.equals(passObj.getLoginStaff().getPassword())) {
                        c.error("Current Password - Not match with original password");
                        return;
                    }
                })
                .decorates(this.txtCurrentPass);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("New Password", this.txtNewPass.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("New Password");
                    textVal = textVal.trim();
                    /*
                     1. Not allow null
                     2. Must follow format
                     */
                    // allow null
                    if (textVal.isEmpty()) {
                        c.error("New Password - Required Field");
                        return;
                    }

                    //- Total length of the password at least 8.
                    if (textVal.length() < 8) {
                        c.error("New Password - Total length of the password at least 8");
                        return;
                    }

                    //-	Consist uppercase letter.
                    if (!textVal.matches("^(?=.*[A-Z]).+$")) {
                        c.error("New Password - uppercase letter required (e.g. !@#$%^&*()_+-=)");
                        return;
                    }
                    //-	Consist lowercase letter.
                    if (!textVal.matches("^(?=.*[a-z]).+$")) {
                        c.error("New Password - lowercase letter required (e.g. !@#$%^&*()_+-=)");
                        return;
                    }
                    //-	Consist numeric number.
                    if (!textVal.matches("^(?=.*\\d).+$")) {
                        c.error("New Password - numeric number required (e.g. !@#$%^&*()_+-=)");
                        return;
                    }
                    //-	Consist of a special symbol.
                    Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
                    Matcher specialCharacterMatcher = pattern.matcher(textVal);

                    if (!specialCharacterMatcher.find()) {
                        c.error("New Password - special symbol required (e.g. !@#$%^&*()_+-=)");
                        return;
                    }

                })
                .decorates(this.txtNewPass);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Repeat Password", this.txtRepeatPass.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Repeat Password");
                    textVal = textVal.trim();
                    /*
                     1. cannot be null
                     2. must match with new password
                     */
                    // allow null
                    if (textVal.isEmpty()) {
                        c.error("Repeat Password - Required Field");
                        return;
                    }

                    if (!textVal.equals(txtNewPass.getText())) {
                        c.error("Repeat Password - Not match with new password");
                        return;
                    }
                })
                .decorates(this.txtRepeatPass);

        validator.add(validatorCheck);

        //=====================================
    }

    public boolean clearAllFieldsValue() {
        // Unused
        return true;
    }

    public ButtonType alertDialog(Alert.AlertType alertType, String title, String headerTxt, String contentTxt) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerTxt);
        alert.setContentText(contentTxt);

        alert.showAndWait();
        return alert.getResult();
    }

    public void switchScene(String fxmlPath, BasicObjs passObj, String direction) {
        // Step 3
        Stage stage = (Stage) this.btnConfirm.getScene().getWindow();
        //stage.close();
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

    public BasicObjs sendData(BasicObjs passObj, String direction) {
        switch (direction) {
            case BasicObjs.forward:
                passObj.getFxmlPaths().addLast("View/ResetPassword_UI.fxml");
                break;
        }
        passObj.setPassDirection(direction);
        passObj.setLoginStaff(this.passObj.getLoginStaff());
        return passObj;
    }

    public void receiveData() {
        // Step 1
        Stage stage = (Stage) btnConfirm.getScene().getWindow();
        // Step 2
        if (stage.getUserData() != null) {
            passObj = (BasicObjs) stage.getUserData();
        } else {
            passObj = new BasicObjs();
        }
    }

    private void prepareStaffToObj() {
        passObj.getLoginStaff().setPassword(this.txtNewPass.getText());
        passObj.getLoginStaff().setResetPassNextLogin(false);
    }

    @FXML
    private void confirmAccount(MouseEvent event) {
        if (event.isPrimaryButtonDown() == true) {
            if (!validator.validate()) {
                return;
            }

            prepareStaffToObj();

            StaffService.updateStaffPassword(passObj.getLoginStaff());

            Stage stage = (Stage) btnConfirm.getScene().getWindow();
            stage.setUserData(passObj.getLoginStaff());
            stage.close();
        }
    }

    public void autoClose() {
        Duration delay1 = Duration.seconds(GeneralRulesService.getSessionTimeOut());
        PauseTransition transitionAlert = new PauseTransition(delay1);
        transitionAlert.setOnFinished(evt -> {
            this.passObj.setLoginStaff(new Staff());
            switchScene("View/Login_UI.fxml", passObj, BasicObjs.back);
        });
        transitionAlert.setCycleCount(1);

        btnConfirm.getScene().addEventFilter(InputEvent.ANY, evt -> transitionAlert.playFromStart());
    }
}
