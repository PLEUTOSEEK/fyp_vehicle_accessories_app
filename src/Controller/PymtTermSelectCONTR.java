/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import BizRulesConfiguration.AccountingRules;
import BizRulesConfiguration.AccountingRules.BaselineDocuments;
import Entity.PaymentTerm;
import Entity.Staff;
import PassObjs.BasicObjs;
import Service.GeneralRulesService;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.synedra.validatorfx.Check;
import net.synedra.validatorfx.Validator;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class PymtTermSelectCONTR implements Initializable {
    //<editor-fold defaultstate="collapsed" desc="util declarations">

    @FXML
    private MFXTextField txtPymtTermID;
    @FXML
    private MFXButton btnCancel;
    @FXML
    private MFXButton btnConfirm;
    @FXML
    private MFXTextField txtPymtTermName;
    @FXML
    private MFXTextField txtDescription;
    @FXML
    private MFXComboBox<?> cmbBaselineDoc;
    @FXML
    private MFXTextField txtDaysLimit;

    private BasicObjs passObj;

    private Validator validator = new Validator();

    AccountingRules accRules = new AccountingRules();

    //</editor-fold>
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(
                new Runnable() {

            @Override
            public void run() {
                intializeComboSelections();
                inputValidation();
                receiveData();

                if (passObj.getCrud().equals(BasicObjs.create)) {
                    defaultValFillIn();
                }

                if (passObj.getCrud().equals(BasicObjs.read)) {
                    fieldFillIn();
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

        this.btnCancel.getScene().addEventFilter(InputEvent.ANY, evt -> transitionAlert.playFromStart());
    }

    public void switchScene(String fxmlPath, BasicObjs passObj, String direction) {
        Stage stage = (Stage) this.btnCancel.getScene().getWindow();
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

    public BasicObjs sendData(BasicObjs passObj, String direction) {
        switch (direction) {
            case BasicObjs.forward:
                passObj.getFxmlPaths().addLast("View/PymtTermSelect_UI.fxml");
                break;
        }
        passObj.setPassDirection(direction);
        passObj.setLoginStaff(this.passObj.getLoginStaff());
        return passObj;
    }

    private void intializeComboSelections() {
        ((MFXComboBox<BaselineDocuments>) this.cmbBaselineDoc).setItems(FXCollections.observableList(accRules.getBaselineDocs()));
    }

    private void defaultValFillIn() {
        this.txtDaysLimit.setText("0");
    }

    private void fieldFillIn() {
        clearAllFieldsValue();
        defaultValFillIn();
        if (passObj.getObj() != null) {
            PaymentTerm pymtTerm = (PaymentTerm) passObj.getObj();
            this.txtPymtTermID.setText(pymtTerm.getPymtTermID());
            this.txtPymtTermName.setText(pymtTerm.getPymtTermName());
            this.txtDescription.setText(pymtTerm.getDescription());
            this.cmbBaselineDoc.setText(pymtTerm.getBaseLineDocumentDate());
            this.txtDaysLimit.setText(String.valueOf(pymtTerm.getDaysLimit()));
        }
    }

    private boolean clearAllFieldsValue() {
        this.txtPymtTermID.clear();
        this.txtPymtTermName.clear();
        this.txtDescription.clear();
        this.cmbBaselineDoc.clear();
        this.txtDaysLimit.clear();

        return true;
    }

    private void inputValidation() {
        Check validatorCheck = (new Validator()).createCheck();

        /*
        No need include:
        1. Remarks
        2.
         */
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Payment Term Name", this.txtPymtTermName.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Payment Term Name");
                    textVal = textVal.trim();
                    /*
                     1. cannot be null
                     2. must be unique
                     3.
                     */
                    if (textVal.isEmpty()) {
                        c.error("Payment Term Name - Required Field");
                        return;
                    }

                    if (passObj.getObjs().size() > 0) {
                        List<PaymentTerm> paymentTerms = passObj.getObjs()
                                .stream()
                                .map(e -> (PaymentTerm) e)
                                .collect(Collectors.toList());

                        PaymentTerm passInPymtTerm = (PaymentTerm) passObj.getObj();

                        if (passObj.getCrud().equals(BasicObjs.read)) {

                            for (PaymentTerm pymtTerm : paymentTerms) {
                                if (pymtTerm.getPymtTermName().toLowerCase().equals(textVal.toLowerCase())
                                        && !pymtTerm.equals(passInPymtTerm)) {
                                    c.error("Payment Term Name - Must be unique");
                                    return;
                                }
                            }
                        } else {
                            for (PaymentTerm pymtTerm : paymentTerms) {
                                if (pymtTerm.getPymtTermName().toLowerCase().equals(textVal.toLowerCase())) {
                                    c.error("Payment Term Name - Must be unique");
                                    return;
                                }
                            }
                        }
                    }

                })
                .decorates(this.txtPymtTermName);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Baseline Document", this.cmbBaselineDoc.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Baseline Document");
                    textVal = textVal.trim();
                    /*
                     1. cannot be null
                     */
                    if (textVal.isEmpty()) {
                        c.error("Baseline Document - Required Field");
                        return;
                    }

                })
                .decorates(this.cmbBaselineDoc);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Days Limit", this.txtDaysLimit.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Days Limit");
                    textVal = textVal.trim();
                    /*
                     1. cannot be null
                     2. must be integer value
                     3. cannot less than 1
                     */
                    if (textVal.isEmpty()) {
                        c.error("Days Limit - Required Field");
                        return;
                    }

                    if (!textVal.matches("^\\d+$")) {
                        c.error("Days Limit - ONLY integer value");
                        return;
                    }

                    Integer daysLimit = Integer.parseInt(textVal);

                    if (daysLimit <= 0) {
                        c.error("Days Limit - Cannot less than 1");
                        return;
                    }
                })
                .decorates(this.txtDaysLimit);

        validator.add(validatorCheck);

//=====================================
    }

    public void receiveData() {
        // Step 1
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        // Step 2
        if (stage.getUserData() != null) {
            passObj = (BasicObjs) stage.getUserData();
        } else {
            passObj = new BasicObjs();
        }
    }

    private PaymentTerm preparePymtTermToObj() {
        PaymentTerm pymtTerm = new PaymentTerm();
        pymtTerm.setPymtTermID(this.txtPymtTermID.getText());
        pymtTerm.setPymtTermName(this.txtPymtTermName.getText());
        pymtTerm.setDescription(this.txtDescription.getText());
        pymtTerm.setBaseLineDocumentDate(this.cmbBaselineDoc.getText());
        pymtTerm.setDaysLimit(Integer.valueOf(this.txtDaysLimit.getText()));
        return pymtTerm;
    }

    @FXML
    private void cancelAction(MouseEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.setUserData(null);
        stage.close();
    }

    @FXML
    private void confirmPymtTerm(MouseEvent event) {
        if (event.isPrimaryButtonDown() == true) {
            if (!validator.validate()) {
                return;
            }

            PaymentTerm pymtTerm = this.preparePymtTermToObj();

            Stage stage = (Stage) btnCancel.getScene().getWindow();
            BasicObjs passObj = new BasicObjs();
            passObj.setObj(pymtTerm);
            stage.setUserData(passObj);
            stage.close();
        }
    }
}
