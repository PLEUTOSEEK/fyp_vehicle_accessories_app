/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import Entity.Staff;
import PassObjs.BasicObjs;
import Service.GeneralRulesService;
import Service.SalesRulesService;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCircleToggleNode;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * FXML Controller class
 *
 * @author Tee Zhuo Xuan
 */
public class SalesRulesConfigurationCONTR implements Initializable, BasicCONTRFunc {

    @FXML
    private MFXTextField txtMaxQuotValidityPeriod;
    @FXML
    private MFXTextField txtMaxOrderAmt;
    @FXML
    private MFXTextField txtUpperLimitDiscount;
    @FXML
    private MFXCircleToggleNode btnBack;
    @FXML
    private MFXButton btnDiscard;
    @FXML
    private MFXButton btnSave;

    //<editor-fold defaultstate="collapsed" desc="util declarations">
    private Validator validator = new Validator();
    private BasicObjs passObj;

    //</editor-fold>
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
                try {
                    fieldFillIn();
                } catch (IOException ex) {
                    Logger.getLogger(SalesRulesConfigurationCONTR.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public void autoClose() {
        Duration delay1 = Duration.seconds(GeneralRulesService.getSessionTimeOut());
        PauseTransition transitionAlert = new PauseTransition(delay1);
        this.passObj.setLoginStaff(new Staff());
        transitionAlert.setOnFinished(evt -> switchScene("View/Login_UI.fxml", passObj, BasicObjs.back));
        transitionAlert.setCycleCount(1);
        this.btnBack.getScene().addEventFilter(InputEvent.ANY, evt -> transitionAlert.playFromStart());
    }

    private void fieldFillIn() throws IOException {
        this.txtMaxOrderAmt.setText(SalesRulesService.getMaxOrderAmtperSO().toString());
        this.txtMaxQuotValidityPeriod.setText(SalesRulesService.getMaxQuotationValidityPeriod().toString());
        this.txtUpperLimitDiscount.setText(SalesRulesService.getUpperLimitPercentageDiscount().toString());
    }

    @Override
    public void inputValidation() {
        Check validatorCheck = (new Validator()).createCheck();

        /*
        No need include:
        1.
         */
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Maximum Quotation Validity Days", this.txtMaxQuotValidityPeriod.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Maximum Quotation Validity Days");
                    textVal = textVal.trim();
                    /*
                     1. cannot be null
                     2. must be integer value
                     3. cannot less than 1
                     */
                    if (textVal.isEmpty()) {
                        c.error("Maximum Quotation Validity Days - Required Field");
                        return;
                    }

                    if (!textVal.matches("^\\d+$")) {
                        c.error("Maximum Quotation Validity Days - ONLY integer value");
                        return;
                    }

                    Integer maxQuotValidityDays = Integer.parseInt(textVal);

                    if (maxQuotValidityDays <= 0) {
                        c.error("Maximum Quotation Validity Days - Cannot less than 1");
                        return;
                    }
                })
                .decorates(this.txtMaxQuotValidityPeriod);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Maximum Order Amount", this.txtMaxOrderAmt.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Maximum Order Amount");
                    textVal = textVal.trim();
                    /*
                     1.
                     */
                    // allow null
                    if (textVal.isEmpty()) {
                        c.error("Maximum Order Amount - Required Field");
                        return;
                    }

                    try {

                        BigDecimal maxOrderAmt = new BigDecimal(textVal);

                        if (maxOrderAmt.doubleValue() <= 0) {
                            c.error("Maximum Order Amount - Cannot less than 1");
                            return;
                        }

                    } catch (Exception ex) {
                        c.error("Maximum Order Amount - ONLY numeric value");
                        return;
                    }
                })
                .decorates(this.txtMaxOrderAmt);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Upper Limit Discount", this.txtUpperLimitDiscount.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Upper Limit Discount");
                    textVal = textVal.trim();
                    /*
                     1.
                     */
                    // allow null
                    if (textVal.isEmpty()) {
                        c.error("Upper Limit Discount - Required Field");
                        return;
                    }

                    try {

                        Double upperLimitDisc = Double.parseDouble(textVal);

                        if (upperLimitDisc < 0 || upperLimitDisc > 100) {
                            c.error("Upper Limit Discount - Must be within [0 <= Upper Limit Discount <= 100]");
                            return;
                        }

                    } catch (Exception ex) {
                        c.error("Upper Limit Discount - ONLY numeric value");
                        return;
                    }
                })
                .decorates(this.txtUpperLimitDiscount);

        validator.add(validatorCheck);

        //=====================================
    }

    @Override
    public boolean clearAllFieldsValue() {
        //do nothing
        return true;
    }

    @Override
    public ButtonType alertDialog(Alert.AlertType alertType, String title, String headerTxt, String contentTxt) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerTxt);
        alert.setContentText(contentTxt);

        alert.showAndWait();
        return alert.getResult();
    }

    @Override
    public void switchScene(String fxmlPath, BasicObjs passObj,
            String direction
    ) {
        Stage stage = (Stage) btnBack.getScene().getWindow();
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
    public BasicObjs sendData(BasicObjs passObj, String direction
    ) {
        switch (direction) {
            case BasicObjs.forward:
                passObj.getFxmlPaths().addLast("View/SalesRulesConfiguration_UI.fxml");
                break;
        }
        passObj.setPassDirection(direction);
        passObj.setLoginStaff(this.passObj.getLoginStaff());
        return passObj;
    }

    @Override
    public void receiveData() {
        // Step 1
        Stage stage = (Stage) btnBack.getScene().getWindow();
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

    @FXML
    private void goBackPrevious(MouseEvent event
    ) {
        if (event.isPrimaryButtonDown() == true) {
            quitWindow("Warning", "Validation Message", "Record haven't been saved.\nAre you sure you want to continue?");
        }
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

    @FXML
    private void discardCurrentData(MouseEvent event) {
        if (event.isPrimaryButtonDown() == true) {
            quitWindow("Warning", "Validation Message", "Record will be discarded.\nAre you sure you want to continue?");
        }
    }

    @FXML
    private void saveSalesRules(MouseEvent event) {

        if (!validator.validate()) {
            return;
        }

        SalesRulesService.updateMaxOrderAmtperSO(this.txtMaxOrderAmt.getText());
        SalesRulesService.updateMaxQuotationValidityPeriod(this.txtMaxQuotValidityPeriod.getText());
        SalesRulesService.updateUpperLimitPercentageDiscount(this.txtUpperLimitDiscount.getText());
    }

}
