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
import Entity.Inventory;
import Entity.Item;
import Entity.PaymentTerm;
import Entity.Quotation;
import Entity.SalesOrder;
import Entity.ShipmentTerm;
import Entity.Staff;
import PassObjs.BasicObjs;
import Service.GeneralRulesService;
import Service.InventoryService;
import Service.ItemService;
import Service.QuotationService;
import Service.SalesOrderService;
import Utils.DateFilter;
import Utils.ImageUtils;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCircleToggleNode;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXDateCell;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.DoubleFilter;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.synedra.validatorfx.Check;
import net.synedra.validatorfx.Validator;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * FXML Controller class
 *
 * @author Tee Zhuo Xuan
 */
public class SalesOrderCONTR implements Initializable, BasicCONTRFunc {

    //<editor-fold defaultstate="collapsed" desc="fields">
    @FXML
    private MFXCircleToggleNode btnBack;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private MFXCircleToggleNode ctnBillToSelection;
    @FXML
    private MFXTextField txtBillTo;
    @FXML
    private MFXTextField txtSOID;
    @FXML
    private MFXTextField txtDeliverTo;
    @FXML
    private MFXDatePicker dtRefDate;
    @FXML
    private MFXTextField txtCustPORef;
    @FXML
    private MFXTextField txtQuotRef;
    @FXML
    private MFXTextField txtRef;
    @FXML
    private MFXTextField txtRefType;
    @FXML
    private MFXComboBox<?> cmbCurrencyCode;
    @FXML
    private MFXCircleToggleNode ctnDeliverToSelection;
    @FXML
    private MFXTextField txtSalesPerson;
    @FXML
    private MFXCircleToggleNode ctnSalesPersonSelection;
    @FXML
    private MFXComboBox<?> cmbStatus;
    @FXML
    private MFXDatePicker dtReqDlvrDate;
    @FXML
    private MFXTableView<?> tblVw;
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
    private MFXTextField txtReleeaseAVerifiedBy;
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
    private MFXButton btnAdd;
    @FXML
    private MFXCircleToggleNode ctnQuotRefSelection;
    @FXML
    private Label lblImgStrs;
    @FXML
    private MFXTextField txtPymtTerm;
    @FXML
    private MFXCircleToggleNode ctnPymtTermSelection;
    @FXML
    private MFXTextField txtShipmentTerm;
    @FXML
    private MFXCircleToggleNode ctnShipmentTermSelection;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="util declarations">
    private BasicObjs passObj;

    private Validator validator = new Validator();

    AccountingRules accRules = new AccountingRules();

    WarehouseRules warehouseRules = new WarehouseRules();

    SalesRules salesRules = new SalesRules();

    private List<Item> oriItems = new ArrayList<>(); // use to know which Item been update, and perform update action for those modified address

    private List<Item> items = new ArrayList<>(); // use to know which Item been update, and perform update action for those modified address

    private List<Item> tempItems = new ArrayList<>(); // use to know which Item been update, and perform update action for those modified address

    private static List<String> rowSelected = new ArrayList<>();

    private SalesOrder soInDraft;
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
                initializeUIControls();
                inputValidation();
                receiveData();
                privilegeDetect();
                autoClose();
                if (passObj.getCrud().equals(BasicObjs.create)) {
                    defaultValFillIn();
                }

