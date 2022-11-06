/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import BizRulesConfiguration.AccountingRules;
import BizRulesConfiguration.SalesRules;
import BizRulesConfiguration.SalesRules.QuotStatus;
import BizRulesConfiguration.WarehouseRules;
import Entity.CollectAddress;
import Entity.Customer;
import Entity.CustomerInquiry;
import Entity.Item;
import Entity.Quotation;
import Entity.Staff;
import PassObjs.BasicObjs;
import Service.CustomerInquiryService;
import Service.ItemService;
import Service.QuotationService;
import Utils.DateFilter;
import Utils.ImageUtils;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCircleToggleNode;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.DoubleFilter;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.synedra.validatorfx.Validator;

/**
 * FXML Controller class
 *
 * @author Tee Zhuo Xuan
 */
public class QuotationCONTR implements Initializable, BasicCONTRFunc {

    private BasicObjs passObj;

    private Validator validator = new Validator();

    @FXML
    private MFXCircleToggleNode btnBack;
    @FXML
    private MFXCircleToggleNode ctnBillToSelection;
    @FXML
    private MFXTextField txtBillTo;
    @FXML
    private MFXTextField txtQuotID;
    @FXML
    private MFXTextField txtDeliverTo;
    @FXML
    private MFXTextField txtSalesPerson;
    @FXML
    private MFXDatePicker dtRefDate;
    @FXML
    private MFXTextField txtCIRef;
    @FXML
    private MFXTextField txtRef;
    @FXML
    private MFXTextField txtRefType;
    @FXML
    private MFXComboBox<?> cmbCurrencyCode;
    @FXML
    private MFXComboBox<?> cmbPymtTerm;
    @FXML
    private MFXComboBox<?> cmbShipmentTerm;
    @FXML
    private MFXCircleToggleNode ctnDeliverToSelection;
    @FXML
    private MFXCircleToggleNode ctnSalesPersonSelection;
    @FXML
    private MFXComboBox<?> cmbStatus;
    @FXML
    private MFXDatePicker dtQuotValidDate;
    @FXML
    private MFXDatePicker dtReqDlvrDate;
    @FXML
    private MFXTableView<?> tblVw;
    @FXML
    private MFXButton btnAdd;
    @FXML
    private MFXTextField txtSubTtl;
    @FXML
    private MFXTextField txtNett;
    @FXML
    private MFXTextField txtDiscount;
    @FXML
    private MFXTextField txtGross;
    @FXML
    private MFXTextField txtIssuedBy;
    @FXML
    private MFXCircleToggleNode ctnIssuedBySelection;
    @FXML
    private MFXTextField txtReleasedAVerifiedBy;
    @FXML
    private MFXCircleToggleNode ctnReleasedAVerifiedBySelection;
    @FXML
    private ImageView imgDocs;
    @FXML
    private MFXTextField txtCustSignature;
    @FXML
    private MFXCircleToggleNode ctnCustSignatureSelection;
    @FXML
    private MFXButton btnSave;
    @FXML
    private MFXButton btnDiscard;

    @FXML
    private MFXCircleToggleNode ctnCIRefSelection;
    @FXML
    private Label lblImgStr;

    private Quotation quotInDraft;

    AccountingRules accRules = new AccountingRules();

    WarehouseRules warehouseRules = new WarehouseRules();

    SalesRules salesRules = new SalesRules();

    private List<Item> items = new ArrayList<>(); // use to know which Item been update, and perform update action for those modified address

    private List<Item> tempItems = new ArrayList<>(); // use to know which Item been update, and perform update action for those modified address

    private static List<String> rowSelected = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        setupItemTable();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                initializeComboSelections();
                inputValidation();
                receiveData();

