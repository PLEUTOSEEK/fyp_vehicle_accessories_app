/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import Entity.Address;
import Entity.CollectAddress;
import Entity.Contact;
import Entity.Customer;
import PassObjs.BasicObjs;
import Service.AddressService;
import Service.CollectAddressService;
import Service.CustomerService;
import Utils.ImageUtils;
import Utils.ValidationUtils;
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
import java.text.ParseException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.synedra.validatorfx.Validator;

/**
 * FXML Controller class
 *
 * @author Tee Zhuo Xuan
 */
public class CustomerCONTR implements Initializable, BasicCONTRFunc {

    //<editor-fold defaultstate="collapsed" desc="fields">
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
    @FXML
    private MFXButton btnDiscard;
    @FXML
    private MFXButton btnUploadImage;
    @FXML
    private MFXButton btnAdd;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="util declarations">
    private BasicObjs passObj;
    private Validator validator = new Validator();
    private List<CollectAddress> newCollectAddresses = new ArrayList<>(); // use to know which address been update, and perform update action for those modified address
    private List<CollectAddress> tempCollectAddresses = new ArrayList<>(); // use to know which address been update, and perform update action for those modified address
    private List<CollectAddress> collectAddresses = new ArrayList<>(); // use to know which address been update, and perform update action for those modified address
    private Customer custInDraft;
    //</editor-fold>

    @FXML
    private MFXTableView<?> tblVw;

    private static List<String> rowSelected = new ArrayList<>();

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

