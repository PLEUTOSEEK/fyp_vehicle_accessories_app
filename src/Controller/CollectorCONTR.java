/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Entity.Address;
import Entity.CollectAddress;
import Entity.Contact;
import PassObjs.BasicObjs;
import Utils.ImageUtils;
import Utils.ValidationUtils;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCircleToggleNode;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import net.synedra.validatorfx.Validator;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class CollectorCONTR implements Initializable, BasicCONTRFunc {

    private BasicObjs passObj;
    @FXML
    private MFXCircleToggleNode btnBack;
    @FXML
    private ImageView imgAvatarImg;
    @FXML
    private MFXButton btnUploadImage;
    @FXML
    private MFXTextField txtName;
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
    private MFXTextField txtOccupation;
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
    private MFXButton btnSave;
    @FXML
    private MFXButton btnDiscard;
    @FXML
    private MFXTextField txtCollectAddrCity;
    @FXML
    private MFXTextField txtCollectAddrLocationName;
    @FXML
    private MFXComboBox<?> cmbCollectAddrState;
    @FXML
    private MFXTextField txtCollectAddrAddress;
    @FXML
    private MFXTextField txtCollectAddrPostalCode;
    @FXML
    private MFXComboBox<?> cmbCollectAddrCountry;
    private Validator validator = new Validator();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                inputValidation();
                receiveData();

                if (passObj.getCrud().equals(BasicObjs.read)) {
                    fieldFillIn();
                    isViewMode(true);
                }
            }
        });
    }

    private void isViewMode(boolean disable) {
        if (disable == true) {
            this.btnSave.setText("Update");
        } else {
            this.btnSave.setText("Save");
        }
        this.btnDiscard.setDisable(disable);
        this.cmbHonorifics.setDisable(disable);
        this.txtName.setDisable(disable);
        this.cmbGender.setDisable(disable);
        this.txtOccupation.setDisable(disable);
        this.dtDOB.setDisable(disable);
        this.txtIC.setDisable(disable);
        this.cmbNationality.setDisable(disable);
        this.cmbRace.setDisable(disable);
        this.cmbMaritalStatus.setDisable(disable);

        this.txtMobileNo.setDisable(disable);
        this.txtEmail.setDisable(disable);
        this.txtExt.setDisable(disable);
        this.txtOffPhNo.setDisable(disable);
        this.txtHomePhNo.setDisable(disable);

        this.btnUploadImage.setDisable(disable);
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

        this.txtCollectAddrLocationName.setDisable(disable);
        this.txtCollectAddrAddress.setDisable(disable);
        this.txtCollectAddrCity.setDisable(disable);
        this.txtCollectAddrPostalCode.setDisable(disable);
        this.cmbCollectAddrState.setDisable(disable);
        this.cmbCollectAddrCountry.setDisable(disable);
    }

    private void fieldFillIn() {
        if (passObj.getObj() != null) {
            CollectAddress collAddr = (CollectAddress) passObj.getObj();

            this.cmbHonorifics.setText(collAddr.getPerson().getHonorifics());
            this.txtName.setText(collAddr.getPerson().getName());
            this.cmbGender.setText(collAddr.getPerson().getGender());
            this.txtOccupation.setText(collAddr.getPerson().getOccupation());
            this.dtDOB.setValue(Instant.ofEpochMilli(collAddr.getPerson().getDOB().getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate());
            this.txtIC.setText(collAddr.getPerson().getIC());
            this.cmbNationality.setText(collAddr.getPerson().getNationality());
            this.cmbRace.setText(collAddr.getPerson().getRace());
            this.cmbReligion.setText(collAddr.getPerson().getReligion());
            this.cmbMaritalStatus.setText(collAddr.getPerson().getMaritalStatus());

            this.txtMobileNo.setText(collAddr.getPerson().getContact().getMobileNo());
            this.txtEmail.setText(collAddr.getPerson().getContact().getEmail());
            this.txtExt.setText(collAddr.getPerson().getContact().getExt());
            this.txtOffPhNo.setText(collAddr.getPerson().getContact().getOffPhNo());
            this.txtHomePhNo.setText(collAddr.getPerson().getContact().getHomePhNo());

            this.txtResidentialAddrLocationName.setText(collAddr.getPerson().getResidentialAddr().getLocationName());
            this.txtResidentialAddrAddress.setText(collAddr.getPerson().getResidentialAddr().getAddress());
            this.txtResidentialAddrCity.setText(collAddr.getPerson().getResidentialAddr().getCity());
            this.txtResidentialAddrPostalCode.setText(collAddr.getPerson().getResidentialAddr().getPostalCode());
            this.cmbResidentialAddrState.setText(collAddr.getPerson().getResidentialAddr().getState());
            this.cmbResidentialAddrCountry.setText(collAddr.getPerson().getResidentialAddr().getCountry());

            this.txtCorAddrLocationName.setText(collAddr.getPerson().getCorAddr().getLocationName());
            this.txtCorAddrAddress.setText(collAddr.getPerson().getCorAddr().getAddress());
            this.txtCorAddrCity.setText(collAddr.getPerson().getCorAddr().getCity());
            this.txtCorAddrPostalCode.setText(collAddr.getPerson().getCorAddr().getPostalCode());
            this.cmbCorAddrState.setText(collAddr.getPerson().getCorAddr().getState());
            this.cmbCorAddrCountry.setText(collAddr.getPerson().getCorAddr().getCountry());

            this.txtCollectAddrLocationName.setText(collAddr.getAddr().getLocationName());
            this.txtCollectAddrAddress.setText(collAddr.getAddr().getAddress());
            this.txtCollectAddrCity.setText(collAddr.getAddr().getCity());
            this.txtCollectAddrPostalCode.setText(collAddr.getAddr().getPostalCode());
            this.cmbCollectAddrState.setText(collAddr.getAddr().getState());
            this.cmbCollectAddrCountry.setText(collAddr.getAddr().getCountry());
        }
    }
//<editor-fold defaultstate="collapsed" desc="comment">

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
        validationUtils.validationCreator(validator, listOfControls, characterLimit, true, "Gender - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.dtDOB);
        characterLimit = 100;
        validationUtils.validationCreator(validator, listOfControls, characterLimit, true, "Date of birth - cannot be after current date", ValidationUtils.isBeforeCurrentDate);
        listOfControls.clear();
        //================================
        listOfControls.add(this.txtIC);
        characterLimit = 100;
        validationUtils.validationCreator(validator, listOfControls, characterLimit, true, "IC - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.cmbMaritalStatus);
        characterLimit = 50;
        validationUtils.validationCreator(validator, listOfControls, characterLimit, true, "Marital Status - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.cmbNationality);
        characterLimit = 100;
        validationUtils.validationCreator(validator, listOfControls, characterLimit, true, "Nationality - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
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
        validationUtils.validationCreator(validator, listOfControls, characterLimit, true, "Email - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
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
        validationUtils.validationCreator(validator, listOfControls, characterLimit, true, "Extension No - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
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
        validationUtils.validationCreator(validator, listOfControls, characterLimit, true, "Occupation - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.cmbRace);
        characterLimit = 50;
        validationUtils.validationCreator(validator, listOfControls, characterLimit, true, "Race - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.cmbReligion);
        characterLimit = 50;
        validationUtils.validationCreator(validator, listOfControls, characterLimit, true, "Religion - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.txtResidentialAddrLocationName);
        characterLimit = 500;
        validationUtils.validationCreator(validator, listOfControls, characterLimit, true, "Residential Address Location Name - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.txtResidentialAddrAddress);
        characterLimit = 1000;
        validationUtils.validationCreator(validator, listOfControls, characterLimit, true, "Residential Address - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.txtResidentialAddrCity);
        characterLimit = 200;
        validationUtils.validationCreator(validator, listOfControls, characterLimit, true, "Residential Address City - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.txtResidentialAddrPostalCode);
        characterLimit = 200;
        validationUtils.validationCreator(validator, listOfControls, characterLimit, true, "Residential Address Postal Code - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.cmbResidentialAddrState);
        characterLimit = 200;
        validationUtils.validationCreator(validator, listOfControls, characterLimit, true, "Residential Address State - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.cmbResidentialAddrCountry);
        characterLimit = 200;
        validationUtils.validationCreator(validator, listOfControls, characterLimit, true, "Residential Address Country - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
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
        //================================
        listOfControls.add(this.txtCollectAddrLocationName);
        characterLimit = 500;
        validationUtils.validationCreator(validator, listOfControls, characterLimit, true, "Corresponding Address Location Name - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.txtCollectAddrAddress);
        characterLimit = 1000;
        validationUtils.validationCreator(validator, listOfControls, characterLimit, true, "Corresponding Address - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.txtCollectAddrCity);
        characterLimit = 200;
        validationUtils.validationCreator(validator, listOfControls, characterLimit, true, "Corresponding Address City - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.txtCollectAddrPostalCode);
        characterLimit = 200;
        validationUtils.validationCreator(validator, listOfControls, characterLimit, true, "Corresponding Address Postal Code - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.cmbCollectAddrState);
        characterLimit = 200;
        validationUtils.validationCreator(validator, listOfControls, characterLimit, true, "Corresponding Address State - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.cmbCollectAddrCountry);
        characterLimit = 200;
        validationUtils.validationCreator(validator, listOfControls, characterLimit, true, "Corresponding Address Country - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================

    }
//</editor-fold>

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

    @Override
    public void receiveData() {
        // Step 1
        Stage stage = (Stage) btnBack.getScene().getWindow();
        // Step 2
        if (stage.getUserData() != null) {
            passObj = (BasicObjs) stage.getUserData();
        } else {
            passObj = new BasicObjs();
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
            quitWindow("Warning", "Validation Message", "Record will be discarded.\nAre you sure you want to continue?");
        }
    }

    @FXML
    private void saveCollector(MouseEvent event) throws IOException {
        Stage stage = (Stage) btnDiscard.getScene().getWindow();
        BasicObjs passObj = new BasicObjs();
        passObj.setObj(prepareCollectAddressInforToObj());
        stage.setUserData(passObj);
        stage.close();
    }

    private void quitWindow(String title, String headerTxt, String contentTxt) {
        ButtonType alertBtnClicked = alertDialog(Alert.AlertType.CONFIRMATION,
                title,
                headerTxt,
                contentTxt);

        if (alertBtnClicked == ButtonType.OK) {
            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.setUserData(null);
            stage.close();
        } else if (alertBtnClicked == ButtonType.CANCEL) {
            //nothing need to do, remain same page
        }
    }

    @Override
    public void switchScene(String fxmlPath, BasicObjs passObj, String direction) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public BasicObjs sendData(BasicObjs passObj, String direction) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private CollectAddress prepareCollectAddressInforToObj() throws IOException {
        CollectAddress collAddr = new CollectAddress();

        Address residentialAddr = new Address();
        residentialAddr.setLocationName(this.txtResidentialAddrLocationName.getText());
        residentialAddr.setAddress(this.txtResidentialAddrAddress.getText());
        residentialAddr.setCity(this.txtResidentialAddrCity.getText());
        residentialAddr.setPostalCode(this.txtResidentialAddrPostalCode.getText());
        residentialAddr.setState(this.cmbResidentialAddrState.getText());
        residentialAddr.setCountry(this.cmbResidentialAddrCountry.getText());
        collAddr.getPerson().setResidentialAddr(residentialAddr);

        Address corrAddr = new Address();
        corrAddr.setLocationName(this.txtCorAddrLocationName.getText());
        corrAddr.setAddress(this.txtCorAddrAddress.getText());
        corrAddr.setCity(this.txtCorAddrCity.getText());
        corrAddr.setPostalCode(this.txtCorAddrPostalCode.getText());
        corrAddr.setState(this.cmbCorAddrState.getText());
        corrAddr.setCountry(this.cmbCorAddrCountry.getText());
        collAddr.getPerson().setCorAddr(corrAddr);

        Address collectAddress = new Address();
        collectAddress.setLocationName(this.txtCollectAddrLocationName.getText());
        collectAddress.setAddress(this.txtCollectAddrAddress.getText());
        collectAddress.setCity(this.txtCollectAddrCity.getText());
        collectAddress.setPostalCode(this.txtCollectAddrPostalCode.getText());
        collectAddress.setState(this.cmbCollectAddrState.getText());
        collectAddress.setCountry(this.cmbCollectAddrCountry.getText());
        collAddr.setAddr(collectAddress);

        Contact contact = new Contact();
        contact.setEmail(this.txtEmail.getText());
        contact.setMobileNo(this.txtMobileNo.getText());
        contact.setExt(this.txtExt.getText());
        contact.setOffPhNo(this.txtOffPhNo.getText());
        contact.setHomePhNo(this.txtHomePhNo.getText());
        collAddr.getPerson().setContact(contact);

        collAddr.getPerson().setAvatarImg(ImageUtils.imgViewToByte(this.imgAvatarImg));
        collAddr.getPerson().setName(this.txtName.getText());
        collAddr.getPerson().setGender(this.cmbGender.getText());
        collAddr.getPerson().setDOB(this.dtDOB.getValue() == null ? null : (java.sql.Date) Date.from(Instant.from(this.dtDOB.getValue().atStartOfDay(ZoneId.systemDefault())))); //https://stackoverflow.com/questions/20446026/get-value-from-date-picker
        collAddr.getPerson().setIC(this.txtIC.getText());
        collAddr.getPerson().setMaritalStatus(this.cmbMaritalStatus.getText());
        collAddr.getPerson().setNationality(this.cmbNationality.getText());
        collAddr.getPerson().setHonorifics(this.cmbHonorifics.getText());
        collAddr.getPerson().setOccupation(this.txtOccupation.getText());
        collAddr.getPerson().setRace(this.cmbRace.getText());
        collAddr.getPerson().setReligion(this.cmbReligion.getText());

        return collAddr;
    }

}
