/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Entity.ShipmentTerm;
import Entity.Staff;
import PassObjs.BasicObjs;
import Service.GeneralRulesService;
import Service.ShipmentTermService;
import Service.WarehouseRulesService;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCircleToggleNode;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.StringFilter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.synedra.validatorfx.Check;
import net.synedra.validatorfx.Validator;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class WarehouseRulesConfigurationCONTR implements Initializable, BasicCONTRFunc {

    @FXML
    private MFXTextField txtMaxRequiredDeliveryDate;
    @FXML
    private MFXButton btnAdd;
    @FXML
    private MFXCircleToggleNode btnBack;
    @FXML
    private MFXButton btnDiscard;
    @FXML
    private MFXButton btnSave;
    @FXML
    private MFXTableView<?> tblShipmentTerm;

//<editor-fold defaultstate="collapsed" desc="util declarations">
    private Validator validator = new Validator();
    private BasicObjs passObj;
    private List<ShipmentTerm> tempShipmentTerms = new ArrayList<>();
    private List<ShipmentTerm> shipmentTerms = new ArrayList<>();
    private static List<String> rowSelected = new ArrayList<>();

    //</editor-fold>
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                inputValidation();
                receiveData();
                try {
                    fieldFillIn();
                } catch (IOException ex) {
                    Logger.getLogger(AccRulesConfigurationCONTR.class.getName()).log(Level.SEVERE, null, ex);
                }

                shipmentTerms.addAll(ShipmentTermService.getAllShipmentTerms());

                setUpShipmentTermTable();
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

    private void fieldFillIn() throws IOException {
        this.txtMaxRequiredDeliveryDate.setText(String.valueOf(WarehouseRulesService.getMaxRequiredDeliveryDate()));
    }

    private void setUpShipmentTermTable() {
        // Shipment Term ID
        // Shipment Term Name
        // Description
        //
        // Shipment Term ID
        MFXTableColumn<ShipmentTerm> shipmentTermIDCol = new MFXTableColumn<>("Shipment Term ID", true, Comparator.comparing(shipmentTerm -> shipmentTerm.getShipmentTermID()));
        // Shipment Term Name
        MFXTableColumn<ShipmentTerm> shipmentTermNmCol = new MFXTableColumn<>("Name", true, Comparator.comparing(shipmentTerm -> shipmentTerm.getShipmentTermName()));
        // Description
        MFXTableColumn<ShipmentTerm> descriptionCol = new MFXTableColumn<>("Description", true, Comparator.comparing(shipmentTerm -> shipmentTerm.getDescription()));
        //
        // Shipment Term ID
        shipmentTermIDCol.setRowCellFactory(sTerm -> new MFXTableRowCell<>(shipmentTerm -> shipmentTerm.getShipmentTermID()));
        // Shipment Term Name
        shipmentTermNmCol.setRowCellFactory(sTerm -> new MFXTableRowCell<>(shipmentTerm -> shipmentTerm.getShipmentTermName()));
        // Description
        descriptionCol.setRowCellFactory(sTerm -> new MFXTableRowCell<>(shipmentTerm -> shipmentTerm.getDescription()));

        ((MFXTableView<ShipmentTerm>) tblShipmentTerm).getTableColumns().clear();

        ((MFXTableView<ShipmentTerm>) tblShipmentTerm).getTableColumns().addAll(
                shipmentTermIDCol,
                shipmentTermNmCol,
                descriptionCol
        );

        ((MFXTableView<ShipmentTerm>) tblShipmentTerm).getFilters().clear();

        ((MFXTableView<ShipmentTerm>) tblShipmentTerm).getFilters().addAll(
                new StringFilter<>("Shipment Term ID", shipmentTerm -> shipmentTerm.getShipmentTermID()),
                new StringFilter<>("Name", shipmentTerm -> shipmentTerm.getShipmentTermName()),
                new StringFilter<>("Description", shipmentTerm -> shipmentTerm.getDescription())
        );

        tempShipmentTerms.addAll(shipmentTerms);
        ((MFXTableView<ShipmentTerm>) tblShipmentTerm).getItems().clear();
        ((MFXTableView<ShipmentTerm>) tblShipmentTerm).setItems(FXCollections.observableArrayList(tempShipmentTerms));
        tempShipmentTerms.clear();
        ((MFXTableView<ShipmentTerm>) tblShipmentTerm).getSelectionModel().selectionProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {

                if (((MFXTableView<ShipmentTerm>) tblShipmentTerm).getSelectionModel().getSelectedValues().size() != 0) {

                    ShipmentTerm shipmentTerm = (((MFXTableView<ShipmentTerm>) tblShipmentTerm).getSelectionModel().getSelectedValues().get(0));
                    rowSelected.add(shipmentTerm.getShipmentTermID());

                    if (rowSelected.size() == 2) {
                        if (rowSelected.get(0).equals(rowSelected.get(1))) {
                            // action here
                            Parent root;
                            try {
                                root = FXMLLoader.load(getClass().getClassLoader().getResource("View/ShipmentTermSelect_UI.fxml"));
                                Stage stage = new Stage();
                                stage.initModality(Modality.WINDOW_MODAL);
                                stage.initOwner(btnBack.getScene().getWindow());
                                stage.setScene(new Scene(root));

                                BasicObjs passObj = new BasicObjs();
                                passObj.setObj(shipmentTerm);
                                passObj.setObjs((List<Object>) (Object) shipmentTerms);
                                passObj.setCrud(BasicObjs.read);

                                stage.setUserData(passObj);
                                stage.showAndWait();

                                // if have any change on the selected collect address
                                if (stage.getUserData() != null) {

                                    BasicObjs receiveObj = (BasicObjs) stage.getUserData();
                                    ShipmentTerm catchedShipmentTerm = new ShipmentTerm();
                                    catchedShipmentTerm = ((ShipmentTerm) receiveObj.getObj()).clone();

                                    shipmentTerms.set(shipmentTerms.indexOf(shipmentTerm), catchedShipmentTerm);

                                    setUpShipmentTermTable();
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

    @Override
    public void inputValidation() {
        Check validatorCheck = (new Validator()).createCheck();

        /*
        No need include:
        1.
         */
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Max Required Delivery Days", this.txtMaxRequiredDeliveryDate.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Max Required Delivery Days");
                    textVal = textVal.trim();
                    /*
                     1.
                     */
                    // allow null
                    if (textVal.isEmpty()) {
                        c.error("Max Required Delivery Days - Required Field");

                        return;
                    }
                    if (!textVal.matches("^\\d+$")) {
                        c.error("Max Required Delivery Days - ONLY integer value");
                        return;
                    }

                    Integer maxRequiredDeliveryDays = Integer.parseInt(textVal);

                    if (maxRequiredDeliveryDays <= 0) {
                        c.error("Max Required Delivery Days - Cannot less than 1");
                        return;
                    }
                })
                .decorates(this.txtMaxRequiredDeliveryDate);

        validator.add(validatorCheck);

        //=====================================
    }

    private void defaultValFillIn() {
        this.txtMaxRequiredDeliveryDate.setText("0");
    }

    @Override
    public boolean clearAllFieldsValue() {
        this.txtMaxRequiredDeliveryDate.clear();
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
                passObj.getFxmlPaths().addLast("View/WarehouseRulesConfiguration_UI.fxml");
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

    @FXML
    private void addShipmentTerm(MouseEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("View/ShipmentTermSelect_UI.fxml"));

            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(btnBack.getScene().getWindow());
            stage.setScene(new Scene(root));

            BasicObjs passObj = new BasicObjs();
            passObj.setCrud(BasicObjs.create);
            passObj.setObj(new ShipmentTerm());
            passObj.setObjs((List<Object>) (Object) shipmentTerms);
            stage.setUserData(passObj);
            stage.showAndWait();

            // if have any change on the selected collect address
            if (stage.getUserData() != null) {

                BasicObjs receiveObj = (BasicObjs) stage.getUserData();
                ShipmentTerm catchedShipmentTerm = new ShipmentTerm();
                catchedShipmentTerm = (ShipmentTerm) receiveObj.getObj();

                shipmentTerms.add(catchedShipmentTerm);

                this.setUpShipmentTermTable();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            quitWindow("Warning", "Validation Message", "Record haven't been saved.\nAre you sure you want to continue?");
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

    @FXML
    private void saveWarehouseRules(MouseEvent event) {
        if (!validator.validate()) {
            return;
        }

        WarehouseRulesService.updateMaxRequiredDeliveryDays(this.txtMaxRequiredDeliveryDate.getText());
        ShipmentTermService.updateInsertShipmentTerms(shipmentTerms);

        switchScene(passObj.getFxmlPaths().getLast().toString(), passObj, BasicObjs.back);

    }

}
