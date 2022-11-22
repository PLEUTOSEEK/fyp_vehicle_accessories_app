/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import Entity.Customer;
import Entity.CustomerInquiry;
import Entity.Inventory;
import Entity.Invoice;
import Entity.Quotation;
import Entity.Receipt;
import Entity.ReturnDeliveryNote;
import Entity.SalesOrder;
import Entity.Staff;
import Entity.TransferOrder;
import PassObjs.BasicObjs;
import io.github.palexdev.materialfx.controls.MFXCircleToggleNode;
import io.github.palexdev.materialfx.controls.MFXContextMenuItem;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Tee Zhuo Xuan
 */
public class HomePageCONTR implements Initializable, BasicCONTRFunc {

    private BasicObjs passObj;

    @FXML
    private MFXCircleToggleNode btnSetting;
    @FXML
    private MenuItem mniCreateStaff;
    @FXML
    private MenuItem mniUpdateStaff;
    @FXML
    private MenuItem mniViewStaff;
    @FXML
    private MenuItem mniCreateCust;
    @FXML
    private MenuItem mniUpdateCust;
    @FXML
    private MenuItem mniViewCust;
    @FXML
    private MenuItem mniCreateCI;
    @FXML
    private MenuItem mniUpdateCI;
    @FXML
    private MenuItem mniViewCI;
    @FXML
    private MenuItem mniCreateQuotation;
    @FXML
    private MenuItem mniUpdateQuotation;
    @FXML
    private MenuItem mniViewQuotation;
    @FXML
    private MenuItem mniCreateSO;
    @FXML
    private MenuItem mniUpdateSO;
    @FXML
    private MenuItem mniViewSO;
    @FXML
    private MenuItem mniViewInventory;
    @FXML
    private MenuItem mniCreateTO;
    @FXML
    private MenuItem mniUpdateTO;
    @FXML
    private MenuItem mniViewTO;
    @FXML
    private MenuItem mniCreateRDN;
    @FXML
    private MenuItem mniUpdateRDN;
    @FXML
    private MenuItem mniViewRDN;
    @FXML
    private MenuItem mniCreateInvoice;
    @FXML
    private MenuItem mniViewInvoice;
    @FXML
    private MenuItem mniCreatePayment;
    @FXML
    private MenuItem mniViewReceipt;
    @FXML
    private MenuItem mniSalesRpt;
    @FXML
    private MenuItem mniInventoryRpt;
    @FXML
    private MenuItem mniAccRpt;

