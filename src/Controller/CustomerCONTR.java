/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import Entity.Address;
import Entity.Contact;
import Entity.Customer;
import PassObjs.BasicObjs;
import Service.CustomerService;
import Utils.ImageUtils;
import Utils.ValidationUtils;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCircleToggleNode;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import net.synedra.validatorfx.Validator;

/**
 * FXML Controller class
 *
 * @author Tee Zhuo Xuan
 */
public class CustomerCONTR implements Initializable, BasicCONTRFunc {

    private BasicObjs passObj;
    private Validator validator = new Validator();
    @FXML
    private MFXCircleToggleNode btnBack;
    @FXML
    private MFXButton btnSave;
    @FXML
    private MFXTextField txtName;
    @FXML
    private MFXTextField txtCustID;
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
    private MFXTextField txtBillToAddrCity;
    @FXML
    private MFXTextField txtBillToAddrLocationName;
    @FXML
    private MFXComboBox<?> cmbBillToAddrState;
    @FXML
    private MFXTextField txtBillToAddrAddress;
    @FXML
    private MFXTextField txtBillToAddrPostalCode;
    @FXML
    private MFXComboBox<?> cmbBillToAddrCountry;
    @FXML
    private MFXComboBox<?> cmbBankAccProvider;
    @FXML
    private MFXTextField txtBankAccNo;
    @FXML
    private MFXComboBox<?> cmbCustType;
    @FXML
    private MFXComboBox<?> cmbStatus;
    @FXML
    private ImageView imgAvatarImg;

