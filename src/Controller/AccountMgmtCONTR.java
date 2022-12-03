/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import Entity.Staff;
import PassObjs.BasicObjs;
import Service.GeneralRulesService;
import Service.StaffService;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXCircleToggleNode;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.synedra.validatorfx.Check;
import net.synedra.validatorfx.Validator;

/**
 * FXML Controller class
 *
 * @author Tee Zhuo Xuan
 */
public class AccountMgmtCONTR implements Initializable, BasicCONTRFunc {

    @FXML
    private MFXTextField txtStaffID;
    @FXML
    private MFXPasswordField txtPassword;
    @FXML
    private MFXButton btnConfirm;
    @FXML
    private MFXCheckbox chkFrozen;
    @FXML
    private MFXCheckbox chkResetPass;

    private Validator validator = new Validator();

    private BasicObjs passObj;
    @FXML
    private MFXCircleToggleNode ctnStaffSelection;
    @FXML
    private MFXButton btnCancel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                inputValidation();
                receiveData();
                autoClose();
            }
        });
    }

    private void fieldFillIn() {
        if (passObj.getObj() != null) {

            Staff staff = (Staff) passObj.getObj();

            this.txtPassword.setText(staff.getPassword());
            this.chkFrozen.setSelected(staff.getIsFrozen());
            this.chkResetPass.setSelected(staff.getResetPassNextLogin());

        }

    }

    private void defaultValFillIn() {
    }

    @Override
    public void inputValidation() {
        Check validatorCheck = (new Validator()).createCheck();

        /*
        No need include:
        1. is frozen
        2. reset password checkbox
         */
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Staff ID", this.txtStaffID.textProperty()
                )
                .withMethod(c -> {
                    String textVal = c.get("Staff ID");
                    textVal = textVal.trim();
                    /*
                     1. cannot be null
                     */
                    // allow null
                    if (textVal.isEmpty()) {
                        c.error("Staff ID - Required Field");
                        return;
                    }
                })
                .decorates(this.txtStaffID);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Password", this.txtPassword.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Password");
                    textVal = textVal.trim();
                    /*
                     1. cannot be null
                     2. must have at least low character
                     */
                    // allow null
                    if (textVal.isEmpty()) {
                        c.error("Password - Required Field");
                        return;
                    }

                    //- Total length of the password at least 8.
                    if (textVal.length() < 8) {
                        c.error("Password - Total length of the password at least 8");
                        return;
                    }

                    //-	Consist uppercase letter.
                    if (!textVal.matches("^(?=.*[A-Z]).+$")) {
                        c.error("Password - uppercase letter required (e.g. !@#$%^&*()_+-=)");
                        return;
                    }
                    //-	Consist lowercase letter.
                    if (!textVal.matches("^(?=.*[a-z]).+$")) {
                        c.error("Password - lowercase letter required (e.g. !@#$%^&*()_+-=)");
                        return;
                    }
                    //-	Consist numeric number.
                    if (!textVal.matches("^(?=.*\\d).+$")) {
                        c.error("Password - numeric number required (e.g. !@#$%^&*()_+-=)");
                        return;
                    }
                    //-	Consist of a special symbol.
                    Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
                    Matcher specialCharacterMatcher = pattern.matcher(textVal);

                    if (!specialCharacterMatcher.find()) {
                        c.error("Password - special symbol required (e.g. !@#$%^&*()_+-=)");
                        return;
                    }
                })
                .decorates(this.txtPassword);

        validator.add(validatorCheck);

        //=====================================
    }

    @Override
    public boolean clearAllFieldsValue() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ButtonType alertDialog(Alert.AlertType alertType, String title, String headerTxt, String contentTxt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void quitWindow(String title, String headerTxt, String contentTxt) {
        ButtonType alertBtnClicked = alertDialog(Alert.AlertType.CONFIRMATION,
                title,
                headerTxt,
                contentTxt);

        if (alertBtnClicked == ButtonType.OK) {
            switchScene(passObj.getFxmlPaths().getLast().toString(), passObj, BasicObjs.back);
        } else if (alertBtnClicked == ButtonType.CANCEL) {
            //nothing need to do, remain same page
        }
    }

    @Override
    public void switchScene(String fxmlPath, BasicObjs passObj, String direction) {
        Stage stage = (Stage) btnConfirm.getScene().getWindow();
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

        } catch (Exception e) {
            System.err.println(String.format("Error: %s", e.getMessage()));

        }
    }

    @Override
    public BasicObjs sendData(BasicObjs passObj, String direction) {
        switch (direction) {
            case BasicObjs.forward:
                passObj.getFxmlPaths().addLast("View/AccountMgmt_UI.fxml");
                break;
        }
        passObj.setPassDirection(direction);
        passObj.setLoginStaff(this.passObj.getLoginStaff());
        return passObj;
    }

    @Override
    public void receiveData() {
        // Step 1
        Stage stage = (Stage) btnConfirm.getScene().getWindow();
        // Step 2
        if (stage.getUserData() != null) {
            passObj = (BasicObjs) stage.getUserData();

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

    private Staff prepareStaffToObj() {
        Staff staff = new Staff();

        staff.setStaffID(this.txtStaffID.getText());
        staff.setPassword(this.txtPassword.getText());
        staff.setIsFrozen(this.chkFrozen.isSelected());
        staff.setResetPassNextLogin(this.chkResetPass.isSelected());

        return staff;
    }

    @FXML
    private void confirmAccount(MouseEvent event) {
        if (event.isPrimaryButtonDown() == true) {

            if (!validator.validate()) {
                return;
            }

            Staff staff = prepareStaffToObj();
            StaffService.updateStaffAcc(staff);
        }
    }

    @FXML
    private void cancelAction(MouseEvent event) {
        if (event.isPrimaryButtonDown() == true) {
            quitWindow("Warning", "Validation Message", "Record will be discarded.\nAre you sure you want to continue?");
        }
    }

    @FXML
    private void openStaffAccSelection(MouseEvent event) {
        if (event.isPrimaryButtonDown() == true) {
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getClassLoader().getResource("View/InnerEntitySelect_UI.fxml"));
                Stage stage = new Stage();
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(btnConfirm.getScene().getWindow());
                stage.setScene(new Scene(root));

                BasicObjs passObj = new BasicObjs();
                passObj.setObj(new Staff());

                stage.setUserData(passObj);
                stage.showAndWait();

                if (stage.getUserData() != null) {
                    BasicObjs receiveObj = (BasicObjs) stage.getUserData();
                    this.txtStaffID.setText(((Staff) receiveObj.getObj()).getStaffID());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void autoClose() {
        Duration delay1 = Duration.seconds(GeneralRulesService.getSessionTimeOut());
        PauseTransition transitionAlert = new PauseTransition(delay1);
        this.passObj.setLoginStaff(new Staff());
        transitionAlert.setOnFinished(evt -> switchScene("View/Login_UI.fxml", passObj, BasicObjs.back));
        transitionAlert.setCycleCount(1);

        btnConfirm.getScene().addEventFilter(InputEvent.ANY, evt -> transitionAlert.playFromStart());
    }

}
