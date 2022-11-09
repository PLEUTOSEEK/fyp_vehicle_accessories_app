/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import BizRulesConfiguration.SalesRules;
import BizRulesConfiguration.WarehouseRules;
import Entity.CollectAddress;
import Entity.Item;
import Entity.Place;
import Entity.ReturnDeliveryNote;
import Entity.SalesOrder;
import Entity.Staff;
import PassObjs.BasicObjs;
import Service.ItemService;
import Service.RDNService;
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
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.synedra.validatorfx.Validator;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class ReturnDerliveryNoteCONTR implements Initializable, BasicCONTRFunc {

    //<editor-fold defaultstate="collapsed" desc="fields">
    @FXML
    private MFXCircleToggleNode btnBack;
    @FXML
    private MFXCircleToggleNode ctnCllctBckToSelection;
    @FXML
    private MFXTextField txtCllctBckTo;
    @FXML
    private MFXTextField txtRDNID;
    @FXML
    private MFXDatePicker dtRefDate;
    @FXML
    private MFXTextField txtSORef;
    @FXML
    private MFXTextField txtCllctBckFr;
    @FXML
    private MFXCircleToggleNode ctnCllctBckFrSelection;
    @FXML
    private MFXComboBox<?> cmbStatus;
    @FXML
    private MFXDatePicker dtCllctDate;
    @FXML
    private MFXCircleToggleNode ctnSORefSelection;
    @FXML
    private MFXTableView<?> tblVw;
    @FXML
    private MFXButton btnAdd;
    @FXML
    private MFXTextField txtIssuedBy;
    @FXML
    private MFXCircleToggleNode ctnIssuedBySelection;
    @FXML
    private MFXTextField txtInspectedBy;
    @FXML
    private MFXCircleToggleNode ctnInspectedBySelection;
    @FXML
    private ImageView imgDocs;
    @FXML
    private MFXTextField txtCollectBackBy;
    @FXML
    private MFXCircleToggleNode ctnCllctBckBySelection;
    @FXML
    private MFXTextField txtItemPassedBckBy;
    @FXML
    private MFXCircleToggleNode ctnItemPassedBckBySelection;
    @FXML
    private MFXTextField txtItemReceivedBy;
    @FXML
    private MFXCircleToggleNode ctnItemReceivedBySelection;
    @FXML
    private MFXButton btnSave;
    @FXML
    private MFXButton btnDiscard;
    @FXML
    private TextArea txtInspectorMsg;
    @FXML
    private Label lblImgStrs;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="util declarations">
    private BasicObjs passObj;

    private Validator validator = new Validator();

    private WarehouseRules warehouseRules = new WarehouseRules();

    private List<Item> itemsNotYetReturn = new ArrayList<>();

    private List<Item> items = new ArrayList<>(); // use to know which Item been update, and perform update action for those modified address

    private List<Item> tempItems = new ArrayList<>(); // use to know which Item been update, and perform update action for those modified address

    private static List<String> rowSelected = new ArrayList<>();

    private ReturnDeliveryNote rdnInDraft;
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

                    if (passObj.getObj() instanceof ReturnDeliveryNote) {
                        ReturnDeliveryNote rdn = (ReturnDeliveryNote) passObj.getObj();

                        itemsNotYetReturn.addAll(ItemService.getReturnableItemsBySO(rdn.getSO().getCode()));
                        items.addAll(ItemService.getItemByRDNID(rdn.getCode()));

                    } else if (passObj.getObj() instanceof SalesOrder) {
                        itemsNotYetReturn.addAll(ItemService.getReturnableItemsBySO(((SalesOrder) passObj.getObj()).getCode()));
                    }

                }

                if (passObj.getCrud().equals(BasicObjs.read)) {
                    isViewMode(true);
                }
            }
        });
    }

    /*
    Logic Step to get back what been deliver
    1. track DO with SO
    2. track RDN with SO from DO
        This will use up product and qty on item obj information
    3. show the user to use the control panel
    4.

     */
    private void initializeComboSelections() {
        ((MFXComboBox<WarehouseRules.RDNStatus>) this.cmbStatus).setItems(FXCollections.observableList(warehouseRules.getRdnStatuses()));
    }

    private void setupItemTable() {
        // Product Code
        // Part No.
        // Quantity
        // Reason
        // Remark/ Additional Description

        // Product Code
        MFXTableColumn<Item> prodIDCol = new MFXTableColumn<>("Product ID", true, Comparator.comparing(item -> item.getProduct() == null ? "" : item.getProduct().getProdID()));
        // Part No.
        MFXTableColumn<Item> partNoCol = new MFXTableColumn<>("Part No.", true, Comparator.comparing(item -> item.getProduct() == null ? "" : item.getProduct().getPartNo()));
        // Quantity
        MFXTableColumn<Item> qtyCol = new MFXTableColumn<>("Quantity", true, Comparator.comparing(item -> item.getQty()));
        // Reason
        MFXTableColumn<Item> reasonCol = new MFXTableColumn<>("Reason", true, Comparator.comparing(item -> item.getReason()));
        // Remark/ Additional Description
        MFXTableColumn<Item> remarksCol = new MFXTableColumn<>("Remarks", true, Comparator.comparing(item -> item.getRemark()));

        // Product Code
        prodIDCol.setRowCellFactory(i -> new MFXTableRowCell<>(item -> item.getProduct() == null ? "" : item.getProduct().getProdID()));
        // Part No.
        partNoCol.setRowCellFactory(i -> new MFXTableRowCell<>(item -> item.getProduct() == null ? "" : item.getProduct().getPartNo()));
        // Quantity
        qtyCol.setRowCellFactory(i -> new MFXTableRowCell<>(item -> item.getQty()));
        // Reason
        reasonCol.setRowCellFactory(i -> new MFXTableRowCell<>(item -> item.getReason()));
        // Remark/ Additional Description
        remarksCol.setRowCellFactory(i -> new MFXTableRowCell<>(item -> item.getRemark()));

        ((MFXTableView<Item>) tblVw).getTableColumns().clear();
        ((MFXTableView<Item>) tblVw).getTableColumns().addAll(
                prodIDCol,
                partNoCol,
                qtyCol,
                reasonCol,
                remarksCol
        );

        ((MFXTableView<Item>) tblVw).getFilters().clear();

        ((MFXTableView<Item>) tblVw).getFilters().addAll(
                new StringFilter<>("Product ID", item -> item.getProduct() == null ? "" : item.getProduct().getProdID()),
                new StringFilter<>("Part No.", item -> item.getProduct() == null ? "" : item.getProduct().getPartNo()),
                new IntegerFilter<>("Quantity", item -> item.getQty()),
                new StringFilter<>("Reason", item -> item.getReason()),
                new StringFilter<>("Remarks", item -> item.getRemark())
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
                                passObj.setObjs((List<Object>) (Object) itemsNotYetReturn);
                                passObj.setCrud(BasicObjs.read);

                                stage.setUserData(passObj);
                                stage.showAndWait();

                                // if have any change on the selected item
                                if (stage.getUserData() != null) {

                                    BasicObjs receiveObj = (BasicObjs) stage.getUserData();
                                    Item catchedItem = new Item();
                                    catchedItem = ((Item) receiveObj.getObj()).clone();

                                    adjustItemsNotYetTransfer(catchedItem);
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

    private void adjustItemsNotYetTransfer(Item catchedItem) {
        if (catchedItem.getProduct() == null) {//remove
            Item itemInReturnable = itemsNotYetReturn.get(itemsNotYetReturn.indexOf(catchedItem));
            Item itemInRDN = (Item) items.get(items.indexOf(catchedItem));

            itemInReturnable.setQty(itemInReturnable.getQty() + itemInRDN.getQty());
            items.remove(catchedItem);
        } else if (!items.contains(catchedItem)) { //add
            items.add(catchedItem);

            Item itemInReturnable = itemsNotYetReturn.get(itemsNotYetReturn.indexOf(catchedItem));
            Item itemInRDN = (Item) items.get(items.indexOf(catchedItem));

            itemInRDN.setOriQty(0);
            itemInReturnable.setQty(itemInReturnable.getQty() - itemInRDN.getQty() + itemInRDN.getOriQty());

        } else { // update
            //remove
            Item itemInReturnable = itemsNotYetReturn.get(itemsNotYetReturn.indexOf(catchedItem));
            Item itemInRDN = (Item) items.get(items.indexOf(catchedItem));

            itemInReturnable.setQty(itemInReturnable.getQty() + itemInRDN.getQty());
            items.remove(catchedItem);

            //add
            items.add(catchedItem);

            itemInReturnable = itemsNotYetReturn.get(itemsNotYetReturn.indexOf(catchedItem));
            itemInRDN = (Item) items.get(items.indexOf(catchedItem));

            itemInRDN.setOriQty(0);
            itemInReturnable.setQty(itemInReturnable.getQty() - itemInRDN.getQty() + itemInRDN.getOriQty());
        }
        setupItemTable();
    }

    private void fieldFillIn() throws IOException {
        clearAllFieldsValue();
        if (passObj.getObj() != null) {
            if (passObj.getObj() instanceof ReturnDeliveryNote) {
                ReturnDeliveryNote rdn = new ReturnDeliveryNote();
                this.txtRDNID.setText(rdn.getCode());
                this.txtCllctBckFr.setText(rdn.getCollBckFr() == null ? "" : rdn.getCollBckFr().getCollectAddrID());
                this.txtCllctBckTo.setText(rdn.getCollBackTo() == null ? "" : rdn.getCollBackTo().getPlaceID());
                this.dtRefDate.setValue(rdn.getCreatedDate() == null ? null : Instant.ofEpochMilli(rdn.getCreatedDate().getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate());
                this.txtSORef.setText(rdn.getSO() == null ? "" : rdn.getSO().getCode());
                this.dtCllctDate.setValue(rdn.getCollectDate() == null ? null : Instant.ofEpochMilli(rdn.getCollectDate().getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate());
                this.cmbStatus.setText(rdn.getStatus());
                this.txtInspectorMsg.setText(rdn.getInspectorMsg());

                this.txtIssuedBy.setText(rdn.getIssuedBy() == null ? "" : rdn.getIssuedBy().getStaffID());
                this.txtInspectedBy.setText(rdn.getInspectedBy() == null ? "" : rdn.getInspectedBy().getStaffID());
                this.txtCollectBackBy.setText(rdn.getCollectBackBy() == null ? "" : rdn.getCollectBackBy().getStaffID());
                this.txtItemPassedBckBy.setText(rdn.getItemPassedBackBy() == null ? "" : rdn.getItemPassedBackBy().getCollectAddrID());
                this.txtItemReceivedBy.setText(rdn.getItemReceivedBy() == null ? "" : rdn.getItemReceivedBy().getStaffID());

            } else if (passObj.getObj() instanceof SalesOrder) {

                SalesOrder salesOrder = new SalesOrder();
                this.txtSORef.setText(salesOrder.getCode());
            }
        }
    }

    private void isViewMode(boolean disable) {
        if (disable == true) {
            this.btnSave.setText("Update");
        } else {
            this.btnSave.setText("Save");
        }

        this.txtRDNID.setDisable(disable);
        this.txtCllctBckFr.setDisable(disable);
        this.txtCllctBckTo.setDisable(disable);
        this.dtRefDate.setDisable(disable);
        this.txtSORef.setDisable(disable);
        this.dtCllctDate.setDisable(disable);
        this.cmbStatus.setDisable(disable);
        this.txtInspectorMsg.setDisable(disable);

        this.ctnCllctBckFrSelection.setDisable(disable);
        this.ctnCllctBckToSelection.setDisable(disable);
        this.ctnSORefSelection.setDisable(disable);

        this.btnAdd.setDisable(disable);
        this.tblVw.setDisable(disable);
        this.imgDocs.setDisable(disable);

        this.txtIssuedBy.setDisable(disable);
        this.txtInspectedBy.setDisable(disable);
        this.txtCollectBackBy.setDisable(disable);
        this.txtItemPassedBckBy.setDisable(disable);
        this.txtItemReceivedBy.setDisable(disable);

        this.ctnIssuedBySelection.setDisable(disable);
        this.ctnInspectedBySelection.setDisable(disable);
        this.ctnCllctBckBySelection.setDisable(disable);
        this.ctnItemPassedBckBySelection.setDisable(disable);
        this.ctnItemReceivedBySelection.setDisable(disable);
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
                passObj.getFxmlPaths().addLast("View/ReturnDeliveryNote_UI.fxml");
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
        //this.txtRDNID.clear();
        this.txtCllctBckFr.clear();
        this.txtCllctBckTo.clear();
        this.dtRefDate.clear();
        this.txtSORef.clear();
        this.dtCllctDate.clear();
        this.cmbStatus.clear();
        this.txtInspectorMsg.clear();
        this.txtIssuedBy.clear();
        this.txtInspectedBy.clear();
        this.txtCollectBackBy.clear();
        this.txtItemPassedBckBy.clear();
        this.txtItemReceivedBy.clear();

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

    private ReturnDeliveryNote prepareTransferOrderToObj() {
        ReturnDeliveryNote rdn = new ReturnDeliveryNote();
        rdn.setCode(this.txtRDNID.getText());

        CollectAddress collectBckFr = new CollectAddress();
        collectBckFr.setCollectAddrID(this.txtCllctBckTo.getText());
        rdn.setCollBckFr(collectBckFr);

        Place collBackTo = new Place();
        collBackTo.setPlaceID(this.txtCllctBckTo.getText());
        rdn.setCollBackTo(collBackTo);

        rdn.setCreatedDate(this.dtRefDate.getValue() == null ? null : Timestamp.valueOf(this.dtRefDate.getValue().atStartOfDay()));

        SalesOrder salesOrder = new SalesOrder();
        salesOrder.setCode(this.txtSORef.getText());
        rdn.setSO(salesOrder);

        rdn.setCollectDate(this.dtCllctDate.getValue() == null ? null : java.sql.Date.valueOf(this.dtCllctDate.getValue()));
        rdn.setStatus(this.cmbStatus.getText());

        rdn.setInspectorMsg(this.txtInspectorMsg.getText());

        Staff issuedBy = new Staff();
        issuedBy.setStaffID(this.txtIssuedBy.getText());
        rdn.setIssuedBy(issuedBy);

        Staff inspectedBy = new Staff();
        inspectedBy.setStaffID(this.txtInspectedBy.getText());
        rdn.setInspectedBy(inspectedBy);

        Staff collectBackBy = new Staff();
        collectBackBy.setStaffID(this.txtCollectBackBy.getText());
        rdn.setCollectBackBy(collectBackBy);

        CollectAddress itemPassedBackBy = new CollectAddress();
        itemPassedBackBy.setCollectAddrID(this.txtItemPassedBckBy.getText());
        rdn.setItemPassedBackBy(itemPassedBackBy);

        Staff itemReceivedBy = new Staff();
        itemReceivedBy.setStaffID(this.txtItemReceivedBy.getText());
        rdn.setItemReceivedBy(itemReceivedBy);

        rdn.setItems(items);

        return rdn;
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
    private void openInspectedBySelection(MouseEvent event) {
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
                    this.txtInspectedBy.setText(((Staff) receiveObj.getObj()).getStaffID());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @FXML
    private void openCllctBckBySelection(MouseEvent event) {
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
                    this.txtCollectBackBy.setText(((Staff) receiveObj.getObj()).getStaffID());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @FXML
    private void openItemPassedBckBySelection(MouseEvent event) {
        if (event.isPrimaryButtonDown() == true) {

            Parent root;
            try {
                root = FXMLLoader.load(getClass().getClassLoader().getResource("View/InnerEntitySelect_UI.fxml"));
                Stage stage = new Stage();
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(btnBack.getScene().getWindow());
                stage.setScene(new Scene(root));

                BasicObjs passObj = new BasicObjs();
                passObj.setObj(new CollectAddress());

                stage.setUserData(passObj);
                stage.showAndWait();

                if (stage.getUserData() != null) {
                    BasicObjs receiveObj = (BasicObjs) stage.getUserData();
                    this.txtItemPassedBckBy.setText(((CollectAddress) receiveObj.getObj()).getCollectAddrID());
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
    private void openSORefSelection(MouseEvent event) {
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
                        // condition to check SO status haven't implemented
                        this.passObj.setObj(so);
                        this.fieldFillIn();
                    } else {
                        alertDialog(Alert.AlertType.INFORMATION,
                                "Information",
                                "Document Blocked Message",
                                "Sales Order with COMPLETED/ ON_HOLD status are not allowed to become any document reference.");
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @FXML
    private void goImgViewer(MouseEvent event
    ) {
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
    private void addProductItem(MouseEvent event
    ) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("View/InnerEntitySelectWithItemsProvided_UI.fxml"));

            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(btnBack.getScene().getWindow());
            stage.setScene(new Scene(root));

            BasicObjs passObj = new BasicObjs();
            passObj.setCrud(BasicObjs.create);

            Item<ReturnDeliveryNote> item = new Item();
            item.setRefDoc((ReturnDeliveryNote) this.passObj.getObj());

            passObj.setObj(item);
            passObj.setObjs((List<Object>) (Object) itemsNotYetReturn);
            stage.setUserData(passObj);
            stage.showAndWait();

            // if have any change on the selected item
            if (stage.getUserData() != null) {

                BasicObjs receiveObj = (BasicObjs) stage.getUserData();
                Item catchedItem = ((Item) receiveObj.getObj()).clone();

                adjustItemsNotYetTransfer(catchedItem);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void saveRDN(MouseEvent event
    ) throws SQLException {
        if (event.isPrimaryButtonDown() == true) {

            if (!this.btnSave.getText().equals("Save")) {
                isViewMode(false);
                return;
            }

            if (validator.containsErrors()) {
                alertDialog(Alert.AlertType.WARNING, "Warning", "Validation Message", validator.createStringBinding().getValue());
                return;
            }

            rdnInDraft = this.prepareTransferOrderToObj();

            if (this.txtRDNID.getText().isEmpty()) {
                rdnInDraft.setCode(RDNService.saveNewRDN(rdnInDraft));

            } else if (this.passObj.getCrud().equals(BasicObjs.update)) {
                RDNService.updateRDN(rdnInDraft);
            }

            for (Item item : rdnInDraft.getItems()) {
                item.setOriQty(item.getQty());
            }

            ItemService.updateItemsByDoc(rdnInDraft.getItems(), rdnInDraft.getCode());

            updateSOStatus();
        }
    }

    private void updateSOStatus() {
        // if approved change to in progress
    }

}
