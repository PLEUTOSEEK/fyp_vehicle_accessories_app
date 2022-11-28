/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Entity.PaymentTerm;
import Entity.Staff;
import PassObjs.BasicObjs;
import Service.AccountingRulesService;
import Service.GeneralRulesService;
import Service.PaymentTermService;
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
public class AccRulesConfigurationCONTR implements Initializable, BasicCONTRFunc {

    @FXML
    private MFXTableView<?> tblPymtTerm;
    @FXML
    private MFXButton btnAdd;
    @FXML
    private MFXCircleToggleNode btnBack;
    @FXML
    private MFXButton btnDiscard;
    @FXML
    private MFXButton btnSave;
    @FXML
    private MFXTextField txtTaxRate;
    //<editor-fold defaultstate="collapsed" desc="util declarations">
    private Validator validator = new Validator();
    private BasicObjs passObj;
    private List<PaymentTerm> tempPaymentTerms = new ArrayList<>();
    private List<PaymentTerm> paymentTerms = new ArrayList<>();
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
                autoClose();
                try {
                    fieldFillIn();
                    paymentTerms = PaymentTermService.getAllPaymentTerms();
                    setUpPytmtTermTable();
                } catch (IOException ex) {
                    Logger.getLogger(AccRulesConfigurationCONTR.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void fieldFillIn() throws IOException {

        this.txtTaxRate.setText(AccountingRulesService.getTaxRate().toString());
    }

    private void setUpPytmtTermTable() {
        // payment term ID
        MFXTableColumn<PaymentTerm> pymtTermIDCol = new MFXTableColumn<>("Payment Term ID", true, Comparator.comparing(paymentTerm -> paymentTerm.getPymtTermID()));
        // patment term name
        MFXTableColumn<PaymentTerm> pymtTermNmCol = new MFXTableColumn<>("Name", true, Comparator.comparing(paymentTerm -> paymentTerm.getPymtTermName()));
        // baseline documen date
        MFXTableColumn<PaymentTerm> baselineDocDateCol = new MFXTableColumn<>("Baseline Document Date", true, Comparator.comparing(paymentTerm -> paymentTerm.getBaseLineDocumentDate()));
        // day limits
        MFXTableColumn<PaymentTerm> dayLimitCol = new MFXTableColumn<>("Day Limits", true, Comparator.comparing(paymentTerm -> paymentTerm.getDaysLimit()));
        // description
        MFXTableColumn<PaymentTerm> descCol = new MFXTableColumn<>("Description", true, Comparator.comparing(paymentTerm -> paymentTerm.getDescription()));

        // payment term ID
        pymtTermIDCol.setRowCellFactory(pymtTerm -> new MFXTableRowCell<>(paymentTerm -> paymentTerm.getPymtTermID()));
        // patment term name
        pymtTermNmCol.setRowCellFactory(pymtTerm -> new MFXTableRowCell<>(paymentTerm -> paymentTerm.getPymtTermName()));
        // baseline documen date
        baselineDocDateCol.setRowCellFactory(pymtTerm -> new MFXTableRowCell<>(paymentTerm -> paymentTerm.getBaseLineDocumentDate()));
        // day limits
        dayLimitCol.setRowCellFactory(pymtTerm -> new MFXTableRowCell<>(paymentTerm -> paymentTerm.getDaysLimit()));
        // description
        descCol.setRowCellFactory(pymtTerm -> new MFXTableRowCell<>(paymentTerm -> paymentTerm.getDescription()));

        ((MFXTableView<PaymentTerm>) tblPymtTerm).getTableColumns().clear();

        ((MFXTableView<PaymentTerm>) tblPymtTerm).getTableColumns().addAll(
                pymtTermIDCol,
                pymtTermNmCol,
                baselineDocDateCol,
                dayLimitCol,
                descCol
        );

        ((MFXTableView<PaymentTerm>) tblPymtTerm).getFilters().clear();

        ((MFXTableView<PaymentTerm>) tblPymtTerm).getFilters().addAll(
                new StringFilter<>("Payment Term ID", paymentTerm -> paymentTerm.getPymtTermID()),
                new StringFilter<>("Name", paymentTerm -> paymentTerm.getPymtTermName()),
                new StringFilter<>("Baseline Document Date", paymentTerm -> paymentTerm.getBaseLineDocumentDate()),
                new IntegerFilter<>("Day Limits", paymentTerm -> paymentTerm.getDaysLimit()),
                new StringFilter<>("Description", paymentTerm -> paymentTerm.getDescription())
        );

        tempPaymentTerms.addAll(paymentTerms);
        ((MFXTableView<PaymentTerm>) tblPymtTerm).getItems().clear();
        ((MFXTableView<PaymentTerm>) tblPymtTerm).setItems(FXCollections.observableArrayList(tempPaymentTerms));
        tempPaymentTerms.clear();

        ((MFXTableView<PaymentTerm>) tblPymtTerm).getSelectionModel().selectionProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {

                if (((MFXTableView<PaymentTerm>) tblPymtTerm).getSelectionModel().getSelectedValues().size() != 0) {

                    PaymentTerm paymentTerm = (((MFXTableView<PaymentTerm>) tblPymtTerm).getSelectionModel().getSelectedValues().get(0));
                    rowSelected.add(paymentTerm.getPymtTermID());

                    if (rowSelected.size() == 2) {
                        if (rowSelected.get(0).equals(rowSelected.get(1))) {
                            // action here
                            Parent root;
                            try {
                                root = FXMLLoader.load(getClass().getClassLoader().getResource("View/PymtTermSelect_UI.fxml"));
                                Stage stage = new Stage();
                                stage.initModality(Modality.WINDOW_MODAL);
                                stage.initOwner(btnBack.getScene().getWindow());
                                stage.setScene(new Scene(root));

                                BasicObjs passObj = new BasicObjs();
                                passObj.setObjs((List<Object>) (Object) paymentTerms);
                                passObj.setObj(paymentTerm);
                                passObj.setCrud(BasicObjs.read);

                                stage.setUserData(passObj);
                                stage.showAndWait();

                                // if have any change on the selected collect address
                                if (stage.getUserData() != null) {

                                    BasicObjs receiveObj = (BasicObjs) stage.getUserData();
                                    PaymentTerm catchedPymtTerm = new PaymentTerm();
                                    catchedPymtTerm = ((PaymentTerm) receiveObj.getObj()).clone();

                                    paymentTerms.set(paymentTerms.indexOf(paymentTerm), catchedPymtTerm);

                                    setUpPytmtTermTable();
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
                .dependsOn("Tax Rate", this.txtTaxRate.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Tax Rate");
                    textVal = textVal.trim();
                    /*
                     1.
                     */
                    // allow null
                    if (textVal.isEmpty()) {
                        c.error("Tax Rate - Required Field");
                        return;
                    }

                    try {

                        Double maxOrderAmt = Double.parseDouble(textVal);

                        if (maxOrderAmt < 0 || maxOrderAmt > 100) {
                            c.error("Tax Rate - Must be within [0 <= Tax Rate <= 100]");
                            return;
                        }

                    } catch (Exception ex) {
                        c.error("Tax Rate - ONLY numeric value");
                        return;
                    }
                })
                .decorates(this.txtTaxRate);

        validator.add(validatorCheck);

//=====================================
    }

    private void defaultValFillIn() {
        this.txtTaxRate.setText("0.00");
    }

    @Override
    public boolean clearAllFieldsValue() {
        this.txtTaxRate.clear();
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
                passObj.getFxmlPaths().addLast("View/AccountingRulesConfiguration_UI.fxml");
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
    private void addPaymentTerm(MouseEvent event) {

        Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("View/PymtTermSelect_UI.fxml"));

            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(btnBack.getScene().getWindow());
            stage.setScene(new Scene(root));

            BasicObjs passObj = new BasicObjs();
            passObj.setCrud(BasicObjs.create);
            passObj.setObj(new PaymentTerm());
            passObj.setObjs((List<Object>) (Object) paymentTerms);
            stage.setUserData(passObj);
            stage.showAndWait();

            // if have any change on the selected collect address
            if (stage.getUserData() != null) {

                BasicObjs receiveObj = (BasicObjs) stage.getUserData();
                PaymentTerm catchedPymtTerm = new PaymentTerm();
                catchedPymtTerm = (PaymentTerm) receiveObj.getObj();

                paymentTerms.add(catchedPymtTerm);

                this.setUpPytmtTermTable();

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
    private void saveAccRules(MouseEvent event) {
        if (!validator.validate()) {
            return;
        }

        AccountingRulesService.updateTaxRate(this.txtTaxRate.getText());
        PaymentTermService.updateInsertPaymentTerms(paymentTerms);
    }

    public void autoClose() {
        Duration delay1 = Duration.seconds(GeneralRulesService.getSessionTimeOut());
        PauseTransition transitionAlert = new PauseTransition(delay1);
        this.passObj.setLoginStaff(new Staff());
        transitionAlert.setOnFinished(evt -> switchScene("View/Login_UI.fxml", passObj, BasicObjs.back));
        transitionAlert.setCycleCount(1);

        btnDiscard.getScene().addEventFilter(InputEvent.ANY, evt -> transitionAlert.playFromStart());
    }
}