                if (passObj.getCrud().equals(BasicObjs.read) || passObj.getCrud().equals(BasicObjs.update)) {
                    try {
                        fieldFillIn();
                    } catch (IOException ex) {
                        Logger.getLogger(QuotationCONTR.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    // haven't verify the same product ID, must have different delivery date
                    if (passObj.getObj() instanceof SalesOrder) {
                        items.addAll(ItemService.getItemBySOID(((SalesOrder) passObj.getObj()).getCode()));
                    } else if (passObj.getObj() instanceof Quotation) {
                        items.addAll(ItemService.getItemByQuotID(((Quotation) passObj.getObj()).getCode()));
                    }

                    oriItems = items
                            .stream()
                            .map(e -> (Item) e)
                            .collect(Collectors.toList());
                }

                if (passObj.getCrud().equals(BasicObjs.read)) {
                    isViewMode(true);
                }

                setupItemTable();

            }
        });
    }

    private void initializeUIControls() {
        this.dtReqDlvrDate.setCellFactory(new Function<>() {
            @Override
            public MFXDateCell apply(LocalDate t) {
                return new MFXDateCell(dtReqDlvrDate, t) {
                    @Override
                    public void updateItem(LocalDate item) {
                        super.updateItem(item);

                        if (item.isAfter(LocalDate.now())) {
                            setDisable(false);
                        } else {
                            setDisable(true);
                        }
                    }
                };

            }
        });
    }

    public void autoClose() {
        Duration delay1 = Duration.seconds(GeneralRulesService.getSessionTimeOut());
        PauseTransition transitionAlert = new PauseTransition(delay1);
        this.passObj.setLoginStaff(new Staff());
        transitionAlert.setOnFinished(evt -> switchScene("View/Login_UI.fxml", passObj, BasicObjs.back));
        transitionAlert.setCycleCount(1);

        this.btnBack.getScene().addEventFilter(InputEvent.ANY, evt -> transitionAlert.playFromStart());
    }

    private void initializeComboSelections() {
        ((MFXComboBox<String>) this.cmbCurrencyCode).setItems(FXCollections.observableList(accRules.getCurrencyCodes()));
        ((MFXComboBox<SalesRules.SOStatus>) this.cmbStatus).setItems(FXCollections.observableList(salesRules.getSOStatuses()));
    }

    private void setupItemTable() {
        // Product ID
        MFXTableColumn<Item> prodIDCol = new MFXTableColumn<>("Product ID", true, Comparator.comparing(item -> item.getProduct() == null ? "" : item.getProduct().getProdID()));
        // Inventory ID
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
        // Inventory ID
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
                exclCol,
                discCol,
                inclCol,
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
                                    catchedItem = ((Item) receiveObj.getObj());

                                    if (catchedItem.getProduct() == null) { // remove
                                        items.remove(item);
                                    } else {

                                        if (isDemandOverReadyStock(catchedItem) == false) {
                                            if (!items.contains(catchedItem)) {
                                                items.remove(item);
                                                items.add(catchedItem);
                                            } else {
                                                items.set(items.indexOf(item), catchedItem);
                                            }
                                        }
                                    }

                                    setupItemTable();

                                    calculateTotalInformation(items);

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

    private void defaultValFillIn() {
        this.dtRefDate.setValue(LocalDate.now());
        this.txtGross.setText("0.00");
        this.txtSubTtl.setText("0.00");
        this.txtDiscount.setText("0.00");
        this.txtNett.setText("0.00");
        this.txtSalesPerson.setText(passObj.getLoginStaff().getStaffID());
        this.cmbStatus.setText(SalesRules.SOStatus.NEW.toString());
    }

    private void fieldFillIn() throws IOException {
        clearAllFieldsValue();
        defaultValFillIn();

        if (passObj.getObj() != null) {
            if (passObj.getObj() instanceof SalesOrder) {
                SalesOrder so = (SalesOrder) passObj.getObj();

                this.txtSOID.setText(so.getCode());
                this.txtSalesPerson.setText(so.getSalesPerson() == null ? "" : so.getSalesPerson().getStaffID());
                this.txtBillTo.setText(so.getBillToCust() == null ? "" : so.getBillToCust().getCustID());
                this.txtDeliverTo.setText(so.getDeliverToCust() == null ? "" : so.getDeliverToCust().getCollectAddrID());
                this.dtRefDate.setValue(so.getCreatedDate() == null ? null : Instant.ofEpochMilli(so.getCreatedDate().getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate());
                this.txtQuotRef.setText(so.getQuotRef() == null ? "" : so.getQuotRef().getCode());
                this.txtCustPORef.setText(so.getCustPOReference());
                this.txtRefType.setText(so.getReferenceType());
                this.txtRef.setText(so.getReference());
                this.dtReqDlvrDate.setValue(so.getRequiredDeliveryDate() == null ? null : Instant.ofEpochMilli(so.getRequiredDeliveryDate().getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate());
                this.cmbCurrencyCode.setText(so.getCurrencyCode());
                this.txtPymtTerm.setText(so.getPymtTerm().getPymtTermID());
                this.txtShipmentTerm.setText(so.getShipmentTerm().getShipmentTermID());
                this.cmbStatus.setText(so.getStatus());

                this.txtGross.setText(so.getGross().toString());
                this.txtDiscount.setText(so.getDiscount().toString());
                this.txtSubTtl.setText(so.getSubTotal().toString());
                this.txtNett.setText(so.getNett().toString());

                this.txtIssuedBy.setText(so.getIssuedBy() == null ? "" : so.getIssuedBy().getStaffID());
                this.txtReleeaseAVerifiedBy.setText(so.getReleasedAVerifiedBy() == null ? "" : so.getReleasedAVerifiedBy().getStaffID());
                this.txtCustSignature.setText(so.getCustomerSignature() == null ? "" : so.getCustomerSignature().getCollectAddrID());

                this.lblImgStrs.setText(so.getSignedDocPic());
                this.imgDocs.setImage(ImageUtils.byteToImg(ImageUtils.encodedStrToByte(((String) ImageUtils.splitImgStr(so.getSignedDocPic().isEmpty() == true ? null : so.getSignedDocPic()).getFirst()))));

            } else if (passObj.getObj() instanceof Quotation) {
                Quotation quotation = (Quotation) passObj.getObj();

                this.txtSalesPerson.setText(quotation.getSalesPerson() == null ? "" : quotation.getSalesPerson().getStaffID());
                this.txtBillTo.setText(quotation.getBillToCust() == null ? "" : quotation.getBillToCust().getCustID());
                this.txtDeliverTo.setText(quotation.getDeliverToCust() == null ? "" : quotation.getDeliverToCust().getCollectAddrID());
                this.txtQuotRef.setText(quotation.getCode());
                this.txtRefType.setText(quotation.getReferenceType());
                this.txtRef.setText(quotation.getReference());
                this.dtReqDlvrDate.setValue(quotation.getRequiredDeliveryDate() == null ? null : Instant.ofEpochMilli(quotation.getRequiredDeliveryDate().getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate());
                this.cmbCurrencyCode.setText(quotation.getCurrencyCode());

                this.txtPymtTerm.setText(quotation.getPymtTerm().getPymtTermID());

                this.txtShipmentTerm.setText(quotation.getShipmentTerm().getShipmentTermID());

                this.txtGross.setText(quotation.getGross().toString());
                this.txtDiscount.setText(quotation.getDiscount().toString());
                this.txtSubTtl.setText(quotation.getSubTotal().toString());
                this.txtNett.setText(quotation.getNett().toString());

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
        this.txtQuotRef.setDisable(disable);
        this.txtCustPORef.setDisable(disable);
        this.txtRefType.setDisable(disable);
        this.txtRef.setDisable(disable);
        this.dtReqDlvrDate.setDisable(disable);
        this.cmbCurrencyCode.setDisable(disable);
        this.txtPymtTerm.setDisable(disable);
        this.txtShipmentTerm.setDisable(disable);
        this.cmbStatus.setDisable(disable);
        this.txtGross.setDisable(disable);
        this.txtDiscount.setDisable(disable);
        this.txtSubTtl.setDisable(disable);
        this.txtNett.setDisable(disable);
        this.txtIssuedBy.setDisable(disable);
        this.txtReleeaseAVerifiedBy.setDisable(disable);
        this.txtCustSignature.setDisable(disable);

        this.ctnBillToSelection.setDisable(disable);
        this.ctnDeliverToSelection.setDisable(disable);
        this.ctnSalesPersonSelection.setDisable(disable);
        this.ctnQuotRefSelection.setDisable(disable);
        this.ctnIssuedBySelection.setDisable(disable);
        this.ctnReleasedAVerifiedBySelection.setDisable(disable);
        this.ctnCustSignatureSelection.setDisable(disable);
        this.ctnPymtTermSelection.setDisable(disable);
        this.ctnShipmentTermSelection.setDisable(disable);

        this.tblVw.setDisable(disable);
        this.btnAdd.setDisable(disable);
        this.imgDocs.setDisable(disable);

        privilegeDetect();
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
                passObj.getFxmlPaths().addLast("View/SalesOrder_UI.fxml");
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
                .dependsOn("SalesPerson", this.txtSalesPerson.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("SalesPerson");
                    textVal = textVal.trim();

                    /*
                     1. cannot be null
                     */
                    if (textVal.isEmpty()) {
                        c.error("SalesPerson - Requried Field");
                        return;
                    }

                })
                .decorates(this.txtSalesPerson);

        validator.add(validatorCheck);
        //=====================================
        //=====================================

        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Bill To", this.txtBillTo.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Bill To");
                    textVal = textVal.trim();

                    /*
                     1. cannot be null
                     */
                    if (textVal.isEmpty()) {
                        c.error("Bill To - Required Field");
                        return;
                    }

                })
                .decorates(this.txtBillTo);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Deliver To", this.txtDeliverTo.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Deliver To");
                    textVal = textVal.trim();

                    /*
                     1. cannot be null
                     */
                    if (textVal.isEmpty()) {
                        c.error("Deliver To - Required Field");
                        return;
                    }

                })
                .decorates(this.txtDeliverTo);

        validator.add(validatorCheck);

        //=====================================
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
                .dependsOn("Required Delivery Date", this.dtReqDlvrDate.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Required Delivery Date");
                    textVal = textVal.trim();

                    /*
                     1. cannot be null
                     2. cannot early than reference date
                     */
                    if (textVal.isEmpty()) {
                        c.error("Required Delivery Date - Required Field");
                        return;
                    }

                    LocalDate date = LocalDate.parse(textVal, formatter);

                    if (date.isBefore(LocalDate.now())) {
                        c.error("Required Delivery Date - Cannot be before the current date");
                        return;
                    }
                })
                .decorates(this.dtReqDlvrDate);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Currency Code", this.cmbCurrencyCode.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Currency Code");
                    textVal = textVal.trim();

                    /*
                     1. cannot be null
                     */
                    if (textVal.isEmpty()) {
                        c.error("Currency Code - Required Field");
                        return;
                    }

                    return;
                })
                .decorates(this.cmbCurrencyCode);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Payment Term", this.txtPymtTerm.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Payment Term");
                    textVal = textVal.trim();

                    /*
                     1. cannot be null
                     */
                    if (textVal.isEmpty()) {
                        c.error("Payment Term - Required Field");
                        return;
                    }

                })
                .decorates(this.txtPymtTerm);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Shipment Term", this.txtShipmentTerm.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Shipment Term");
                    textVal = textVal.trim();

                    /*
                     1. cannot be null
                     */
                    if (textVal.isEmpty()) {
                        c.error("Shipment Term - Required Field");
                        return;
                    }

                })
                .decorates(this.txtShipmentTerm);

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
                .dependsOn("Sub Total", this.txtSubTtl.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Sub Total");
                    textVal = textVal.trim();
                    /*
                     1.
                     */
                    // allow null
                    if (textVal.isEmpty()) {
                        c.error("Sub Total - Required Field");
                        return;
                    }

                    BigDecimal subTtl = new BigDecimal(textVal);

                    if (subTtl.compareTo(salesRules.getMaxOrderAmtperSO()) == 1 && items.size() != 1) {
                        c.error("Every Sales Order’s total amount must not exceed RM 10,000 after discount and before including tax.\n"
                                + "This rule is ignored if the Sales Order has only one single item and exceeds the defined limit.");
                        return;
                    }

                    if (subTtl.compareTo(salesRules.getMaxOrderAmtperSO()) == 1 && items.size() == 1) {
                        if (items.get(0).getOriQty() != 1) {
                            c.error("Every Sales Order’s total amount must not exceed RM 10,000 after discount and before including tax.\n"
                                    + "This rule is ignored if the Sales Order has only one single item and exceeds the defined limit.");
                            return;
                        }
                    }

                })
                .decorates(this.txtSubTtl);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .withMethod(c -> {

                    if (items.size() <= 0) {
                        c.error("Item - At least one item are required to build a quotation");
                        return;
                    }

                })
                .decorates(this.tblVw);

        validator.add(validatorCheck);
        //=====================================
    }

    @Override
    public boolean clearAllFieldsValue() {
        //this.txtSOID.clear();
        this.txtSalesPerson.clear();
        this.txtBillTo.clear();
        this.txtDeliverTo.clear();
        this.dtRefDate.clear();
        this.txtQuotRef.clear();
        this.txtCustPORef.clear();
        this.txtRefType.clear();
        this.txtRef.clear();
        this.dtReqDlvrDate.clear();
        this.cmbCurrencyCode.clear();
        this.txtPymtTerm.clear();
        this.txtShipmentTerm.clear();
        this.cmbStatus.clear();

        this.txtGross.clear();
        this.txtDiscount.clear();
        this.txtSubTtl.clear();
        this.txtNett.clear();

        this.txtIssuedBy.clear();
        this.txtReleeaseAVerifiedBy.clear();
        this.txtCustSignature.clear();

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

    private SalesOrder prepareSalesOrderToObj() {
        SalesOrder so = new SalesOrder();
        so.setCode(this.txtSOID.getText());

        Staff salesPerson = new Staff();
        salesPerson.setStaffID(this.txtSalesPerson.getText());
        so.setSalesPerson(salesPerson);

        Customer billTo = new Customer();
        billTo.setCustID(this.txtBillTo.getText());
        so.setBillToCust(billTo);

        CollectAddress deliverTo = new CollectAddress();
        deliverTo.setCollectAddrID(this.txtDeliverTo.getText());
        so.setDeliverToCust(deliverTo);

        so.setCreatedDate(this.dtRefDate.getValue() == null ? null : Timestamp.valueOf(this.dtRefDate.getValue().atStartOfDay()));

        Quotation quotation = new Quotation();
        if (isBlank(this.txtQuotRef.getText())) {
            quotation.setCode(null);

        } else {
            quotation.setCode(this.txtQuotRef.getText());

        }
        so.setQuotRef(quotation);

        so.setCustPOReference(this.txtCustPORef.getText());
        so.setReferenceType(this.txtRefType.getText());
        so.setReference(this.txtRef.getText());
        so.setRequiredDeliveryDate(this.dtReqDlvrDate.getValue() == null ? null : java.sql.Date.valueOf(this.dtReqDlvrDate.getValue()));
        so.setCurrencyCode(this.cmbCurrencyCode.getText());
        so.setPymtTerm(new PaymentTerm());
        so.getPymtTerm().setPymtTermID(this.txtPymtTerm.getText());
        so.setShipmentTerm(new ShipmentTerm());
        so.getShipmentTerm().setShipmentTermID(this.txtShipmentTerm.getText());
        so.setStatus(this.cmbStatus.getText());

        so.setGross(new BigDecimal(this.txtGross.getText()));
        so.setDiscount(new BigDecimal(this.txtDiscount.getText()));
        so.setSubTotal(new BigDecimal(this.txtSubTtl.getText()));
        so.setNett(new BigDecimal(this.txtNett.getText()));

        Staff issuedBy = new Staff();
        if (isBlank(this.txtIssuedBy.getText())) {
            issuedBy.setStaffID(null);
        } else {
            issuedBy.setStaffID(this.txtIssuedBy.getText());
        }
        so.setIssuedBy(issuedBy);

        Staff releasedAVerifiedBy = new Staff();
        if (isBlank(this.txtReleeaseAVerifiedBy.getText())) {
            releasedAVerifiedBy.setStaffID(null);
        } else {
            releasedAVerifiedBy.setStaffID(this.txtReleeaseAVerifiedBy.getText());
        }
        so.setReleasedAVerifiedBy(releasedAVerifiedBy);

        CollectAddress customerSignature = new CollectAddress();
        if (isBlank(this.txtCustSignature.getText())) {
            customerSignature.setCollectAddrID(null);
        } else {
            customerSignature.setCollectAddrID(this.txtCustSignature.getText());
        }
        so.setCustomerSignature(customerSignature);

        so.setSignedDocPic(this.lblImgStrs.getText());

        so.setItems(items);
        return so;
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
    private void openQuotRefSelection(MouseEvent event) throws SQLException {
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
                passObj.setObj(new Quotation());

                stage.setUserData(passObj);
                stage.showAndWait();

                if (stage.getUserData() != null) {

                    BasicObjs receiveObj = (BasicObjs) stage.getUserData();
                    Quotation quotation = (Quotation) receiveObj.getObj();
                    if (quotation.getQuotValidityDate().after(new Date(Calendar.getInstance().getTimeInMillis()))
                            && quotation.getStatus().equals(SalesRules.QuotStatus.NEW.toString())) {
                        this.passObj.setObj(quotation);
                        defaultValFillIn();
                        fieldFillIn();
                        items.clear();
                        items.addAll(ItemService.getItemsByCIID(quotation.getCode()));
                        setupItemTable();
                        calculateTotalInformation(items);

                        //
                    } else {
                        alertDialog(Alert.AlertType.INFORMATION,
                                "Information",
                                "Document Blocked Message",
                                "Quotation without NEW status or exceed quotation validity date are not allowed to become any document reference.");
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
                    this.txtReleeaseAVerifiedBy.setText(((Staff) receiveObj.getObj()).getStaffID());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @FXML
    private void openCustSignSelection(MouseEvent event) {
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
    private void addproductItem(MouseEvent event) {
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

                catchedItem = ((Item) receiveObj.getObj());

                if (isDemandOverReadyStock(catchedItem) == false) {
                    if (!items.contains(catchedItem)) {
                        items.add(catchedItem);
                    } else {
                        items.set(items.indexOf(catchedItem), catchedItem);
                    }
                }

                setupItemTable();
                calculateTotalInformation(items);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void calculateTotalInformation(List<Item> items) {

        SalesOrder salesOrder = new SalesOrder();

        salesOrder.setGross(BigDecimal.ZERO);
        salesOrder.setDiscount(BigDecimal.ZERO);

        for (Item item : items) {
            salesOrder.setGross(salesOrder.getGross().add(item.getExclTaxAmt()));
            salesOrder.setDiscount(salesOrder.getDiscount().add(item.getDiscAmt()));
        }

        if (salesOrder.getSubTotal().equals(BigDecimal.ZERO)) {
            salesOrder.setNett(BigDecimal.ZERO);
        } else {
            salesOrder.setNett(salesOrder.getSubTotal().multiply(new BigDecimal(1 + (accRules.getTaxRate() / 100))));
        }

        this.txtGross.setText(salesOrder.getGross().toString());
        this.txtDiscount.setText(salesOrder.getDiscount().toString());
        this.txtSubTtl.setText(salesOrder.getSubTotal().toString());
        this.txtNett.setText(salesOrder.getNett().toString());
    }

    @FXML
    private void saveSO(MouseEvent event) throws SQLException {
        if (event.isPrimaryButtonDown() == true) {

            if (!this.btnSave.getText().equals("Save")) {
                isViewMode(false);
                return;
            }

            if (!validator.validate()) {
                return;
            }

            soInDraft = prepareSalesOrderToObj();

            if (this.txtSOID.getText().isEmpty()) {
                soInDraft.setCode(SalesOrderService.saveNewSalesOrder(soInDraft));

            } else {
                SalesOrderService.updateSalesOrder(soInDraft);
            }

            for (Item i : soInDraft.getItems()) {
                i.setOriQty(i.getQty());
            }

            soInDraft.setItems(reserveStock());

            for (Item item : soInDraft.getItems()) {
                InventoryService.reserveQtyForSalesDoc(item);
            }

            ItemService.updateItemsByDoc(soInDraft.getItems(), soInDraft.getCode());

            updateRefDoc();

        }
    }

    private void updateRefDoc() {
        if (!this.txtQuotRef.getText().isEmpty()) {

            soInDraft.getQuotRef().setStatus(SalesRules.QuotStatus.COMPLETED.toString());
            QuotationService.updateQuotationStatus(soInDraft.getQuotRef());

        }
    }

    @FXML
    private void openPymtTermSelection(MouseEvent event) {
        if (event.isPrimaryButtonDown() == true) {
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getClassLoader().getResource("View/InnerEntitySelect_UI.fxml"));
                Stage stage = new Stage();
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(btnBack.getScene().getWindow());
                stage.setScene(new Scene(root));

                BasicObjs passObj = new BasicObjs();
                passObj.setObj(new PaymentTerm());

                stage.setUserData(passObj);
                stage.showAndWait();

                if (stage.getUserData() != null) {
                    BasicObjs receiveObj = (BasicObjs) stage.getUserData();
                    this.txtPymtTerm.setText(((PaymentTerm) receiveObj.getObj()).getPymtTermID());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void openShipmentTermSelection(MouseEvent event) {
        if (event.isPrimaryButtonDown() == true) {
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getClassLoader().getResource("View/InnerEntitySelect_UI.fxml"));
                Stage stage = new Stage();
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(btnBack.getScene().getWindow());
                stage.setScene(new Scene(root));

                BasicObjs passObj = new BasicObjs();
                passObj.setObj(new ShipmentTerm());

                stage.setUserData(passObj);
                stage.showAndWait();

                if (stage.getUserData() != null) {
                    BasicObjs receiveObj = (BasicObjs) stage.getUserData();
                    this.txtShipmentTerm.setText(((ShipmentTerm) receiveObj.getObj()).getShipmentTermID());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<Item> reserveStock() {
        for (Item item : oriItems) {
            InventoryService.freeUpReservedQtyByInventoryID(item.getInventory(), item.getOriQty());
        }

        List<Item> finalItems = new ArrayList<>();

        for (Item item : items) {
            List<Inventory> inventories = new ArrayList<>();
            inventories = InventoryService.getReadyInventoriesByProdID(item.getProduct().getProdID(), item.getOriQty());

            int oriQty = item.getOriQty();

            for (Inventory inventory : inventories) {
                Item cloneItem = item.clone();

                if (oriQty > inventory.getReadyQty()) {
                    cloneItem.setQty(inventory.getReadyQty());
                    oriQty -= inventory.getReadyQty();
                } else {
                    cloneItem.setQty(oriQty);
                }
                cloneItem.setOriQty(cloneItem.getQty());
                cloneItem.setInventory(inventory);

                finalItems.add(cloneItem);
            }
        }

        return finalItems;

    }

    private boolean isDemandOverReadyStock(Item item) {
        Integer ttlDemandQty = 0;

        ttlDemandQty += item.getOriQty();
        Integer ttlReadyQty = InventoryService.getInventoryReadyQtyByProdID(item.getProduct().getProdID());

        for (Item itm : oriItems) {
            if (itm.getProduct().getProdID().equals(item.getProduct().getProdID())) {
                ttlReadyQty += itm.getOriQty();
            }
        }

        for (Item itm : items) {
            if (itm.getProduct().getProdID().equals(item.getProduct().getProdID())
                    && !itm.equals(item)) {
                ttlDemandQty += itm.getOriQty();
            }
        }

        if (ttlDemandQty > ttlReadyQty) {
            alertDialog(Alert.AlertType.WARNING,
                    "Warning",
                    "Insufficient Stock Alert", "Ready Qty = " + ttlReadyQty + "\nDemand Qty = " + ttlDemandQty + "\nKindly make Demand Qty below or equal Ready Qty.");
            return true;
        } else {
            return false;
        }
    }

    private void privilegeDetect() {
        String privilege = passObj.getLoginStaff().getRole();

        switch (privilege) {
            case "ADMINISTRATOR":
                this.ctnSalesPersonSelection.setDisable(false);
                break;
            default:
                this.ctnSalesPersonSelection.setDisable(true);
        }

    }
}
