/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import BizRulesConfiguration.SalesRules;
import BizRulesConfiguration.WarehouseRules;
import BizRulesConfiguration.WarehouseRules.TOStatus;
import Entity.Item;
import Entity.Place;
import Entity.ReturnDeliveryNote;
import Entity.SalesOrder;
import Entity.Staff;
import Entity.TransferOrder;
import PassObjs.BasicObjs;
import Service.ItemService;
import Service.SalesOrderService;
import Service.TransferOrderService;
import Utils.ImageUtils;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCircleToggleNode;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
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
public class TransferOrderCONTR implements Initializable, BasicCONTRFunc {
    //<editor-fold defaultstate="collapsed" desc="fields">

    @FXML
    private MFXCircleToggleNode btnBack;
    @FXML
    private MFXTextField txtTOID;
    @FXML
    private MFXDatePicker dtRefDate;
    @FXML
    private MFXTextField txtPIC;
    @FXML
    private MFXCircleToggleNode ctnPICSelection;
    @FXML
    private MFXComboBox<?> cmbStatus;
    @FXML
    private MFXTextField txtDestination;
    @FXML
    private MFXCircleToggleNode ctnDestinationSelection;
    @FXML
    private MFXComboBox<?> cmbRefType;
    @FXML
    private MFXTextField txtRef;
    @FXML
    private MFXCircleToggleNode ctnDocSelection;
    @FXML
    private MFXTableView<?> tblVw;
    @FXML
    private MFXButton btnAdd;
    @FXML
    private MFXTextField txtIssuedBy;
    @FXML
    private MFXCircleToggleNode ctnIssuedBySelection;
    @FXML
    private MFXTextField txtTransferBy;
    @FXML
    private MFXCircleToggleNode ctnTransferBySelection;
    @FXML
    private ImageView imgDocs;
    @FXML
    private MFXTextField txtItemReceivedBy;
    @FXML
    private MFXCircleToggleNode ctnItemReceivedBySelection;
    @FXML
    private MFXButton btnSave;
    @FXML
    private MFXButton btnDiscard;
    @FXML
    private Label lblImgStrs;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="util declarations">
    private BasicObjs passObj;

    private Validator validator = new Validator();

    private WarehouseRules warehouseRules = new WarehouseRules();

    private List<Item> itemsNotYetTransfer = new ArrayList<>(); // use to know which Item been update, and perform update action for those modified address

    private List<Item> items = new ArrayList<>(); // use to know which Item been update, and perform update action for those modified address

    private List<Item> tempItems = new ArrayList<>(); // use to know which Item been update, and perform update action for those modified address

    private static List<String> rowSelected = new ArrayList<>();

    private TransferOrder toInDraft;
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

