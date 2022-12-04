/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import Entity.Inventory;
import Entity.Product;
import Entity.Staff;
import PassObjs.BasicObjs;
import Service.GeneralRulesService;
import Service.InventoryService;
import io.github.palexdev.materialfx.controls.MFXCircleToggleNode;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Tee Zhuo Xuan
 */
public class ViewInventoryCONTR implements Initializable {

    @FXML
    private Label lblProdID;
    @FXML
    private Label lblPartNo;
    @FXML
    private Label lblColor;
    @FXML
    private Label lblSellPrice;
    @FXML
    private Label lblMSRP;
    @FXML
    private Label lblMaxLvl;
    @FXML
    private Label lblAvgLvl;
    @FXML
    private Label lblMinLvl;
    @FXML
    private Label lblDangerLvl;
    @FXML
    private Label lblReorderLvl;
    @FXML
    private MFXCircleToggleNode btnBack;
    @FXML
    private MFXTableView<?> tblInventoryTable;

    private BasicObjs passObj;
    @FXML
    private Label lblDesc;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    receiveData();
                    autoClose();
                    fieldFillIn();
                    forInventory();
                    tblInventoryTable.autosizeColumnsOnInitialization();
                } catch (Exception ex) {
                    Logger.getLogger(InnerEntitySelectCONTR.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void fieldFillIn() throws IOException {
        if (passObj.getObj() != null) {
            //fill all data column with object information

            Product p = (Product) passObj.getObj();

            this.lblProdID.setText(p.getProdID());
            this.lblPartNo.setText(p.getPartNo());
            this.lblColor.setText(p.getColor());
            this.lblSellPrice.setText(p.getSellPrice().toString());
            this.lblMSRP.setText(p.getMSRP().toString());
            this.lblMaxLvl.setText(p.getMaxLvl().toString());
            this.lblAvgLvl.setText(p.getAvgLvl().toString());
            this.lblMinLvl.setText(p.getMinLvl().toString());
            this.lblDangerLvl.setText(p.getDangerLvl().toString());
            this.lblReorderLvl.setText(p.getReorderLvl().toString());
            this.lblDesc.setText(p.getDescription().toString());
        }
    }

    private void forInventory() {
        // Inventory ID
        // Place ID
        // Reserved Qty
        // Ready Qty
        // Total Qty
        // Place ID
        // Place Name
        // Location Name
        // Address
        // City
        // State

        // Inventory ID
        MFXTableColumn<Inventory> inventoryIDCol = new MFXTableColumn<>("Inventory ID", true, Comparator.comparing(inventory -> inventory.getInventoryID()));
        // Reserved Qty
        MFXTableColumn<Inventory> reservedQtyCol = new MFXTableColumn<>("Reserved Qty", true, Comparator.comparing(inventory -> inventory.getReservedQty()));
        // Ready Qty
        MFXTableColumn<Inventory> readyQtyCol = new MFXTableColumn<>("Ready Qty", true, Comparator.comparing(inventory -> inventory.getReadyQty()));
        // Total Qty
        MFXTableColumn<Inventory> ttlQtyCol = new MFXTableColumn<>("Total Qty", true, Comparator.comparing(inventory -> inventory.getTtlQty()));
        // Place ID
        MFXTableColumn<Inventory> placeIDCol = new MFXTableColumn<>("Place ID", true, Comparator.comparing(inventory -> inventory.getStorePlace().getPlaceID()));
        // Place Name
        MFXTableColumn<Inventory> placeNmCol = new MFXTableColumn<>("Place Name", true, Comparator.comparing(inventory -> inventory.getStorePlace().getPlaceName()));
        // Location Name
        MFXTableColumn<Inventory> locationNmCol = new MFXTableColumn<>("Location Name", true, Comparator.comparing(inventory -> inventory.getStorePlace().getPlaceAddr().getLocationName()));
        // Address
        MFXTableColumn<Inventory> addrCol = new MFXTableColumn<>("Address", true, Comparator.comparing(inventory -> inventory.getStorePlace().getPlaceAddr().getAddress()));
        // City
        MFXTableColumn<Inventory> cityCol = new MFXTableColumn<>("City", true, Comparator.comparing(inventory -> inventory.getStorePlace().getPlaceAddr().getCity()));
        // State
        MFXTableColumn<Inventory> stateCol = new MFXTableColumn<>("State", true, Comparator.comparing(inventory -> inventory.getStorePlace().getPlaceAddr().getState()));

        // Inventory ID
        inventoryIDCol.setRowCellFactory(invt -> new MFXTableRowCell<>(inventory -> inventory.getInventoryID()));
        // Reserved Qty
        reservedQtyCol.setRowCellFactory(invt -> new MFXTableRowCell<>(inventory -> inventory.getReservedQty()));
        // Ready Qty
        readyQtyCol.setRowCellFactory(invt -> new MFXTableRowCell<>(inventory -> inventory.getReadyQty()));
        // Total Qty
        ttlQtyCol.setRowCellFactory(invt -> new MFXTableRowCell<>(inventory -> inventory.getTtlQty()));
        // Place ID
        placeIDCol.setRowCellFactory(invt -> new MFXTableRowCell<>(inventory -> inventory.getStorePlace().getPlaceID()));
        // Place Name
        placeNmCol.setRowCellFactory(invt -> new MFXTableRowCell<>(inventory -> inventory.getStorePlace().getPlaceName()));
        // Location Name
        locationNmCol.setRowCellFactory(invt -> new MFXTableRowCell<>(inventory -> inventory.getStorePlace().getPlaceAddr().getLocationName()));
        // Address
        addrCol.setRowCellFactory(invt -> new MFXTableRowCell<>(inventory -> inventory.getStorePlace().getPlaceAddr().getAddress()));
        // City
        cityCol.setRowCellFactory(invt -> new MFXTableRowCell<>(inventory -> inventory.getStorePlace().getPlaceAddr().getCity()));
        // State
        stateCol.setRowCellFactory(invt -> new MFXTableRowCell<>(inventory -> inventory.getStorePlace().getPlaceAddr().getState()));

        ((MFXTableView<Inventory>) tblInventoryTable).getTableColumns().addAll(
                inventoryIDCol,
                reservedQtyCol,
                readyQtyCol,
                ttlQtyCol,
                placeIDCol,
                placeNmCol,
                locationNmCol,
                addrCol,
                cityCol,
                stateCol
        );

        ((MFXTableView<Inventory>) tblInventoryTable).getFilters().addAll(
                new StringFilter<>("Inventory ID", inventory -> inventory.getInventoryID()),
                new IntegerFilter<>("Reserved Qty", inventory -> inventory.getReservedQty()),
                new IntegerFilter<>("Ready Qty", inventory -> inventory.getReadyQty()),
                new IntegerFilter<>("Total Qty", inventory -> inventory.getTtlQty()),
                new StringFilter<>("Place ID", inventory -> inventory.getStorePlace().getPlaceID()),
                new StringFilter<>("Place Name", inventory -> inventory.getStorePlace().getPlaceName()),
                new StringFilter<>("Location Name", inventory -> inventory.getStorePlace().getPlaceAddr().getLocationName()),
                new StringFilter<>("Address", inventory -> inventory.getStorePlace().getPlaceAddr().getAddress()),
                new StringFilter<>("City", inventory -> inventory.getStorePlace().getPlaceAddr().getCity()),
                new StringFilter<>("State", inventory -> inventory.getStorePlace().getPlaceAddr().getState())
        );

        List<Inventory> inventories = InventoryService.getInventoryByProdID(((Product) passObj.getObj()).getProdID());

        ((MFXTableView<Inventory>) tblInventoryTable).setItems(FXCollections.observableList(inventories));

    }

    @FXML
    private void goBackPrevious(MouseEvent event) {
        if (event.isPrimaryButtonDown() == true) {
            switchScene(passObj.getFxmlPaths().getLast().toString(), passObj, BasicObjs.back);
        }
    }

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
        transitionAlert.setOnFinished(evt -> {
            this.passObj.setLoginStaff(new Staff());
            switchScene("View/Login_UI.fxml", passObj, BasicObjs.back);
        });
        transitionAlert.setCycleCount(1);

        btnBack.getScene().addEventFilter(InputEvent.ANY, evt -> transitionAlert.playFromStart());

    }

    public void switchScene(String fxmlPath, BasicObjs passObj, String direction) {
        // Step 3
        Stage stage = (Stage) this.btnBack.getScene().getWindow();
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

    public BasicObjs sendData(BasicObjs passObj, String direction) {
        switch (direction) {
            case BasicObjs.forward:
                passObj.getFxmlPaths().addLast("View/ViewInventory_UI.fxml");
                break;
        }
        passObj.setPassDirection(direction);
        passObj.setLoginStaff(this.passObj.getLoginStaff());
        return passObj;
    }

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

}
