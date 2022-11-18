/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import BizRulesConfiguration.WarehouseRules;
import Entity.Item;
import Entity.PackingSlip;
import Entity.TransferOrder;
import PassObjs.BasicObjs;
import Service.ItemService;
import Service.PackingSlipService;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCircleToggleNode;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.synedra.validatorfx.Validator;

/**
 * FXML Controller class
 *
 * @author Tee Zhuo Xuan
 */
public class PackingSlipCONTR implements Initializable, BasicCONTRFunc {

    //<editor-fold defaultstate="collapsed" desc="Fields">
    @FXML
    private MFXCircleToggleNode btnBack;
    @FXML
    private MFXTextField txtPSID;
    @FXML
    private MFXTextField txtTORef;
    @FXML
    private MFXCircleToggleNode ctnTOSelection;
    @FXML
    private MFXTableView<?> tblVw;
    @FXML
    private MFXButton btnAdd;
    @FXML
    private MFXButton btnSave;
    @FXML
    private MFXButton btnDiscard;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="util declarations">
    private BasicObjs passObj;

    private Validator validator = new Validator();

    private List<Item> itemsNotYetPack = new ArrayList<>(); // use to know which Item been update, and perform update action for those modified address

    private List<Item> items = new ArrayList<>(); // use to know which Item been update, and perform update action for those modified address

    private List<Item> tempItems = new ArrayList<>(); // use to know which Item been update, and perform update action for those modified address

    private static List<String> rowSelected = new ArrayList<>();