                if (passObj.getCrud().equals(BasicObjs.read) || passObj.getCrud().equals(BasicObjs.update)) {
                    try {
                        fieldFillIn();
                    } catch (IOException ex) {
                        java.util.logging.Logger.getLogger(TransferOrderCONTR.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    }

                    if (passObj.getObj() instanceof TransferOrder) {

                        TransferOrder to = (TransferOrder) passObj.getObj();

                        if (to.getReqTypeRef() instanceof SalesOrder) {

                            itemsNotYetTransfer.addAll(ItemService.getItemBySOID(((SalesOrder) to.getReqTypeRef()).getCode()));

                        } else if (to.getReqTypeRef() instanceof ReturnDeliveryNote) {

                            itemsNotYetTransfer.addAll(ItemService.getItemByRDNID(((ReturnDeliveryNote) to.getReqTypeRef()).getCode()));

                        }

                        items.addAll(ItemService.getItemByTOID(to.getCode()));

                    } else if (passObj.getObj() instanceof SalesOrder) {

                        itemsNotYetTransfer.addAll(ItemService.getItemBySOID(((SalesOrder) passObj.getObj()).getCode()));

                    } else if (passObj.getObj() instanceof ReturnDeliveryNote) {

                        itemsNotYetTransfer.addAll(ItemService.getItemByRDNID(((ReturnDeliveryNote) passObj.getObj()).getCode()));

                    }

                }

                if (passObj.getCrud().equals(BasicObjs.read)) {
                    isViewMode(true);
                }
            }
        });
    }

    private void initializeComboSelections() {
        ((MFXComboBox<String>) this.cmbRefType).setItems(FXCollections.observableList(warehouseRules.getRefTypeForTO()));
        ((MFXComboBox<TOStatus>) this.cmbStatus).setItems(FXCollections.observableList(warehouseRules.getToStatuses()));

        this.cmbRefType.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            this.txtRef.clear();
        });

    }

    private void setupItemTable() {
        // Product ID
        // Part No
        // Qty
        // UOM
        // Source

        // Product ID
        MFXTableColumn<Item> prodIDCol = new MFXTableColumn<>("Product ID", true, Comparator.comparing(item -> item.getProduct() == null ? null : item.getProduct().getProdID()));
        // Part No
        MFXTableColumn<Item> partNoCol = new MFXTableColumn<>("Part No.", true, Comparator.comparing(item -> item.getProduct() == null ? null : item.getProduct().getPartNo()));
        // Qty
        MFXTableColumn<Item> qtyCol = new MFXTableColumn<>("Qty", true, Comparator.comparing(item -> item.getQty()));
        // UOM
        MFXTableColumn<Item> uomCol = new MFXTableColumn<>("UOM", true, Comparator.comparing(item -> item.getProduct() == null ? null : item.getProduct().getUom()));
        // Source
        MFXTableColumn<Item> sourceCol = new MFXTableColumn<>("Source", true, Comparator.comparing(item -> item.getInventory() == null ? null : item.getInventory().getInventoryID()));

        // Product ID
        prodIDCol.setRowCellFactory(i -> new MFXTableRowCell<>(item -> item.getProduct() == null ? null : item.getProduct().getProdID()));
        // Part No
        partNoCol.setRowCellFactory(i -> new MFXTableRowCell<>(item -> item.getProduct() == null ? null : item.getProduct().getPartNo()));
        // Qty
        qtyCol.setRowCellFactory(i -> new MFXTableRowCell<>(item -> item.getQty()));
        // UOM
        uomCol.setRowCellFactory(i -> new MFXTableRowCell<>(item -> item.getProduct() == null ? null : item.getProduct().getUom()));
        // Source
        sourceCol.setRowCellFactory(i -> new MFXTableRowCell<>(item -> item.getInventory() == null ? null : item.getInventory().getInventoryID()));

        ((MFXTableView<Item>) tblVw).getTableColumns().clear();

        ((MFXTableView<Item>) tblVw).getTableColumns().addAll(
                prodIDCol,
                partNoCol,
                qtyCol,
                qtyCol,
                uomCol,
                sourceCol
        );

        ((MFXTableView<Item>) tblVw).getFilters().clear();

        ((MFXTableView<Item>) tblVw).getFilters().addAll(
                new StringFilter<>("Product ID", item -> item.getProduct() == null ? null : item.getProduct().getProdID()),
                new StringFilter<>("Part No.", item -> item.getProduct() == null ? null : item.getProduct().getPartNo()),
                new IntegerFilter<>("Qty", item -> item.getQty()),
                new StringFilter<>("UOM", item -> item.getProduct() == null ? null : item.getProduct().getUom()),
                new StringFilter<>("Source", item -> item.getInventory() == null ? null : item.getInventory().getInventoryID())
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
                            // action here
                            Parent root;
                            try {
                                root = FXMLLoader.load(getClass().getClassLoader().getResource("View/TOPSSelect_UI.fxml"));
                                Stage stage = new Stage();
                                stage.initModality(Modality.WINDOW_MODAL);
                                stage.initOwner(btnBack.getScene().getWindow());
                                stage.setScene(new Scene(root));

                                BasicObjs passObj = new BasicObjs();
                                passObj.setObj(item);
                                passObj.setObjs((List<Object>) (Object) itemsNotYetTransfer);
                                passObj.setCrud(BasicObjs.read);

                                stage.setUserData(passObj);
                                stage.showAndWait();

                                // if have any change on the selected item
                                if (stage.getUserData() != null) {

                                    BasicObjs receiveObj = (BasicObjs) stage.getUserData();
                                    Item catchedItem = new Item();
                                    catchedItem = ((Item) receiveObj.getObj()).clone();

                                    adjustItemsNotYetTransfer(catchedItem, item);

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

    private void adjustItemsNotYetTransfer(Item catchedItem, Item item) { // item passed in is consider as the item going to be transfer

        if (catchedItem == null) {//remove
            Item itemInSO = itemsNotYetTransfer.get(itemsNotYetTransfer.indexOf(item));
            Item itemInTO = (Item) items.get(items.indexOf(item));

            itemInSO.setQty(itemInSO.getQty() + itemInTO.getQty());
            items.remove(item);
        } else if (!items.contains(catchedItem)) { //add
            items.add(catchedItem);

            Item itemInSO = itemsNotYetTransfer.get(itemsNotYetTransfer.indexOf(catchedItem));
            Item itemInTO = (Item) items.get(items.indexOf(catchedItem));

            itemInTO.setOriQty(0);
            itemInSO.setQty(itemInSO.getQty() - itemInTO.getQty() + itemInTO.getOriQty());

        } else { // update
            //remove
            Item itemInSO = itemsNotYetTransfer.get(itemsNotYetTransfer.indexOf(catchedItem));
            Item itemInTO = (Item) items.get(items.indexOf(catchedItem));

            itemInSO.setQty(itemInSO.getQty() + itemInTO.getQty());
            items.remove(catchedItem);

            //add
            items.add(catchedItem);

            itemInSO = itemsNotYetTransfer.get(itemsNotYetTransfer.indexOf(catchedItem));
            itemInTO = (Item) items.get(items.indexOf(catchedItem));

            itemInTO.setOriQty(0);
            itemInSO.setQty(itemInSO.getQty() - itemInTO.getQty() + itemInTO.getOriQty());
        }

        setupItemTable();

    }

    private void fieldFillIn() throws IOException {
        // before field in, make sure the UI control is fresh
        clearAllFieldsValue();

        if (passObj.getObj() != null) {
            if (passObj.getObj() instanceof SalesOrder) {

                SalesOrder so = (SalesOrder) passObj.getObj();
                this.txtRef.setText(so.getCode());

            } else if (passObj.getObj() instanceof ReturnDeliveryNote) {

                ReturnDeliveryNote rdn = (ReturnDeliveryNote) passObj.getObj();
                this.txtRef.setText(rdn.getCode());

            } else if (passObj.getObj() instanceof TransferOrder) {
                TransferOrder to = (TransferOrder) passObj.getObj();
                this.txtTOID.setText(to.getCode());
                this.txtPIC.setText(to.getPIC() == null ? "" : to.getPIC().getStaffID());
                this.txtDestination.setText(to.getDestination() == null ? "" : to.getDestination().getPlaceID());
                this.dtRefDate.setValue(to.getCreatedDate() == null ? null : Instant.ofEpochMilli(to.getCreatedDate().getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate());
                this.cmbRefType.setText(to.getReqType());

                if (passObj.getObj() instanceof SalesOrder) {
                    this.txtRef.setText(((SalesOrder) to.getReqTypeRef()).getCode());
                } else if (passObj.getObj() instanceof ReturnDeliveryNote) {
                    this.txtRef.setText(((ReturnDeliveryNote) to.getReqTypeRef()).getCode());
                }

                this.cmbStatus.setText(to.getStatus());
                this.txtIssuedBy.setText(to.getIssuedBy() == null ? "" : to.getIssuedBy().getStaffID());
                this.txtTransferBy.setText(to.getTransferBy() == null ? "" : to.getTransferBy().getStaffID());
                this.txtItemReceivedBy.setText(to.getItemReceivedBy() == null ? "" : to.getItemReceivedBy().getStaffID());
            }

        }
    }

    private void isViewMode(boolean disable) {
        if (disable == true) {
            this.btnSave.setText("Update");
        } else {
            this.btnSave.setText("Save");
        }

        this.txtTOID.setDisable(disable);
        this.txtPIC.setDisable(disable);
        this.txtDestination.setDisable(disable);
        this.cmbRefType.setDisable(disable);
        this.txtRef.setDisable(disable);
        this.cmbStatus.setDisable(disable);
        this.txtIssuedBy.setDisable(disable);
        this.txtTransferBy.setDisable(disable);
        this.txtItemReceivedBy.setDisable(disable);

        this.ctnPICSelection.setDisable(disable);
        this.ctnDestinationSelection.setDisable(disable);
        this.ctnDocSelection.setDisable(disable);
        this.ctnIssuedBySelection.setDisable(disable);
        this.ctnTransferBySelection.setDisable(disable);
        this.ctnItemReceivedBySelection.setDisable(disable);

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
                passObj.getFxmlPaths().addLast("View/TransferOrder_UI.fxml");
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
        // this.txtTOID.clear();
        this.txtPIC.clear();
        this.txtDestination.clear();
        this.dtRefDate.clear();
        this.cmbRefType.clear();
        this.txtRef.clear();
        this.cmbStatus.clear();
        this.txtIssuedBy.clear();
        this.txtTransferBy.clear();
        this.txtItemReceivedBy.clear();

        items.clear();
        itemsNotYetTransfer.clear();
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

    private TransferOrder prepareTransferOrderToObj() {
        TransferOrder transferOrder = new TransferOrder();

        transferOrder.setCode(this.txtTOID.getText());

        Staff PIC = new Staff();
        PIC.setStaffID(this.txtPIC.getText());

        Place destination = new Place();
        destination.setPlaceID(this.txtDestination.getText());

        if (this.passObj.getObj() instanceof SalesOrder) {
            SalesOrder salesOrder = new SalesOrder();
            salesOrder.setCode(this.txtRef.getText());
            transferOrder.setReqTypeRef(salesOrder);
        } else if (this.passObj.getObj() instanceof ReturnDeliveryNote) {
            ReturnDeliveryNote rdn = new ReturnDeliveryNote();
            rdn.setCode(this.txtRef.getText());
            transferOrder.setReqTypeRef(rdn);
        }

        transferOrder.setCreatedDate(this.dtRefDate.getValue() == null ? null : Timestamp.valueOf(this.dtRefDate.getValue().atStartOfDay()));
        transferOrder.setReqType(this.cmbRefType.getText());
        transferOrder.setReqTypeRef(this.txtRef.getText());

        transferOrder.setStatus(this.cmbStatus.getText());

        Staff issuedBy = new Staff();
        issuedBy.setStaffID(this.txtIssuedBy.getText());
        transferOrder.setIssuedBy(issuedBy);

        Staff transferBy = new Staff();
        transferBy.setStaffID(this.txtTransferBy.getText());
        transferOrder.setIssuedBy(transferBy);

        Staff itemReceivedBy = new Staff();
        itemReceivedBy.setStaffID(this.txtItemReceivedBy.getText());
        transferOrder.setIssuedBy(itemReceivedBy);

        transferOrder.setSignedDocPic(this.lblImgStrs.getText());

        transferOrder.setItems(items);

        return transferOrder;
    }

    @FXML
    private void openPICSelection(MouseEvent event) {
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
                    this.txtPIC.setText(((Staff) receiveObj.getObj()).getStaffID());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @FXML
    private void openDestinationSelection(MouseEvent event) {
        if (event.isPrimaryButtonDown() == true) {

            Parent root;
            try {
                root = FXMLLoader.load(getClass().getClassLoader().getResource("View/InnerEntitySelect_UI.fxml"));
                Stage stage = new Stage();
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(btnBack.getScene().getWindow());
                stage.setScene(new Scene(root));

                BasicObjs passObj = new BasicObjs();
                passObj.setObj(new Place());

                stage.setUserData(passObj);
                stage.showAndWait();

                if (stage.getUserData() != null) {
                    BasicObjs receiveObj = (BasicObjs) stage.getUserData();
                    this.txtDestination.setText(((Place) receiveObj.getObj()).getPlaceID());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @FXML
    private void openDocSelection(MouseEvent event) {
        if (event.isPrimaryButtonDown() == true) {

            if (this.cmbRefType.getText().isEmpty()) {
                alertDialog(Alert.AlertType.INFORMATION,
                        "Information",
                        "Prerequisite Condition",
                        "Must fill in deliver to column, before select customer signature");
                return;
            }

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

                if (this.cmbRefType.getText().equals("RDN")) {
                    passObj.setObj(new ReturnDeliveryNote());
                } else if (this.cmbRefType.getText().equals("SO")) {
                    passObj.setObj(new SalesOrder());
                }

                stage.setUserData(passObj);
                stage.showAndWait();

                if (stage.getUserData() != null) {

                    BasicObjs receiveObj = (BasicObjs) stage.getUserData();

                    if (receiveObj.getObj() instanceof ReturnDeliveryNote) {
                        // later done RDN just come back
                        ReturnDeliveryNote rdn = (ReturnDeliveryNote) receiveObj.getObj();

                        if (rdn.getStatus().equals(WarehouseRules.RDNStatus.NEW)) {
                            this.passObj.setObj(rdn);
                            //this.passObj.setObj(SalesOrderService.getSOByID(so.getCode()));
                            fieldFillIn();

                            itemsNotYetTransfer.addAll(ItemService.getItemByRDNID(rdn.getCode()));

                            for (Item i : itemsNotYetTransfer) {
                                items.add(i.clone());
                            }

                            setupItemTable();

                        } else {
                            alertDialog(Alert.AlertType.INFORMATION,
                                    "Information",
                                    "Document Blocked Message",
                                    "RDN without NEW status are not allowed to become any document reference.");
                        }
                    } else if (receiveObj.getObj() instanceof SalesOrder) {
                        SalesOrder so = (SalesOrder) receiveObj.getObj();

                        if (so.getStatus().equals(SalesRules.SOStatus.NEW)) {
                            this.passObj.setObj(so);
                            //this.passObj.setObj(SalesOrderService.getSOByID(so.getCode()));
                            fieldFillIn();

                            itemsNotYetTransfer.addAll(ItemService.getItemBySOID(so.getCode()));

                            for (Item i : itemsNotYetTransfer) {
                                items.add(i.clone());
                            }

                            setupItemTable();

                        } else {
                            alertDialog(Alert.AlertType.INFORMATION,
                                    "Information",
                                    "Document Blocked Message",
                                    "Sales Order without NEW status are not allowed to become any document reference.");
                        }
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @FXML
    private void addProductItem(MouseEvent event) {

        if (this.txtRef.getText().isEmpty()) {
            alertDialog(Alert.AlertType.INFORMATION,
                    "Information",
                    "Prerequisite Condition",
                    "Must fill in Reference Document column, before add item");
            return;
        }

        Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("View/InnerEntitySelectWithItemsProvided_UI.fxml"));

            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(btnBack.getScene().getWindow());
            stage.setScene(new Scene(root));

            BasicObjs passObj = new BasicObjs();
            passObj.setCrud(BasicObjs.create);
            passObj.setObj(new TransferOrder());
            passObj.setObjs((List<Object>) (Object) itemsNotYetTransfer);

            stage.setUserData(passObj);
            stage.showAndWait();

            // if have any change on the selected item
            if (stage.getUserData() != null) {

                BasicObjs receiveObj = (BasicObjs) stage.getUserData();
                Item catchedItem = ((Item) receiveObj.getObj()).clone();

                adjustItemsNotYetTransfer(catchedItem, null);

            }
        } catch (IOException e) {
            e.printStackTrace();
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
    private void openTransferBySelection(MouseEvent event) {
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
                    this.txtTransferBy.setText(((Staff) receiveObj.getObj()).getStaffID());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @FXML
    private void openItemReceivedBySelection(MouseEvent event) {
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
                    this.txtItemReceivedBy.setText(((Staff) receiveObj.getObj()).getStaffID());
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
    private void savveTO(MouseEvent event) throws SQLException {
        if (event.isPrimaryButtonDown() == true) {

            if (!this.btnSave.getText().equals("Save")) {
                isViewMode(false);
                return;
            }

            if (!validator.validate()) {
                alertDialog(Alert.AlertType.WARNING, "Warning", "Validation Message", validator.createStringBinding().getValue());
                return;
            }

            toInDraft = this.prepareTransferOrderToObj();

            if (this.txtRef.getText().isEmpty()) {
                TransferOrderService.saveNewTO(toInDraft);

            } else if (this.passObj.getCrud().equals(BasicObjs.update)) {
                TransferOrderService.updateTO(toInDraft);
            }

            for (int i = 0; i < toInDraft.getItems().size(); i++) {
                Item item = ((Item) toInDraft.getItems().get(i));
                item.setOriQty(item.getQty());
            }

            ItemService.updateItemsByDoc(toInDraft.getItems(), toInDraft.getCode());

            updateRefDoc();
        }

    }

    private void updateRefDoc() throws SQLException {
        if (!this.txtRef.getText().isEmpty()) {

            if (toInDraft.getReqTypeRef() instanceof ReturnDeliveryNote) {

                ReturnDeliveryNote rdn = (ReturnDeliveryNote) toInDraft.getReqTypeRef();

                ItemService.updateItemsByDoc(this.itemsNotYetTransfer, rdn.getCode());

            } else if (toInDraft.getReqTypeRef() instanceof SalesOrder) {

                SalesOrder salesOrder = (SalesOrder) toInDraft.getReqTypeRef();

                salesOrder.setStatus(SalesRules.SOStatus.IN_PROGRESS.toString());
                SalesOrderService.updateSalesOrderStatus(salesOrder);

                ItemService.updateItemsByDoc(this.itemsNotYetTransfer, salesOrder.getCode());

            }
        }
    }

}
