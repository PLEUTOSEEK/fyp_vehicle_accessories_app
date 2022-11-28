/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import Entity.Customer;
import Entity.CustomerInquiry;
import Entity.DeliveryOrder;
import Entity.Inventory;
import Entity.Invoice;
import Entity.PackingSlip;
import Entity.Quotation;
import Entity.Receipt;
import Entity.ReturnDeliveryNote;
import Entity.SalesOrder;
import Entity.Staff;
import Entity.TransferOrder;
import PassObjs.BasicObjs;
import Service.GeneralRulesService;
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
    @FXML
    private MenuItem mniGeneral;
    @FXML
    private MenuItem mniSales;
    @FXML
    private MenuItem mniWarehouse;
    @FXML
    private MenuItem mniAccounting;

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
                logInStaff = (Staff) passObj.getLoginStaff();
                autoClose();

            }
        });
    }

    @FXML
    private void goToGeneralConfigurationUI(ActionEvent event) {
        passObj.setObj(new Receipt());
        switchScene("View/GeneralRulesConfiguration_UI.fxml", passObj, BasicObjs.forward);
    }

    @FXML
    private void goToSalesConfigurationUI(ActionEvent event) {
        passObj.setObj(new Receipt());
        switchScene("View/SalesRulesConfiguration_UI.fxml", passObj, BasicObjs.forward);
    }

    @FXML
    private void goToWarehouseConfigurationUI(ActionEvent event) {
        passObj.setObj(new Receipt());
        switchScene("View/WarehouseRulesConfiguration_UI.fxml", passObj, BasicObjs.forward);
    }

    @FXML
    private void goToAccountingConfigurationUI(ActionEvent event) {
        passObj.setObj(new Receipt());
        switchScene("View/AccountingRulesConfiguration_UI.fxml", passObj, BasicObjs.forward);
    }

    @FXML
    private void goToCreateStaffUI(ActionEvent event) {
        passObj.setObj(new Staff());
        passObj.setCrud(BasicObjs.create);
        switchScene("View/Staff_UI.fxml", passObj, BasicObjs.forward);
    }

    @FXML
    private void goToUpdateStaffUI(ActionEvent event) {
        passObj.setObj(new Staff());
        passObj.setCrud(BasicObjs.update);
        switchScene("View/EntityOverview_UI.fxml", passObj, BasicObjs.forward);
    }

    @FXML
    private void goToViewStaffUI(ActionEvent event) {
        passObj.setObj(new Staff());
        passObj.setCrud(BasicObjs.read);
        switchScene("View/EntityOverview_UI.fxml", passObj, BasicObjs.forward);
    }

    @FXML
    private void goToCreateCustUI(ActionEvent event) {
        passObj.setObj(new Customer());
        passObj.setCrud(BasicObjs.create);
        switchScene("View/Customer_UI.fxml", passObj, BasicObjs.forward);
    }

    @FXML
    private void goToUpdateCustUI(ActionEvent event) {
        passObj.setObj(new Customer());
        passObj.setCrud(BasicObjs.update);
        switchScene("View/EntityOverview_UI.fxml", passObj, BasicObjs.forward);
    }

    @FXML
    private void goToViewCustUI(ActionEvent event) {
        passObj.setObj(new Customer());
        passObj.setCrud(BasicObjs.read);
        switchScene("View/EntityOverview_UI.fxml", passObj, BasicObjs.forward);
    }

    @FXML
    private void goToCreateCIUI(ActionEvent event) {
        passObj.setObj(new CustomerInquiry());
        passObj.setCrud(BasicObjs.create);
        switchScene("View/CustomerInquiry_UI.fxml", passObj, BasicObjs.forward);
    }

    @FXML
    private void goToUpdateCIUI(ActionEvent event) {
        passObj.setObj(new CustomerInquiry());
        passObj.setCrud(BasicObjs.update);
        switchScene("View/EntityOverview_UI.fxml", passObj, BasicObjs.forward);
    }

    @FXML
    private void goToViewCIUI(ActionEvent event) {
        passObj.setObj(new CustomerInquiry());
        passObj.setCrud(BasicObjs.read);
        switchScene("View/EntityOverview_UI.fxml", passObj, BasicObjs.forward);
    }

    @FXML
    private void goToCreateQuotationUI(ActionEvent event) {
        passObj.setObj(new Quotation());
        passObj.setCrud(BasicObjs.create);
        switchScene("View/Quotations_UI.fxml", passObj, BasicObjs.forward);
    }

    @FXML
    private void goToUpdateQuotationUI(ActionEvent event) {
        passObj.setObj(new Quotation());
        passObj.setCrud(BasicObjs.update);
        switchScene("View/EntityOverview_UI.fxml", passObj, BasicObjs.forward);
    }

    @FXML
    private void goToViewQuotationUI(ActionEvent event) {
        passObj.setObj(new Quotation());
        passObj.setCrud(BasicObjs.read);
        switchScene("View/EntityOverview_UI.fxml", passObj, BasicObjs.forward);
    }

    @FXML
    private void goToCreateSOUI(ActionEvent event) {
        passObj.setObj(new SalesOrder());
        passObj.setCrud(BasicObjs.create);
        switchScene("View/SalesOrder_UI.fxml", passObj, BasicObjs.forward);

    }

    @FXML
    private void goToUpdateSOUI(ActionEvent event) {
        passObj.setObj(new SalesOrder());
        passObj.setCrud(BasicObjs.update);
        switchScene("View/EntityOverview_UI.fxml", passObj, BasicObjs.forward);
    }

    @FXML
    private void goToViewSOUI(ActionEvent event) {
        passObj.setObj(new SalesOrder());
        passObj.setCrud(BasicObjs.read);
        switchScene("View/EntityOverview_UI.fxml", passObj, BasicObjs.forward);
    }

    @FXML
    private void goToViewInventory(ActionEvent event) {
        passObj.setObj(new Inventory());
        passObj.setCrud(BasicObjs.read);
        switchScene("View/EntityOverview_UI.fxml", passObj, BasicObjs.forward);
    }

    @FXML
    private void goToCreateTOUI(ActionEvent event) {
        passObj.setObj(new TransferOrder());
        passObj.setCrud(BasicObjs.create);
        switchScene("View/TransferOrder_UI.fxml", passObj, BasicObjs.forward);
    }

    @FXML
    private void goToUpdateTOUI(ActionEvent event) {
        passObj.setObj(new TransferOrder());
        passObj.setCrud(BasicObjs.update);
        switchScene("View/EntityOverview_UI.fxml", passObj, BasicObjs.forward);
    }

    @FXML
    private void goToViewTOUI(ActionEvent event) {
        passObj.setObj(new TransferOrder());
        passObj.setCrud(BasicObjs.read);
        switchScene("View/EntityOverview_UI.fxml", passObj, BasicObjs.forward);
    }

    @FXML
    private void goToCreatePSUI(ActionEvent event) {
        passObj.setObj(new PackingSlip());
        passObj.setCrud(BasicObjs.create);
        switchScene("View/PackingSlip_UI.fxml", passObj, BasicObjs.forward);
    }

    @FXML
    private void goToUpdatePSUI(ActionEvent event) {
        passObj.setObj(new PackingSlip());
        passObj.setCrud(BasicObjs.update);
        switchScene("View/EntityOverview_UI.fxml", passObj, BasicObjs.forward);
    }

    @FXML
    private void goToViewPSUI(ActionEvent event) {
        passObj.setObj(new PackingSlip());
        passObj.setCrud(BasicObjs.read);
        switchScene("View/EntityOverview_UI.fxml", passObj, BasicObjs.forward);
    }

    @FXML
    private void goToCreateDOUI(ActionEvent event) {
        passObj.setObj(new DeliveryOrder());
        passObj.setCrud(BasicObjs.create);
        switchScene("View/DeliveryOrder_UI.fxml", passObj, BasicObjs.forward);
    }

    @FXML
    private void goToUpdateDOUI(ActionEvent event) {
        passObj.setObj(new DeliveryOrder());
        passObj.setCrud(BasicObjs.update);
        switchScene("View/EntityOverview_UI.fxml", passObj, BasicObjs.forward);
    }

    @FXML
    private void goToViewDOUI(ActionEvent event) {
        passObj.setObj(new DeliveryOrder());
        passObj.setCrud(BasicObjs.read);
        switchScene("View/EntityOverview_UI.fxml", passObj, BasicObjs.forward);
    }

    @FXML
    private void goToCreateRDN(ActionEvent event) {
        passObj.setObj(new ReturnDeliveryNote());
        passObj.setCrud(BasicObjs.create);
        switchScene("View/ReturnDeliveryNote_UI.fxml", passObj, BasicObjs.forward);
    }

    @FXML
    private void goToUpdateRDN(ActionEvent event) {
        passObj.setObj(new ReturnDeliveryNote());
        passObj.setCrud(BasicObjs.update);
        switchScene("View/EntityOverview_UI.fxml", passObj, BasicObjs.forward);
    }

    @FXML
    private void goToViewRDN(ActionEvent event) {
        passObj.setObj(new ReturnDeliveryNote());
        passObj.setCrud(BasicObjs.read);
        switchScene("View/EntityOverview_UI.fxml", passObj, BasicObjs.forward);
    }

    @FXML
    private void goToCreateInvoiceUI(ActionEvent event) {
        passObj.setObj(new Invoice());
        passObj.setCrud(BasicObjs.create);
        switchScene("View/Invoice_UI.fxml", passObj, BasicObjs.forward);
    }

    @FXML
    private void goToViewInvoiceUI(ActionEvent event) {
        passObj.setObj(new Invoice());
        passObj.setCrud(BasicObjs.read);
        switchScene("View/EntityOverview_UI.fxml", passObj, BasicObjs.forward);
    }

    @FXML
    private void goToCreatePaymentUI(ActionEvent event) {
        passObj.setObj(new Receipt());
        passObj.setCrud(BasicObjs.create);
        switchScene("View/Payment_UI.fxml", passObj, BasicObjs.forward);

    }

    @FXML
    private void goToViewReceiptUI(ActionEvent event) {
        passObj.setObj(new Receipt());
        passObj.setCrud(BasicObjs.read);
        switchScene("View/EntityOverview_UI.fxml", passObj, BasicObjs.forward);
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
        return passObj;
    }

    @Override
    public void receiveData() {
        // Step 1
        Stage stage = (Stage) btnSetting.getScene().getWindow();
        // Step 2
        if (stage.getUserData() != null) {
            passObj = (BasicObjs) stage.getUserData();
            System.out.println("Here is home oage" + passObj.getLoginStaff().getStaffID());

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
        Duration delay1 = Duration.seconds(GeneralRulesService.getSessionTimeOut());
        PauseTransition transitionAlert = new PauseTransition(delay1);
        this.passObj.setLoginStaff(new Staff());
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
    private void signOut(MouseEvent event) {
        if (event.isPrimaryButtonDown() == true) {
            this.passObj.setLoginStaff(null);
            quitWindow("Warning", "Record Unsaved Message", "Once sign out all the unsaved record will be discarded\nAre you sure you want to continue?");
        }
    }

}
