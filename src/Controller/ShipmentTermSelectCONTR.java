/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Entity.ShipmentTerm;
import Entity.Staff;
import PassObjs.BasicObjs;
import Service.GeneralRulesService;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
public class ShipmentTermSelectCONTR implements Initializable {

    @FXML
    private MFXTextField txtShipmentTermID;
    @FXML
    private MFXTextField txtShipmentTermName;
    @FXML
    private MFXTextField txtDescription;
    @FXML
    private MFXButton btnCancel;
    @FXML
    private MFXButton btnConfirm;

    private BasicObjs passObj;

    private Validator validator = new Validator();

    @Override
    public void initialize(URL url, ResourceBundle rb) {        // TODO
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                inputValidation();

                receiveData();

                if (passObj.getCrud()
                        .equals(BasicObjs.create)) {
                    defaultValFillIn();
                }

                if (passObj.getCrud()
                        .equals(BasicObjs.read)) {
                    fieldFillIn();
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
        btnCancel.getScene().addEventFilter(InputEvent.ANY, evt -> transitionAlert.playFromStart());
    }

    public void switchScene(String fxmlPath, BasicObjs passObj,
            String direction
    ) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
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

    public BasicObjs sendData(BasicObjs passObj, String direction
    ) {
        switch (direction) {
            case BasicObjs.forward:
                passObj.getFxmlPaths().addLast("View/ShipmentTermSelect_UI.fxml");
                break;
        }
        passObj.setPassDirection(direction);
        passObj.setLoginStaff(this.passObj.getLoginStaff());
        return passObj;
    }

    private void defaultValFillIn() {
        // do nothing
    }

    private void fieldFillIn() {
        clearAllFieldsValue();
        defaultValFillIn();
        if (passObj.getObj() != null) {
            ShipmentTerm s = (ShipmentTerm) passObj.getObj();
            this.txtShipmentTermID.setText(s.getShipmentTermID());
            this.txtShipmentTermName.setText(s.getShipmentTermName());
            this.txtDescription.setText(s.getDescription());
        }
    }

    private boolean clearAllFieldsValue() {
        this.txtShipmentTermID.clear();
        this.txtShipmentTermName.clear();
        this.txtDescription.clear();

        return true;
    }

    private void inputValidation() {
        Check validatorCheck = (new Validator()).createCheck();

        /*
        No need include:
        1. Remarks
        2.
         */
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Shipment Term Name", this.txtShipmentTermName.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Shipment Term Name");
                    textVal = textVal.trim();
                    /*
                     1. cannot be null
                     2. must be unique
                     3.
                     */
                    if (textVal.isEmpty()) {
                        c.error("Shipment Term Name - Required Field");
                        return;
                    }

                    if (passObj.getObjs().size() > 0) {
                        List<ShipmentTerm> shipmentTerms = passObj.getObjs()
                                .stream()
                                .map(e -> (ShipmentTerm) e)
                                .collect(Collectors.toList());

                        ShipmentTerm passInShipmentTerm = (ShipmentTerm) passObj.getObj();

                        if (passObj.getCrud().equals(BasicObjs.read)) {

                            for (ShipmentTerm shipmentTerm : shipmentTerms) {
                                if (shipmentTerm.getShipmentTermName().toLowerCase().equals(textVal.toLowerCase())
                                        && !shipmentTerm.equals(passInShipmentTerm)) {
                                    c.error("Shipment Term Name - Must be unique");
                                    return;
                                }
                            }
                        } else {
                            for (ShipmentTerm shipmentTerm : shipmentTerms) {
                                if (shipmentTerm.getShipmentTermName().toLowerCase().equals(textVal.toLowerCase())) {
                                    c.error("Shipment Term Name - Must be unique");
                                    return;
                                }
                            }
                        }
                    }

                })
                .decorates(this.txtShipmentTermName);

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

    private ShipmentTerm prepareShipmentTermToObj() {
        ShipmentTerm shipmentTerm = new ShipmentTerm();
        shipmentTerm.setShipmentTermID(this.txtShipmentTermID.getText());
        shipmentTerm.setShipmentTermName(this.txtShipmentTermName.getText());
        shipmentTerm.setDescription(this.txtDescription.getText());
        return shipmentTerm;
    }

    @FXML
    private void cancelAction(MouseEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.setUserData(null);
        stage.close();
    }

    @FXML
    private void confirmShipmentTerm(MouseEvent event) {
        if (event.isPrimaryButtonDown() == true) {
            if (!validator.validate()) {
                return;
            }

            ShipmentTerm shipmentTerm = this.prepareShipmentTermToObj();

            Stage stage = (Stage) btnCancel.getScene().getWindow();
            BasicObjs passObj = new BasicObjs();
            passObj.setObj(shipmentTerm);
            stage.setUserData(passObj);
            stage.close();
        }
    }

}
