/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import BizRulesConfiguration.StaffRules;
import Entity.Address;
import Entity.Contact;
import Entity.Place;
import Entity.Staff;
import PassObjs.BasicObjs;
import Service.AddressService;
import Service.GeneralRulesService;
import Service.StaffService;
import Utils.ImageUtils;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCircleToggleNode;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXDateCell;
import io.github.palexdev.materialfx.utils.SwingFXUtils;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Function;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import net.synedra.validatorfx.Check;
import net.synedra.validatorfx.Validator;

/**
 * FXML Controller class
 *
 * @author Tee Zhuo Xuan
 */
public class StaffCONTR implements Initializable, BasicCONTRFunc {

    //<editor-fold defaultstate="collapsed" desc="fields">
    @FXML
    private MFXCircleToggleNode btnBack;
    @FXML
    private MFXButton btnSave;
    @FXML
    private MFXButton btnDiscard;
    @FXML
    private MFXTextField cmbStatus;
    @FXML
    private MFXTextField txtName;
    @FXML
    private MFXTextField txtStaffID;
    @FXML
    private MFXComboBox<?> cmbGender;
    @FXML
    private MFXComboBox<?> cmbMaritalStatus;
    @FXML
    private MFXComboBox<?> cmbHonorifics;
    @FXML
    private MFXTextField txtIC;
    @FXML
    private MFXDatePicker dtDOB;
    @FXML
    private MFXComboBox<?> cmbNationality;
    @FXML
    private MFXComboBox<?> cmbRace;
    @FXML
    private MFXComboBox<?> cmbReligion;
    @FXML
    private MFXTextField txtMobileNo;
    @FXML
    private MFXTextField txtEmail;
    @FXML
    private MFXTextField txtOffPhNo;
    @FXML
    private MFXTextField txtHomePhNo;
    @FXML
    private MFXTextField txtExt;
    @FXML
    private MFXTextField txtResidentialAddrCity;
    @FXML
    private MFXTextField txtResidentialAddrLocationName;
    @FXML
    private MFXComboBox<?> cmbResidentialAddrState;
    @FXML
    private MFXTextField txtResidentialAddrAddress;
    @FXML
    private MFXTextField txtResidentialAddrPostalCode;
    @FXML
    private MFXComboBox<?> cmbResidentialAddrCountry;
    @FXML
    private MFXTextField txtCorAddrCity;
    @FXML
    private MFXTextField txtCorAddrLocationName;
    @FXML
    private MFXComboBox<?> cmbCorAddrState;
    @FXML
    private MFXTextField txtCorAddrAddress;
    @FXML
    private MFXTextField txtCorAddrPostalCode;
    @FXML
    private MFXComboBox<?> cmbCorAddrCountry;
    @FXML
    private MFXTextField txtWorkPlaceID;
    @FXML
    private MFXComboBox<?> cmbEmpType;
    @FXML
    private MFXTextField txtReportTo;
    @FXML
    private MFXDatePicker dtEntryDate;
    @FXML
    private MFXComboBox<?> cmbRole;
    @FXML
    private MFXTextField txtOccupation;
    @FXML
    private ImageView imgAvatarImg;
    @FXML
    private MFXButton btnUploadImage;
    @FXML
    private MFXCircleToggleNode ctnWorkPlaceSelection;
    @FXML
    private MFXCircleToggleNode ctnReportToSelection;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="util declarations">
    StaffRules staffRules = new StaffRules();
    private BasicObjs passObj;
    private Validator validator = new Validator();
    private Staff staffInDraft;
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

                initializeComboSelections();

                initializeUIControls();

                inputValidation();

                receiveData();

                autoClose();

