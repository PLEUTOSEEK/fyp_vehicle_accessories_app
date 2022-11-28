/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import BizRulesConfiguration.SalesRules;
import BizRulesConfiguration.WarehouseRules;
import Entity.CollectAddress;
import Entity.DeliveryOrder;
import Entity.PackingSlip;
import Entity.Place;
import Entity.SalesOrder;
import Entity.Staff;
import PassObjs.BasicObjs;
import Service.DeliveryOrderService;
import Service.GeneralRulesService;
import Service.PackingSlipService;
import Utils.ImageUtils;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCircleToggleNode;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.StringFilter;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.layout.AnchorPane;
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
public class DeliveryOrderCONTR implements Initializable, BasicCONTRFunc {

    //<editor-fold defaultstate="collapsed" desc="fields">
    @FXML
    private MFXCircleToggleNode btnBack;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private MFXDatePicker dtRefDate;
    @FXML
    private MFXTextField txtDeliverFrom;
    @FXML
    private MFXCircleToggleNode ctnDeliverFromSelection;
    @FXML
    private MFXComboBox<?> cmbStatus;
    @FXML
    private MFXDatePicker dtDlvrDt;
    @FXML
    private MFXButton btnAdd;
    @FXML
    private MFXCircleToggleNode ctnDeliveryBySelection;
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
    private MFXTextField txtDeliveryBy;
    @FXML
    private MFXTextField txtItemReceivedBy;
    @FXML
    private MFXCircleToggleNode ctnItemReceivedBySelection;
    @FXML
    private MFXButton btnSave;
    @FXML
    private MFXButton btnDiscard;
    @FXML
    private MFXTableView<?> itemTblVw;
    @FXML
    private MFXTableView<?> psTblVw;
    @FXML
    private MFXTextField txtDOID;
    @FXML
    private Label lblImgStrs;
    @FXML
    private MFXCircleToggleNode ctnSOSelection;
    @FXML
    private MFXTextField txtSORef;

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="util declarations">
    private BasicObjs passObj;

    private Validator validator = new Validator();

    WarehouseRules warehouseRules = new WarehouseRules();

    private List<PackingSlip> packingSlipsNotYetDeliver = new ArrayList<>(); // use to know which Item been update, and perform update action for those modified address

    private List<PackingSlip> packingSlips = new ArrayList<>(); // use to know which Item been update, and perform update action for those modified address

    private List<PackingSlip> tempPackingSlips = new ArrayList<>(); // use to know which Item been update, and perform update action for those modified address

    private static List<String> rowSelected = new ArrayList<>();

    private MFXTextField txtTORef;