    public static Staff logInStaff;
    @FXML
    private MenuItem mniCreateRDN1;
    @FXML
    private MenuItem mniUpdateRDN1;
    @FXML
    private MenuItem mniViewRDN1;
    @FXML
    private MenuItem mniCreateTO1;
    @FXML
    private MenuItem mniUpdateTO1;
    @FXML
    private MenuItem mniViewTO1;
    @FXML
    private MFXScrollPane settingScrollPanel;
    @FXML
    private MFXContextMenuItem cxmniSignOut;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                receiveData();
                logInStaff = (Staff) passObj.getObj();
                autoClose();
            }
        });
    }

    @FXML
    private void goToCreateStaffUI(ActionEvent event) {
        switchScene("View/Staff_UI.fxml", new BasicObjs(new Staff(), BasicObjs.create), BasicObjs.forward);
    }

    @FXML
    private void goToUpdateStaffUI(ActionEvent event) {
        switchScene("View/EntityOverview_UI.fxml", new BasicObjs(new Staff()), BasicObjs.forward);
    }

    @FXML
    private void goToViewStaffUI(ActionEvent event) {
        switchScene("View/EntityOverview_UI.fxml", new BasicObjs(new Staff()), BasicObjs.forward);
    }

    @FXML
    private void goToCreateCustUI(ActionEvent event) {
        switchScene("View/Customer_UI.fxml", new BasicObjs(new Customer(), BasicObjs.create), BasicObjs.forward);
    }

    @FXML
    private void goToUpdateCustUI(ActionEvent event) {
        switchScene("View/EntityOverview_UI.fxml", new BasicObjs(new Customer()), BasicObjs.forward);
    }

    @FXML
    private void goToViewCustUI(ActionEvent event) {
        switchScene("View/EntityOverview_UI.fxml", new BasicObjs(new Customer()), BasicObjs.forward);
    }

    @FXML
    private void goToCreateCIUI(ActionEvent event) {
        switchScene("View/CustomerInquiry_UI.fxml", new BasicObjs(new CustomerInquiry(), BasicObjs.create), BasicObjs.forward);
    }

    @FXML
    private void goToUpdateCIUI(ActionEvent event) {
        switchScene("View/EntityOverview_UI.fxml", new BasicObjs(new CustomerInquiry()), BasicObjs.forward);
    }

    @FXML
    private void goToViewCIUI(ActionEvent event) {
        switchScene("View/EntityOverview_UI.fxml", new BasicObjs(new CustomerInquiry()), BasicObjs.forward);
    }

    @FXML
    private void goToCreateQuotationUI(ActionEvent event) {
        switchScene("View/Quotations.fxml", new BasicObjs(new Quotation(), BasicObjs.create), BasicObjs.forward);
    }

    @FXML
    private void goToUpdateQuotationUI(ActionEvent event) {
        switchScene("View/EntityOverview_UI.fxml", new BasicObjs(new Quotation()), BasicObjs.forward);
    }

    @FXML
    private void goToViewQuotationUI(ActionEvent event) {
        switchScene("View/EntityOverview_UI.fxml", new BasicObjs(new Quotation()), BasicObjs.forward);
    }

    @FXML
    private void goToCreateSOUI(ActionEvent event) {
        switchScene("View/SalesOrder_UI.fxml", new BasicObjs(new SalesOrder(), BasicObjs.create), BasicObjs.forward);

    }

    @FXML
    private void goToUpdateSOUI(ActionEvent event) {
        switchScene("View/EntityOverview_UI.fxml", new BasicObjs(new SalesOrder()), BasicObjs.forward);
    }

    @FXML
    private void goToViewSOUI(ActionEvent event) {
        switchScene("View/EntityOverview_UI.fxml", new BasicObjs(new SalesOrder()), BasicObjs.forward);
    }

    @FXML
    private void goToViewInventory(ActionEvent event) {
        switchScene("View/EntityOverview_UI.fxml", new BasicObjs(new Inventory()), BasicObjs.forward);
    }

    @FXML
    private void goToCreateTOUI(ActionEvent event) {
        switchScene("View/TransferOrder_UI.fxml", new BasicObjs(new TransferOrder(), BasicObjs.create), BasicObjs.forward);
    }

    @FXML
    private void goToUpdateTOUI(ActionEvent event) {
        switchScene("View/EntityOverview_UI.fxml", new BasicObjs(new TransferOrder()), BasicObjs.forward);
    }

    @FXML
    private void goToViewTOUI(ActionEvent event) {
        switchScene("View/EntityOverview_UI.fxml", new BasicObjs(new TransferOrder()), BasicObjs.forward);
    }

    @FXML
    private void goToCreateRDN(ActionEvent event) {
        switchScene("View/ReturnDeliveryNote_UI.fxml", new BasicObjs(new ReturnDeliveryNote(), BasicObjs.create), BasicObjs.forward);
    }

    @FXML
    private void goToUpdateRDN(ActionEvent event) {
        switchScene("View/EntityOverview_UI.fxml", new BasicObjs(new ReturnDeliveryNote()), BasicObjs.forward);
    }

    @FXML
    private void goToViewRDN(ActionEvent event) {
        switchScene("View/EntityOverview_UI.fxml", new BasicObjs(new ReturnDeliveryNote()), BasicObjs.forward);
    }

    @FXML
    private void goToCreateInvoiceUI(ActionEvent event) {
        switchScene("View/Invoice_UI.fxml", new BasicObjs(new Invoice(), BasicObjs.create), BasicObjs.forward);
    }

    @FXML
    private void goToViewInvoiceUI(ActionEvent event) {
        switchScene("View/EntityOverview_UI.fxml", new BasicObjs(new Invoice()), BasicObjs.forward);
    }

    @FXML
    private void goToCreatePaymentUI(ActionEvent event) {
        switchScene("View/Payment_UI.fxml", new BasicObjs(new Receipt(), BasicObjs.create), BasicObjs.forward);

    }

    @FXML
    private void goToViewReceiptUI(ActionEvent event) {
        switchScene("View/EntityOverview_UI.fxml", new BasicObjs(new Receipt()), BasicObjs.forward);
    }

    @FXML
    private void goToSalesReportUI(ActionEvent event) {
        //haven't create report UI controls
    }

    @FXML
    private void goToInventoryReportUI(ActionEvent event) {
        //haven't create report UI controls
    }

    @FXML
    private void goToAccountingReportUI(ActionEvent event) {
        //haven't create report UI controls
    }

    @Override
    public void switchScene(String fxmlPath, BasicObjs passObj, String direction) {
        // Step 3
        Stage stage = (Stage) btnSetting.getScene().getWindow();
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

    @Override
    public BasicObjs sendData(BasicObjs passObj, String direction) {
        switch (direction) {
            case BasicObjs.forward:
                passObj.getFxmlPaths().addLast("View/HomePage_UI.fxml");
                break;
        }
        passObj.setPassDirection(direction);
        passObj.setLoginStaff(this.passObj.getLoginStaff());
        return passObj;
    }

    @Override
    public void receiveData() {
        // Step 1
        Stage stage = (Stage) btnSetting.getScene().getWindow();
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

    @Override
    public void inputValidation() {
        // Unused
        return;
    }

    @Override
    public boolean clearAllFieldsValue() {
        // Unused
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

    public void autoClose() {
        Duration delay1 = Duration.seconds(10);
        PauseTransition transitionAlert = new PauseTransition(delay1);
        transitionAlert.setOnFinished(evt -> switchScene("View/Login_UI.fxml", passObj, BasicObjs.back));
        transitionAlert.setCycleCount(1);

        btnSetting.getScene().addEventFilter(InputEvent.ANY, evt -> transitionAlert.playFromStart());
    }

    @FXML
    private void openSettingDrawer(MouseEvent event) {
        if (this.settingScrollPanel.isVisible()) {
            this.settingScrollPanel.setVisible(false);
        } else {
            this.settingScrollPanel.setVisible(true);
        }
    }
}
