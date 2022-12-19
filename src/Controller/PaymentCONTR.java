/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import BizRulesConfiguration.AccountingRules;
import Entity.Invoice;
import Entity.Item;
import Entity.Receipt;
import Entity.Staff;
import PassObjs.BasicObjs;
import Service.GeneralRulesService;
import Service.InvoiceService;
import Service.ItemService;
import Service.ReceiptService;
import Utils.ImageUtils;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCircleToggleNode;
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
public class PaymentCONTR implements Initializable, BasicCONTRFunc {
    //<editor-fold defaultstate="collapsed" desc="fields">

    @FXML
    private MFXCircleToggleNode btnBack;
    @FXML
    private MFXCircleToggleNode ctnINVRefSelection;
    @FXML
    private MFXTextField txtRcptID;
    @FXML
    private MFXDatePicker dtRefDate;
    @FXML
    private MFXTextField txtInvoiceRef;
    @FXML
    private MFXTextField txtRef;
    @FXML
    private MFXTextField txtRefType;
    @FXML
    private MFXButton btnAdd;
    @FXML
    private MFXTextField txtPrevPaidAmt;
    @FXML
    private MFXTextField txtBalanceUnPaid;
    @FXML
    private MFXTextField txtPymtAmt;
    @FXML
    private MFXTextField txtTtlPayable;

    @FXML
    private ImageView imgDocs;
    @FXML
    private MFXButton btnSave;
    @FXML
    private MFXButton btnDiscard;
    @FXML
    private Label lblImgStrs;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="util declarations">
    private BasicObjs passObj;

    AccountingRules accRules = new AccountingRules();

    private List<Item> itemsNotYetPaid = new ArrayList<>(); // use to know which Item been update, and perform update action for those modified address

    private List<Item> items = new ArrayList<>(); // use to know which Item been update, and perform update action for those modified address

    private List<Item> tempItems = new ArrayList<>(); // use to know which Item been update, and perform update action for those modified address

    private static List<String> rowSelected = new ArrayList<>();

    private Receipt receiptInDraft;

    private Validator validator = new Validator();
    @FXML
    private MFXTableView<?> tblVw;
    @FXML
    private MFXButton btnPrint;

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
                inputValidation();
                receiveData();
                autoClose();
                if (passObj.getCrud().equals(BasicObjs.create)) {
                    defaultValFillIn();
                    btnPrint.setVisible(false);
                }