    private DeliveryOrder doInDraft;
    //</editor-fold>
    @FXML
    private MFXTextField txtRef;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setupPackingTable();

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
                        Logger.getLogger(QuotationCONTR.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    // haven't verify the same product ID, must have different delivery date
                    if (passObj.getObj() instanceof DeliveryOrder) {
                        DeliveryOrder deliveryOrder = (DeliveryOrder) passObj.getObj();

                        packingSlipsNotYetDeliver.addAll(PackingSlipService.getPSsBySOID(deliveryOrder.getSo().getCode()));
                        packingSlips.addAll(PackingSlipService.getPSsByDOID(deliveryOrder.getCode()));

                    } else if (passObj.getObj() instanceof SalesOrder) {
                        packingSlipsNotYetDeliver.addAll(PackingSlipService.getPSsBySOID(((SalesOrder) passObj.getObj()).getCode()));
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
        this.passObj.setLoginStaff(new Staff());
        transitionAlert.setOnFinished(evt -> switchScene("View/Login_UI.fxml", passObj, BasicObjs.back));
        transitionAlert.setCycleCount(1);

        btnDiscard.getScene().addEventFilter(InputEvent.ANY, evt -> transitionAlert.playFromStart());
    }

    private void initializeComboSelections() {
        ((MFXComboBox<WarehouseRules.DOStatus>) this.cmbStatus).setItems(FXCollections.observableList(warehouseRules.getDoStatuses()));
    }

    public void setupPackingTable() {

        // Packing Slip Code
        MFXTableColumn<PackingSlip> packingSlipCodeCol = new MFXTableColumn<>("Packing Slip Code", true, Comparator.comparing(ps -> ps.getCode()));

        // Packing Slip Code
        packingSlipCodeCol.setRowCellFactory(i -> new MFXTableRowCell<>(ps -> ps.getCode()));

        ((MFXTableView<PackingSlip>) psTblVw).getTableColumns().clear();
        ((MFXTableView<PackingSlip>) psTblVw).getTableColumns().addAll(
                packingSlipCodeCol
        );

        ((MFXTableView<PackingSlip>) psTblVw).getFilters().clear();

        ((MFXTableView<PackingSlip>) psTblVw).getFilters().addAll(
                new StringFilter<>("Packing Slip Code", item -> item.getCode())
        );

        tempPackingSlips.addAll(packingSlips);
        ((MFXTableView<PackingSlip>) psTblVw).getItems().clear();
        ((MFXTableView<PackingSlip>) psTblVw).setItems(FXCollections.observableArrayList(tempPackingSlips));
        tempPackingSlips.clear();

        // pending re modification
        ((MFXTableView<PackingSlip>) psTblVw).getSelectionModel().selectionProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {

                if (((MFXTableView<PackingSlip>) psTblVw).getSelectionModel().getSelectedValues().size() != 0) {
                    PackingSlip packingSlip = (((MFXTableView<PackingSlip>) psTblVw).getSelectionModel().getSelectedValues().get(0));
                    rowSelected.add(packingSlip.getCode());

                    if (rowSelected.size() == 2) {
                        if (rowSelected.get(0).equals(rowSelected.get(1))) {
                            System.out.println(packingSlip.getCode() + "This is product ID");

                            // action here
                            Parent root;
                            try {
                                root = FXMLLoader.load(getClass().getClassLoader().getResource("View/PackingSlipSelect_UI.fxml"));
                                Stage stage = new Stage();
                                stage.initModality(Modality.WINDOW_MODAL);
                                stage.initOwner(btnBack.getScene().getWindow());
                                stage.setScene(new Scene(root));

                                BasicObjs passObj = new BasicObjs();
                                passObj.setObj(packingSlip);
                                passObj.setObjs((List<Object>) (Object) packingSlipsNotYetDeliver);
                                passObj.setCrud(BasicObjs.read);

                                stage.setUserData(passObj);
                                stage.showAndWait();

                                // if have any change on the selected item
                                if (stage.getUserData() != null) {

                                    BasicObjs receiveObj = (BasicObjs) stage.getUserData();
                                    PackingSlip catchedPackingSlip = new PackingSlip();
                                    catchedPackingSlip = ((PackingSlip) receiveObj.getObj()).clone();

                                    adjustPackingSlipNotYetDeliver(catchedPackingSlip, packingSlip);

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

    private void adjustPackingSlipNotYetDeliver(PackingSlip catchedPackingSlip, PackingSlip packingSlip) {
        //  SO = TO
        //  TO = DO
        if (catchedPackingSlip == null) {//remove

            PackingSlip packingSlipInTO = packingSlipsNotYetDeliver.get(packingSlipsNotYetDeliver.indexOf(packingSlip));

            packingSlipInTO.setStatus(WarehouseRules.PSStatus.NOT_YET_REFERED.toString());

            packingSlips.remove(packingSlip);

        } else if (!packingSlips.contains(catchedPackingSlip)) { //add

            packingSlips.add(catchedPackingSlip);

            PackingSlip packingSlipInTO = packingSlipsNotYetDeliver.get(packingSlipsNotYetDeliver.indexOf(catchedPackingSlip));

            packingSlipInTO.setStatus(WarehouseRules.PSStatus.REFERED.toString());

        } else { // update
            //remove
            PackingSlip packingSlipInTO = packingSlipsNotYetDeliver.get(packingSlipsNotYetDeliver.indexOf(catchedPackingSlip));

            packingSlipInTO.setStatus(WarehouseRules.PSStatus.NOT_YET_REFERED.toString());

            packingSlips.remove(catchedPackingSlip);
            //add
            packingSlips.add(catchedPackingSlip);

            packingSlipInTO = packingSlipsNotYetDeliver.get(packingSlipsNotYetDeliver.indexOf(catchedPackingSlip));

            packingSlipInTO.setStatus(WarehouseRules.PSStatus.REFERED.toString());
        }
        this.setupPackingTable();

    }

    private void defaultValFillIn() {
        this.dtDlvrDt.setValue(LocalDate.now());
        this.dtRefDate.setValue(LocalDate.now());
        this.cmbStatus.setText(WarehouseRules.DOStatus.NEW.toString());
    }

    private void fieldFillIn() throws IOException {
        clearAllFieldsValue();
        defaultValFillIn();

        if (passObj.getObj() != null) {

            if (passObj.getObj() instanceof DeliveryOrder) {
                DeliveryOrder d = (DeliveryOrder) passObj.getObj();

                this.txtDOID.setText(d.getCode());
                this.txtDeliverFrom.setText(d.getDeliverFr() == null ? "" : d.getDeliverFr().getPlaceID());
                this.dtDlvrDt.setValue(d.getDeliveryDate() == null ? null : Instant.ofEpochMilli(d.getDeliveryDate().getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate());
                this.dtRefDate.setValue(d.getCreatedDate() == null ? null : Instant.ofEpochMilli(d.getCreatedDate().getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate());
                this.txtSORef.setText(d.getSo() == null ? "" : d.getSo().getCode());
                this.cmbStatus.setText(d.getStatus());

                this.txtIssuedBy.setText(d.getIssuedBy() == null ? "" : d.getIssuedBy().getStaffID());
                this.txtReleasedAVerifiedBy.setText(d.getReleasedAVerifiedBy() == null ? "" : d.getReleasedAVerifiedBy().getStaffID());
                this.txtDeliveryBy.setText(d.getDeliveryBy() == null ? "" : d.getDeliveryBy().getStaffID());
                this.txtItemReceivedBy.setText(d.getItemReceivedBy() == null ? "" : d.getItemReceivedBy().getCollectAddrID());

            } else if (passObj.getObj() instanceof SalesOrder) {
                SalesOrder salesOrder = (SalesOrder) passObj.getObj();
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
        this.txtDOID.setDisable(disable);
        this.txtDeliverFrom.setDisable(disable);
        this.dtDlvrDt.setDisable(disable);
        this.dtRefDate.setDisable(disable);
        this.txtSORef.setDisable(disable);
        this.txtRef.setDisable(disable);
        this.cmbStatus.setDisable(disable);

        this.ctnDeliverFromSelection.setDisable(disable);
        this.ctnSOSelection.setDisable(disable);

        this.txtIssuedBy.setDisable(disable);
        this.txtReleasedAVerifiedBy.setDisable(disable);
        this.txtDeliveryBy.setDisable(disable);
        this.txtItemReceivedBy.setDisable(disable);

        this.ctnIssuedBySelection.setDisable(disable);
        this.ctnReleasedAVerifiedBySelection.setDisable(disable);
        this.ctnDeliveryBySelection.setDisable(disable);
        this.ctnItemReceivedBySelection.setDisable(disable);

        this.btnAdd.setDisable(disable);
        this.psTblVw.setDisable(disable);
        this.imgDocs.setImage(null);
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
                passObj.getFxmlPaths().addLast("View/DeliveryOrder_UI.fxml");
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
                .dependsOn("Deliver From", this.txtDeliverFrom.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Deliver From");
                    textVal = textVal.trim();

                    /*
                     1. cannot be null
                     */
                    if (textVal.isEmpty()) {
                        c.error("Deliver From - Required Field");
                        return;
                    }

                    return;
                })
                .decorates(this.txtDeliverFrom);

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
                .dependsOn("Delivery Date", this.dtDlvrDt.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Delivery Date");
                    textVal = textVal.trim();

                    /*
                     1. cannot be null
                     2. cannot early than reference date
                     */
                    if (textVal.isEmpty()) {
                        c.error("Delivery Date - Required Field");
                        return;
                    }

                    LocalDate date = LocalDate.parse(textVal, formatter);

                    if (date.isBefore(LocalDate.now())) {
                        c.error("Required Delivery Date - Cannot be before the current date");
                        return;
                    }
                })
                .decorates(this.dtDlvrDt);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("SO Ref.", this.txtSORef.textProperty()
                )
                .withMethod(c -> {
                    String textVal = c.get("SO Ref.");
                    textVal = textVal.trim();

                    /*
                     1. cannot be null
                     */
                    if (textVal.isEmpty()) {
                        c.error("SO Ref. - Required Field");

                        return;
                    }

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

                    if (packingSlips.size() <= 0) {
                        c.error("Packing Slip - At least one package are required to build a Delivery Order");
                        return;
                    }

                })
                .decorates(this.psTblVw);

        validator.add(validatorCheck);
        //=====================================
    }

    @Override
    public boolean clearAllFieldsValue() {
        //this.txtDOID.clear();
        this.txtDeliverFrom.clear();
        this.dtDlvrDt.clear();
        this.dtRefDate.clear();
        this.txtSORef.clear();
        this.txtRef.clear();
        this.cmbStatus.clear();
        this.txtIssuedBy.clear();
        this.txtReleasedAVerifiedBy.clear();
        this.txtDeliveryBy.clear();
        this.txtItemReceivedBy.clear();

        this.imgDocs.setImage(null);

        packingSlips.clear();
        packingSlipsNotYetDeliver.clear();
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

    private DeliveryOrder prepareDeliveryOrderToObj() {
        DeliveryOrder d = new DeliveryOrder();
        d.setCode(this.txtDOID.getText());

        Place deliverFr = new Place();
        deliverFr.setPlaceID(this.txtDeliverFrom.getText());
        d.setDeliverFr(deliverFr);

        d.setDeliveryDate(this.dtDlvrDt.getValue() == null ? null : java.sql.Date.valueOf(this.dtDlvrDt.getValue()));
        d.setCreatedDate(this.dtRefDate.getValue() == null ? null : Timestamp.valueOf(this.dtRefDate.getValue().atStartOfDay()));

        SalesOrder soRef = new SalesOrder();
        soRef.setCode(this.txtSORef.getText());
        d.setSo(soRef);

        d.setReference(this.txtRef.getText());
        d.setStatus(this.cmbStatus.getText());

        Staff issuedBy = new Staff();
        issuedBy.setStaffID(this.txtIssuedBy.getText());
        d.setIssuedBy(issuedBy);

        Staff releasedAVerifiedBy = new Staff();
        releasedAVerifiedBy.setStaffID(this.txtReleasedAVerifiedBy.getText());
        d.setReleasedAVerifiedBy(releasedAVerifiedBy);

        Staff deliveryBy = new Staff();
        deliveryBy.setStaffID(this.txtDeliveryBy.getText());
        d.setDeliveryBy(deliveryBy);

        CollectAddress itemReceivedBy = new CollectAddress();
        itemReceivedBy.setCollectAddrID(this.txtItemReceivedBy.getText());
        d.setItemReceivedBy(itemReceivedBy);

        d.setSignedDocPic(this.lblImgStrs.getText());
        d.setPackingSlips(packingSlips);
        return d;
    }

    @FXML
    private void openDeliverFromSelection(MouseEvent event) {
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
                    this.txtDeliverFrom.setText(((CollectAddress) receiveObj.getObj()).getCollectAddrID());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @FXML
    private void openSOSelection(MouseEvent event) {
        if (event.isPrimaryButtonDown() == true) {

            ButtonType alertBtnClicked = alertDialog(Alert.AlertType.CONFIRMATION,
                    "Confirmation",
                    "Modified Data Loss Alert",
                    "All modified data will be overwrite with selected document Information. Please select OK to proceed.");

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

                        fieldFillIn();

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
                    this.txtReleasedAVerifiedBy.setText(((Staff) receiveObj.getObj()).getStaffID());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @FXML
    private void openDeliveryBySelection(MouseEvent event) {
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
                    this.txtDeliveryBy.setText(((Staff) receiveObj.getObj()).getStaffID());
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
                passObj.setObj(new CollectAddress());

                stage.setUserData(passObj);
                stage.showAndWait();

                if (stage.getUserData() != null) {
                    BasicObjs receiveObj = (BasicObjs) stage.getUserData();
                    this.txtItemReceivedBy.setText(((CollectAddress) receiveObj.getObj()).getCollectAddrID());
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
            passObj.setObj(new DeliveryOrder());
            passObj.setObjs((List<Object>) (Object) packingSlipsNotYetDeliver);

            stage.setUserData(passObj);
            stage.showAndWait();

            // if have any change on the selected item
            if (stage.getUserData() != null) {

                BasicObjs receiveObj = (BasicObjs) stage.getUserData();
                PackingSlip catchedPackingSlip = new PackingSlip();
                catchedPackingSlip = ((PackingSlip) receiveObj.getObj()).clone();

                if (!catchedPackingSlip.getStatus().equals(WarehouseRules.PSStatus.REFERED.toString())) {
                    adjustPackingSlipNotYetDeliver(catchedPackingSlip, null);
                } else {
                    alertDialog(Alert.AlertType.INFORMATION,
                            "Information",
                            "Document Blocked Message",
                            "Packing Slip with REFERED status are not allowed to become any document reference.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void saveDO(MouseEvent event) {

        // transfer item's ref doc to PackingSlip Reference
        if (event.isPrimaryButtonDown() == true) {

            if (!this.btnSave.getText().equals("Save")) {
                isViewMode(false);
                return;
            }

            if (!validator.validate()) {

                return;
            }

            doInDraft = this.prepareDeliveryOrderToObj();

            if (this.txtDOID.getText().isEmpty()) {
                doInDraft.setCode(DeliveryOrderService.saveNewDeliveryOrder(doInDraft));

            } else {
                DeliveryOrderService.updateDeliveryOrder(doInDraft);
            }

            packingSlips.forEach(e -> {
                e.setDO(doInDraft);
                e.setStatus(WarehouseRules.PSStatus.REFERED.toString());
            });

            // update packing slip status to all referred
            PackingSlipService.updatePackingSlipsStatus(packingSlips);

        }
    }

}
