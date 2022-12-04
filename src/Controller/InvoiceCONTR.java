/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import BizRulesConfiguration.AccountingRules;
import BizRulesConfiguration.AccountingRules.InvoiceStatus;
import BizRulesConfiguration.SalesRules;
import Entity.CollectAddress;
import Entity.Customer;
import Entity.Invoice;
import Entity.Item;
import Entity.SalesOrder;
import Entity.Staff;
import PassObjs.BasicObjs;
import Service.GeneralRulesService;
import Service.InvoiceService;
import Service.ItemService;
import Service.SalesOrderService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
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
public class InvoiceCONTR implements Initializable, BasicCONTRFunc {

    //<editor-fold defaultstate="collapsed" desc="fields">
    @FXML
    private MFXCircleToggleNode btnBack;
    @FXML
    private MFXTextField txtInvoiceID;
    @FXML
    private MFXDatePicker dtRefDate;
    @FXML
    private MFXTextField txtSORef;
    @FXML
    private MFXTextField txtRef;
    @FXML
    private MFXTextField txtRefType;
    @FXML
    private MFXComboBox<?> cmbStatus;
    @FXML
    private MFXCircleToggleNode ctnSORefSelection;
    @FXML
    private MFXTableView<?> tblVw;
    @FXML
    private MFXButton btnAdd;
    @FXML
    private MFXTextField txtSubTtl;
    @FXML
    private MFXTextField txtTtlPayable;
    @FXML
    private MFXTextField txtDiscount;
    @FXML
    private MFXTextField txtGross;
    @FXML
    private MFXTextField txtIssuedBy;
    @FXML
    private MFXCircleToggleNode ctnIssuedBySelection;
    @FXML
    private MFXTextField txtReleasedAVerrifiedBy;
    @FXML
    private MFXCircleToggleNode ctnReleasedAVerifiedBySelection;
    @FXML
    private MFXCircleToggleNode ctnCustSignatureSelection;
    @FXML
    private MFXTextField txtCustSignature;
    @FXML
    private ImageView imgDocs;
    @FXML
    private Label lblImgStrs;
    @FXML
    private MFXButton btnSave;
    @FXML
    private MFXButton btnDiscard;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="util declarations">
    private BasicObjs passObj;

    AccountingRules accRules = new AccountingRules();

    private List<Item> itemsNotYetBill = new ArrayList<>(); // use to know which Item been update, and perform update action for those modified address

    private List<Item> items = new ArrayList<>(); // use to know which Item been update, and perform update action for those modified address

    private List<Item> tempItems = new ArrayList<>(); // use to know which Item been update, and perform update action for those modified address

    private static List<String> rowSelected = new ArrayList<>();

    private Invoice invoiceInDraft;

    private Validator validator = new Validator();
    //</editor-fold>

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
                autoClose();

                if (passObj.getCrud().equals(BasicObjs.create)) {
                    defaultValFillIn();
                }

