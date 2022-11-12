/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import BizRulesConfiguration.AccountingRules;
import BizRulesConfiguration.SalesRules;
import BizRulesConfiguration.WarehouseRules;
import Entity.CollectAddress;
import Entity.Customer;
import Entity.CustomerInquiry;
import Entity.Item;
import Entity.Staff;
import PassObjs.BasicObjs;
import Service.CustomerInquiryService;
import Service.ItemService;
import Utils.DateFilter;
import Utils.ImageUtils;
import Utils.ValidationUtils;
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
public class CustomerInquiryCONTR implements Initializable, BasicCONTRFunc {

    //<editor-fold defaultstate="collapsed" desc="fields">
    @FXML
    private MFXCircleToggleNode btnBack;
    @FXML
    private MFXCircleToggleNode ctnBillToSelection;
    @FXML
    private MFXTextField txtBillTo;
    @FXML
    private MFXTextField txtCIID;
    @FXML
    private MFXDatePicker dtRefDate;
    @FXML
    private MFXTextField txtRef;
    @FXML
    private MFXTextField txtRefType;
    @FXML
    private MFXComboBox<?> cmbCurrencyCode;
    @FXML
    private MFXComboBox<?> cmbPymtTerm;
    @FXML
    private MFXComboBox<?> cmbShipTerm;
    @FXML
    private MFXCircleToggleNode ctnDeliverToSelection;
    @FXML
    private MFXTextField txtSalesPerson;
    @FXML
    private MFXCircleToggleNode ctnSalesPersonSelection;
    @FXML
    private MFXComboBox<?> cmbStatus;
    @FXML
    private MFXDatePicker dtReqDuireDeliveryDate;
    @FXML
    private MFXButton btnAdd;
    @FXML
    private MFXCircleToggleNode ctnIssuedBySelection;
    @FXML
    private MFXButton btnSave;
    @FXML
    private MFXButton btnDiscard;
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
    private MFXTextField txtDeliverTo;
    @FXML
    private MFXTableView<?> tblVw;
    @FXML
    private Label lblImgStrs;
    @FXML
    private ImageView imgDocs;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="util declarations">
    private BasicObjs passObj;

    private Validator validator = new Validator();

    private CustomerInquiry customerInquiryInDraft;

    AccountingRules accRules = new AccountingRules();

    WarehouseRules warehouseRules = new WarehouseRules();

    SalesRules salesRules = new SalesRules();

    private List<Item> items = new ArrayList<>(); // use to know which Item been update, and perform update action for those modified address

    private List<Item> tempItems = new ArrayList<>(); // use to know which Item been update, and perform update action for those modified address

    private static List<String> rowSelected = new ArrayList<>();
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

                initializeComboSelections();
                inputValidation();
                receiveData();