                if (passObj.getCrud().equals(BasicObjs.read) || passObj.getCrud().equals(BasicObjs.update)) {
                    try {
                        passObj.setObj(StaffService.getStaffByID(((Staff) passObj.getObj()).getStaffID()));
                        fieldFillIn();
                    } catch (IOException ex) {
                        Logger.getLogger(StaffCONTR.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (passObj.getCrud().equals(BasicObjs.read)) {
                    isViewMode(true);
                }
            }
        }
        );
    }

    public void autoClose() {
        Duration delay1 = Duration.seconds(GeneralRulesService.getSessionTimeOut());
        PauseTransition transitionAlert = new PauseTransition(delay1);
        this.passObj.setLoginStaff(new Staff());
        transitionAlert.setOnFinished(evt -> switchScene("View/Login_UI.fxml", passObj, BasicObjs.back));
        transitionAlert.setCycleCount(1);
        btnDiscard.getScene().addEventFilter(InputEvent.ANY, evt -> transitionAlert.playFromStart());
    }

    private void isViewMode(boolean disable) { // if true
        if (disable == true) {
            this.btnSave.setText("Update");
        } else {
            this.btnSave.setText("Save");
        }
        this.btnDiscard.setDisable(disable);
        this.txtStaffID.setDisable(disable);
        this.dtDOB.setDisable(disable); // date view mode
        this.cmbHonorifics.setDisable(disable);
        this.txtName.setDisable(disable);
        this.cmbGender.setDisable(disable);
        this.cmbMaritalStatus.setDisable(disable);
        this.txtIC.setDisable(disable);
        this.cmbNationality.setDisable(disable);
        this.cmbRace.setDisable(disable);
        this.cmbReligion.setDisable(disable);
        this.txtMobileNo.setDisable(disable);
        this.txtEmail.setDisable(disable);
        this.txtExt.setDisable(disable);
        this.txtOffPhNo.setDisable(disable);
        this.txtHomePhNo.setDisable(disable);
        this.btnUploadImage.setDisable(disable);
        this.cmbStatus.setDisable(disable);

        this.txtResidentialAddrLocationName.setDisable(disable);
        this.txtResidentialAddrAddress.setDisable(disable);
        this.txtResidentialAddrCity.setDisable(disable);
        this.txtResidentialAddrPostalCode.setDisable(disable);
        this.cmbResidentialAddrState.setDisable(disable);
        this.cmbResidentialAddrCountry.setDisable(disable);

        this.txtCorAddrLocationName.setDisable(disable);
        this.txtCorAddrAddress.setDisable(disable);
        this.txtCorAddrCity.setDisable(disable);
        this.txtCorAddrPostalCode.setDisable(disable);
        this.cmbCorAddrState.setDisable(disable);
        this.cmbCorAddrCountry.setDisable(disable);

        this.cmbEmpType.setDisable(disable);
        this.txtWorkPlaceID.setDisable(disable);
        this.txtReportTo.setDisable(disable);
        this.dtEntryDate.setDisable(disable);
        this.txtOccupation.setDisable(disable);
        this.cmbRole.setDisable(disable);

        this.ctnReportToSelection.setDisable(disable);
        this.ctnWorkPlaceSelection.setDisable(disable);
        // set all column become diable
    }

    private void fieldFillIn() throws IOException {
        clearAllFieldsValue();

        if (passObj.getObj() != null) {
            //fill all data column with object information
            Staff staff = (Staff) passObj.getObj();

            this.txtStaffID.setText(staff.getStaffID());
            this.cmbHonorifics.setText(staff.getHonorifics());
            this.txtName.setText(staff.getName());
            this.cmbGender.setText(staff.getGender());
            this.cmbMaritalStatus.setText(staff.getMaritalStatus());
            this.dtDOB.setValue(Instant.ofEpochMilli(staff.getDOB().getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate());
            this.txtIC.setText(staff.getIC());
            this.cmbNationality.setText(staff.getNationality());
            this.cmbRace.setText(staff.getRace());
            this.cmbReligion.setText(staff.getReligion());
            this.txtMobileNo.setText(staff.getContact().getMobileNo());
            this.txtEmail.setText(staff.getContact().getEmail());
            this.txtExt.setText(staff.getContact().getExt());
            this.txtOffPhNo.setText(staff.getContact().getOffPhNo());
            this.txtHomePhNo.setText(staff.getContact().getHomePhNo());
            this.cmbStatus.setText(staff.getStatus());

            this.txtResidentialAddrLocationName.setText(staff.getResidentialAddr().getLocationName());
            this.txtResidentialAddrAddress.setText(staff.getResidentialAddr().getAddress());
            this.txtResidentialAddrCity.setText(staff.getResidentialAddr().getCity());
            this.txtResidentialAddrPostalCode.setText(staff.getResidentialAddr().getPostalCode());
            this.cmbResidentialAddrState.setText(staff.getResidentialAddr().getState());
            this.cmbResidentialAddrCountry.setText(staff.getResidentialAddr().getCountry());

            this.txtCorAddrLocationName.setText(staff.getCorAddr().getLocationName());
            this.txtCorAddrAddress.setText(staff.getCorAddr().getAddress());
            this.txtCorAddrCity.setText(staff.getCorAddr().getCity());
            this.txtCorAddrPostalCode.setText(staff.getCorAddr().getPostalCode());
            this.cmbCorAddrState.setText(staff.getCorAddr().getState());
            this.cmbCorAddrCountry.setText(staff.getCorAddr().getCountry());

            this.cmbEmpType.setText(staff.getEmpType());
            this.txtWorkPlaceID.setText(staff.getWorkPlace().getPlaceID());
            this.txtReportTo.setText(staff.getReportTo() == null ? "" : staff.getReportTo().getStaffID());
            this.dtEntryDate.setValue(Instant.ofEpochMilli(staff.getEntryDate().getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate());
            this.txtOccupation.setText(staff.getOccupation());
            this.cmbRole.setText(staff.getRole());

            this.imgAvatarImg.setImage(ImageUtils.byteToImg(
                    ImageUtils.encodedStrToByte(
                            staff.getAvatarImg().isEmpty() == true
                            ? null : staff.getAvatarImg()
                    )));
        }
    }

    private void initializeComboSelections() {
        ((MFXComboBox<String>) this.cmbRace).setItems(FXCollections.observableList(staffRules.getRaces()));
        ((MFXComboBox<String>) this.cmbReligion).setItems(FXCollections.observableList(staffRules.getReligions()));
        ((MFXComboBox<String>) this.cmbNationality).setItems(FXCollections.observableList(staffRules.getNationalities()));
        ((MFXComboBox<String>) this.cmbHonorifics).setItems(FXCollections.observableList(staffRules.getHonorifics()));
        ((MFXComboBox<String>) this.cmbMaritalStatus).setItems(FXCollections.observableList(staffRules.getMaritalStatuses()));
        ((MFXComboBox<String>) this.cmbGender).setItems(FXCollections.observableList(staffRules.getGenders()));
        ((MFXComboBox<String>) this.cmbStatus).setItems(FXCollections.observableList(staffRules.getStatuses()));
        ((MFXComboBox<String>) this.cmbRole).setItems(FXCollections.observableList(staffRules.getRoles()));
        ((MFXComboBox<String>) this.cmbEmpType).setItems(FXCollections.observableList(staffRules.getEmpTypes()));
    }

    private void initializeUIControls() {
        this.dtDOB.setCellFactory(new Function<>() {
            @Override
            public MFXDateCell apply(LocalDate t) {
                return new MFXDateCell(dtDOB, t) {
                    @Override
                    public void updateItem(LocalDate item) {
                        super.updateItem(item);

                        if (item.isAfter(LocalDate.now())) {
                            setDisable(true);
                        } else {
                            setDisable(false);
                        }
                    }
                };

            }
        });

        this.dtEntryDate.setCellFactory(new Function<>() {
            @Override
            public MFXDateCell apply(LocalDate t) {
                return new MFXDateCell(dtEntryDate, t) {
                    @Override
                    public void updateItem(LocalDate item) {
                        super.updateItem(item);

                        if (item.isAfter(LocalDate.now())) {
                            setDisable(true);
                        } else {
                            setDisable(false);
                        }
                    }
                };

            }
        });
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
                passObj.getFxmlPaths().addLast("View/Staff_UI.fxml");
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
        //=====================================

        Check validatorCheck = (new Validator()).createCheck();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.US);

        validatorCheck
                .dependsOn("Honorifics", this.cmbHonorifics.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Honorifics");
                    textVal = textVal.trim();

                    /*
                     1. Cannot be null
                     */
                    // allow null
                    if (textVal.isEmpty()) {
                        c.error("Honorifics - Required Field");
                        return;
                    }

                    return;
                })
                .decorates(this.cmbHonorifics);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Name", this.txtName.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Name");
                    textVal = textVal.trim();


                    /*
                    1. Cannot be null
                    2. Must be alphabet and space allowed ONLY
                     */
                    if (textVal.isEmpty()) {
                        c.error("Name - Required Field");
                        return;
                    }

                    if (!textVal.matches("^[a-zA-Z ]*$")) {
                        c.error("Name - ONLY letter and spaces");
                        return;
                    }
                })
                .decorates(this.txtName);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Gender", this.cmbGender.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Gender");
                    textVal = textVal.trim();

                    /*
                     1. Cannot be null
                     */
                    // allow null
                    if (textVal.isEmpty()) {
                        c.error("Gender - Required Field");

                        return;
                    }

                })
                .decorates(this.cmbGender);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Marital Status", this.cmbMaritalStatus.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Marital Status");
                    textVal = textVal.trim();

                    /*
                     1. Cannot be null
                     */
                    // allow null
                    if (textVal.isEmpty()) {
                        c.error("Marital Status - Required Field");
                        return;
                    }

                })
                .decorates(this.cmbMaritalStatus);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Date of Birth", this.dtDOB.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Date of Birth");
                    textVal = textVal.trim();

                    /*
                     1.
                     */
                    // allow null
                    if (textVal.isEmpty()) {
                        c.error("Date of Birth - Required Field");
                        return;
                    }

                    LocalDate date = LocalDate.parse(textVal, formatter);

                    if (date.isAfter(LocalDate.now())) {

                        c.error("Date of Birth - Cannot be future date");
                        return;
                    }
                })
                .decorates(this.dtDOB);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("IC", this.txtIC.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("IC");
                    textVal = textVal.trim();

                    /*
                     1.
                     */
                    // allow null
                    if (textVal.isEmpty()) {
                        c.error("IC - Required Field");
                        return;
                    }

                    if (!textVal.matches("^\\d{6}-\\d{2}-\\d{4}$")) {

                        c.error("IC - Format not matched");
                        return;

                    }
                })
                .decorates(this.txtIC);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Nationality", this.cmbNationality.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Nationality");
                    textVal = textVal.trim();

                    /*
                     1. Cannot be null
                     2. alphabet and spaces allowed ONLY
                     */
                    if (textVal.isEmpty()) {
                        c.error("Nationality - Required Field");
                        return;
                    }

                    if (!textVal.matches("^[a-zA-Z ]*$")) {
                        c.error("Nationality - ONLY letter and spaces");
                        return;
                    }
                })
                .decorates(this.cmbNationality);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Race", this.cmbRace.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Race");
                    textVal = textVal.trim();

                    /*
                     1. cannot be null
                     2. alphabet and spaces allowed ONLY
                     */
                    if (textVal.isEmpty()) {
                        c.error("Race - Required Field");
                        return;
                    }

                    if (!textVal.matches("^[a-zA-Z ]*$")) {
                        c.error("Race - ONLY letter and spaces");
                        return;
                    }
                })
                .decorates(this.cmbRace);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Religion", this.cmbReligion.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Religion");
                    textVal = textVal.trim();

                    /*
                     1. cannot be null
                     2. alphabet and spaces allowed ONLY
                     */
                    if (textVal.isEmpty()) {
                        c.error("Religion - Required Field");
                        return;
                    }

                    if (!textVal.matches("^[a-zA-Z ]*$")) {
                        c.error("Religion - ONLY letter and spaces");
                        return;
                    }
                })
                .decorates(this.cmbReligion);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Mobile_No", this.txtMobileNo.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Mobile_No");
                    textVal = textVal.trim();

                    /*
                     1. cannot be null
                     2. must follow regex
                     */
                    if (textVal.isEmpty()) {
                        c.error("Mobile No. - Required Field");
                        return;
                    }

                    if (!textVal.matches("^(01)[0|1|2|3|4|6|7|8|9]\\-*[0-9]{7,8}$")) {

                        c.error("Mobile No. - Format not matched");
                        return;

                    }
                })
                .decorates(this.txtMobileNo);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Email", this.txtEmail.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Email");
                    textVal = textVal.trim();

                    /*
                     1. cannot be null
                     2. must follow regex
                     */
                    if (textVal.isEmpty()) {
                        c.error("Email - Required Field");
                        return;
                    }

                    if (!textVal.matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")) {
                        c.error("Email - Format not matched");
                        return;
                    }
                })
                .decorates(this.txtEmail);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Extension_No.", this.txtExt.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Extension_No.");
                    textVal = textVal.trim();

                    /*
                     1. cannot be null
                     2. must follow regex
                     */
                    // allow null
                    if (textVal.isEmpty()) {
                        c.error("Extension No. - Required Field");
                        return;
                    }

                    if (!textVal.matches("^[0-9]{4}$")) {
                        c.error("Extension No. - Format not matched");
                        return;
                    }
                })
                .decorates(this.txtExt);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Office_Phone_No", this.txtOffPhNo.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Office_Phone_No");
                    textVal = textVal.trim();

                    /*
                     1. cannot be null
                     2. must follow regex
                     */
                    if (textVal.isEmpty()) {
                        c.error("Office Phone No. - Required Field");
                        return;
                    }

                    if (!textVal.matches("^(01)[0|1|2|3|4|6|7|8|9]\\-*[0-9]{7,8}$")) {
                        c.error("Office Phone No. - Format not matched");
                        return;
                    }
                })
                .decorates(this.txtOffPhNo);

        validator.add(validatorCheck);
        //=====================================
        //=====================================

        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Home_Phone_No", this.txtHomePhNo.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Home_Phone_No");
                    textVal = textVal.trim();

                    /*
                     1. cannot be null
                     2. must follow regex
                     */
                    if (textVal.isEmpty()) {
                        c.error("Home Phone No. - Required Field");
                        return;
                    }

                    if (!textVal.matches("^(01)[0|1|2|3|4|6|7|8|9]\\-*[0-9]{7,8}$")) {
                        c.error("Home Phone No. - Format not matched");
                        return;
                    }
                })
                .decorates(this.txtHomePhNo);

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
                     1. Cannot be null
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
                .dependsOn("Residential_Location_Name", this.txtResidentialAddrLocationName.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Residential_Location_Name");
                    textVal = textVal.trim();

                    /*
                     1. Cannot be null
                     */
                    if (textVal.isEmpty()) {
                        c.error("Residential Location Name - Required Field");
                        return;
                    }

                })
                .decorates(this.txtResidentialAddrLocationName);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Residential_Address", this.txtResidentialAddrAddress.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Residential_Address");
                    textVal = textVal.trim();

                    /*
                     1. Cannot be null
                     */
                    if (textVal.isEmpty()) {
                        c.error("Residential Address - Required Field");
                        return;
                    }
                })
                .decorates(this.txtResidentialAddrAddress);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Residential_City", this.txtResidentialAddrCity.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Residential_City");
                    textVal = textVal.trim();

                    /*
                     1. Cannot be null
                     2. alphabet and spaces allowed ONLY
                     */
                    if (textVal.isEmpty()) {
                        c.error("Residential City - Required Field");
                        return;
                    }

                    if (!textVal.matches("^[a-zA-Z ]*$")) {
                        c.error("Residential City - ONLY letter and spaces");
                        return;
                    }
                })
                .decorates(this.txtResidentialAddrCity);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Residential_Postal_Code", this.txtResidentialAddrPostalCode.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Residential_Postal_Code");
                    textVal = textVal.trim();

                    /*
                     1. Cannot be null
                     2. must follow regex
                     */
                    if (textVal.isEmpty()) {
                        c.error("Residential Postal Code - Required Field");
                        return;
                    }

                    if (!textVal.matches("^\\d{5}$")) {
                        c.error("Residential Postal Code - Format not matched");
                        return;
                    }
                })
                .decorates(this.txtResidentialAddrPostalCode);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Residential_State", this.cmbResidentialAddrState.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Residential_State");
                    textVal = textVal.trim();

                    /*
                     1. Cannot be null
                     2. alphabet and spaces allowed ONLY
                     */
                    if (textVal.isEmpty()) {
                        c.error("Residential State - ONLY letter and spaces");
                        return;
                    }

                    if (!textVal.matches("^[a-zA-Z ]*$")) {
                        c.error("Residential State - Format not matched");
                        return;
                    }
                })
                .decorates(this.cmbResidentialAddrState);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Residential_Country", this.cmbResidentialAddrCountry.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Residential_Country");
                    textVal = textVal.trim();

                    /*
                     1. Cannot be null
                     2. alphabet and spaces allowed ONLY
                     */
                    if (textVal.isEmpty()) {
                        c.error("Residential Country - Required Field");
                        return;
                    }

                    if (!textVal.matches("^[a-zA-Z ]*$")) {
                        c.error("Residential Country - ONLY letter and spaces");
                        return;
                    }
                })
                .decorates(this.cmbResidentialAddrCountry);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Corresponding_Location_Name", this.txtCorAddrLocationName.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Corresponding_Location_Name");
                    textVal = textVal.trim();

                    /*
                     1. Cannot be null
                     */
                    if (textVal.isEmpty()) {
                        return;
                    }

                })
                .decorates(this.txtCorAddrLocationName);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Corresponding_Address", this.txtCorAddrAddress.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Corresponding_Address");
                    textVal = textVal.trim();

                    /*
                     1. Cannot be null
                     */
                    if (textVal.isEmpty()) {
                        return;
                    }
                })
                .decorates(this.txtCorAddrAddress);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Corresponding_City", this.txtCorAddrCity.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Corresponding_City");
                    textVal = textVal.trim();

                    /*
                     1. alphabet and spaces allowed ONLY
                     */
                    if (textVal.isEmpty()) {
                        return;
                    }

                    if (!textVal.matches("^[a-zA-Z ]*$")) {
                        c.error("Corresponding City - ONLY letter and spaces");
                        return;
                    }
                })
                .decorates(this.txtCorAddrCity);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Corresponding_Postal_Code", this.txtCorAddrPostalCode.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Corresponding_Postal_Code");
                    textVal = textVal.trim();

                    /*
                     1. must follow regex
                     */
                    if (textVal.isEmpty()) {
                        return;
                    }

                    if (!textVal.matches("^\\d{5}$")) {
                        c.error("Corresponding Postal Code - Format not matched");
                        return;
                    }
                })
                .decorates(this.txtCorAddrPostalCode);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Corresponding_State", this.cmbCorAddrState.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Corresponding_State");
                    textVal = textVal.trim();

                    /*
                     1. alphabet and spaces allowed ONLY
                     */
                    if (textVal.isEmpty()) {

                        return;
                    }

                    if (!textVal.matches("^[a-zA-Z ]*$")) {
                        c.error("Corresponding State - Format not matched");
                        return;
                    }
                })
                .decorates(this.cmbCorAddrState);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Corresponding_Country", this.cmbCorAddrCountry.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Corresponding_Country");
                    textVal = textVal.trim();

                    /*
                     1. alphabet and spaces allowed ONLY
                     */
                    if (textVal.isEmpty()) {
                        return;
                    }

                    if (!textVal.matches("^[a-zA-Z ]*$")) {
                        c.error("Corresponding Country - Format not matched");
                        return;
                    }
                })
                .decorates(this.cmbCorAddrCountry);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Employee Type", this.cmbEmpType.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Employee Type");
                    textVal = textVal.trim();

                    /*
                     1. Cannot be null
                     */
                    if (textVal.isEmpty()) {
                        c.error("Employee Type - Required Field");
                        return;
                    }

                })
                .decorates(this.cmbEmpType);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Work Place ID", this.txtWorkPlaceID.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Work Place ID");
                    textVal = textVal.trim();

                    /*
                     1. Cannot be null
                     */
                    // allow null
                    if (textVal.isEmpty()) {
                        c.error("Work Place ID - Required Field");
                        return;
                    }

                })
                .decorates(this.txtWorkPlaceID);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Entry Date", this.dtEntryDate.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Entry Date");
                    textVal = textVal.trim();

                    /*
                     1. cannot be null
                     2. cannot be future date
                     */
                    // allow null
                    if (textVal.isEmpty()) {
                        c.error("Entry Date - Required Field");
                        return;
                    }

                    LocalDate date = LocalDate.parse(textVal, formatter);

                    if (date.isAfter(LocalDate.now())) {

                        c.error("Entry Date - Cannot be future date");
                        return;
                    }
                })
                .decorates(this.dtEntryDate);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Occupation", this.txtOccupation.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Occupation");
                    textVal = textVal.trim();

                    /*
                     1. cannot be null
                     2. alphabet and spaces allowed ONLY
                     */
                    // allow null
                    if (textVal.isEmpty()) {
                        c.error("Occupation - Required Field");
                        return;
                    }

                    if (!textVal.matches("^[a-zA-Z ]*$")) {

                        c.error("Occupation - ONLY letter and spaces");
                        return;
                    }
                })
                .decorates(this.txtOccupation);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Role", this.cmbRole.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Role");
                    textVal = textVal.trim();

                    /*
                     1. cannot be null
                     2. alphabet and spaces allowed ONLY
                     */
                    // allow null
                    if (textVal.isEmpty()) {
                        c.error("Role - Required Field");
                        return;
                    }

                })
                .decorates(this.cmbRole);

        validator.add(validatorCheck);

        //=====================================
    }

    @Override
    public boolean clearAllFieldsValue() {
        //this.txtStaffID.clear();
        this.txtName.clear();
        this.cmbGender.clear();
        this.cmbMaritalStatus.clear();
        this.cmbHonorifics.clear();
        this.txtIC.clear();
        this.dtDOB.clear();
        this.cmbNationality.clear();
        this.cmbRace.clear();
        this.cmbReligion.clear();
        this.txtMobileNo.clear();
        this.txtEmail.clear();
        this.txtOffPhNo.clear();
        this.txtHomePhNo.clear();
        this.txtExt.clear();
        this.txtResidentialAddrCity.clear();
        this.txtResidentialAddrLocationName.clear();
        this.cmbResidentialAddrState.clear();
        this.txtResidentialAddrAddress.clear();
        this.txtResidentialAddrPostalCode.clear();
        this.cmbResidentialAddrCountry.clear();
        this.txtCorAddrCity.clear();
        this.txtCorAddrLocationName.clear();
        this.cmbCorAddrState.clear();
        this.txtCorAddrAddress.clear();
        this.txtCorAddrPostalCode.clear();
        this.cmbCorAddrCountry.clear();
        this.txtWorkPlaceID.clear();
        this.cmbEmpType.clear();
        this.txtReportTo.clear();
        this.dtEntryDate.clear();
        this.cmbRole.clear();
        this.txtOccupation.clear();
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

    @FXML
    private void uploadImage(MouseEvent evt) {
        if (evt.isPrimaryButtonDown() == true) {

            try {
                Stage stage = (Stage) btnBack.getScene().getWindow();

                ExtensionFilter filter = new ExtensionFilter("Image Files (*.png)", "*.png");

                // create a File chooser
                FileChooser fil_chooser = new FileChooser();
                fil_chooser.getExtensionFilters().add(filter);

                // get the file selected
                File file = fil_chooser.showOpenDialog(stage);

                if (file != null) {
                    BufferedImage bi = ImageIO.read(file);
                    Image img = SwingFXUtils.toFXImage(bi, null);
                    this.imgAvatarImg.setImage(img);

                    /*
                        Save Image to database
                        bi = SwingFXUtils.fromFXImage(img, null);
                        byte[] customerImg = ImageUtils.toByteArray(bi, "png");
                        String bytesBase64Img = Base64.encodeBase64String(customerImg);
                     */
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    private void goBackPrevious(MouseEvent event) {
        if (event.isPrimaryButtonDown() == true) {
            if (event.isPrimaryButtonDown() == true) {
                quitWindow("Warning", "Validation Message", "Record haven't been saved.\nAre you sure you want to continue?");
            }
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

    @FXML
    private void saveStaff(MouseEvent event) throws IOException {
        if (event.isPrimaryButtonDown() == true) {

            if (!this.btnSave.getText().equals("Save")) {
                isViewMode(false);
                return;
            }

            if (!validator.validate()) {
                return;
            }

            staffInDraft = prepareStaffInforToObj();

            if (this.passObj.getCrud().equals(BasicObjs.create)) {
                staffInDraft.getResidentialAddr().setAddressID(AddressService.saveNewAddress(staffInDraft.getResidentialAddr()));
                staffInDraft.getCorAddr().setAddressID(AddressService.saveNewAddress(staffInDraft.getCorAddr()));
                StaffService.saveNewStaff(staffInDraft);
            } else if (this.passObj.getCrud().equals(BasicObjs.update) || this.passObj.getCrud().equals(BasicObjs.read)) {

                AddressService.updateAddress(staffInDraft.getResidentialAddr());
                AddressService.updateAddress(staffInDraft.getCorAddr());

                StaffService.updateStaff(staffInDraft);
            }
        }
    }

    private Staff prepareStaffInforToObj() throws IOException {
        Staff staff = new Staff();
        staff.setStaffID(this.txtStaffID.getText());
        staff.setAvatarImg(this.imgAvatarImg.getImage() == null ? "" : ImageUtils.byteToEncodedStr(ImageUtils.imgToByte(this.imgAvatarImg.getImage())));
        staff.setName(this.txtName.getText());
        staff.setGender(this.cmbGender.getText());
        staff.setDOB(this.dtDOB.getValue() == null ? null : Date.valueOf(this.dtDOB.getValue())); //https://stackoverflow.com/questions/20446026/get-value-from-date-picker
        staff.setIC(this.txtIC.getText());
        staff.setMaritalStatus(this.cmbMaritalStatus.getText());
        staff.setNationality(this.cmbNationality.getText());
        staff.setHonorifics(this.cmbHonorifics.getText());

        //Residential Address
        Address residentialAddr = new Address();
        if (!this.passObj.getCrud().equals(BasicObjs.create)) {
            residentialAddr.setAddressID(((Staff) this.passObj.getObj()).getResidentialAddr().getAddressID());
        }
        residentialAddr.setLocationName(this.txtResidentialAddrLocationName.getText());
        residentialAddr.setAddress(this.txtResidentialAddrAddress.getText());
        residentialAddr.setCity(this.txtResidentialAddrCity.getText());
        residentialAddr.setPostalCode(this.txtResidentialAddrPostalCode.getText());
        residentialAddr.setState(this.cmbResidentialAddrState.getText());
        residentialAddr.setCountry(this.cmbResidentialAddrCountry.getText());
        staff.setResidentialAddr(residentialAddr);

        //Corresponding Address
        Address corrAddr = new Address();
        if (!this.passObj.getCrud().equals(BasicObjs.create)) {
            corrAddr.setAddressID(((Staff) this.passObj.getObj()).getCorAddr().getAddressID());
        }
        corrAddr.setLocationName(this.txtCorAddrLocationName.getText());
        corrAddr.setAddress(this.txtCorAddrAddress.getText());
        corrAddr.setCity(this.txtCorAddrCity.getText());
        corrAddr.setPostalCode(this.txtCorAddrPostalCode.getText());
        corrAddr.setState(this.cmbCorAddrState.getText());
        corrAddr.setCountry(this.cmbCorAddrCountry.getText());
        staff.setCorAddr(corrAddr);

        //staff contact information
        Contact staffContact = new Contact();
        staffContact.setEmail(this.txtEmail.getText());
        staffContact.setMobileNo(this.txtMobileNo.getText());
        staffContact.setExt(this.txtExt.getText());
        staffContact.setOffPhNo(this.txtMobileNo.getText());
        staffContact.setHomePhNo(this.txtHomePhNo.getText());
        staff.setContact(staffContact);

        staff.setOccupation(this.txtOccupation.getText());
        staff.setRace(this.cmbRace.getText());
        staff.setReligion(this.cmbReligion.getText());

        //staff work place
        Place workPlace = new Place();
        workPlace.setPlaceID(this.txtWorkPlaceID.getText());
        staff.setWorkPlace(workPlace);

        staff.setEntryDate(this.dtEntryDate.getValue() == null ? null : Date.valueOf(this.dtEntryDate.getValue()));

        Staff reportTo = new Staff();
        if (!this.txtReportTo.getText().isEmpty()) {
            reportTo.setStaffID(this.txtReportTo.getText());
        } else {
            reportTo.setStaffID(null);
        }
        staff.setReportTo(reportTo);

        staff.setEmpType(this.cmbEmpType.getText());

        staff.setRole(this.cmbRole.getText());
        staff.setStatus(this.cmbStatus.getText());

        return staff;
    }

    @FXML
    private void openReportToSelection(MouseEvent event) {
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
                    this.txtReportTo.setText(((Staff) receiveObj.getObj()).getStaffID());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //switchScene("View/InnerEntitySelect_UI.fxml", new BasicObjs(new Place()), BasicObjs.forward, "Dialog");
        }
    }

    @FXML
    private void openWorkPlaceSelection(MouseEvent event) {
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
                    this.txtWorkPlaceID.setText(((Place) receiveObj.getObj()).getPlaceID());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //switchScene("View/InnerEntitySelect_UI.fxml", new BasicObjs(new Place()), BasicObjs.forward, "Dialog");
        }
    }

}
