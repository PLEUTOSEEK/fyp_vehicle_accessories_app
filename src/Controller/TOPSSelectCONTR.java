/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
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
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
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
public class TOPSSelectCONTR implements Initializable {

    //<editor-fold defaultstate="collapsed" desc="fields">
    @FXML
    private MFXTextField txtProdID;
    @FXML
    private MFXButton btnConfirm;
    @FXML
    private MFXButton btnCancel;
    @FXML
    private MFXTextField txtQuantity;
    @FXML
    private MFXButton btnRemove;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="util declarations">
    private BasicObjs passObj;

    private Validator validator = new Validator();
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
                intializeComboSelections();
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
                passObj.getFxmlPaths().addLast("View/TOPSSelect_UI.fxml");
                break;
        }
        passObj.setPassDirection(direction);
        passObj.setLoginStaff(this.passObj.getLoginStaff());
        return passObj;
    }

    private void intializeComboSelections() {

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
        }
    }

    private boolean clearAllFieldsValue() {
        this.txtProdID.clear();
        this.txtQuantity.clear();
        return true;
    }

    private void inputValidation() {
        Check validatorCheck = (new Validator()).createCheck();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.US);

        /*
        No need include:
        1.
        2.
         */
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

                    if (passObj.getObjs().size() > 0) {
                        List<Item> itemNotYetTransfer = passObj.getObjs()
                                .stream()
                                .map(e -> (Item) e)
                                .collect(Collectors.toList());

                        Item passInItem = (Item) passObj.getObj();

                        for (Item itm : itemNotYetTransfer) {
                            if (itm.equals(passInItem)) {
                                if (qty > itm.getOriQty()) {
                                    c.error("Quantity - Cannot more than " + itm.getOriQty());
                                    return;
                                }
                                break;
                            }
                        }
                    }
                })
                .decorates(this.txtQuantity);

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

        item.setOriQty(item.getQty());
        item.setQty(Integer.valueOf(this.txtQuantity.getText()));

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
