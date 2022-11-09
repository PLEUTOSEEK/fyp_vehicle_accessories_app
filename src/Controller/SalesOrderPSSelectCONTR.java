/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import Entity.Inventory;
import Entity.Item;
import Entity.Product;
import PassObjs.BasicObjs;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.math.BigDecimal;
import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import net.synedra.validatorfx.Validator;

/**
 * FXML Controller class
 *
 * @author Tee Zhuo Xuan
 */
public class SalesOrderPSSelectCONTR implements Initializable {

    private MFXTextField txtRemarks;
    @FXML
    private MFXTextField txtQuantity;
    private MFXTextField txtUnitPrice;
    private MFXDatePicker dtDeliveryDate;
    private MFXTextField txtInventoryID;
    @FXML
    private MFXTextField txtProdID;
    private MFXComboBox<?> cmbDiscountAmount;
    @FXML
    private MFXButton btnConfirm;
    @FXML
    private MFXButton btnCancel;

    private BasicObjs passObj;

    private Validator validator = new Validator();
    @FXML
    private MFXButton btnCancel1;
    @FXML
    private MFXTextField txtProdID1;

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

            }
        }
        );

    }

    private void intializeComboSelections() {

    }

    private void fieldFillIn() {

    }
    private String val = "";

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

        Inventory inventory = new Inventory();
        inventory.setInventoryID(this.txtInventoryID.getText());
        item.setInventory(inventory);

        item.setRemark(this.txtRemarks.getText());

        item.setQty(Integer.valueOf(this.txtQuantity.getText()));

        item.setUnitPrice(new BigDecimal(this.txtUnitPrice.getText()));

        this.cmbDiscountAmount.setText("500");
        item.setDiscAmt(new BigDecimal(this.cmbDiscountAmount.getText()));

        item.setDlvrDate(this.dtDeliveryDate.getValue() == null ? null : new java.sql.Date((Date.from(Instant.from(this.dtDeliveryDate.getValue().atStartOfDay(ZoneId.systemDefault())))).getTime()));

        return item;
    }

    @FXML
    private void confirmItem(MouseEvent event) {
        if (event.isPrimaryButtonDown() == true) {
            if (validator.containsErrors()) {
                alertDialog(Alert.AlertType.WARNING, "Warning", "Validation Message", validator.createStringBinding().getValue());
                return;
            }

            Stage stage = (Stage) btnCancel.getScene().getWindow();
            BasicObjs passObj = new BasicObjs();
            passObj.setObj(prepareItemToObj());
            stage.setUserData(passObj);
            stage.close();
        }

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
    private void goBackPrevious(MouseEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.setUserData(null);
        stage.close();
    }

}
