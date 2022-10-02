/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import BizRulesConfiguration.StaffRules;
import DAO.StaffDAO;
import Entity.Address;
import Entity.Contact;
import Entity.Place;
import Entity.Staff;
import PassObjs.BasicObjs;
import Service.StaffService;
import Utils.ImageUtils;
import Utils.ValidationUtils;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCircleToggleNode;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.utils.SwingFXUtils;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.imageio.ImageIO;
import net.synedra.validatorfx.Validator;

/**
 * FXML Controller class
 *
 * @author Tee Zhuo Xuan
 */
public class StaffCONTR implements Initializable, BasicCONTRFunc {

    private BasicObjs passObj;
    private Validator validator = new Validator();
    private Staff staffInDraft;

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

    StaffRules staffRules = new StaffRules();

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

                inputValidation();

                receiveData();
            }
        }
        );
    }

    private void initializeComboSelections() {
        ((MFXComboBox<String>) this.cmbRace).setItems(FXCollections.observableList(staffRules.getRaces()));
        ((MFXComboBox<String>) this.cmbReligion).setItems(FXCollections.observableList(staffRules.getReligions()));
        ((MFXComboBox<String>) this.cmbNationality).setItems(FXCollections.observableList(staffRules.getNationalities()));
        ((MFXComboBox<String>) this.cmbHonorifics).setItems(FXCollections.observableList(staffRules.getHonorifics()));
        ((MFXComboBox<String>) this.cmbMaritalStatus).setItems(FXCollections.observableList(staffRules.getMaritalStatuses()));
        ((MFXComboBox<String>) this.cmbGender).setItems(FXCollections.observableList(staffRules.getGenders()));
        ((MFXComboBox<String>) this.cmbStatus).setItems(FXCollections.observableList(staffRules.getStatuses()));
        //((MFXComboBox<String>) this).setItems(FXCollections.observableList(staffRules.getAccStatuses()));
        ((MFXComboBox<String>) this.cmbRole).setItems(FXCollections.observableList(staffRules.getRoles()));
        ((MFXComboBox<String>) this.cmbEmpType).setItems(FXCollections.observableList(staffRules.getEmpTypes()));
    }

    @FXML
    private void goBackPrevious(MouseEvent event) {
        if (event.isPrimaryButtonDown() == true) {
            ButtonType alertBtnClicked = alertDialog(Alert.AlertType.CONFIRMATION,
                    "Warning",
                    "Validation Message",
                    "Record haven't been saved.\nAre you sure you want to continue?");

            if (alertBtnClicked == ButtonType.OK) {
                switchScene(passObj.getFxmlPaths().getLast().toString(), new BasicObjs(), BasicObjs.back);
            } else if (alertBtnClicked == ButtonType.CANCEL) {
                //nothing need to do, remain same page
            }
        }
    }

    @Override
    public void switchScene(String fxmlPath, BasicObjs passObj, String direction) {
        Stage stage = (Stage) btnBack.getScene().getWindow();
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
        List<MFXTextField> listOfControls = new ArrayList<MFXTextField>();
        ValidationUtils<MFXTextField> validationUtils = new ValidationUtils<>();
        int characterLimit = 0;
        //================================
        listOfControls.add(this.txtName);
        characterLimit = 200;
        validationUtils.validationCreator(validator, listOfControls, 0, false, "Name - Required Field", ValidationUtils.isNotEmpty);
        validationUtils.validationCreator(validator, listOfControls, 0, false, "Name - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        validationUtils.validationCreator(validator, listOfControls, 0, false, "Name - Only Aphabet and Space are allow", ValidationUtils.isAlphaSpace);
        listOfControls.clear();
        //================================
        listOfControls.add(this.cmbGender);
        characterLimit = 30;
        validationUtils.validationCreator(validator, listOfControls, 0, false, "Gender - Required Field", ValidationUtils.isNotEmpty);
        validationUtils.validationCreator(validator, listOfControls, characterLimit, false, "Gender - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.dtDOB);
        characterLimit = 100;
        validationUtils.validationCreator(validator, listOfControls, 0, false, "Date of birth - Required Field", ValidationUtils.isNotEmpty);
        validationUtils.validationCreator(validator, listOfControls, characterLimit, false, "Date of birth - cannot be after current date" + characterLimit + " characters", ValidationUtils.isBeforeCurrentDate);
        listOfControls.clear();
        //================================
        listOfControls.add(this.txtIC);
        characterLimit = 100;
        validationUtils.validationCreator(validator, listOfControls, 0, false, "IC - Required Field", ValidationUtils.isNotEmpty);
        validationUtils.validationCreator(validator, listOfControls, characterLimit, false, "IC - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.cmbMaritalStatus);
        characterLimit = 50;
        validationUtils.validationCreator(validator, listOfControls, 0, false, "Marital Status - Required Field", ValidationUtils.isNotEmpty);
        validationUtils.validationCreator(validator, listOfControls, characterLimit, false, "Marital Status - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.cmbNationality);
        characterLimit = 100;
        validationUtils.validationCreator(validator, listOfControls, 0, false, "Nationality - Required Field", ValidationUtils.isNotEmpty);
        validationUtils.validationCreator(validator, listOfControls, characterLimit, false, "Nationality - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.cmbHonorifics);
        characterLimit = 100;
        validationUtils.validationCreator(validator, listOfControls, 0, false, "Honorifics - Required Field", ValidationUtils.isNotEmpty);
        validationUtils.validationCreator(validator, listOfControls, characterLimit, false, "Honorifics - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.txtEmail);
        characterLimit = 50;
        validationUtils.validationCreator(validator, listOfControls, 0, false, "Email - Required Field", ValidationUtils.isNotEmpty);
        validationUtils.validationCreator(validator, listOfControls, characterLimit, false, "Email - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.txtMobileNo);
        characterLimit = 50;
        validationUtils.validationCreator(validator, listOfControls, 0, false, "Mobile No - Required Field", ValidationUtils.isNotEmpty);
        validationUtils.validationCreator(validator, listOfControls, characterLimit, false, "Mobile No - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.txtExt);
        characterLimit = 50;
        validationUtils.validationCreator(validator, listOfControls, 0, false, "Extension No - Required Field", ValidationUtils.isNotEmpty);
        validationUtils.validationCreator(validator, listOfControls, characterLimit, false, "Extension No - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.txtOffPhNo);
        characterLimit = 50;
        validationUtils.validationCreator(validator, listOfControls, 0, false, "Office Phone No - Required Field", ValidationUtils.isNotEmpty);
        validationUtils.validationCreator(validator, listOfControls, characterLimit, false, "Office Phone No - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.txtHomePhNo);
        characterLimit = 100;
        validationUtils.validationCreator(validator, listOfControls, 0, false, "Home Phone No - Required Field", ValidationUtils.isNotEmpty);
        validationUtils.validationCreator(validator, listOfControls, characterLimit, false, "Home Phone No - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.txtOccupation);
        characterLimit = 100;
        validationUtils.validationCreator(validator, listOfControls, 0, false, "Occupation - Required Field", ValidationUtils.isNotEmpty);
        validationUtils.validationCreator(validator, listOfControls, characterLimit, false, "Occupation - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.cmbRace);
        characterLimit = 50;
        validationUtils.validationCreator(validator, listOfControls, 0, false, "Race - Required Field", ValidationUtils.isNotEmpty);
        validationUtils.validationCreator(validator, listOfControls, characterLimit, false, "Race - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.cmbReligion);
        characterLimit = 50;
        validationUtils.validationCreator(validator, listOfControls, 0, false, "Religion - Required Field", ValidationUtils.isNotEmpty);
        validationUtils.validationCreator(validator, listOfControls, characterLimit, false, "Religion - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.txtWorkPlaceID);
        characterLimit = 20;
        validationUtils.validationCreator(validator, listOfControls, 0, false, "Work Place ID - Required Field", ValidationUtils.isNotEmpty);
        validationUtils.validationCreator(validator, listOfControls, characterLimit, false, "Work Place ID - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.dtEntryDate);
        characterLimit = 20;
        validationUtils.validationCreator(validator, listOfControls, characterLimit, true, "Entry Date - cannot be after current date" + characterLimit + " characters", ValidationUtils.isBeforeCurrentDate);
        listOfControls.clear();
        //================================
        listOfControls.add(this.txtReportTo);
        characterLimit = 20;
        validationUtils.validationCreator(validator, listOfControls, 0, false, "Report To - Required Field", ValidationUtils.isNotEmpty);
        validationUtils.validationCreator(validator, listOfControls, characterLimit, false, "Report To - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.cmbEmpType);
        characterLimit = 50;
        validationUtils.validationCreator(validator, listOfControls, 0, false, "Employee Type - Required Field", ValidationUtils.isNotEmpty);
        validationUtils.validationCreator(validator, listOfControls, characterLimit, false, "Employee Type - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.cmbRole);
        characterLimit = 50;
        validationUtils.validationCreator(validator, listOfControls, 0, false, "Role - Required Field", ValidationUtils.isNotEmpty);
        validationUtils.validationCreator(validator, listOfControls, characterLimit, false, "Role - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.cmbStatus);
        characterLimit = 50;
        validationUtils.validationCreator(validator, listOfControls, 0, false, "Status - Required Field", ValidationUtils.isNotEmpty);
        validationUtils.validationCreator(validator, listOfControls, characterLimit, false, "Status - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.txtResidentialAddrLocationName);
        characterLimit = 500;
        validationUtils.validationCreator(validator, listOfControls, 0, false, "Residential Address Location Name - Required Field", ValidationUtils.isNotEmpty);
        validationUtils.validationCreator(validator, listOfControls, characterLimit, false, "Residential Address Location Name - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.txtResidentialAddrAddress);
        characterLimit = 1000;
        validationUtils.validationCreator(validator, listOfControls, 0, false, "Name - Required Field", ValidationUtils.isNotEmpty);
        validationUtils.validationCreator(validator, listOfControls, characterLimit, false, "Residential Address - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.txtResidentialAddrCity);
        characterLimit = 200;
        validationUtils.validationCreator(validator, listOfControls, 0, false, "Residential Address City - Required Field", ValidationUtils.isNotEmpty);
        validationUtils.validationCreator(validator, listOfControls, characterLimit, false, "Residential Address City - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.txtResidentialAddrPostalCode);
        characterLimit = 200;
        validationUtils.validationCreator(validator, listOfControls, 0, false, "Residential Address Postal Code - Required Field", ValidationUtils.isNotEmpty);
        validationUtils.validationCreator(validator, listOfControls, characterLimit, false, "Residential Address Postal Code - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.cmbResidentialAddrState);
        characterLimit = 200;
        validationUtils.validationCreator(validator, listOfControls, 0, false, "Residential Address State - Required Field", ValidationUtils.isNotEmpty);
        validationUtils.validationCreator(validator, listOfControls, characterLimit, false, "Residential Address State - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.cmbResidentialAddrCountry);
        characterLimit = 200;
        validationUtils.validationCreator(validator, listOfControls, 0, false, "Residential Address Country - Required Field", ValidationUtils.isNotEmpty);
        validationUtils.validationCreator(validator, listOfControls, characterLimit, false, "Residential Address Country - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.txtCorAddrLocationName);
        characterLimit = 500;
        validationUtils.validationCreator(validator, listOfControls, characterLimit, true, "Corresponding Address Location Name - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.txtCorAddrAddress);
        characterLimit = 1000;
        validationUtils.validationCreator(validator, listOfControls, characterLimit, true, "Corresponding Address - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.txtCorAddrCity);
        characterLimit = 200;
        validationUtils.validationCreator(validator, listOfControls, characterLimit, true, "Corresponding Address City - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.txtCorAddrPostalCode);
        characterLimit = 200;
        validationUtils.validationCreator(validator, listOfControls, characterLimit, true, "Corresponding Address Postal Code - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.cmbCorAddrState);
        characterLimit = 200;
        validationUtils.validationCreator(validator, listOfControls, characterLimit, true, "Corresponding Address State - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.cmbCorAddrCountry);
        characterLimit = 200;
        validationUtils.validationCreator(validator, listOfControls, characterLimit, true, "Corresponding Address Country - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
    }

    @Override
    public boolean clearAllFieldsValue() {
        this.txtStaffID.clear();
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
    private void discard(MouseEvent event) {
        if (event.isPrimaryButtonDown() == true) {

            ButtonType alertBtnClicked = alertDialog(Alert.AlertType.CONFIRMATION,
                    "Warning",
                    "Validation Message",
                    "Once discarded, new data provided for current record will be gone.\nAre you sure you want to continue?");

            if (alertBtnClicked == ButtonType.OK) {
                switchScene(passObj.getFxmlPaths().getLast().toString(), new BasicObjs(), BasicObjs.back);
            } else if (alertBtnClicked == ButtonType.CANCEL) {
                //nothing need to do, remain same page
            }
        }
    }

    private void directClose(WindowEvent e) {

        ButtonType alertBtnClicked = alertDialog(Alert.AlertType.CONFIRMATION,
                "Warning",
                "Validation Message",
                "Data unsaved.\nAre you sure you want to continue?");

        if (alertBtnClicked == ButtonType.OK) {
            switchScene(passObj.getFxmlPaths().getLast().toString(), new BasicObjs(), BasicObjs.back);
        } else if (alertBtnClicked == ButtonType.CANCEL) {
            //nothing need to do, remain same page
        }
    }

    @FXML
    private void saveStaff(MouseEvent event) throws IOException {
        if (event.isPrimaryButtonDown() == true) {

            if (validator.containsErrors()) {
                alertDialog(Alert.AlertType.WARNING, "Warning", "Validation Message", validator.createStringBinding().getValue());
                return;
            }

            staffInDraft = prepareStaffInforToObj();

            if (this.passObj.getCrud().equals(BasicObjs.create)) {
                StaffService.saveNewStaff(staffInDraft);
            } else if (this.passObj.getCrud().equals(BasicObjs.update)) {
                StaffService.updateStaff(staffInDraft);
            }
        }
    }

    private Staff prepareStaffInforToObj() throws IOException {
        Staff staff = new Staff();
        staff.setAvatarImg(ImageUtils.imgViewToByte(this.imgAvatarImg));
        staff.setName(this.txtName.getText());
        staff.setGender(this.cmbGender.getText());
        staff.setDOB(this.dtDOB.getValue() == null ? null : (java.sql.Date) Date.from(Instant.from(this.dtDOB.getValue().atStartOfDay(ZoneId.systemDefault())))); //https://stackoverflow.com/questions/20446026/get-value-from-date-picker
        staff.setIC(this.txtIC.getText());
        staff.setMaritalStatus(this.cmbMaritalStatus.getText());
        staff.setNationality(this.cmbNationality.getText());
        staff.setHonorifics(this.cmbHonorifics.getText());

        //Residential Address
        Address residentialAddr = new Address();
        residentialAddr.setLocationName(this.txtResidentialAddrLocationName.getText());
        residentialAddr.setAddress(this.txtResidentialAddrAddress.getText());
        residentialAddr.setCity(this.txtResidentialAddrCity.getText());
        residentialAddr.setPostalCode(this.txtResidentialAddrPostalCode.getText());
        residentialAddr.setState(this.cmbResidentialAddrState.getText());
        residentialAddr.setState(this.cmbResidentialAddrCountry.getText());
        staff.setResidentialAddr(residentialAddr);

        //Corresponding Address
        Address corrAddr = new Address();
        corrAddr.setLocationName(this.txtCorAddrLocationName.getText());
        corrAddr.setAddress(this.txtCorAddrAddress.getText());
        corrAddr.setCity(this.txtCorAddrCity.getText());
        corrAddr.setPostalCode(this.txtCorAddrPostalCode.getText());
        corrAddr.setState(this.cmbCorAddrState.getText());
        corrAddr.setState(this.cmbCorAddrCountry.getText());
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

        staff.setEntryDate(this.dtEntryDate.getValue() == null ? null : (java.sql.Date) Date.from(Instant.from(this.dtEntryDate.getValue().atStartOfDay(ZoneId.systemDefault()))));
        staff.setReportTo(StaffDAO.getStaffByID(this.txtReportTo.getText()));
        staff.setEmpType(this.cmbEmpType.getText());
        staff.setPassword(null);
        staff.setRole(this.cmbRole.getText());
        staff.setStatus(this.cmbStatus.getText());

        return staff;
    }
}