    private Customer custInDraft;

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
            }
        });
    }

    @FXML
    private void goBackPrevious(MouseEvent event) {
        switchScene(passObj.getFxmlPaths().getLast().toString(), new BasicObjs(), BasicObjs.back);
    }

    @FXML
    private void saveCustomer(MouseEvent event) throws ParseException, IOException {
        if (event.isPrimaryButtonDown() == true) {

            if (validator.containsErrors()) {
                alertDialog(Alert.AlertType.WARNING, "Warning", "Validation Message", validator.createStringBinding().getValue());
                return;
            }

            custInDraft = prepareCustomerInforToObj();

            if (this.passObj.getCrud().equals(BasicObjs.create)) {
                CustomerService.saveNewCustomer(custInDraft);

            } else if (this.passObj.getCrud().equals(BasicObjs.update)) {
                CustomerService.updateCustomer(custInDraft);
            }

            /*
            // alert successful insert to database
            alertDialog(Alert.AlertType.INFORMATION, "Information", "Create Success Message", customer.getCustID() + " created");
            // clear all fields for another customer creation
            clearAllFieldsValue();
             */
        }
    }

    @Override
    public boolean clearAllFieldsValue() {
        //notes:
        // Combo Box should consider one default value as it already have selection items
        this.txtName.clear();
        this.txtCustID.clear();
        this.cmbGender.clear();
        this.cmbMaritalStatus.clear();
        this.cmbHonorifics.clear();
        this.txtIC.clear();
        this.dtDOB.clear();
        this.dtDOB.clear();
        this.cmbNationality.clear();
        this.cmbRace.clear();
        this.cmbReligion.clear();
        this.txtOccupation.clear();
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
        this.txtBillToAddrCity.clear();
        this.txtBillToAddrLocationName.clear();
        this.cmbBillToAddrState.clear();
        this.txtBillToAddrAddress.clear();
        this.txtBillToAddrPostalCode.clear();
        this.cmbBillToAddrCountry.clear();
        this.cmbBankAccProvider.clear();
        this.txtBankAccNo.clear();
        this.cmbCustType.clear();
        this.cmbStatus.clear();
        this.imgAvatarImg.setImage(null);
        return true;
    }

    //<editor-fold defaultstate="collapsed" desc="Validation for all the neccessary fields in UI">
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
        validationUtils.validationCreator(validator, listOfControls, characterLimit, true, "Date of birth - cannot be after current date" + characterLimit + " characters", ValidationUtils.isBeforeCurrentDate);
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
        listOfControls.add(this.cmbBankAccProvider);
        characterLimit = 100;
        validationUtils.validationCreator(validator, listOfControls, characterLimit, true, "Bank Account Provider - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.txtBankAccNo);
        characterLimit = 100;
        validationUtils.validationCreator(validator, listOfControls, characterLimit, true, "Bank Account Number - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.cmbCustType);
        characterLimit = 50;
        validationUtils.validationCreator(validator, listOfControls, 0, false, "Customer Type - Required Field", ValidationUtils.isNotEmpty);
        validationUtils.validationCreator(validator, listOfControls, characterLimit, false, "Customer Type - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.cmbStatus);
        characterLimit = 30;
        validationUtils.validationCreator(validator, listOfControls, 0, false, "Status - Required Field", ValidationUtils.isNotEmpty);
        validationUtils.validationCreator(validator, listOfControls, characterLimit, false, "Status - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.txtBillToAddrLocationName);
        characterLimit = 500;
        validationUtils.validationCreator(validator, listOfControls, 0, false, "Bill To Address Location Name - Required Field", ValidationUtils.isNotEmpty);
        validationUtils.validationCreator(validator, listOfControls, characterLimit, false, "Bill To Address Location Name - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.txtBillToAddrAddress);
        characterLimit = 1000;
        validationUtils.validationCreator(validator, listOfControls, 0, false, "Bill To Address - Required Field", ValidationUtils.isNotEmpty);
        validationUtils.validationCreator(validator, listOfControls, characterLimit, false, "Bill To Address - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.txtBillToAddrCity);
        characterLimit = 200;
        validationUtils.validationCreator(validator, listOfControls, 0, false, "Bill To Address City - Required Field", ValidationUtils.isNotEmpty);
        validationUtils.validationCreator(validator, listOfControls, characterLimit, false, "Bill To Address City - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.txtBillToAddrPostalCode);
        characterLimit = 200;
        validationUtils.validationCreator(validator, listOfControls, 0, false, "Bill To Address Postal Code - Required Field", ValidationUtils.isNotEmpty);
        validationUtils.validationCreator(validator, listOfControls, characterLimit, false, "Bill To Address Postal Code - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.cmbBillToAddrState);
        characterLimit = 200;
        validationUtils.validationCreator(validator, listOfControls, 0, false, "Bill To Address State - Required Field", ValidationUtils.isNotEmpty);
        validationUtils.validationCreator(validator, listOfControls, characterLimit, false, "Bill To Address State - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
        listOfControls.clear();
        //================================
        listOfControls.add(this.cmbBillToAddrCountry);
        characterLimit = 200;
        validationUtils.validationCreator(validator, listOfControls, 0, false, "Bill To Address Country - Required Field", ValidationUtils.isNotEmpty);
        validationUtils.validationCreator(validator, listOfControls, characterLimit, false, "Bill To Address Country - cannot exceed " + characterLimit + " characters", ValidationUtils.isExceed);
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
        //...
        //Note: Addresses still haven't figure out how to put
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Alert Dialog Creator">
    @Override
    public ButtonType alertDialog(Alert.AlertType alertType, String title, String headerTxt, String contentTxt) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerTxt);
        alert.setContentText(contentTxt);

        alert.showAndWait();
        return alert.getResult();
    }
    //</editor-fold>

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
                passObj.getFxmlPaths().addLast("View/Customer_UI.fxml");
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

    private Customer prepareCustomerInforToObj() throws IOException {
        Customer customer = new Customer();

        Address billToAddr = new Address();
        billToAddr.setLocationName(this.txtBillToAddrLocationName.getText());
        billToAddr.setAddress(this.txtBillToAddrAddress.getText());
        billToAddr.setCity(this.txtBillToAddrCity.getText());
        billToAddr.setPostalCode(this.txtBillToAddrPostalCode.getText());
        billToAddr.setState(this.cmbBillToAddrState.getText());
        billToAddr.setCountry(this.cmbBillToAddrCountry.getText());
        customer.setBillToAddr(billToAddr);

        Address residentialAddr = new Address();
        billToAddr.setLocationName(this.txtResidentialAddrLocationName.getText());
        billToAddr.setAddress(this.txtResidentialAddrAddress.getText());
        billToAddr.setCity(this.txtResidentialAddrCity.getText());
        billToAddr.setPostalCode(this.txtResidentialAddrPostalCode.getText());
        billToAddr.setState(this.cmbResidentialAddrState.getText());
        billToAddr.setCountry(this.cmbResidentialAddrCountry.getText());
        customer.setResidentialAddr(residentialAddr);

        Address corrAddr = new Address();
        corrAddr.setLocationName(this.txtCorAddrLocationName.getText());
        corrAddr.setAddress(this.txtCorAddrAddress.getText());
        corrAddr.setCity(this.txtCorAddrCity.getText());
        corrAddr.setPostalCode(this.txtCorAddrPostalCode.getText());
        corrAddr.setState(this.cmbCorAddrState.getText());
        corrAddr.setCountry(this.cmbCorAddrCountry.getText());
        customer.setCorAddr(corrAddr);

        Contact contact = new Contact();
        contact.setEmail(this.txtEmail.getText());
        contact.setMobileNo(this.txtMobileNo.getText());
        contact.setExt(this.txtExt.getText());
        contact.setOffPhNo(this.txtOffPhNo.getText());
        contact.setHomePhNo(this.txtHomePhNo.getText());
        customer.setContact(contact);

        customer.setAvatarImg(ImageUtils.imgViewToByte(this.imgAvatarImg));
        customer.setName(this.txtName.getText());
        customer.setGender(this.cmbGender.getText());
        customer.setDOB(this.dtDOB.getValue() == null ? null : (java.sql.Date) Date.from(Instant.from(this.dtDOB.getValue().atStartOfDay(ZoneId.systemDefault())))); //https://stackoverflow.com/questions/20446026/get-value-from-date-picker
        customer.setIC(this.txtIC.getText());
        customer.setMaritalStatus(this.cmbMaritalStatus.getText());
        customer.setNationality(this.cmbNationality.getText());
        customer.setHonorifics(this.cmbHonorifics.getText());
        customer.setOccupation(this.txtOccupation.getText());
        customer.setRace(this.cmbRace.getText());
        customer.setReligion(this.cmbReligion.getText());
        customer.setReligion(this.cmbReligion.getText());
        customer.setBankAccNo(this.txtBankAccNo.getText());
        customer.setCustType(this.cmbCustType.getText());
        customer.setStatus(this.cmbStatus.getText());

        return customer;
    }
}
