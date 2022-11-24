/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import BizRulesConfiguration.SalesRules;
import Entity.Item;
import Entity.Product;
import Entity.Staff;
import PassObjs.BasicObjs;
import Service.GeneralRulesService;
import Service.InventoryService;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCircleToggleNode;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
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
public class SalesOrderPSSelectCONTR implements Initializable {

    //<editor-fold defaultstate="collapsed" desc="fields">
    @FXML
    private MFXTextField txtRemarks;
    @FXML
    private MFXTextField txtQuantity;
    @FXML
    private MFXTextField txtUnitPrice;
    @FXML
    private MFXDatePicker dtDeliveryDate;
    @FXML
    private MFXTextField txtProdID;
    @FXML
    private MFXComboBox<?> cmbDiscountAmount;
    @FXML
    private MFXButton btnConfirm;
    @FXML
    private MFXButton btnCancel;
    @FXML
    private MFXButton btnRemove;
    //<editor-fold defaultstate="collapsed" desc="util declarations">
    private BasicObjs passObj;

    private Validator validator = new Validator();

    SalesRules salesRules = new SalesRules();
    //</editor-fold>
    @FXML
    private MFXCircleToggleNode ctnProductSelection;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Platform.runLater(
                new Runnable() {

            @Override
            public void run() {
                intializeComboSelections();
                inputValidation();
                receiveData();

                if (passObj.getCrud().equals(BasicObjs.create)) {
                    btnRemove.setVisible(false);
                    defaultValFillIn();
                }

                if (passObj.getCrud().equals(BasicObjs.read)) {
                    fieldFillIn();
                }
//                if (passObj.getCrud().equals(BasicObjs.read)) {
//                    ctnProductSelection.setVisible(false);
//                }

            }
        }
        );
    }

    public void autoClose() {
        Duration delay1 = Duration.seconds(GeneralRulesService.getSessionTimeOut());
        PauseTransition transitionAlert = new PauseTransition(delay1);
        this.passObj.setLoginStaff(new Staff());
        transitionAlert.setOnFinished(evt -> switchScene("View/Login_UI.fxml", passObj, BasicObjs.back));
        transitionAlert.setCycleCount(1);

        btnCancel.getScene().addEventFilter(InputEvent.ANY, evt -> transitionAlert.playFromStart());
    }

    public void switchScene(String fxmlPath, BasicObjs passObj, String direction) {
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

    public BasicObjs sendData(BasicObjs passObj, String direction) {
        switch (direction) {
            case BasicObjs.forward:
                passObj.getFxmlPaths().addLast("View/SalesOrderPSSelect_UI.fxml");
                break;
        }
        passObj.setPassDirection(direction);
        passObj.setLoginStaff(this.passObj.getLoginStaff());
        return passObj;
    }

    private void intializeComboSelections() {
        List<Double> discPercentOptions = new ArrayList<>();
        discPercentOptions.add(Double.valueOf(0));
        discPercentOptions.add(Double.valueOf(5));
        discPercentOptions.add(Double.valueOf(10));
        discPercentOptions.add(Double.valueOf(15));
        discPercentOptions.add(Double.valueOf(20));
        discPercentOptions.add(Double.valueOf(25));
        discPercentOptions.add(Double.valueOf(30));

        ((MFXComboBox<Double>) this.cmbDiscountAmount).setItems(FXCollections.observableList(discPercentOptions));

    }

    private void defaultValFillIn() {
        this.txtQuantity.setText("0");
        this.txtUnitPrice.setText("0.00");
        this.cmbDiscountAmount.setText("0");
        this.dtDeliveryDate.setValue(LocalDate.now());
    }

    private void fieldFillIn() {
        clearAllFieldsValue();
        defaultValFillIn();
        if (passObj.getObj() != null) {
            Item item = (Item) passObj.getObj();

            this.txtProdID.setText(item.getProduct() == null ? "" : item.getProduct().getProdID());
            this.txtRemarks.setText(item.getRemark());
            this.txtQuantity.setText(item.getQty().toString());
            this.txtUnitPrice.setText(item.getUnitPrice().toString());
            this.cmbDiscountAmount.setText(Double.toString(item.getDiscPercent()));
            this.dtDeliveryDate.setValue(item.getDlvrDate() == null
                    ? null
                    : Instant.ofEpochMilli(item.getDlvrDate().getTime())
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate());
        }
    }

    private boolean clearAllFieldsValue() {
        this.txtProdID.clear();
        this.txtRemarks.clear();
        this.txtQuantity.clear();
        this.txtUnitPrice.clear();
        this.cmbDiscountAmount.clear();
        this.dtDeliveryDate.clear();

        return true;
    }

    private void inputValidation() {
        Check validatorCheck = (new Validator()).createCheck();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.US);

        /*
        No need include:
        1. Remarks
        2.
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
                    if (textVal.isEmpty()) {
                        c.error("Product ID - Required Field");
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
                .dependsOn("Unit Price", this.txtUnitPrice.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Unit Price");
                    textVal = textVal.trim();
                    /*
                     1. Cannot be null
                     2. Must be numeric
                     3. Cannot negative value
                     */
                    if (textVal.isEmpty()) {
                        c.error("Unit Price - Required Field");
                        return;
                    }

                    try {

                        Double unitPrice = Double.parseDouble(textVal);

                        if (unitPrice < 0) {
                            c.error("Unit Price - Negative value are not allowed");
                            return;
                        }

                    } catch (Exception ex) {
                        c.error("Unit Price - ONLY numeric value");
                        return;
                    }

                })
                .decorates(this.txtUnitPrice);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Disc. %", this.cmbDiscountAmount.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Disc. %");
                    textVal = textVal.trim();
                    /*
                     1. Cannot be null
                     2. Must be numeric value
                     3. Must within 0 <= Disc <= 30
                     */
                    if (textVal.isEmpty()) {
                        c.error("Disc. % - Required Field");
                        return;
                    }

                    try {

                        Double discPercent = Double.parseDouble(textVal);

                        if (0 > discPercent || discPercent > salesRules.getUpperLimitPercentageDiscount()) {
                            c.error("Disc. % - Must within 0 <= Disc <= " + salesRules.getUpperLimitPercentageDiscount().toString());
                            return;
                        }

                    } catch (Exception ex) {
                        c.error("Disc. % - ONLY numeric value");
                        return;
                    }

                })
                .decorates(this.cmbDiscountAmount);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Delivery Date", this.dtDeliveryDate.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Delivery Date");
                    textVal = textVal.trim();

                    /*
                     1. cannot be null
                     2. cannot early than reference date
                     */
                    if (textVal.isEmpty()) {
                        c.error("Delivery Date - Required Field");
                        return;
                    }

                    LocalDate date = LocalDate.parse(textVal, formatter);

                    if (date.isBefore(LocalDate.now())) {
                        c.error("Delivery Date - Cannot be before the current date");
                        return;
                    }
                })
                .decorates(this.dtDeliveryDate);

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
        Item item = new Item();

        Product product = new Product();
        product.setProdID(this.txtProdID.getText());
        item.setProduct(product);

        item.setRemark(this.txtRemarks.getText());

        item.setQty(Integer.valueOf(this.txtQuantity.getText()));

        item.setUnitPrice(new BigDecimal(this.txtUnitPrice.getText()));

        item.setDiscPercent(Double.valueOf(this.cmbDiscountAmount.getText()));

        item.setDlvrDate(this.dtDeliveryDate.getValue() == null ? null : new java.sql.Date((Date.from(Instant.from(this.dtDeliveryDate.getValue().atStartOfDay(ZoneId.systemDefault())))).getTime()));

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

            Item item = prepareItemToObj();

            if (isDemandOverReadyStock(item) == true) {
                return;
            }

            Stage stage = (Stage) btnCancel.getScene().getWindow();
            BasicObjs passObj = new BasicObjs();
            passObj.setObj(item);
            stage.setUserData(passObj);
            stage.close();
        }

    }

    private boolean isDemandOverReadyStock(Item item) {
        if (this.passObj.getObjs() != null) {
            List<Item> items = (List<Item>) (Item) this.passObj.getObjs();

            Integer ttlDemandQty = 0;

            ttlDemandQty += item.getQty();

            for (Item i : items) {
                if (i.getProduct().getProdID().equals(item.getProduct().getProdID())
                        && !i.equals(item)) {
                    ttlDemandQty += i.getQty();
                }
            }

            Integer ttlReadyQty = InventoryService.getInventoryReadyQtyByProdID(item.getProduct().getProdID());

            ttlDemandQty -= InventoryService.getOriginalReservedQty(item.getProduct().getProdID());

            if (ttlDemandQty > ttlReadyQty) {
                alertDialog(Alert.AlertType.WARNING,
                        "Warning",
                        "Insufficient Stock Alert", "Ready Qty = " + ttlReadyQty + "\nDemand Qty = " + ttlDemandQty + "\nKindly make Demand Qty below or equal Ready Qty.");
                return true;
            } else {
                return false;
            }
        }

        return false;

    }

    @FXML
    private void openProductSelection(MouseEvent event) {
        if (event.isPrimaryButtonDown() == true) {
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getClassLoader().getResource("View/InnerEntitySelect_UI.fxml"));
                Stage stage = new Stage();
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(btnCancel.getScene().getWindow());
                stage.setScene(new Scene(root));

                BasicObjs passObj = new BasicObjs();
                passObj.setObj(new Product());

                stage.setUserData(passObj);
                stage.showAndWait();

                if (stage.getUserData() != null) {
                    BasicObjs receiveObj = (BasicObjs) stage.getUserData();
                    this.clearAllFieldsValue();

                    Product product = (Product) receiveObj.getObj();
                    this.txtProdID.setText(product.getProdID());
                    this.txtUnitPrice.setText(product.getSellPrice().toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
