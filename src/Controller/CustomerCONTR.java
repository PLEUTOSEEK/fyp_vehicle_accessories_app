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
import io.github.palexdev.materialfx.utils.SwingFXUtils;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

            //<editor-fold defaultstate="collapsed" desc="Customer constructor">
            Customer customer = new Customer(
                    new Timestamp(System.currentTimeMillis()),
                    new Timestamp(System.currentTimeMillis()),
                    ImageUtils.toByteArray(SwingFXUtils.fromFXImage(this.imgAvatarImg.getImage(), null), "png"),
                    this.txtName.getText(),
                    this.cmbGender.getSelectedText(),
                    (Date) new SimpleDateFormat("MMM dd, yyyy").parse(this.dtDOB.getText()),
                    this.txtIC.getText(),
                    this.cmbMaritalStatus.getText(),
                    this.cmbNationality.getText(),
                    this.cmbHonorifics.getText(),
                    new Address(
                            new Timestamp(System.currentTimeMillis()),
                            new Timestamp(System.currentTimeMillis()),
                            "", // Address ID
                            this.txtResidentialAddrLocationName.getText(),
                            this.txtResidentialAddrAddress.getText(),
                            this.txtResidentialAddrCity.getText(),
                            this.txtResidentialAddrPostalCode.getText(),
                            this.cmbResidentialAddrState.getText(),
                            this.cmbResidentialAddrCountry.getText()
                    ),
                    new Address(
                            new Timestamp(System.currentTimeMillis()),
                            new Timestamp(System.currentTimeMillis()),
                            "", // Address ID
                            this.txtCorAddrLocationName.getText(),
                            this.txtCorAddrAddress.getText(),
                            this.txtCorAddrCity.getText(),
                            this.txtCorAddrPostalCode.getText(),
                            this.cmbCorAddrState.getText(),
                            this.cmbCorAddrCountry.getText()
                    ),
                    new Contact(
                            this.txtEmail.getText(),
                            this.txtMobileNo.getText(),
                            this.txtExt.getText(),
                            this.txtOffPhNo.getText(),
                            this.txtHomePhNo.getText()
                    ),
                    this.txtOccupation.getText(),
                    this.cmbRace.getText(),
                    this.cmbReligion.getText(),
                    this.cmbStatus.getText(),
                    "", // Customer ID
                    this.cmbBankAccProvider.getText(),
                    this.txtBankAccNo.getText(),
                    new Address(
                            new Timestamp(System.currentTimeMillis()),
                            new Timestamp(System.currentTimeMillis()),
                            "", // Address ID
                            this.txtBillToAddrLocationName.getText(),
                            this.txtBillToAddrAddress.getText(),
                            this.txtBillToAddrCity.getText(),
                            this.txtBillToAddrPostalCode.getText(),
                            this.cmbBillToAddrState.getText(),
                            this.cmbBillToAddrCountry.getText()
                    ),
                    null, // collect Addresses
                    this.cmbCustType.getText()
            );
            //</editor-fold>

            Validator validateWithDB = CustomerService.saveCustomer(customer);

            if (validateWithDB.containsErrors()) {
                alertDialog(Alert.AlertType.WARNING, "Warning", "Validation Message", validateWithDB.createStringBinding().getValue());
                return;
            } else {
                // alert successful insert to database
                alertDialog(Alert.AlertType.INFORMATION, "Information", "Create Success Message", customer.getCustID() + " created");
                // clear all fields for another customer creation
                clearAllFieldsValue();
            }
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
        //================================
        listOfControls.add(this.txtName);
        validationUtils.validationCreator(validator, listOfControls, "Name - Required Field", ValidationUtils.isNotEmpty);
        validationUtils.validationCreator(validator, listOfControls, "Name - Only Aphabet and Space are allow", ValidationUtils.isAlphaSpace);
        listOfControls.clear();
        //================================
        listOfControls.add(this.cmbGender);
        validationUtils.validationCreator(validator, listOfControls, "Gender - Required Field", ValidationUtils.isNotEmpty);
        listOfControls.clear();
        //================================
        //...
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Alert Dialog Creator">
    @Override
    public void alertDialog(Alert.AlertType alertType, String title, String headerTxt, String contentTxt) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerTxt);
        alert.setContentText(contentTxt);

        alert.showAndWait();
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

}
