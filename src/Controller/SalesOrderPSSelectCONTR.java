/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import Entity.Item;
import Entity.Product;
import PassObjs.BasicObjs;
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
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
    @FXML
    private MFXCircleToggleNode ctnProductSelection;
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
        Platform.runLater(
                new Runnable() {

            @Override
            public void run() {
                intializeComboSelections();
                inputValidation();
                receiveData();
                fieldFillIn();

                if (passObj.getCrud().equals(BasicObjs.create)) {
                    btnRemove.setVisible(false);
                }
//                if (passObj.getCrud().equals(BasicObjs.read)) {
//                    ctnProductSelection.setVisible(false);
//                }

            }
        }
        );
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

    private void fieldFillIn() {
        clearAllFieldsValue();

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
                alertDialog(Alert.AlertType.WARNING, "Warning", "Validation Message", validator.createStringBinding().getValue());
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