                if (passObj.getCrud().equals(BasicObjs.read) || passObj.getCrud().equals(BasicObjs.update)) {

                    try {
                        fieldFillIn();
                    } catch (IOException ex) {
                        Logger.getLogger(CustomerInquiryCONTR.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    items.addAll(ItemService.getItemsByCIID(((CustomerInquiry) passObj.getObj()).getCode()));

                }

                if (passObj.getCrud().equals(BasicObjs.read)) {
                    isViewMode(true);
                }
            }
        });

        setupItemTable();

    }

    public void setupItemTable() {
        // Product ID
        MFXTableColumn<Item> prodIDCol = new MFXTableColumn<>("Product ID", true, Comparator.comparing(item -> item.getProduct() == null ? "" : item.getProduct().getProdID()));
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
        prodIDCol.setRowCellFactory(i -> new MFXTableRowCell<>(item -> item.getProduct() == null ? "" : item.getProduct().getProdID()));
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
                remarksCol,
                qtyCol,
                unitPriceCol,
                discCol,
                dlvrDtCol
        );

        ((MFXTableView<Item>) tblVw).getFilters().clear();

        ((MFXTableView<Item>) tblVw).getFilters().addAll(
                new StringFilter<>("Product ID", item -> item.getProduct() == null ? "" : item.getProduct().getProdID()),
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

        ((MFXTableView<CollectAddress>) tblVw).getSelectionModel().selectionProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {

                if (((MFXTableView<CollectAddress>) tblVw).getSelectionModel().getSelectedValues().size() != 0) {

                    Item item = (((MFXTableView<Item>) tblVw).getSelectionModel().getSelectedValues().get(0));
                    rowSelected.add(item.getProduct().getProdID());

                    if (rowSelected.size() == 2) {
                        if (rowSelected.get(0).equals(rowSelected.get(1))) {
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

                                // if have any change on the selected collect address
                                if (stage.getUserData() != null) {

                                    BasicObjs receiveObj = (BasicObjs) stage.getUserData();
                                    Item catchedItem = new Item();
                                    catchedItem = ((Item) receiveObj.getObj()).clone();

                                    if (catchedItem.getProduct() == null) { // remove
                                        items.remove(item);
                                    } else if (!items.contains(catchedItem)) {
                                        items.remove(item);
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
        clearAllFieldsValue();

        if (passObj.getObj() != null) {
            CustomerInquiry customerInquiry = (CustomerInquiry) passObj.getObj();
            this.txtCIID.setText(customerInquiry.getCode());
            this.txtSalesPerson.setText(customerInquiry.getSalesPerson() == null ? "" : customerInquiry.getSalesPerson().getStaffID());
            this.txtBillTo.setText(customerInquiry.getBillToCust() == null ? "" : customerInquiry.getBillToCust().getCustID());
            this.txtDeliverTo.setText(customerInquiry.getDeliverToCust() == null ? "" : customerInquiry.getDeliverToCust().getCollectAddrID());
            this.dtRefDate.setValue(customerInquiry.getCreatedDate() == null ? null : Instant.ofEpochMilli(customerInquiry.getCreatedDate().getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate());
            this.txtRefType.setText(customerInquiry.getReferenceType());
            this.txtRef.setText(customerInquiry.getReference());
            this.dtReqDuireDeliveryDate.setValue(customerInquiry.getRequiredDeliveryDate() == null ? null : Instant.ofEpochMilli(customerInquiry.getRequiredDeliveryDate().getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate());
            this.cmbCurrencyCode.setText(customerInquiry.getCurrencyCode());
            this.cmbPymtTerm.setText(customerInquiry.getPymtTerm());
            this.cmbShipTerm.setText(customerInquiry.getShipmentTerm());
            this.cmbStatus.setText(customerInquiry.getStatus());

            this.txtGross.setText(customerInquiry.getGross() == null ? "" : customerInquiry.getGross().toString());
            this.txtDiscount.setText(customerInquiry.getDiscount() == null ? "" : customerInquiry.getDiscount().toString());
            this.txtSubTtl.setText(customerInquiry.getSubTotal() == null ? "" : customerInquiry.getSubTotal().toString());
            this.txtNett.setText(customerInquiry.getNett() == null ? "" : customerInquiry.getNett().toString());

            this.txtIssuedBy.setText(customerInquiry.getIssuedBy() == null ? "" : customerInquiry.getIssuedBy().getStaffID());
            this.lblImgStrs.setText(customerInquiry.getSignedDocPic());
            this.imgDocs.setImage(ImageUtils.byteToImg(ImageUtils.encodedStrToByte(((String) ImageUtils.splitImgStr(customerInquiry.getSignedDocPic().isEmpty() == true ? null : customerInquiry.getSignedDocPic()).getFirst()))));
        }
    }

    private void isViewMode(boolean disable) {
        if (disable == true) {
            this.btnSave.setText("Update");
        } else {
            this.btnSave.setText("Save");
        }
        this.txtCIID.setDisable(disable);
        this.txtSalesPerson.setDisable(disable);
        this.txtBillTo.setDisable(disable);
        this.txtDeliverTo.setDisable(disable);
        this.dtRefDate.setDisable(disable);
        this.txtRefType.setDisable(disable);
        this.txtRef.setDisable(disable);
        this.dtReqDuireDeliveryDate.setDisable(disable);
        this.cmbCurrencyCode.setDisable(disable);
        this.cmbPymtTerm.setDisable(disable);
        this.cmbShipTerm.setDisable(disable);
        this.cmbStatus.setDisable(disable);
        this.txtGross.setDisable(disable);
        this.txtDiscount.setDisable(disable);
        this.txtSubTtl.setDisable(disable);
        this.txtNett.setDisable(disable);
        this.txtIssuedBy.setDisable(disable);

        this.ctnBillToSelection.setDisable(disable);
        this.ctnDeliverToSelection.setDisable(disable);
        this.ctnIssuedBySelection.setDisable(disable);
        this.ctnSalesPersonSelection.setDisable(disable);

        this.tblVw.setDisable(disable);
        this.btnAdd.setDisable(disable);
        this.imgDocs.setDisable(disable);
    }

    private void initializeComboSelections() {
        ((MFXComboBox<String>) this.cmbCurrencyCode).setItems(FXCollections.observableList(accRules.getCurrencyCodes()));
        ((MFXComboBox<String>) this.cmbPymtTerm).setItems(FXCollections.observableList(accRules.getPymtTerms()));
        ((MFXComboBox<String>) this.cmbShipTerm).setItems(FXCollections.observableList(warehouseRules.getShipmentTerms()));
        ((MFXComboBox<SalesRules.CIStatus>) this.cmbStatus).setItems(FXCollections.observableList(salesRules.getCiStatuses()));
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
                passObj.getFxmlPaths().addLast("View/CustomerInquiry_UI.fxml");
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
        List<MFXTextField> listOfControls = new ArrayList<MFXTextField>();
        ValidationUtils<MFXTextField> validationUtils = new ValidationUtils<>();
        int characterLimit = 0;
        //================================
        listOfControls.add(this.txtSalesPerson);
        characterLimit = 21;
        validationUtils.validationCreator(validator, listOfControls, 0, false, "Sales Person - Required Field", ValidationUtils.isNotEmpty);
        listOfControls.clear();
        //================================
        listOfControls.add(this.txtBillTo);
        characterLimit = 21;
        validationUtils.validationCreator(validator, listOfControls, 0, false, "Bill To - Required Field", ValidationUtils.isNotEmpty);
        listOfControls.clear();
        //================================
        listOfControls.add(this.txtDeliverTo);
        characterLimit = 21;
        validationUtils.validationCreator(validator, listOfControls, 0, false, "Deliver To - Required Field", ValidationUtils.isNotEmpty);
        listOfControls.clear();
        //================================
        listOfControls.add(this.dtRefDate);
        characterLimit = 100;
        validationUtils.validationCreator(validator, listOfControls, characterLimit, true, "Reference Date - cannot be after current date", ValidationUtils.isBeforeCurrentDate);
        listOfControls.clear();
        //================================
        listOfControls.add(this.txtRefType);
        characterLimit = 11;
        validationUtils.validationCreator(validator, listOfControls, characterLimit, true, "Reference Type - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.txtRef);
        characterLimit = 21;
        validationUtils.validationCreator(validator, listOfControls, characterLimit, true, "Reference - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.dtRefDate);
        characterLimit = 100;
        validationUtils.validationCreator(validator, listOfControls, characterLimit, true, "Reference Date - cannot be after current date", ValidationUtils.isBeforeCurrentDate);
        listOfControls.clear();
        //================================
        listOfControls.add(this.dtReqDuireDeliveryDate);
        characterLimit = 100;
        validationUtils.validationCreator(validator, listOfControls, characterLimit, true, "Required Delivery Date Date - cannot be before current date", ValidationUtils.isAfterCurrentDate);
        listOfControls.clear();
        //================================
    }

    @Override
    public boolean clearAllFieldsValue() {
        //this.txtCIID.clear();
        this.txtSalesPerson.clear();
        this.txtBillTo.clear();
        this.txtDeliverTo.clear();
        this.dtRefDate.clear();
        this.txtRefType.clear();
        this.txtRef.clear();
        this.dtReqDuireDeliveryDate.clear();
        this.cmbCurrencyCode.clear();
        this.cmbPymtTerm.clear();
        this.cmbShipTerm.clear();
        this.cmbStatus.clear();

        this.txtGross.clear();
        this.txtDiscount.clear();
        this.txtSubTtl.clear();
        this.txtNett.clear();

        this.txtIssuedBy.clear();
        this.lblImgStrs.setText("");
        this.imgDocs.setImage(null);

        this.items.clear();
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

    private CustomerInquiry prepareCustomerInquiryToObj() {
        CustomerInquiry customerInquiry = new CustomerInquiry();
        customerInquiry.setCode(this.txtCIID.getText());

        Staff salesPerson = new Staff();
        salesPerson.setStaffID(this.txtSalesPerson.getText());
        customerInquiry.setSalesPerson(salesPerson);

        Customer billTo = new Customer();
        billTo.setCustID(this.txtBillTo.getText());
        customerInquiry.setBillToCust(billTo);

        CollectAddress collectAddr = new CollectAddress();
        collectAddr.setCollectAddrID(this.txtDeliverTo.getText());
        customerInquiry.setDeliverToCust(collectAddr);

        customerInquiry.setCreatedDate(this.dtRefDate.getValue() == null ? null : Timestamp.valueOf(this.dtRefDate.getValue().atStartOfDay()));
        customerInquiry.setReferenceType(this.txtRefType.getText());
        customerInquiry.setReference(this.txtRef.getText());
        customerInquiry.setRequiredDeliveryDate(this.dtReqDuireDeliveryDate.getValue() == null ? null : java.sql.Date.valueOf(this.dtReqDuireDeliveryDate.getValue()));
        customerInquiry.setCurrencyCode(this.cmbCurrencyCode.getText());
        customerInquiry.setPymtTerm(this.cmbPymtTerm.getText());
        customerInquiry.setShipmentTerm(this.cmbShipTerm.getText());
        customerInquiry.setStatus(this.cmbStatus.getText());

        customerInquiry.setGross(new BigDecimal(this.txtGross.getText()));
        customerInquiry.setDiscount(new BigDecimal(this.txtDiscount.getText()));
        customerInquiry.setSubTotal(new BigDecimal(this.txtSubTtl.getText()));
        customerInquiry.setNett(new BigDecimal(this.txtNett.getText()));

        Staff issuedBy = new Staff();
        issuedBy.setStaffID(this.txtIssuedBy.getText());
        customerInquiry.setIssuedBy(issuedBy);

        customerInquiry.setSignedDocPic(this.lblImgStrs.getText());

        customerInquiry.setItems(items);

        return customerInquiry;
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
                    this.txtDeliverTo.clear();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
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
                    this.txtDeliverTo.setText(((CollectAddress) receiveObj.getObj()).getCollectAddrID());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
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

            passObj.setObj(this.lblImgStrs.getText());
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
                this.lblImgStrs.setText(catchedImagesStr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addProductItem(MouseEvent event) {
        // action here

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
    private void saveCustomerInquiry(MouseEvent event) throws SQLException {
        if (event.isPrimaryButtonDown() == true) {

            if (!this.btnSave.getText().equals("Save")) {
                isViewMode(false);
                return;
            }

            if (validator.containsErrors()) {
                alertDialog(Alert.AlertType.WARNING, "Warning", "Validation Message", validator.createStringBinding().getValue());
                return;
            }

            customerInquiryInDraft = prepareCustomerInquiryToObj();

            if (this.passObj.getCrud().equals(BasicObjs.create)) {
                customerInquiryInDraft.setCode(CustomerInquiryService.saveNewCustomerInquiry(customerInquiryInDraft));;

            } else if (this.passObj.getCrud().equals(BasicObjs.update)) {
                CustomerInquiryService.updateCustomerInquiry(customerInquiryInDraft);
            }

            ItemService.updateItemsByDoc(customerInquiryInDraft.getItems(), customerInquiryInDraft.getCode());
        }
    }

}