                if (passObj.getCrud().equals(BasicObjs.read) || passObj.getCrud().equals(BasicObjs.update)) {
                    try {
                        fieldFillIn();
                    } catch (IOException ex) {
                        Logger.getLogger(QuotationCONTR.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    // haven't verify the same product ID, must have different delivery date
                    if (passObj.getObj() instanceof Quotation) {
                        items.addAll(ItemService.getItemByQuotID(((Quotation) passObj.getObj()).getCode()));
                    } else if (passObj.getObj() instanceof CustomerInquiry) {
                        items.addAll(ItemService.getItemsByCIID(((CustomerInquiry) passObj.getObj()).getCode()));
                    }
                }

                if (passObj.getCrud().equals(BasicObjs.read)) {
                    isViewMode(true);
                }
            }
        });
    }

    private void initializeComboSelections() {

        ((MFXComboBox<String>) this.cmbCurrencyCode).setItems(FXCollections.observableList(accRules.getCurrencyCodes()));
        ((MFXComboBox<String>) this.cmbPymtTerm).setItems(FXCollections.observableList(accRules.getPymtTerms()));
        ((MFXComboBox<String>) this.cmbShipmentTerm).setItems(FXCollections.observableList(warehouseRules.getShipmentTerms()));
        ((MFXComboBox<QuotStatus>) this.cmbStatus).setItems(FXCollections.observableList(salesRules.getQuotStatuses()));
    }

    public void setupItemTable() {
        // Product ID
        MFXTableColumn<Item> prodIDCol = new MFXTableColumn<>("Product ID", true, Comparator.comparing(item -> item.getProduct().getProdID()));
        // Inventory ID
        MFXTableColumn<Item> inventoryIDCol = new MFXTableColumn<>("Inventory ID", true, Comparator.comparing(item -> item.getInventory().getInventoryID()));
        // Remarks
        MFXTableColumn<Item> remarksCol = new MFXTableColumn<>("Remarks", true, Comparator.comparing(item -> item.getRemark()));
        // Quantity
        MFXTableColumn<Item> qtyCol = new MFXTableColumn<>("Quantity", true, Comparator.comparing(item -> item.getQty()));
        // Unit Price
        MFXTableColumn<Item> unitPriceCol = new MFXTableColumn<>("Unit Price", true, Comparator.comparing(item -> item.getUnitPrice()));
        // Excl. Amount
        MFXTableColumn<Item> exclCol = new MFXTableColumn<>("Excl. Amount", true, Comparator.comparing(item -> item.getExclTaxAmt()));
        // Discount Amount
        MFXTableColumn<Item> discCol = new MFXTableColumn<>("Discount Amount", true, Comparator.comparing(item -> item.getDiscAmt()));
        // Incl. Amount
        MFXTableColumn<Item> inclCol = new MFXTableColumn<>("Incl. Amount", true, Comparator.comparing(item -> item.getInclTaxAmt()));
        // Delivery Date
        MFXTableColumn<Item> dlvrDtCol = new MFXTableColumn<>("Delivery Date", true, Comparator.comparing(item -> item.getDlvrDate()));

        // Product ID
        prodIDCol.setRowCellFactory(i -> new MFXTableRowCell<>(item -> item.getProduct().getProdID()));
        // Inventory ID
        inventoryIDCol.setRowCellFactory(i -> new MFXTableRowCell<>(item -> item.getInventory().getInventoryID()));
        // Remarks
        remarksCol.setRowCellFactory(i -> new MFXTableRowCell<>(item -> item.getRemark()));
        // Quantity
        qtyCol.setRowCellFactory(i -> new MFXTableRowCell<>(item -> item.getQty()));
        // Unit Price
        unitPriceCol.setRowCellFactory(i -> new MFXTableRowCell<>(item -> item.getUnitPrice()));
        // Excl. Amount
        exclCol.setRowCellFactory(i -> new MFXTableRowCell<>(item -> item.getExclTaxAmt()));
        // Discount Amount
        discCol.setRowCellFactory(i -> new MFXTableRowCell<>(item -> item.getDiscAmt()));
        // Incl. Amount
        inclCol.setRowCellFactory(i -> new MFXTableRowCell<>(item -> item.getInclTaxAmt()));
        // Delivery Date
        dlvrDtCol.setRowCellFactory(i -> new MFXTableRowCell<>(item -> item.getDlvrDate()));

        ((MFXTableView<Item>) tblVw).getTableColumns().clear();
        ((MFXTableView<Item>) tblVw).getTableColumns().addAll(
                prodIDCol,
                inventoryIDCol,
                remarksCol,
                qtyCol,
                unitPriceCol,
                discCol,
                dlvrDtCol
        );

        ((MFXTableView<Item>) tblVw).getFilters().clear();

        ((MFXTableView<Item>) tblVw).getFilters().addAll(
                new StringFilter<>("Product ID", item -> item.getProduct().getProdID()),
                new StringFilter<>("Inventory ID", item -> item.getInventory().getInventoryID()),
                new StringFilter<>("Remark", item -> item.getRemark()),
                new IntegerFilter<>("Quantity", item -> item.getQty()),
                new DoubleFilter<>("Unit Price", item -> item.getUnitPrice().doubleValue()),
                new DoubleFilter<>("Excl. Amount", item -> item.getExclTaxAmt().doubleValue()),
                new DoubleFilter<>("Discount Amount", item -> item.getDiscAmt().doubleValue()),
                new DoubleFilter<>("Incl. Amount", item -> item.getInclTaxAmt().doubleValue()),
                new DateFilter<>("Delivery Date", item -> item.getDlvrDate())
        );
        tempItems.addAll(items);
        ((MFXTableView<Item>) tblVw).getItems().clear();
        ((MFXTableView<Item>) tblVw).setItems(FXCollections.observableArrayList(tempItems));
        tempItems.clear();

        ((MFXTableView<Item>) tblVw).getSelectionModel().selectionProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {

                if (((MFXTableView<Item>) tblVw).getSelectionModel().getSelectedValues().size() != 0) {
                    Item item = (((MFXTableView<Item>) tblVw).getSelectionModel().getSelectedValues().get(0));
                    rowSelected.add(item.getProduct().getProdID());

                    if (rowSelected.size() == 2) {
                        if (rowSelected.get(0).equals(rowSelected.get(1))) {
                            System.out.println(item.getProduct().getProdID() + "This is product ID");
                            // action here
                            Parent root;
                            try {
                                root = FXMLLoader.load(getClass().getClassLoader().getResource("View/SalesDocsPSSelect_UI.fxml"));
                                Stage stage = new Stage();
                                stage.initModality(Modality.WINDOW_MODAL);
                                stage.initOwner(btnBack.getScene().getWindow());
                                stage.setScene(new Scene(root));

                                BasicObjs passObj = new BasicObjs();
                                passObj.setObj(item);
                                passObj.setCrud(BasicObjs.read);

                                stage.setUserData(passObj);
                                stage.showAndWait();

                                // if have any change on the selected item
                                if (stage.getUserData() != null) {

                                    BasicObjs receiveObj = (BasicObjs) stage.getUserData();
                                    Item catchedItem = new Item();
                                    catchedItem = (Item) receiveObj.getObj();

                                    if (!items.contains(catchedItem)) {
                                        items.add(catchedItem);
                                    } else {
                                        items.set(items.indexOf(item), catchedItem);
                                    }

                                    setupItemTable();

                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        rowSelected.clear();

                    }
                }
            }
        });

    }

    private void fieldFillIn() throws IOException {
        if (passObj.getObj() != null) {
            if (passObj.getObj() instanceof Quotation) {
                Quotation quotation = (Quotation) passObj.getObj();
                this.txtQuotID.setText(quotation.getCode());
                this.txtSalesPerson.setText(quotation.getSalesPerson().getStaffID());
                this.txtBillTo.setText(quotation.getBillToCust().getCustID());
                this.txtDeliverTo.setText(quotation.getDeliverToCust().getCollectAddrID());
                this.dtRefDate.setValue(Instant.ofEpochMilli(quotation.getCreatedDate().getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate());
                this.txtCIRef.setText(quotation.getCI().getCode());
                this.txtRefType.setText(quotation.getReferenceType());
                this.txtRef.setText(quotation.getReference());
                this.dtQuotValidDate.setValue(Instant.ofEpochMilli(quotation.getQuotValidityDate().getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate());
                this.dtReqDlvrDate.setValue(Instant.ofEpochMilli(quotation.getRequiredDeliveryDate().getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate());
                this.cmbCurrencyCode.setText(quotation.getCurrencyCode());
                this.cmbPymtTerm.setText(quotation.getPymtTerm());
                this.cmbShipmentTerm.setText(quotation.getShipmentTerm());
                this.cmbStatus.setText(quotation.getStatus());

                this.txtGross.setText(quotation.getGross().toString());
                this.txtDiscount.setText(quotation.getDiscount().toString());
                this.txtSubTtl.setText(quotation.getSubTotal().toString());
                this.txtNett.setText(quotation.getNett().toString());

                this.txtIssuedBy.setText(quotation.getIssuedBy().getStaffID());
                this.txtReleasedAVerifiedBy.setText(quotation.getReleasedAVerifiedBy().getStaffID());
                this.txtCustSignature.setText(quotation.getCustomerSignature().getCollectAddrID());

                this.lblImgStr.setText(quotation.getSignedDocPic());
                this.imgDocs.setImage(ImageUtils.byteToImg(ImageUtils.encodedStrToByte(((String) ImageUtils.splitImgStr(quotation.getSignedDocPic()).getFirst()))));

            } else if (passObj.getObj() instanceof CustomerInquiry) {
                CustomerInquiry customerInquiry = (CustomerInquiry) passObj.getObj();
                this.txtQuotID.setText("");
                this.txtSalesPerson.setText(customerInquiry.getSalesPerson().getStaffID());
                this.txtBillTo.setText(customerInquiry.getBillToCust().getCustID());
                this.txtDeliverTo.setText(customerInquiry.getDeliverToCust().getCollectAddrID());
                this.dtRefDate.setValue(Instant.ofEpochMilli(customerInquiry.getCreatedDate().getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate());
                this.txtCIRef.setText(customerInquiry.getCode());
                this.txtRefType.setText(customerInquiry.getReferenceType());
                this.txtRef.setText(customerInquiry.getReference());
                this.dtQuotValidDate.setValue(null);
                this.dtReqDlvrDate.setValue(Instant.ofEpochMilli(customerInquiry.getRequiredDeliveryDate().getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate());
                this.cmbCurrencyCode.setText(customerInquiry.getCurrencyCode());
                this.cmbPymtTerm.setText(customerInquiry.getPymtTerm());
                this.cmbShipmentTerm.setText(customerInquiry.getShipmentTerm());
                this.cmbStatus.setText(customerInquiry.getStatus());

                this.txtGross.setText(customerInquiry.getGross().toString());
                this.txtDiscount.setText(customerInquiry.getDiscount().toString());
                this.txtSubTtl.setText(customerInquiry.getSubTotal().toString());
                this.txtNett.setText(customerInquiry.getNett().toString());

                this.txtIssuedBy.setText(customerInquiry.getIssuedBy().getStaffID());
                this.txtReleasedAVerifiedBy.setText("");
                this.txtCustSignature.setText("");

                this.lblImgStr.setText("");
                this.imgDocs.setImage(null);

                items.clear();
                items.addAll(ItemService.getItemsByCIID(customerInquiry.getCode()));
                setupItemTable();
            }

        }
    }

    private void isViewMode(boolean disable) {
        if (disable == true) {
            this.btnSave.setText("Update");
        } else {
            this.btnSave.setText("Save");
        }

        this.txtSalesPerson.setDisable(disable);
        this.txtBillTo.setDisable(disable);
        this.txtDeliverTo.setDisable(disable);
        this.dtRefDate.setDisable(disable);
        this.txtCIRef.setDisable(disable);
        this.txtRefType.setDisable(disable);
        this.txtRef.setDisable(disable);
        this.dtQuotValidDate.setDisable(disable);
        this.dtReqDlvrDate.setDisable(disable);
        this.cmbCurrencyCode.setDisable(disable);
        this.cmbPymtTerm.setDisable(disable);
        this.cmbShipmentTerm.setDisable(disable);
        this.cmbStatus.setDisable(disable);
        this.txtGross.setDisable(disable);
        this.txtDiscount.setDisable(disable);
        this.txtSubTtl.setDisable(disable);
        this.txtNett.setDisable(disable);
        this.txtIssuedBy.setDisable(disable);
        this.txtReleasedAVerifiedBy.setDisable(disable);
        this.txtCustSignature.setDisable(disable);

        this.ctnBillToSelection.setDisable(disable);
        this.ctnDeliverToSelection.setDisable(disable);
        this.ctnSalesPersonSelection.setDisable(disable);
        this.ctnCIRefSelection.setDisable(disable);
        this.ctnIssuedBySelection.setDisable(disable);
        this.ctnReleasedAVerifiedBySelection.setDisable(disable);
        this.ctnCustSignatureSelection.setDisable(disable);

        this.tblVw.setDisable(disable);
        this.btnAdd.setDisable(disable);
        this.imgDocs.setDisable(disable);

    }

    @FXML
    private void goBackPrevious(MouseEvent event) {
        if (event.isPrimaryButtonDown() == true) {
            quitWindow("Warning", "Validation Message", "Record haven't been saved.\nAre you sure you want to continue?");
        }
    }

    @FXML
    private void discardCurrentData(MouseEvent event) {
        if (event.isPrimaryButtonDown() == true) {
            quitWindow("Warning", "Validation Message", "Record will be discarded.\nAre you sure you want to continue?");
        }
    }

    private void quitWindow(String title, String headerTxt, String contentTxt) {
        ButtonType alertBtnClicked = alertDialog(Alert.AlertType.CONFIRMATION,
                title,
                headerTxt,
                contentTxt);

        if (alertBtnClicked == ButtonType.OK) {
            switchScene(passObj.getFxmlPaths().getLast().toString(), new BasicObjs(), BasicObjs.back);
        } else if (alertBtnClicked == ButtonType.CANCEL) {
            //nothing need to do, remain same page
        }
    }

    @Override
    public void switchScene(String fxmlPath, BasicObjs passObj, String direction) {
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
    public BasicObjs sendData(BasicObjs passObj, String direction) {
        switch (direction) {
            case BasicObjs.forward:
                passObj.getFxmlPaths().addLast("View/Quotations_UI.fxml");
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

    @Override
    public void inputValidation() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean clearAllFieldsValue() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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

    private Quotation prepareQuotationToObj() {
        Quotation quotation = new Quotation();

        quotation.setCode(this.txtQuotID.getText());

        CustomerInquiry customerInquiry = new CustomerInquiry();
        customerInquiry.setCode(this.txtCIRef.getText());
        quotation.setCI(customerInquiry);

        Staff salesPerson = new Staff();
        salesPerson.setStaffID(this.txtSalesPerson.getText());
        quotation.setSalesPerson(salesPerson);

        Customer billTo = new Customer();
        billTo.setCustID(this.txtBillTo.getText());
        quotation.setBillToCust(billTo);

        CollectAddress deliverTo = new CollectAddress();
        deliverTo.setCollectAddrID(this.txtDeliverTo.getText());
        quotation.setDeliverToCust(deliverTo);

        quotation.setCreatedDate(this.dtRefDate.getValue() == null ? null : Timestamp.valueOf(this.dtRefDate.getValue().atStartOfDay()));
        quotation.setReferenceType(this.txtRefType.getText());
        quotation.setReference(this.txtRef.getText());
        quotation.setQuotValidityDate(this.dtQuotValidDate.getValue() == null ? null : java.sql.Date.valueOf(this.dtQuotValidDate.getValue()));
        quotation.setRequiredDeliveryDate(this.dtReqDlvrDate.getValue() == null ? null : java.sql.Date.valueOf(this.dtReqDlvrDate.getValue()));
        quotation.setCurrencyCode(this.cmbCurrencyCode.getText());
        quotation.setPymtTerm(this.cmbPymtTerm.getText());
        quotation.setShipmentTerm(this.cmbShipmentTerm.getText());
        quotation.setStatus(this.cmbStatus.getText());

        quotation.setGross(new BigDecimal(this.txtGross.getText()));
        quotation.setDiscount(new BigDecimal(this.txtDiscount.getText()));
        quotation.setSubTotal(new BigDecimal(this.txtSubTtl.getText()));
        quotation.setNett(new BigDecimal(this.txtNett.getText()));

        Staff issuedBy = new Staff();
        issuedBy.setStaffID(this.txtIssuedBy.getText());
        quotation.setIssuedBy(issuedBy);

        Staff releasedAVerifiedBy = new Staff();
        releasedAVerifiedBy.setStaffID(this.txtReleasedAVerifiedBy.getText());
        quotation.setReleasedAVerifiedBy(releasedAVerifiedBy);

        CollectAddress customerSignature = new CollectAddress();
        customerSignature.setCollectAddrID(this.txtCustSignature.getText());
        quotation.setCustomerSignature(customerSignature);

        quotation.setSignedDocPic(this.lblImgStr.getText());

        quotation.setItems(items);
        return quotation;
    }

    @FXML
    private void openBillToSelection(MouseEvent event) {
        if (event.isPrimaryButtonDown() == true) {
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getClassLoader().getResource("View/InnerEntitySelect_UI.fxml"));
                Stage stage = new Stage();
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(btnBack.getScene().getWindow());
                stage.setScene(new Scene(root));

                BasicObjs passObj = new BasicObjs();
                passObj.setObj(new Customer());

                stage.setUserData(passObj);
                stage.showAndWait();

                if (stage.getUserData() != null) {
                    BasicObjs receiveObj = (BasicObjs) stage.getUserData();
                    this.txtBillTo.setText(((Customer) receiveObj.getObj()).getCustID());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //switchScene("View/InnerEntitySelect_UI.fxml", new BasicObjs(new Place()), BasicObjs.forward, "Dialog");
        }
    }

    @FXML
    private void openDeliverToSelection(MouseEvent event) {
        if (event.isPrimaryButtonDown() == true) {
            if (this.txtBillTo.getText().isEmpty()) {
                alertDialog(Alert.AlertType.INFORMATION,
                        "Information",
                        "Prerequisite Condition",
                        "Must fill in deliver to column, before select customer signature");
                return;
            }

            Parent root;
            try {
                root = FXMLLoader.load(getClass().getClassLoader().getResource("View/InnerEntitySelect_UI.fxml"));
                Stage stage = new Stage();
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(btnBack.getScene().getWindow());
                stage.setScene(new Scene(root));

                BasicObjs passObj = new BasicObjs();
                CollectAddress cllctAddr = new CollectAddress();

                cllctAddr.setCustomer(new Customer());
                cllctAddr.getCustomer().setCustID(this.txtBillTo.getText());
                passObj.setObj(cllctAddr);

                stage.setUserData(passObj);
                stage.showAndWait();

                if (stage.getUserData() != null) {
                    BasicObjs receiveObj = (BasicObjs) stage.getUserData();
                    this.txtIssuedBy.setText(((CollectAddress) receiveObj.getObj()).getCollectAddrID());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //switchScene("View/InnerEntitySelect_UI.fxml", new BasicObjs(new Place()), BasicObjs.forward, "Dialog");
        }
    }

    @FXML
    private void openSalesPersonSelection(MouseEvent event) {

        if (event.isPrimaryButtonDown() == true) {
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getClassLoader().getResource("View/InnerEntitySelect_UI.fxml"));
                Stage stage = new Stage();
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(btnBack.getScene().getWindow());
                stage.setScene(new Scene(root));

                BasicObjs passObj = new BasicObjs();
                passObj.setObj(new Staff());

                stage.setUserData(passObj);
                stage.showAndWait();

                if (stage.getUserData() != null) {
                    BasicObjs receiveObj = (BasicObjs) stage.getUserData();
                    this.txtSalesPerson.setText(((Staff) receiveObj.getObj()).getStaffID());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //switchScene("View/InnerEntitySelect_UI.fxml", new BasicObjs(new Place()), BasicObjs.forward, "Dialog");
        }
    }

    @FXML
    private void openCISelection(MouseEvent event) throws SQLException {
        if (event.isPrimaryButtonDown() == true) {

            ButtonType alertBtnClicked = alertDialog(Alert.AlertType.CONFIRMATION,
                    "Confirmation",
                    "Modified Data Loss Alert",
                    "All modified data will be overwrite with selected Customer Inquiry Information. Please select OK to proceed.");

            if (alertBtnClicked != ButtonType.OK) {
                return;
            }

            Parent root;
            try {
                root = FXMLLoader.load(getClass().getClassLoader().getResource("View/InnerEntitySelect_UI.fxml"));
                Stage stage = new Stage();
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(btnBack.getScene().getWindow());
                stage.setScene(new Scene(root));

                BasicObjs passObj = new BasicObjs();
                passObj.setObj(new CustomerInquiry());

                stage.setUserData(passObj);
                stage.showAndWait();

                if (stage.getUserData() != null) {

                    BasicObjs receiveObj = (BasicObjs) stage.getUserData();
                    CustomerInquiry customerInquiry = (CustomerInquiry) receiveObj.getObj();

                    if (!customerInquiry.getStatus().equals(SalesRules.CIStatus.COMPLETED)) {
                        this.txtCIRef.setText(customerInquiry.getCode());
                        passObj.setObj(CustomerInquiryService.getCustomerInquiryByCode(customerInquiry.getCode()));
                        fieldFillIn();
                    } else {
                        alertDialog(Alert.AlertType.INFORMATION,
                                "Information",
                                "Document Blocked Message",
                                "Customer Inquiry with COMPLETED status are not allowed to become any document reference.");
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //switchScene("View/InnerEntitySelect_UI.fxml", new BasicObjs(new Place()), BasicObjs.forward, "Dialog");
        }
    }

    @FXML
    private void openIssuedBySelection(MouseEvent event) {
        if (event.isPrimaryButtonDown() == true) {
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getClassLoader().getResource("View/InnerEntitySelect_UI.fxml"));
                Stage stage = new Stage();
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(btnBack.getScene().getWindow());
                stage.setScene(new Scene(root));

                BasicObjs passObj = new BasicObjs();
                passObj.setObj(new Staff());

                stage.setUserData(passObj);
                stage.showAndWait();

                if (stage.getUserData() != null) {
                    BasicObjs receiveObj = (BasicObjs) stage.getUserData();
                    this.txtIssuedBy.setText(((Staff) receiveObj.getObj()).getStaffID());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //switchScene("View/InnerEntitySelect_UI.fxml", new BasicObjs(new Place()), BasicObjs.forward, "Dialog");
        }
    }

    @FXML
    private void openReleasedAVerifiedBySelection(MouseEvent event) {
        if (event.isPrimaryButtonDown() == true) {
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getClassLoader().getResource("View/InnerEntitySelect_UI.fxml"));
                Stage stage = new Stage();
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(btnBack.getScene().getWindow());
                stage.setScene(new Scene(root));

                BasicObjs passObj = new BasicObjs();
                passObj.setObj(new Staff());

                stage.setUserData(passObj);
                stage.showAndWait();

                if (stage.getUserData() != null) {
                    BasicObjs receiveObj = (BasicObjs) stage.getUserData();
                    this.txtIssuedBy.setText(((Staff) receiveObj.getObj()).getStaffID());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //switchScene("View/InnerEntitySelect_UI.fxml", new BasicObjs(new Place()), BasicObjs.forward, "Dialog");
        }
    }

    @FXML
    private void openCustSignatureSelection(MouseEvent event) {
        if (event.isPrimaryButtonDown() == true) {
            if (this.txtBillTo.getText().isEmpty()) {
                alertDialog(Alert.AlertType.INFORMATION,
                        "Information",
                        "Prerequisite Condition",
                        "Must fill in deliver to column, before select customer signature");
                return;
            }

            Parent root;
            try {
                root = FXMLLoader.load(getClass().getClassLoader().getResource("View/InnerEntitySelect_UI.fxml"));
                Stage stage = new Stage();
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(btnBack.getScene().getWindow());
                stage.setScene(new Scene(root));

                BasicObjs passObj = new BasicObjs();
                CollectAddress cllctAddr = new CollectAddress();

                cllctAddr.setCustomer(new Customer());
                cllctAddr.getCustomer().setCustID(this.txtBillTo.getText());
                passObj.setObj(cllctAddr);

                stage.setUserData(passObj);
                stage.showAndWait();

                if (stage.getUserData() != null) {
                    BasicObjs receiveObj = (BasicObjs) stage.getUserData();
                    this.txtIssuedBy.setText(((CollectAddress) receiveObj.getObj()).getCollectAddrID());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //switchScene("View/InnerEntitySelect_UI.fxml", new BasicObjs(new Place()), BasicObjs.forward, "Dialog");
        }
    }

    @FXML
    private void goToImgViewer(MouseEvent event) {
        // action here
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("View/ImgViewers_UI.fxml"));

            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(btnBack.getScene().getWindow());
            stage.setScene(new Scene(root));

            BasicObjs passObj = new BasicObjs();

            passObj.setObj(this.lblImgStr.getText());
            stage.setUserData(passObj);
            stage.showAndWait();

            // if have any change on the selected collect address
            if (stage.getUserData() != null) {

                BasicObjs receiveObj = (BasicObjs) stage.getUserData();
                String catchedImagesStr = new String();
                catchedImagesStr = (String) receiveObj.getObj();

                String splittedImgStrFirst = (String) ImageUtils.splitImgStr(catchedImagesStr).getFirst();
                byte[] decodedByteFirst = ImageUtils.encodedStrToByte(splittedImgStrFirst);
                Image imageFirst = ImageUtils.byteToImg(decodedByteFirst);
                this.imgDocs.setImage(imageFirst);
                this.lblImgStr.setText(catchedImagesStr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addProductItem(MouseEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("View/SalesDocsPSSelect_UI.fxml"));

            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(btnBack.getScene().getWindow());
            stage.setScene(new Scene(root));

            BasicObjs passObj = new BasicObjs();
            passObj.setCrud(BasicObjs.create);
            passObj.setObj(new Item());
            stage.setUserData(passObj);
            stage.showAndWait();

            // if have any change on the selected collect address
            if (stage.getUserData() != null) {

                BasicObjs receiveObj = (BasicObjs) stage.getUserData();
                Item catchedItem = new Item();
                catchedItem = (Item) receiveObj.getObj();

                if (!items.contains(catchedItem)) {
                    items.add(catchedItem);
                } else {
                    items.set(items.indexOf(catchedItem), catchedItem);
                }

                setupItemTable();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void saveQuotation(MouseEvent event) throws SQLException {
        if (event.isPrimaryButtonDown() == true) {

            if (!this.btnSave.getText().equals("Save")) {
                isViewMode(false);
                return;
            }

            if (validator.containsErrors()) {
                alertDialog(Alert.AlertType.WARNING, "Warning", "Validation Message", validator.createStringBinding().getValue());
                return;
            }

            quotInDraft = prepareQuotationToObj();

            if (this.txtQuotID.getText().isEmpty()) {
                QuotationService.saveNewQuotation(quotInDraft);

            } else if (this.passObj.getCrud().equals(BasicObjs.update)) {
                QuotationService.updateQuotation(quotInDraft);
            }

            if (!this.txtCIRef.getText().isEmpty()) {
                quotInDraft.getCI().setStatus(SalesRules.CIStatus.COMPLETED.toString());
                CustomerInquiryService.updateCustomerInquiryStatus(quotInDraft.getCI());
            }

        }
    }

}