                if (passObj.getCrud().equals(BasicObjs.read) || passObj.getCrud().equals(BasicObjs.update)) {
                    try {
                        fieldFillIn();
                        btnPrint.setVisible(true);
                    } catch (IOException ex) {
                        Logger.getLogger(InvoiceCONTR.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    if (passObj.getObj() instanceof Receipt) {
                        Receipt receipt = (Receipt) passObj.getObj();

                        itemsNotYetPaid.addAll(ItemService.getItemNotYetBillBySO(receipt.getCode()));

                        items.addAll(ItemService.getItemByRcptID(receipt.getCode()));

                    } else if (passObj.getObj() instanceof Invoice) {
                        itemsNotYetPaid.addAll(ItemService.getItemNotYetPaidByInvoice(((Invoice) passObj.getObj()).getCode()));

                        for (Item i : itemsNotYetPaid) {
                            items.add(i.clone());
                        }
                    }
                }

                if (passObj.getCrud().equals(BasicObjs.read)) {
                    isViewMode(true);
                }
                setupItemTable();

                if (passObj.getCrud().equals(BasicObjs.create)) {
                    try {
                        calculateTotalInformation(items);
                    } catch (Exception ex) {
                        Logger.getLogger(PaymentCONTR.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        });
    }

    private void calculateTotalInformation(List<Item> items) throws Exception {

        Receipt receipt = new Receipt();
        System.out.println("Invoice vode here" + this.txtInvoiceRef.getText());
        BigDecimal ttlPayable = InvoiceService.getInvoiceByID(this.txtInvoiceRef.getText()).getTtlPayable();
        BigDecimal ttlPrevPaidAmt = ReceiptService.getPrevPaid(this.txtInvoiceRef.getText());
        BigDecimal ttlPaidAmt = new BigDecimal("0.00");
        for (Item item : items) {
            ttlPaidAmt = ttlPaidAmt.add(item.getInclTaxAmt());
        }

        BigDecimal ttlBlcUnpaid = ttlPayable.subtract(ttlPrevPaidAmt.add(ttlPaidAmt));

        receipt.setTtlPayable(ttlPayable);
        receipt.setPaidAmtPrev(ttlPrevPaidAmt);
        receipt.setPaidAmt(ttlPaidAmt);
        receipt.setBalUnpaid(ttlBlcUnpaid);

        this.txtTtlPayable.setText(receipt.getTtlPayable().toString());
        this.txtPrevPaidAmt.setText(receipt.getPaidAmtPrev().toString());
        this.txtPymtAmt.setText(receipt.getPaidAmt().toString());
        this.txtBalanceUnPaid.setText(receipt.getBalUnpaid().toString());
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

    private void setupItemTable() {
        // Product ID
        MFXTableColumn<Item> prodIDCol = new MFXTableColumn<>("Product ID", true, Comparator.comparing(item -> item.getProduct() == null ? "" : item.getProduct().getProdID()));
        //Part No.
        MFXTableColumn<Item> partNoCol = new MFXTableColumn<>("Part No.", true, Comparator.comparing(item -> item.getProduct() == null ? "" : item.getProduct().getPartNo()));
        // Remarks
        MFXTableColumn<Item> remarksCol = new MFXTableColumn<>("Remarks", true, Comparator.comparing(item -> item.getRemark()));
        // Quantity
        MFXTableColumn<Item> qtyCol = new MFXTableColumn<>("Quantity", true, Comparator.comparing(item -> item.getOriQty()));
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
        qtyCol.setRowCellFactory(i -> new MFXTableRowCell<>(item -> item.getOriQty()));
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
                new IntegerFilter<>("Quantity", item -> item.getOriQty()),
                new DoubleFilter<>("Unit Price", item -> item.getUnitPrice().doubleValue()),
                new DoubleFilter<>("Excl. Amount", item -> item.getExclTaxAmt().doubleValue()),
                new DoubleFilter<>("Discount Amount", item -> item.getDiscAmt().doubleValue()),
                new DoubleFilter<>("Incl. Amount", item -> item.getInclTaxAmt().doubleValue())
        );

        tempItems.clear();
        tempItems.addAll(items);
        for (Item item : tempItems) {
            if (item.getQty() == 0) {
                tempItems.remove(item);
            }
        }
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
                                passObj.setObjs((List<Object>) (Object) itemsNotYetPaid);
                                passObj.setCrud(BasicObjs.read);

                                stage.setUserData(passObj);
                                stage.showAndWait();

                                // if have any change on the selected item
                                if (stage.getUserData() != null) {

                                    BasicObjs receiveObj = (BasicObjs) stage.getUserData();
                                    Item catchedItem = new Item();
                                    if (receiveObj.getObj() != null) {
                                        catchedItem = ((Item) receiveObj.getObj()).clone();
                                    } else {
                                        catchedItem = null;
                                    }

                                    try {
                                        adjustItemsNotYetPaid(catchedItem, item);
                                    } catch (Exception ex) {
                                        Logger.getLogger(PaymentCONTR.class.getName()).log(Level.SEVERE, null, ex);
                                    }

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

    private void adjustItemsNotYetPaid(Item catchedItem, Item item) throws Exception {
        if (catchedItem == null) {//remove
            Item itemInInvoice = itemsNotYetPaid.get(itemsNotYetPaid.indexOf(item));
            Item itemInRcpt = (Item) items.get(items.indexOf(item));

            itemInInvoice.setQty(itemInInvoice.getQty() + itemInRcpt.getQty());
            items.remove(item);
        } else if (!items.contains(catchedItem)) { //add
            items.add(catchedItem);

            Item itemInInvoice = itemsNotYetPaid.get(itemsNotYetPaid.indexOf(catchedItem));
            Item itemInRcpt = (Item) items.get(items.indexOf(catchedItem));

            itemInRcpt.setOriQty(0);
            itemInInvoice.setQty(itemInInvoice.getQty() - itemInRcpt.getQty() + itemInRcpt.getOriQty());

        } else { // update
            //remove
            Item itemInInvoice = itemsNotYetPaid.get(itemsNotYetPaid.indexOf(catchedItem));
            Item itemInRcpt = (Item) items.get(items.indexOf(catchedItem));

            itemInInvoice.setQty(itemInInvoice.getQty() + itemInRcpt.getOriQty());
            items.remove(catchedItem);

            //add
            items.add(catchedItem);

            itemInInvoice = itemsNotYetPaid.get(itemsNotYetPaid.indexOf(catchedItem));
            itemInRcpt = (Item) items.get(items.indexOf(catchedItem));

            itemInRcpt.setOriQty(0);
            itemInInvoice.setQty(itemInInvoice.getQty() - itemInRcpt.getQty() + itemInRcpt.getOriQty());
        }

        for (Item i : items) {
            i.setOriQty(i.getQty());
        }
        setupItemTable();
        this.calculateTotalInformation(items);
    }

    private void defaultValFillIn() {
        this.dtRefDate.setValue(LocalDate.now());
    }

    private void fieldFillIn() throws IOException {
        defaultValFillIn();

        if (passObj.getObj() != null) {
            if (passObj.getObj() instanceof Receipt) {

                Receipt receipt = (Receipt) passObj.getObj();

                this.txtRcptID.setText(receipt.getCode());
                this.dtRefDate.setValue(receipt.getCreatedDate() == null ? null : Instant.ofEpochMilli(receipt.getCreatedDate().getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate());
                this.txtInvoiceRef.setText(receipt.getINV() == null ? "" : receipt.getINV().getCode());
                this.txtRefType.setText(receipt.getReferenceType());
                this.txtRef.setText(receipt.getReference());

                this.txtTtlPayable.setText(receipt.getINV() == null ? "" : receipt.getINV().getTtlPayable().toString());
                this.txtPymtAmt.setText(receipt.getPaidAmt().toString());
                this.txtPrevPaidAmt.setText(receipt.getPaidAmtPrev().toString());
                this.txtBalanceUnPaid.setText(receipt.getBalUnpaid().toString());

                this.imgDocs.setImage(ImageUtils.byteToImg(ImageUtils.encodedStrToByte(((String) ImageUtils.splitImgStr(receipt.getSignedDocPic()).getFirst()))));

            } else if (passObj.getObj() instanceof Invoice) {

                Invoice invoice = (Invoice) passObj.getObj();
                this.txtInvoiceRef.setText(invoice.getCode());
                this.txtTtlPayable.setText(invoice.getTtlPayable().toString());
            }

        }
    }

    private void isViewMode(boolean disable) {
        if (disable == true) {
            this.btnSave.setText("Update");
        } else {
            this.btnSave.setText("Save");
        }

        this.txtRcptID.setDisable(disable);
        this.dtRefDate.setDisable(disable);
        this.txtInvoiceRef.setDisable(disable);
        this.txtRefType.setDisable(disable);
        this.txtRef.setDisable(disable);

        this.txtTtlPayable.setDisable(disable);
        this.txtPymtAmt.setDisable(disable);
        this.txtPrevPaidAmt.setDisable(disable);
        this.txtBalanceUnPaid.setDisable(disable);

        this.ctnINVRefSelection.setDisable(disable);

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
                passObj.getFxmlPaths().addLast("View/Payment_UI.fxml");
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
                .dependsOn("Invoice Reference", this.txtInvoiceRef.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Invoice Reference");
                    textVal = textVal.trim();

                    /*
                     1. cannot be null
                     */
                    if (textVal.isEmpty()) {
                        c.error("Invoice Reference - Required Field");
                        return;
                    }

                    return;
                })
                .decorates(this.txtInvoiceRef);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .withMethod(c -> {

                    if (items.size() <= 0) {
                        c.error("Item - At least one item are required to build a payment");
                        return;
                    }

                })
                .decorates(this.tblVw);

        validator.add(validatorCheck);
        //=====================================
    }

    @Override
    public boolean clearAllFieldsValue() {
        this.dtRefDate.clear();
        this.txtInvoiceRef.clear();
        this.txtRefType.clear();
        this.txtRef.clear();

        this.txtTtlPayable.clear();
        this.txtPymtAmt.clear();
        this.txtPrevPaidAmt.clear();
        this.txtBalanceUnPaid.clear();

        this.lblImgStrs.setText("");
        this.imgDocs.setImage(null);

        items.clear();
        itemsNotYetPaid.clear();

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

    private Receipt prepareReceiptToObj() {
        Receipt receipt = new Receipt();
        receipt.setCode(this.txtRcptID.getText());
        receipt.setCreatedDate(this.dtRefDate.getValue() == null ? null : Timestamp.valueOf(this.dtRefDate.getValue().atStartOfDay()));

        receipt.setINV(new Invoice());
        receipt.getINV().setCode(this.txtInvoiceRef.getText());
        receipt.setReferenceType(this.txtRefType.getText());
        receipt.setReference(this.txtRef.getText());

        receipt.setTtlPayable(new BigDecimal(this.txtTtlPayable.getText()));
        receipt.setPaidAmtPrev(new BigDecimal(this.txtPrevPaidAmt.getText()));
        receipt.setPaidAmt(new BigDecimal(this.txtPymtAmt.getText()));
        receipt.setBalUnpaid(new BigDecimal(this.txtBalanceUnPaid.getText()));

        receipt.setSignedDocPic(this.lblImgStrs.getText());
        receipt.setItems(items);
        return receipt;

    }

    @FXML
    private void openINVSelection(MouseEvent event) throws Exception {
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
                passObj.setObj(new Invoice());

                stage.setUserData(passObj);
                stage.showAndWait();

                if (stage.getUserData() != null) {

                    BasicObjs receiveObj = (BasicObjs) stage.getUserData();

                    Invoice invoice = (Invoice) receiveObj.getObj();

                    if (!invoice.getStatus().equals(AccountingRules.InvoiceStatus.COMPLETED)) {
                        this.clearAllFieldsValue();
                        this.itemsNotYetPaid.addAll(ItemService.getItemsByINVID(invoice.getCode()));
                        if (isValidDocumentByItems()) {
                            this.passObj.setObj(invoice);
                            fieldFillIn();
                            items.clear();
                            for (Item i : this.itemsNotYetPaid) {
                                items.add(i.clone());

                                i.setQty(0);
                            }

                            for (Item i : items) {
                                i.setOriQty(i.getQty());
                            }

                            setupItemTable();
                            calculateTotalInformation(items);
                        } else {
                            alertDialog(Alert.AlertType.INFORMATION,
                                    "Information",
                                    "Document Blocked Message",
                                    "There are no more item needed to be pay for the selected document.");
                        }

                    } else {
                        alertDialog(Alert.AlertType.INFORMATION,
                                "Information",
                                "Document Blocked Message",
                                "Invoice with COMPLETED status are not allowed to become any document reference.");
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private boolean isValidDocumentByItems() {
        Integer count = 0;
        for (Item item : itemsNotYetPaid) {
            if (item.getQty() == 0) {
                count++;
            }
        }

        if (count == itemsNotYetPaid.size()) {
            return false;
        } else {
            return true;
        }
    }

    @FXML
    private void addProductItem(MouseEvent event) throws Exception {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("View/InnerEntitySelectWithItemsProvided_UI.fxml"));

            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(btnBack.getScene().getWindow());
            stage.setScene(new Scene(root));

            BasicObjs passObj = new BasicObjs();
            passObj.setCrud(BasicObjs.create);
            passObj.setObj(new Receipt());

            tempItems.clear();
            for (Item item : itemsNotYetPaid) {
                tempItems.add(item.clone());
            }

            for (Item item : tempItems) {
                if (item.getQty() == 0) {
                    tempItems.remove(item);
                }
            }
            passObj.setObjs((List<Object>) (Object) tempItems);

            stage.setUserData(passObj);
            stage.showAndWait();

            // if have any change on the selected item
            if (stage.getUserData() != null) {

                BasicObjs receiveObj = (BasicObjs) stage.getUserData();
                Item catchedItem = ((Item) receiveObj.getObj()).clone();

                this.adjustItemsNotYetPaid(catchedItem, null);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goImgViewer(MouseEvent event) {
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
    private void saveReceipt(MouseEvent event) throws SQLException {
        if (event.isPrimaryButtonDown() == true) {

            if (!this.btnSave.getText().equals("Save")) {
                isViewMode(false);
                return;
            }

            if (!validator.validate()) {
                return;
            }

            receiptInDraft = this.prepareReceiptToObj();

            if (this.txtRcptID.getText().isEmpty()) {
                receiptInDraft.setCode(ReceiptService.saveNewReceipt(receiptInDraft));
            } else {
                ReceiptService.updateReceipt(receiptInDraft);
            }

            ItemService.updateItemsByDoc(receiptInDraft.getItems(), receiptInDraft.getCode());

            updateDocStatus();

            switchScene(passObj.getFxmlPaths().getLast().toString(), passObj, BasicObjs.back);

        }
    }

    private void updateDocStatus() throws SQLException {
        if (!this.txtInvoiceRef.getText().isEmpty()) {

            receiptInDraft.getINV().setStatus(AccountingRules.InvoiceStatus.PARTIALLY_PAID.toString());
            InvoiceService.updateInvoiceStatus(receiptInDraft.getINV());
            ItemService.updateItemsByDoc(itemsNotYetPaid, receiptInDraft.getINV().getCode());
        }
    }

    @FXML
    private void printPayment(MouseEvent event) {
        ReceiptService.getPaymentSheet(this.txtRcptID.getText());
    }

}