                if (passObj.getCrud().equals(BasicObjs.read) || passObj.getCrud().equals(BasicObjs.update)) {
                    try {
                        fieldFillIn();
                        collectAddresses = CollectAddressService.getCollectAddressByCustID(((Customer) passObj.getObj()).getCustID());
                        setupCollectAddressTable();
                    } catch (IOException ex) {
                        Logger.getLogger(CustomerCONTR.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                if (passObj.getCrud().equals(BasicObjs.read)) {
                    isViewMode(true);
                }
            }
        });
    }

    private void setupCollectAddressTable() {
        // Collect Address ID
        MFXTableColumn<CollectAddress> collAddrIDCol = new MFXTableColumn<>("Collect Address ID", true, Comparator.comparing(collAddr -> collAddr.getCollectAddrID()));
        // Location Name
        MFXTableColumn<CollectAddress> locationNmCol = new MFXTableColumn<>("Location Name", true, Comparator.comparing(collAddr -> collAddr.getAddr().getLocationName()));
        // Address
        MFXTableColumn<CollectAddress> addrCol = new MFXTableColumn<>("Address", true, Comparator.comparing(collAddr -> collAddr.getAddr().getAddress()));
        // City
        MFXTableColumn<CollectAddress> cityCol = new MFXTableColumn<>("City", true, Comparator.comparing(collAddr -> collAddr.getAddr().getCity()));
        // Name
        MFXTableColumn<CollectAddress> nmCol = new MFXTableColumn<>("Name", true, Comparator.comparing(collAddr -> collAddr.getPerson().getName()));
        // Email
        MFXTableColumn<CollectAddress> emailCol = new MFXTableColumn<>("Email", true, Comparator.comparing(collAddr -> collAddr.getPerson().getContact().getEmail()));
        // Mobile Number
        MFXTableColumn<CollectAddress> mobNoCol = new MFXTableColumn<>("Mobile No.", true, Comparator.comparing(collAddr -> collAddr.getPerson().getContact().getMobileNo()));

        // Collect Address ID
        collAddrIDCol.setRowCellFactory(collectAddress -> new MFXTableRowCell<>(collAddr -> collAddr.getCollectAddrID()));
        // Location Name
        locationNmCol.setRowCellFactory(collectAddress -> new MFXTableRowCell<>(collAddr -> collAddr.getAddr().getLocationName()));
        // Address
        addrCol.setRowCellFactory(collectAddress -> new MFXTableRowCell<>(collAddr -> collAddr.getAddr().getAddress()));
        // City
        cityCol.setRowCellFactory(collectAddress -> new MFXTableRowCell<>(collAddr -> collAddr.getAddr().getCity()));
        // Name
        nmCol.setRowCellFactory(collectAddress -> new MFXTableRowCell<>(collAddr -> collAddr.getPerson().getName()));
        // Email
        emailCol.setRowCellFactory(collectAddress -> new MFXTableRowCell<>(collAddr -> collAddr.getPerson().getContact().getEmail()));
        // Mobile Number
        mobNoCol.setRowCellFactory(collectAddress -> new MFXTableRowCell<>(collAddr -> collAddr.getPerson().getContact().getMobileNo()));

        ((MFXTableView<CollectAddress>) tblVw).getTableColumns().clear();
        ((MFXTableView<CollectAddress>) tblVw).getTableColumns().addAll(
                collAddrIDCol,
                locationNmCol,
                addrCol,
                cityCol,
                nmCol,
                emailCol,
                mobNoCol
        );

        ((MFXTableView<CollectAddress>) tblVw).getFilters().clear();
        ((MFXTableView<CollectAddress>) tblVw).getFilters().addAll(
                new StringFilter<>("Collect Address ID", collAddr -> collAddr.getCollectAddrID()),
                new StringFilter<>("Location Name", collAddr -> collAddr.getAddr().getLocationName()),
                new StringFilter<>("Address", collAddr -> collAddr.getAddr().getAddress()),
                new StringFilter<>("City", collAddr -> collAddr.getAddr().getCity()),
                new StringFilter<>("Name", collAddr -> collAddr.getPerson().getName()),
                new StringFilter<>("Email", collAddr -> collAddr.getPerson().getContact().getEmail()),
                new StringFilter<>("Mobile No.", collAddr -> collAddr.getPerson().getContact().getMobileNo())
        );

        tempCollectAddresses.addAll(collectAddresses);
        ((MFXTableView<CollectAddress>) tblVw).getItems().clear();
        ((MFXTableView<CollectAddress>) tblVw).setItems(FXCollections.observableList(collectAddresses));
        tempCollectAddresses.clear();

        ((MFXTableView<CollectAddress>) tblVw).getSelectionModel().selectionProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {

                if (((MFXTableView<CollectAddress>) tblVw).getSelectionModel().getSelectedValues().size() != 0) {
                    CollectAddress collectAddress = (((MFXTableView<CollectAddress>) tblVw).getSelectionModel().getSelectedValues().get(0));
                    rowSelected.add(collectAddress.getCollectAddrID());

                    if (rowSelected.size() == 2) {
                        if (rowSelected.get(0).equals(rowSelected.get(1))) {
                            System.out.println(collectAddress.getCollectAddrID());
                            // action here
                            Parent root;
                            try {
                                root = FXMLLoader.load(getClass().getClassLoader().getResource("View/InnerEntitySelect_UI.fxml"));
                                Stage stage = new Stage();
                                stage.initModality(Modality.WINDOW_MODAL);
                                stage.initOwner(btnBack.getScene().getWindow());
                                stage.setScene(new Scene(root));

                                BasicObjs passObj = new BasicObjs();
                                passObj.setObj(collectAddress);
                                passObj.setCrud(BasicObjs.read);

                                stage.setUserData(passObj);
                                stage.showAndWait();

                                // if have any change on the selected collect address
                                if (stage.getUserData() != null) {

                                    BasicObjs receiveObj = (BasicObjs) stage.getUserData();
                                    CollectAddress catchedCollectAddress = new CollectAddress();
                                    catchedCollectAddress = (CollectAddress) receiveObj.getObj();

                                    if (!collectAddresses.contains(catchedCollectAddress)) {
                                        collectAddresses.add(catchedCollectAddress);
                                    } else {
                                        collectAddresses.set(collectAddresses.indexOf(collectAddress), catchedCollectAddress);
                                    }

                                    setupCollectAddressTable();

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
    private void fieldFillIn() throws IOException {
        if (passObj.getObj() != null) {
            //fill all data column with object information
            Customer customer = (Customer) passObj.getObj();
            this.txtCustID.setText(customer.getCustID());
            this.cmbHonorifics.setText(customer.getHonorifics());
            this.txtName.setText(customer.getName());
            this.cmbGender.setText(customer.getGender());
            this.txtOccupation.setText(customer.getOccupation());
            this.dtDOB.setValue(Instant.ofEpochMilli(customer.getDOB().getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate());
            this.txtIC.setText(customer.getIC());
            this.cmbNationality.setText(customer.getNationality());
            this.cmbRace.setText(customer.getRace());
            this.cmbReligion.setText(customer.getReligion());
            this.cmbMaritalStatus.setText(customer.getMaritalStatus());

            this.txtMobileNo.setText(customer.getContact().getMobileNo());
            this.txtEmail.setText(customer.getContact().getEmail());
            this.txtExt.setText(customer.getContact().getExt());
            this.txtOffPhNo.setText(customer.getContact().getOffPhNo());
            this.txtHomePhNo.setText(customer.getContact().getHomePhNo());

            this.txtResidentialAddrLocationName.setText(customer.getResidentialAddr().getLocationName());
            this.txtResidentialAddrAddress.setText(customer.getResidentialAddr().getAddress());
            this.txtResidentialAddrCity.setText(customer.getResidentialAddr().getCity());
            this.txtResidentialAddrPostalCode.setText(customer.getResidentialAddr().getPostalCode());
            this.cmbResidentialAddrState.setText(customer.getResidentialAddr().getState());
            this.cmbResidentialAddrCountry.setText(customer.getResidentialAddr().getCountry());

            this.txtCorAddrLocationName.setText(customer.getCorAddr().getLocationName());
            this.txtCorAddrAddress.setText(customer.getCorAddr().getAddress());
            this.txtCorAddrCity.setText(customer.getCorAddr().getCity());
            this.txtCorAddrPostalCode.setText(customer.getCorAddr().getPostalCode());
            this.cmbCorAddrState.setText(customer.getCorAddr().getState());
            this.cmbCorAddrCountry.setText(customer.getCorAddr().getCountry());

            this.cmbCustType.setText(customer.getCustType());
            this.cmbBankAccProvider.setText(customer.getBankAccProvider());
            this.txtBankAccNo.setText(customer.getBankAccNo());

            this.txtBillToAddrLocationName.setText(customer.getBillToAddr().getLocationName());
            this.txtBillToAddrAddress.setText(customer.getBillToAddr().getAddress());
            this.txtBillToAddrCity.setText(customer.getBillToAddr().getCity());
            this.txtBillToAddrPostalCode.setText(customer.getBillToAddr().getPostalCode());
            this.cmbBillToAddrState.setText(customer.getBillToAddr().getState());
            this.cmbBillToAddrCountry.setText(customer.getBillToAddr().getCountry());

            this.cmbStatus.setText(customer.getStatus());

            this.imgAvatarImg.setImage(ImageUtils.byteToImg(
                    ImageUtils.encodedStrToByte(
                            customer.getAvatarImg().isEmpty() == true
                            ? null : customer.getAvatarImg()
                    )));
        }
    }

    private void isViewMode(boolean disable) {
        if (disable == true) {
            this.btnSave.setText("Update");
        } else {
            this.btnSave.setText("Save");
        }
        this.btnAdd.setDisable(disable);
        this.btnDiscard.setDisable(disable);
        this.txtCustID.setDisable(disable);
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
        this.cmbCustType.setDisable(disable);
        this.cmbBankAccProvider.setDisable(disable);
        this.txtBankAccNo.setDisable(disable);

        this.txtBillToAddrLocationName.setDisable(disable);
        this.txtBillToAddrAddress.setDisable(disable);
        this.txtBillToAddrCity.setDisable(disable);
        this.txtBillToAddrPostalCode.setDisable(disable);
        this.cmbBillToAddrState.setDisable(disable);
        this.cmbBillToAddrCountry.setDisable(disable);

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

    private void quitWindow(String title, String headerTxt, String contentTxt) {
        ButtonType alertBtnClicked = alertDialog(Alert.AlertType.CONFIRMATION,
                title,
                headerTxt,
                contentTxt);

        if (alertBtnClicked == ButtonType.OK) {
            switchScene(passObj.getFxmlPaths().getLast().toString(), new BasicObjs(), BasicObjs.back);
        } else if (alertBtnClicked == ButtonType.CANCEL) {
            //nothing need to do, remain same page
        }
    }

    @FXML
    private void saveCustomer(MouseEvent event) throws ParseException, IOException {
        if (event.isPrimaryButtonDown() == true) {

            if (!this.btnSave.getText().equals("Save")) {
                isViewMode(false);
                return;
            }

            if (validator.containsErrors()) {
                alertDialog(Alert.AlertType.WARNING, "Warning", "Validation Message", validator.createStringBinding().getValue());
                return;
            }

            saveCustomer();
            saveCollectAddresses();

            /*
            // alert successful insert to database
            alertDialog(Alert.AlertType.INFORMATION, "Information", "Create Success Message", customer.getCustID() + " created");
            // clear all fields for another customer creation
            clearAllFieldsValue();
             */
        }
    }

    public void saveCustomer() {
        if (this.passObj.getCrud().equals(BasicObjs.create)) {
            custInDraft.getResidentialAddr().setAddressID(AddressService.saveNewAddress(custInDraft.getResidentialAddr()));

            custInDraft.getCorAddr().setAddressID(AddressService.saveNewAddress(custInDraft.getCorAddr()));

            custInDraft.setCustID(CustomerService.saveNewCustomer(custInDraft));

        } else if (this.passObj.getCrud().equals(BasicObjs.update) || this.passObj.getCrud().equals(BasicObjs.read)) {

            AddressService.updateAddress(custInDraft.getResidentialAddr());
            AddressService.updateAddress(custInDraft.getCorAddr());

            CustomerService.updateCustomer(custInDraft);
        }
    }

    public void saveCollectAddresses() {
        for (CollectAddress collAddr : newCollectAddresses) {
            if (!collectAddresses.contains(collAddr) && collAddr.getCollectAddrID().isEmpty()) {
                Customer customer = new Customer();
                customer.setCustID(custInDraft.getCustID());
                collAddr.setCustomer(customer);

                collAddr.getPerson().getResidentialAddr().setAddressID(AddressService.saveNewAddress(collAddr.getPerson().getResidentialAddr()));
                collAddr.getPerson().getCorAddr().setAddressID(AddressService.saveNewAddress(collAddr.getPerson().getCorAddr()));
                collAddr.getAddr().setAddressID(AddressService.saveNewAddress(collAddr.getAddr()));

                CollectAddressService.saveNewCollectAddress(collAddr);
            } else {
                AddressService.updateAddress(collAddr.getAddr());
                AddressService.updateAddress(collAddr.getPerson().getResidentialAddr());
                AddressService.updateAddress(collAddr.getPerson().getCorAddr());
                CollectAddressService.updateCollectAddress(collAddr);
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
        residentialAddr.setLocationName(this.txtResidentialAddrLocationName.getText());
        residentialAddr.setAddress(this.txtResidentialAddrAddress.getText());
        residentialAddr.setCity(this.txtResidentialAddrCity.getText());
        residentialAddr.setPostalCode(this.txtResidentialAddrPostalCode.getText());
        residentialAddr.setState(this.cmbResidentialAddrState.getText());
        residentialAddr.setCountry(this.cmbResidentialAddrCountry.getText());
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

        customer.setAvatarImg(this.imgAvatarImg.getImage() == null ? "" : ImageUtils.byteToEncodedStr(ImageUtils.imgToByte(this.imgAvatarImg.getImage())));
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
        customer.setBankAccNo(this.txtBankAccNo.getText());
        customer.setCustType(this.cmbCustType.getText());
        customer.setStatus(this.cmbStatus.getText());

        return customer;
    }

}
