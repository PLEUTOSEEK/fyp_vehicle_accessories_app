/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Entity.Item;
import Entity.Staff;
import PassObjs.BasicObjs;
import Service.GeneralRulesService;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
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
public class RDNPSSelectCONTR implements Initializable {

    @FXML
    private MFXTextField txtProdID;
    @FXML
    private MFXButton btnConfirm;
    @FXML
    private MFXButton btnCancel;
    @FXML
    private MFXButton btnRemove;
    @FXML
    private MFXTextField txtQuantity;
    @FXML
    private MFXTextField txtReason;
    @FXML
    private MFXTextField txtRemark;

    //<editor-fold defaultstate="collapsed" desc="util declarations">
    private BasicObjs passObj;

    private Validator validator = new Validator();

    //</editor-fold>
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                inputValidation();
                receiveData();
                fieldFillIn();
                autoClose();
            }
        });
    }

    public void autoClose() {
        Duration delay1 = Duration.seconds(GeneralRulesService.getSessionTimeOut());
        PauseTransition transitionAlert = new PauseTransition(delay1);
        this.passObj.setLoginStaff(new Staff());
        transitionAlert.setOnFinished(evt -> switchScene("View/Login_UI.fxml", passObj, BasicObjs.back));
        transitionAlert.setCycleCount(1);

        this.btnCancel.getScene().addEventFilter(InputEvent.ANY, evt -> transitionAlert.playFromStart());
    }

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

    public BasicObjs sendData(BasicObjs passObj, String direction) {
        switch (direction) {
            case BasicObjs.forward:
                passObj.getFxmlPaths().addLast("View/RDNPSSelect_UI.fxml");
                break;
        }
        passObj.setPassDirection(direction);
        passObj.setLoginStaff(this.passObj.getLoginStaff());
        return passObj;
    }

    private void defaultValFillIn() {
        this.txtQuantity.setText("0");
    }

    private void fieldFillIn() {
        clearAllFieldsValue();
        defaultValFillIn();
        if (this.passObj.getObj() != null) {
            Item item = (Item) this.passObj.getObj();
            this.txtProdID.setText(item.getProduct().getProdID());
            this.txtQuantity.setText(Integer.toString(item.getQty()));
            this.txtReason.setText(item.getReason());
            this.txtRemark.setText(item.getRemark());
        }
    }

    private boolean clearAllFieldsValue() {
        this.txtProdID.clear();
        this.txtQuantity.clear();
        this.txtReason.clear();
        this.txtRemark.clear();
        return true;
    }

    private void inputValidation() {
        Check validatorCheck = (new Validator()).createCheck();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.US);

        /*
        No need include:
        1. Additional Desc
         */
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Product ID", this.txtProdID.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Product ID");
                    textVal = textVal.trim();
                    /*
                     1. cannot be null
                     */
                    // allow null
                    if (textVal.isEmpty()) {
                        c.error("Product ID - Required Field ");
                        return;
                    }

                })
                .decorates(this.txtProdID);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Quantity", this.txtQuantity.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Quantity");
                    textVal = textVal.trim();

                    /*
                     1. cannot be null
                     2. must be integer
                     2. must be more than zero
                     */
                    if (textVal.isEmpty()) {
                        c.error("Quantity - Required Field");
                        return;
                    }

                    if (!textVal.matches("^\\d+$")) {
                        c.error("Quantity - ONLY integer value");
                        return;
                    }

                    Integer qty = Integer.parseInt(textVal);

                    if (qty <= 0) {
                        c.error("Quantity - Cannot less than 1");
                        return;
                    }
                })
                .decorates(this.txtQuantity);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Reason", this.txtReason.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Reason");
                    textVal = textVal.trim();
                    /*
                     1. cannot be null
                     */
                    // allow null

                    if (textVal.isEmpty()) {
                        c.error("Reason - Required Field");
                        return;
                    }

                    if (!textVal.matches("^[a-zA-Z ]*$")) {
                        c.error("Reason - ONLY letter and spaces");
                        return;
                    }

                })
                .decorates(this.txtReason);

        validator.add(validatorCheck);

        //=====================================
    }

    public void receiveData() {
        // Step 1
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        // Step 2
        if (stage.getUserData() != null) {
            passObj = (BasicObjs) stage.getUserData();
        } else {
            passObj = new BasicObjs();
        }
    }

    private Item prepareItemToObj() {
        Item item = (Item) this.passObj.getObj();

        item.setQty(Integer.valueOf(this.txtQuantity.getText()));
        item.setOriQty(item.getQty());
        item.setReason(this.txtReason.getText());
        item.setRemark(this.txtRemark.getText());

        return item;
    }

    public ButtonType alertDialog(Alert.AlertType alertType, String title, String headerTxt, String contentTxt) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerTxt);
        alert.setContentText(contentTxt);

        alert.showAndWait();
        return alert.getResult();
    }

    @FXML
    private void cancelAction(MouseEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.setUserData(null);
        stage.close();
    }

    @FXML
    private void removeCurrentItem(MouseEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        BasicObjs passObj = new BasicObjs();
        passObj.setObj(null);
        stage.setUserData(passObj);
        stage.close();
    }

    @FXML
    private void confirmItem(MouseEvent event) {
        if (event.isPrimaryButtonDown() == true) {
            if (!validator.validate()) {
                return;
            }

            Stage stage = (Stage) btnCancel.getScene().getWindow();
            BasicObjs passObj = new BasicObjs();
            passObj.setObj(prepareItemToObj());
            stage.setUserData(passObj);
            stage.close();
        }
    }

}