                if (passObj.getCrud().equals(BasicObjs.read) || passObj.getCrud().equals(BasicObjs.update)) {
                    try {
                        fieldFillIn();
                    } catch (IOException ex) {
                        Logger.getLogger(InvoiceCONTR.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    if (passObj.getObj() instanceof Invoice) {

                        Invoice invoice = (Invoice) passObj.getObj();

                        itemsNotYetBill.addAll(ItemService.getItemNotYetBillBySO(invoice.getSO().getCode()));

                        items.addAll(ItemService.getItemsByINVID(invoice.getCode()));

                    } else if (passObj.getObj() instanceof SalesOrder) {
                        itemsNotYetBill.addAll(ItemService.getItemNotYetBillBySO(((SalesOrder) passObj.getObj()).getCode()));

                        for (Item i : itemsNotYetBill) {
                            items.add(i.clone());
                        }
                    }

                }

                if (passObj.getCrud().equals(BasicObjs.read)) {
                    isViewMode(true);
                }
            }
        });
    }

    public void autoClose() {
        Duration delay1 = Duration.seconds(GeneralRulesService.getSessionTimeOut());
        PauseTransition transitionAlert = new PauseTransition(delay1);
        transitionAlert.setOnFinished(evt -> {
            this.passObj.setLoginStaff(new Staff());
            switchScene("View/Login_UI.fxml", passObj, BasicObjs.back);
        });
        transitionAlert.setCycleCount(1);

        btnBack.getScene().addEventFilter(InputEvent.ANY, evt -> transitionAlert.playFromStart());
    }

    private void initializeComboSelections() {
        ((MFXComboBox<InvoiceStatus>) this.cmbStatus).setItems(FXCollections.observableList(accRules.getInvoiceStatuses()));
    }

    private void setupItemTable() {
        // Product ID
        MFXTableColumn<Item> prodIDCol = new MFXTableColumn<>("Product ID", true, Comparator.comparing(item -> item.getProduct() == null ? "" : item.getProduct().getProdID()));
        //Part No.
        MFXTableColumn<Item> partNoCol = new MFXTableColumn<>("Part No.", true, Comparator.comparing(item -> item.getProduct() == null ? "" : item.getProduct().getPartNo()));
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

        // Product ID
        prodIDCol.setRowCellFactory(i -> new MFXTableRowCell<>(item -> item.getProduct() == null ? "" : item.getProduct().getProdID()));
        //Part No.
        partNoCol.setRowCellFactory(i -> new MFXTableRowCell<>(item -> item.getProduct() == null ? "" : item.getProduct().getPartNo()));
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

        ((MFXTableView<Item>) tblVw).getTableColumns().clear();
        ((MFXTableView<Item>) tblVw).getTableColumns().addAll(
                prodIDCol,
                partNoCol,
                remarksCol,
                qtyCol,
                unitPriceCol,
                exclCol,
                discCol,
                inclCol
        );

        ((MFXTableView<Item>) tblVw).getFilters().clear();

        ((MFXTableView<Item>) tblVw).getFilters().addAll(
                new StringFilter<>("Product ID", item -> item.getProduct() == null ? "" : item.getProduct().getProdID()),
                new StringFilter<>("Part No.", item -> item.getProduct() == null ? "" : item.getProduct().getPartNo()),
                new StringFilter<>("Remark", item -> item.getRemark()),
                new IntegerFilter<>("Quantity", item -> item.getQty()),
                new DoubleFilter<>("Unit Price", item -> item.getUnitPrice().doubleValue()),
                new DoubleFilter<>("Excl. Amount", item -> item.getExclTaxAmt().doubleValue()),
                new DoubleFilter<>("Discount Amount", item -> item.getDiscAmt().doubleValue()),
                new DoubleFilter<>("Incl. Amount", item -> item.getInclTaxAmt().doubleValue())
        );

        tempItems.addAll(items);

        List<Item> tempTempItems = new ArrayList<>();
        for (Item item : tempItems) {
            Item clonedItem = item.clone();
            clonedItem.setQty(0);
            for (Item i : tempItems) {
                if (i.getDlvrDate().equals(clonedItem.getDlvrDate())
                        && i.getProduct().getProdID().equals(clonedItem.getProduct().getProdID())) {
                    clonedItem.setQty(clonedItem.getQty() + i.getQty());
                }
            }
            clonedItem.setOriQty(clonedItem.getQty());
            tempTempItems.add(clonedItem);
        }

        ((MFXTableView<Item>) tblVw).getItems().clear();
        ((MFXTableView<Item>) tblVw).setItems(FXCollections.observableArrayList(tempTempItems));
        tempItems.clear();

        ((MFXTableView<Item>) tblVw).getSelectionModel().selectionProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {

                if (((MFXTableView<Item>) tblVw).getSelectionModel().getSelectedValues().size() != 0) {
                    Item item = (((MFXTableView<Item>) tblVw).getSelectionModel().getSelectedValues().get(0));
                    rowSelected.add(item.getProduct().getProdID());

                    if (rowSelected.size() == 2) {
                        if (rowSelected.get(0).equals(rowSelected.get(1))) {
                            // action here
                            Parent root;
                            try {
                                root = FXMLLoader.load(getClass().getClassLoader().getResource("View/InvoiceItemSelect_UI.fxml"));
                                Stage stage = new Stage();
                                stage.initModality(Modality.WINDOW_MODAL);
                                stage.initOwner(btnBack.getScene().getWindow());
                                stage.setScene(new Scene(root));

                                BasicObjs passObj = new BasicObjs();
                                passObj.setObj(item);
                                passObj.setObjs((List<Object>) (Object) itemsNotYetBill);
                                passObj.setCrud(BasicObjs.read);

                                stage.setUserData(passObj);
                                stage.showAndWait();

                                // if have any change on the selected item
                                if (stage.getUserData() != null) {

                                    BasicObjs receiveObj = (BasicObjs) stage.getUserData();
                                    Item catchedItem = new Item();
                                    catchedItem = ((Item) receiveObj.getObj()).clone();

                                    adjustItemsNotYetBill(catchedItem, item);

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

    private void adjustItemsNotYetBill(Item catchedItem, Item item) {
        if (catchedItem.getProduct() == null) {//remove
            Item itemInSO = itemsNotYetBill.get(itemsNotYetBill.indexOf(item));
            Item itemInTO = (Item) items.get(items.indexOf(item));

            itemInSO.setQty(itemInSO.getQty() + itemInTO.getQty());
            items.remove(item);
        } else if (!items.contains(catchedItem)) { //add
            items.add(catchedItem);

            Item itemInSO = itemsNotYetBill.get(itemsNotYetBill.indexOf(catchedItem));
            Item itemInTO = (Item) items.get(items.indexOf(catchedItem));

            itemInTO.setOriQty(0);
            itemInSO.setQty(itemInSO.getQty() - itemInTO.getQty() + itemInTO.getOriQty());

        } else { // update
            //remove
            Item itemInSO = itemsNotYetBill.get(itemsNotYetBill.indexOf(catchedItem));
            Item itemInTO = (Item) items.get(items.indexOf(catchedItem));

            itemInSO.setQty(itemInSO.getQty() + itemInTO.getQty());
            items.remove(catchedItem);

            //add
            items.add(catchedItem);

            itemInSO = itemsNotYetBill.get(itemsNotYetBill.indexOf(catchedItem));
            itemInTO = (Item) items.get(items.indexOf(catchedItem));

            itemInTO.setOriQty(0);
            itemInSO.setQty(itemInSO.getQty() - itemInTO.getQty() + itemInTO.getOriQty());
        }
        setupItemTable();

        calculateTotalInformation(items);
    }

    private void defaultValFillIn() {
        this.dtRefDate.setValue(LocalDate.now());
        this.cmbStatus.setText(AccountingRules.InvoiceStatus.NEW.toString());
    }

    private void fieldFillIn() throws IOException {
        clearAllFieldsValue();
        defaultValFillIn();

        if (passObj.getObj() != null) {
            if (passObj.getObj() instanceof Invoice) {

                Invoice invoice = (Invoice) passObj.getObj();

                this.txtInvoiceID.setText(invoice.getCode());
                this.dtRefDate.setValue(invoice.getCreatedDate() == null ? null : Instant.ofEpochMilli(invoice.getCreatedDate().getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate());
                this.txtSORef.setText(invoice.getSO() == null ? "" : invoice.getSO().getCode());
                this.txtRefType.setText(invoice.getReferenceType());
                this.txtRef.setText(invoice.getReference());
                this.cmbStatus.setText(invoice.getStatus());

                this.txtGross.setText(invoice.getGross().toString());
                this.txtDiscount.setText(invoice.getDiscount().toString());
                this.txtSubTtl.setText(invoice.getSubTotal().toString());
                this.txtTtlPayable.setText(invoice.getTtlPayable().toString());

                this.txtIssuedBy.setText(invoice.getIssuedBy() == null ? "" : invoice.getIssuedBy().getStaffID());
                this.txtReleasedAVerrifiedBy.setText(invoice.getReleasedAVerifiedBy() == null ? "" : invoice.getReleasedAVerifiedBy().getStaffID());
                this.txtCustSignature.setText(invoice.getCustomerSignature() == null ? "" : invoice.getCustomerSignature().getCollectAddrID());

                this.imgDocs.setImage(ImageUtils.byteToImg(ImageUtils.encodedStrToByte(((String) ImageUtils.splitImgStr(invoice.getSignedDocPic()).getFirst()))));

            } else if (passObj.getObj() instanceof SalesOrder) {
                SalesOrder so = (SalesOrder) passObj.getObj();
                this.txtRef.setText(so.getCode());
            }
        }
    }

    private void isViewMode(boolean disable) {
        if (disable == true) {
            this.btnSave.setText("Update");
        } else {
            this.btnSave.setText("Save");
        }

        this.txtInvoiceID.setDisable(disable);
        this.dtRefDate.setDisable(disable);
        this.txtSORef.setDisable(disable);
        this.txtRefType.setDisable(disable);
        this.txtRef.setDisable(disable);
        this.cmbStatus.setDisable(disable);
        this.txtGross.setDisable(disable);
        this.txtDiscount.setDisable(disable);
        this.txtSubTtl.setDisable(disable);
        this.txtTtlPayable.setDisable(disable);

        this.ctnSORefSelection.setDisable(disable);

        this.txtIssuedBy.setDisable(disable);
        this.txtReleasedAVerrifiedBy.setDisable(disable);
        this.txtCustSignature.setDisable(disable);

        this.ctnIssuedBySelection.setDisable(disable);
        this.ctnReleasedAVerifiedBySelection.setDisable(disable);
        this.ctnCustSignatureSelection.setDisable(disable);

        this.btnAdd.setDisable(disable);
        this.tblVw.setDisable(disable);
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
                passObj.getFxmlPaths().addLast("View/Invoice_UI.fxml");
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
        Check validatorCheck = (new Validator()).createCheck();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.US);

        /*
        No need include:
        1.
         */
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Reference Date", this.dtRefDate.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Reference Date");
                    textVal = textVal.trim();

                    /*
                     1. cannot be null
                     */
                    if (textVal.isEmpty()) {
                        c.error("Reference Date - Required Field");
                        return;
                    }

                    return;
                })
                .decorates(this.dtRefDate);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Sales Order Reference", this.txtSORef.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Sales Order Reference");
                    textVal = textVal.trim();

                    /*
                     1. cannot be null
                     */
                    if (textVal.isEmpty()) {
                        c.error("Sales Order Reference - Required Field");
                        return;
                    }

                    return;
                })
                .decorates(this.txtSORef);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Status", this.cmbStatus.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Status");
                    textVal = textVal.trim();

                    /*
                     1. cannot be null
                     */
                    if (textVal.isEmpty()) {
                        c.error("Status - Required Field");
                        return;
                    }
                })
                .decorates(this.cmbStatus);

        validator.add(validatorCheck);
        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .withMethod(c -> {

                    if (items.size() <= 0) {
                        c.error("Item - At least one item are required to build a Return Delivery Note");
                        return;
                    }

                })
                .decorates(this.tblVw);

        validator.add(validatorCheck);
        //=====================================
    }

    @Override
    public boolean clearAllFieldsValue() {
        //this.txtInvoiceID.clear();
        this.dtRefDate.clear();
        this.txtSORef.clear();
        this.txtRefType.clear();
        this.txtRef.clear();
        this.cmbStatus.clear();
        this.txtGross.clear();
        this.txtDiscount.clear();
        this.txtSubTtl.clear();
        this.txtTtlPayable.clear();

        this.txtIssuedBy.clear();
        this.txtReleasedAVerrifiedBy.clear();
        this.txtCustSignature.clear();

        this.lblImgStrs.setText("");
        this.imgDocs.setImage(null);

        items.clear();
        itemsNotYetBill.clear();

        return true;
    }

    @Override
    public ButtonType alertDialog(Alert.AlertType alertType, String title,
            String headerTxt, String contentTxt
    ) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerTxt);
        alert.setContentText(contentTxt);

        alert.showAndWait();
        return alert.getResult();
    }

    private Invoice prepareInvoiceToObj() {
        Invoice invoice = new Invoice();
        invoice.setCode(this.txtInvoiceID.getText());
        invoice.setCreatedDate(this.dtRefDate.getValue() == null ? null : Timestamp.valueOf(this.dtRefDate.getValue().atStartOfDay()));

        SalesOrder so = new SalesOrder();
        so.setCode(this.txtSORef.getText());
        invoice.setSO(so);

        invoice.setReferenceType(this.txtRefType.getText());
        invoice.setReference(this.txtRef.getText());
        invoice.setStatus(this.cmbStatus.getText());

        invoice.setGross(new BigDecimal(this.txtGross.getText()));
        invoice.setSubTotal(new BigDecimal(this.txtSubTtl.getText()));
        invoice.setSubTotal(new BigDecimal(this.txtSubTtl.getText()));
        invoice.setTtlPayable(new BigDecimal(this.txtTtlPayable.getText()));

        Staff issuedBy = new Staff();
        issuedBy.setStaffID(this.txtIssuedBy.getText());
        invoice.setIssuedBy(issuedBy);

        Staff releasedAVerifiedBy = new Staff();
        releasedAVerifiedBy.setStaffID(this.txtReleasedAVerrifiedBy.getText());
        invoice.setReleasedAVerifiedBy(releasedAVerifiedBy);

        CollectAddress customerSignature = new CollectAddress();
        customerSignature.setCollectAddrID(this.txtCustSignature.getText());
        invoice.setCustomerSignature(customerSignature);

        invoice.setSignedDocPic(this.lblImgStrs.getText());

        invoice.setItems(items);
        return invoice;
    }

    @FXML
    private void openSOSelection(MouseEvent event) {
        if (event.isPrimaryButtonDown() == true) {

            ButtonType alertBtnClicked = alertDialog(Alert.AlertType.CONFIRMATION,
                    "Confirmation",
                    "Modified Data Loss Alert",
                    "All modified data will be overwrite with selected Sales Order Information. Please select OK to proceed.");

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
                passObj.setObj(new SalesOrder());

                stage.setUserData(passObj);
                stage.showAndWait();

                if (stage.getUserData() != null) {

                    BasicObjs receiveObj = (BasicObjs) stage.getUserData();

                    SalesOrder so = (SalesOrder) receiveObj.getObj();

                    if (!so.getStatus().equals(SalesRules.SOStatus.COMPLETED)
                            && !so.getStatus().equals(SalesRules.SOStatus.ON_HOLD)) {
                        this.passObj.setObj(so);
                        //this.passObj.setObj(SalesOrderService.getSOByID(so.getCode()));
                        fieldFillIn();
                    } else {
                        alertDialog(Alert.AlertType.INFORMATION,
                                "Information",
                                "Document Blocked Message",
                                "Sales Order with COMPLETED/ ON HOLD status are not allowed to become any document reference.");
                    }

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
    private void openReleasedAVerifiedbySelection(MouseEvent event) {
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
                    this.txtReleasedAVerrifiedBy.setText(((Staff) receiveObj.getObj()).getStaffID());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @FXML
    private void openCustSignatureSelection(MouseEvent event) {
        if (event.isPrimaryButtonDown() == true) {

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
                if (this.passObj.getObj() instanceof Invoice) {
                    cllctAddr.getCustomer().setCustID(((Invoice) this.passObj.getObj()).getSO().getBillToCust().getCustID());
                } else if (this.passObj.getObj() instanceof SalesOrder) {
                    cllctAddr.getCustomer().setCustID(((SalesOrder) this.passObj.getObj()).getBillToCust().getCustID());
                }
                passObj.setObj(cllctAddr);

                stage.setUserData(passObj);
                stage.showAndWait();

                if (stage.getUserData() != null) {
                    BasicObjs receiveObj = (BasicObjs) stage.getUserData();
                    this.txtCustSignature.setText(((CollectAddress) receiveObj.getObj()).getCollectAddrID());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @FXML
    private void goImgViewer(MouseEvent event) {
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
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("View/InnerEntitySelectWithItemsProvided_UI.fxml"));

            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(btnBack.getScene().getWindow());
            stage.setScene(new Scene(root));

            BasicObjs passObj = new BasicObjs();
            passObj.setCrud(BasicObjs.create);
            passObj.setObj(new Invoice());
            passObj.setObjs((List<Object>) (Object) itemsNotYetBill);

            stage.setUserData(passObj);
            stage.showAndWait();

            // if have any change on the selected item
            if (stage.getUserData() != null) {

                BasicObjs receiveObj = (BasicObjs) stage.getUserData();
                Item catchedItem = ((Item) receiveObj.getObj()).clone();

                adjustItemsNotYetBill(catchedItem, null);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void saveInvoice(MouseEvent event) throws SQLException {
        if (event.isPrimaryButtonDown() == true) {

            if (!this.btnSave.getText().equals("Save")) {
                isViewMode(false);
                return;
            }

            if (!validator.validate()) {

                return;
            }

            invoiceInDraft = this.prepareInvoiceToObj();

            if (this.txtInvoiceID.getText().isEmpty()) {
                invoiceInDraft.setCode(InvoiceService.saveNewInvoice(invoiceInDraft));
            } else {
                InvoiceService.updateInvoice(invoiceInDraft);
            }

            ItemService.updateItemsByDoc(invoiceInDraft.getItems(), invoiceInDraft.getCode());

            updateSOStatus();

            switchScene(passObj.getFxmlPaths().getLast().toString(), passObj, BasicObjs.back);

        }
    }

    private void updateSOStatus() {
        if (!this.txtSORef.getText().isEmpty()) {

            invoiceInDraft.getSO().setStatus(SalesRules.SOStatus.IN_PROGRESS.toString());
            SalesOrderService.updateSalesOrderStatus(invoiceInDraft.getSO());
        }

    }

    private void calculateTotalInformation(List<Item> items) {
        Invoice invoice = new Invoice();

        invoice.setGross(BigDecimal.ZERO);
        invoice.setDiscount(BigDecimal.ZERO);

        for (Item item : items) {
            invoice.setGross(invoice.getGross().add(item.getExclTaxAmt()));
            invoice.setDiscount(invoice.getDiscount().add(item.getDiscAmt()));
        }

        invoice.setTtlPayable(invoice.getSubTotal().multiply(new BigDecimal(1 + (accRules.getTaxRate() / 100))));

        this.txtGross.setText(invoice.getGross().toString());
        this.txtDiscount.setText(invoice.getDiscount().toString());
        this.txtSubTtl.setText(invoice.getSubTotal().toString());
        this.txtTtlPayable.setText(invoice.getTtlPayable().toString());
    }

}