    private PackingSlip packingSlipInDraft;
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
                receiveData();
                if (passObj.getCrud().equals(BasicObjs.read) || passObj.getCrud().equals(BasicObjs.update)) {
                    try {
                        fieldFillIn();
                    } catch (IOException ex) {
                        java.util.logging.Logger.getLogger(TransferOrderCONTR.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    }

                    //transfer order compulsory must based on SO to generate for Order-To-Cash Process
                    itemsNotYetPack.addAll(ItemService.getItemByTOID(((TransferOrder) passObj.getObj()).getCode()));

                }

                if (passObj.getCrud().equals(BasicObjs.read)) {
                    isViewMode(true);
                }
            }
        });
    }

    private void setupItemTable() {
        // Product ID
        MFXTableColumn<Item> prodIDCol = new MFXTableColumn<>("Product ID", true, Comparator.comparing(item -> item.getProduct() == null ? null : item.getProduct().getProdID()));
        // Part No
        MFXTableColumn<Item> partNoCol = new MFXTableColumn<>("Part No.", true, Comparator.comparing(item -> item.getProduct() == null ? null : item.getProduct().getPartNo()));
        // Qty
        MFXTableColumn<Item> qtyCol = new MFXTableColumn<>("Qty", true, Comparator.comparing(item -> item.getQty()));

        // Product ID
        prodIDCol.setRowCellFactory(i -> new MFXTableRowCell<>(item -> item.getProduct() == null ? null : item.getProduct().getProdID()));
        // Part No
        partNoCol.setRowCellFactory(i -> new MFXTableRowCell<>(item -> item.getProduct() == null ? null : item.getProduct().getPartNo()));
        // Qty
        qtyCol.setRowCellFactory(i -> new MFXTableRowCell<>(item -> item.getQty()));

        ((MFXTableView<Item>) tblVw).getTableColumns().clear();

        ((MFXTableView<Item>) tblVw).getTableColumns().addAll(
                prodIDCol,
                partNoCol,
                qtyCol
        );

        ((MFXTableView<Item>) tblVw).getFilters().clear();

        ((MFXTableView<Item>) tblVw).getFilters().addAll(
                new StringFilter<>("Product ID", item -> item.getProduct() == null ? null : item.getProduct().getProdID()),
                new StringFilter<>("Part No.", item -> item.getProduct() == null ? null : item.getProduct().getPartNo()),
                new IntegerFilter<>("Qty", item -> item.getQty())
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
                            Item itemNotYetPack = itemsNotYetPack.get(itemsNotYetPack.indexOf(item));
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
                                passObj.setObjs((List<Object>) (Object) itemNotYetPack);
                                passObj.setCrud(BasicObjs.read);

                                stage.setUserData(passObj);
                                stage.showAndWait();

                                // if have any change on the selected item
                                if (stage.getUserData() != null) {

                                    BasicObjs receiveObj = (BasicObjs) stage.getUserData();
                                    Item catchedItem = new Item();
                                    catchedItem = ((Item) receiveObj.getObj()).clone();

                                    // remember to perform slow change for qty move to original quantity on UI [View/TOPSSelect_UI.fxml]
                                    adjustItemsNotYetPack(catchedItem, item);
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

    private void adjustItemsNotYetPack(Item catchedItem, Item item) {

        //=============
        if (catchedItem == null) {
            Item itemInSO = itemsNotYetPack.get(itemsNotYetPack.indexOf(item));
            Item itemInTO = (Item) items.get(items.indexOf(item));

            itemInSO.setQty(itemInSO.getQty() + itemInTO.getQty());
            items.remove(item);
        } else if (!items.contains(catchedItem)) { //add
            items.add(catchedItem);

            Item itemInTO = itemsNotYetPack.get(itemsNotYetPack.indexOf(catchedItem));
            Item itemInPS = (Item) items.get(items.indexOf(catchedItem));

            itemInPS.setOriQty(0);
            itemInTO.setQty(itemInTO.getQty() - itemInPS.getQty() + itemInPS.getOriQty());

        } else { // update
            //remove
            Item itemInTO = itemsNotYetPack.get(itemsNotYetPack.indexOf(catchedItem));
            Item itemInPS = (Item) items.get(items.indexOf(catchedItem));

            itemInTO.setQty(itemInTO.getQty() + itemInPS.getQty());
            items.remove(catchedItem);

            //add
            items.add(catchedItem);

            itemInTO = itemsNotYetPack.get(itemsNotYetPack.indexOf(catchedItem));
            itemInPS = (Item) items.get(items.indexOf(catchedItem));

            itemInTO.setQty(itemInTO.getQty() - itemInPS.getQty() + itemInPS.getOriQty());
        }
        setupItemTable();
    }

    private void fieldFillIn() throws IOException {
        clearAllFieldsValue();

        if (passObj.getObj() != null) {
            if (passObj.getObj() instanceof TransferOrder) {

                TransferOrder to = (TransferOrder) passObj.getObj();
                this.txtTORef.setText(to.getCode());

            } else if (passObj.getObj() instanceof PackingSlip) {

                PackingSlip ps = (PackingSlip) passObj.getObj();

                this.txtPSID.setText(ps.getCode());
                this.txtTORef.setText(ps.getTO() == null ? "" : ps.getTO().getCode());
            }

        }
    }

    private void isViewMode(boolean disable) {
        if (disable == true) {
            this.btnSave.setText("Update");
        } else {
            this.btnSave.setText("Save");
        }

        this.txtPSID.setDisable(disable);
        this.txtTORef.setDisable(disable);

        this.ctnTOSelection.setDisable(disable);

        this.btnAdd.setDisable(disable);
        this.tblVw.setDisable(disable);

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
                passObj.getFxmlPaths().addLast("View/PackingSlip_UI.fxml");
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
        //this.txtPSID.clear();
        this.txtTORef.clear();

        items.clear();
        this.itemsNotYetPack.clear();
        return true;
    }

    @Override
    public ButtonType alertDialog(Alert.AlertType alertType, String title,
            String headerTxt, String contentTxt) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerTxt);
        alert.setContentText(contentTxt);

        alert.showAndWait();
        return alert.getResult();
    }

    private PackingSlip preparePackingSlipToObj() {
        PackingSlip packingSlip = new PackingSlip();
        packingSlip.setCode(this.txtPSID.getText());

        TransferOrder to = new TransferOrder();
        to.setCode(this.txtTORef.getText());
        packingSlip.setTO(to);

        packingSlip.setItems(items);

        return packingSlip;
    }

    @FXML

    private void openTOSelection(MouseEvent event) {
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
                passObj.setObj(new TransferOrder());

                stage.setUserData(passObj);
                stage.showAndWait();

                if (stage.getUserData() != null) {

                    BasicObjs receiveObj = (BasicObjs) stage.getUserData();
                    TransferOrder to = (TransferOrder) receiveObj.getObj();

                    if (to.getStatus().equals(WarehouseRules.TOStatus.NEW)) { // check Document Reference already or not
                        this.passObj.setObj(to);
                        fieldFillIn();

                        itemsNotYetPack.addAll(ItemService.getItemByTOID(to.getCode()));

                    } else {
                        alertDialog(Alert.AlertType.INFORMATION,
                                "Information",
                                "Document Blocked Message",
                                "Transfer Order without NEW status are not allowed to become any document reference.");
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @FXML
    private void addProductItem(MouseEvent event) {
        if (this.txtTORef.getText().isEmpty()) {
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
            passObj.setObj(new PackingSlip());
            passObj.setObjs((List<Object>) (Object) itemsNotYetPack);

            stage.setUserData(passObj);
            stage.showAndWait();

            // if have any change on the selected item
            if (stage.getUserData() != null) {

                BasicObjs receiveObj = (BasicObjs) stage.getUserData();
                Item catchedItem = (Item) receiveObj.getObj();

                adjustItemsNotYetPack(catchedItem, null);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void savePS(MouseEvent event) throws SQLException {
        if (event.isPrimaryButtonDown() == true) {

            if (!this.btnSave.getText().equals("Save")) {
                isViewMode(false);
                return;
            }

            if (!validator.validate()) {
                alertDialog(Alert.AlertType.WARNING, "Warning", "Validation Message", validator.createStringBinding().getValue());
                return;
            }

            packingSlipInDraft = this.preparePackingSlipToObj();

            if (this.txtPSID.getText().isEmpty()) {
                packingSlipInDraft.setCode(PackingSlipService.saveNewPackingSlip(packingSlipInDraft));

            } else {
                PackingSlipService.updatePackingSlip(packingSlipInDraft);
            }

            for (Item i : packingSlipInDraft.getItems()) {
                i.setOriQty(i.getQty());
            }

            ItemService.updateItemsByDoc(packingSlipInDraft.getItems(), packingSlipInDraft.getCode());

            updateRefDoc();
        }
    }

    private void updateRefDoc() throws SQLException {
        if (!this.txtTORef.getText().isEmpty()) {

            TransferOrder to = packingSlipInDraft.getTO();

            ItemService.updateItemsByDoc(this.itemsNotYetPack, to.getCode());

            // packing slip no need update the refer document (TO) status
        }
    }
}
