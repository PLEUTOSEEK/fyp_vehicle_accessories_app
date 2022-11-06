/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import BizRulesConfiguration.WarehouseRules;
import Entity.Item;
import Entity.ReturnDeliveryNote;
import Entity.SalesOrder;
import PassObjs.BasicObjs;
import Service.ItemService;
import Service.RDNService;
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
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class ReturnDerliveryNoteCONTR implements Initializable, BasicCONTRFunc {

    private BasicObjs passObj;
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
    private MFXTextField itemPassedBckBy;
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
//</editor-fold>

    private WarehouseRules warehouseRules = new WarehouseRules();

    private List<Item> itemsNotYetReturn = new ArrayList<>();

    private List<Item> items = new ArrayList<>(); // use to know which Item been update, and perform update action for those modified address

    private List<Item> tempItems = new ArrayList<>(); // use to know which Item been update, and perform update action for those modified address

    private static List<String> rowSelected = new ArrayList<>();

    private ReturnDeliveryNote rdnInDraft;

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
                        itemsNotYetReturn.addAll(RDNService.getItemByRDNID(((ReturnDeliveryNote) passObj.getObj()).getCode()));

                    } else if (passObj.getObj() instanceof SalesOrder) {
                        itemsNotYetReturn.addAll(ItemService.getReturnableItemsBySO(((SalesOrder) passObj.getObj()).getCode()));
                    }

                    //deep copy
                    for (Item i : itemsNotYetReturn) {
                        items.add(i.clone());
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
        MFXTableColumn<Item> prodIDCol = new MFXTableColumn<>("Product ID", true, Comparator.comparing(item -> item.getProduct().getProdID()));
        // Part No.
        MFXTableColumn<Item> partNoCol = new MFXTableColumn<>("Part No.", true, Comparator.comparing(item -> item.getProduct().getPartNo()));
        // Quantity
        MFXTableColumn<Item> qtyCol = new MFXTableColumn<>("Quantity", true, Comparator.comparing(item -> item.getQty()));
        // Reason
        MFXTableColumn<Item> reasonCol = new MFXTableColumn<>("Reason", true, Comparator.comparing(item -> item.getReason()));
        // Remark/ Additional Description
        MFXTableColumn<Item> remarksCol = new MFXTableColumn<>("Remarks", true, Comparator.comparing(item -> item.getRemark()));

        // Product Code
        prodIDCol.setRowCellFactory(i -> new MFXTableRowCell<>(item -> item.getProduct().getProdID()));
        // Part No.
        partNoCol.setRowCellFactory(i -> new MFXTableRowCell<>(item -> item.getProduct().getPartNo()));
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
                new StringFilter<>("Product ID", item -> item.getProduct().getProdID()),
                new StringFilter<>("Part No.", item -> item.getProduct().getPartNo()),
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
                            System.out.println(item.getProduct().getProdID() + "This is product ID");
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
                                passObj.setCrud(BasicObjs.read);

                                stage.setUserData(passObj);
                                stage.showAndWait();

                                // if have any change on the selected item
                                if (stage.getUserData() != null) {

                                    BasicObjs receiveObj = (BasicObjs) stage.getUserData();
                                    Item catchedItem = new Item();
                                    catchedItem = (Item) receiveObj.getObj();

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

    }

    private void isViewMode(boolean disable) {

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

    private ReturnDeliveryNote prepareTransferOrderToObj() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @FXML
    private void openIssuedBySelection(MouseEvent event) {
    }

    @FXML
    private void openInspectedBySelection(MouseEvent event) {
    }

    @FXML
    private void openCllctBckBySelection(MouseEvent event) {
    }

    @FXML
    private void openItemPassedBckBySelection(MouseEvent event) {
    }

    @FXML
    private void openItemReceivedBySelection(MouseEvent event) {
    }

    @FXML
    private void addProductItem(MouseEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("View/InnerEntitySelect_UI.fxml"));

            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(btnBack.getScene().getWindow());
            stage.setScene(new Scene(root));

            BasicObjs passObj = new BasicObjs();
            passObj.setCrud(BasicObjs.create);

            Item<ReturnDeliveryNote> item = new Item();
            item.setRefDoc((ReturnDeliveryNote) this.passObj.getObj());
            passObj.setObj(item);

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
    private void saveRDN(MouseEvent event) {
    }

}
