/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Entity.Item;
import PassObjs.BasicObjs;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
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
            }
        });
    }

    private void fieldFillIn() {
        clearAllFieldsValue();

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

}
