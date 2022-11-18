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
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
import net.synedra.validatorfx.Check;
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
    @FXML
    private MFXTableView<?> tblVw;
    @FXML
    private MFXTextField txtBankAccOwnerName;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="util declarations">
    private BasicObjs passObj;
    private Validator validator = new Validator();
    private List<CollectAddress> newCollectAddresses = new ArrayList<>(); // use to know which address been update, and perform update action for those modified address
    private List<CollectAddress> tempCollectAddresses = new ArrayList<>(); // use to know which address been update, and perform update action for those modified address
    private List<CollectAddress> collectAddresses = new ArrayList<>(); // use to know which address been update, and perform update action for those modified address
    private Customer custInDraft;
    //</editor-fold>

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

                                    if (catchedCollectAddress == null) { // remove
                                        collectAddresses.remove(collectAddress);
                                    } else if (!collectAddresses.contains(catchedCollectAddress)) {
                                        collectAddresses.remove(collectAddress);
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
        Check validatorCheck = (new Validator()).createCheck();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.US);

        /*
        No need include:
        1. Gender
        2. Marital Status
        3. Residential Address (Postal Code have)
        4. Corresponding Address (Postal Code have) 
         */
        validatorCheck
                .dependsOn("Honorifics", this.cmbHonorifics.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Honorifics");

                    /*
                    1. Cannot be null
                     */
                    if (textVal.isEmpty()) {
                        c.error("Honorifics - Required Field");
                        return;
                    }
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
                .dependsOn("Occupation", this.txtOccupation.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Occupation");
                    /*
                     1. Alphabet and spaces allowed ONLY
                     */

                    // allow null
                    if (textVal.isEmpty()) {
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
                .dependsOn("DOB", this.cmbGender.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("DOB");

                    /*
                    1. Cannot be future date
                     */
                    if (textVal.isEmpty()) {
                        return;
                    }

                    LocalDate date = LocalDate.parse(textVal, formatter);

                    if (date.isAfter(LocalDate.now())) {

                        c.error("Date of Birth - Cannot be future date");
                        return;
                    }
                })
                .decorates(this.cmbGender);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("IC", this.txtIC.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("IC");
                    /*
                     1. must follow regex
                     */
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
                    /*
                     1. alphabet and spaces allowed ONLY
                     */
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
                    /*
                     1. alphabet and spaces allowed ONLY
                     */
                    if (textVal.isEmpty()) {
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
                    /*
                     1. alphabet and spaces allowed ONLY
                     */

                    if (textVal.isEmpty()) {
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
                    /*
                     1. must follow regex
                     */
                    if (textVal.isEmpty()) {
                        return;
                    }

                    if (!textVal.matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")) {
                        c.error("Email - Message");
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
                    /*
                     1.
                     */
                    // allow null
                    if (textVal.isEmpty()) {
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
                .dependsOn("Bank_Acc._Provider", this.cmbBankAccProvider.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Bank_Acc._Provider");
                    /*
                     1. alphabet and spaces allowed ONLY
                     */
                    // allow null
                    if (textVal.isEmpty()) {
                        return;
                    }

                    if (!textVal.matches("^[a-zA-Z ]*$")) {
                        c.error("Bank Acc. Provider - ONLY letter and spaces");
                        return;
                    }

                })
                .decorates(this.cmbBankAccProvider);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Bank_Acc._Owner_Name", this.txtBankAccOwnerName.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Bank_Acc._Owner_Name");
                    /*
                     1. alphabet and spaces allowed ONLY
                     */
                    // allow null
                    if (textVal.isEmpty()) {
                        return;
                    }

                    if (!textVal.matches("^[a-zA-Z ]*$")) {
                        c.error("Bank Acc. Owner Name - ONLY letter and spaces");
                        return;
                    }
                })
                .decorates(this.txtBankAccOwnerName);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Bank_Acc._No", this.txtBankAccNo.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Bank_Acc._No");
                    /*
                     1. alphabet and spaces allowed ONLY
                     */
                    // allow null
                    if (textVal.isEmpty()) {
                        return;
                    }

                    if (!textVal.matches("^[a-zA-Z ]*$")) {
                        c.error("Bank Acc. No - ONLY letter and spaces");
                        return;
                    }

                })
                .decorates(this.txtBankAccNo);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Bill_To_Location_Name", this.txtBillToAddrLocationName.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Bill_To_Location_Name");
                    /*
                     1. Cannot be null
                     2. alphabet and spaces allowed ONLY
                     */
                    if (textVal.isEmpty()) {
                        c.error("Bill To Location Name - Required Field");
                        return;
                    }

                    if (!textVal.matches("^[a-zA-Z ]*$")) {
                        c.error("Bill To Location Name - ONLY letter and spaces");
                        return;
                    }
                })
                .decorates(this.txtBillToAddrLocationName);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Bill_To_Address", this.txtBillToAddrAddress.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Bill_To_Address");
                    /*
                     1. Cannot be null
                     2. alphabet and spaces allowed ONLY
                     */
                    if (textVal.isEmpty()) {
                        c.error("Bill To Address - Required Field");
                        return;
                    }

                    if (!textVal.matches("^[a-zA-Z ]*$")) {
                        c.error("Bill To Address - ONLY letter and spaces");
                        return;
                    }
                })
                .decorates(this.txtBillToAddrAddress);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Bill_To_City", this.txtBillToAddrCity.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Bill_To_City");
                    /*
                     1. Cannot be null
                     2. alphabet and spaces allowed ONLY
                     */
                    if (textVal.isEmpty()) {
                        c.error("Bill To City - Required Field");
                        return;
                    }

                    if (!textVal.matches("^[a-zA-Z ]*$")) {
                        c.error("Bill To City - ONLY letter and spaces");
                        return;
                    }
                })
                .decorates(this.txtBillToAddrCity);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Bill_To_Postal_Code", this.txtBillToAddrPostalCode.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Bill_To_Postal_Code");
                    /*
                     1. Cannot be null
                     2. must follow regex
                     */
                    if (textVal.isEmpty()) {
                        c.error("Bill To Postal Code - Required Field");
                        return;
                    }

                    if (!textVal.matches("^\\d{5}$")) {
                        c.error("Bill To Postal Code - Format not matched");
                        return;
                    }
                })
                .decorates(this.txtBillToAddrPostalCode);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Bill_To_State", this.cmbBillToAddrState.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Bill_To_State");
                    /*
                     1. Cannot be null
                     2. alphabet and spaces allowed ONLY
                     */
                    if (textVal.isEmpty()) {
                        c.error("Bill To State - ONLY letter and spaces");
                        return;
                    }

                    if (!textVal.matches("^[a-zA-Z ]*$")) {
                        c.error("Bill To State - Format not matched");
                        return;
                    }
                })
                .decorates(this.cmbBillToAddrState);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Bill_To_Country", this.cmbBillToAddrCountry.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Bill_To_Country");
                    /*
                     1. Cannot be null
                     2. alphabet and spaces allowed ONLY
                     */
                    if (textVal.isEmpty()) {
                        c.error("Bill To Country - ONLY letter and spaces");
                        return;
                    }

                    if (!textVal.matches("^[a-zA-Z ]*$")) {
                        c.error("Bill To Country - Format not matched");
                        return;
                    }
                })
                .decorates(this.cmbBillToAddrCountry);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Residential_Postal_Code", this.txtResidentialAddrPostalCode.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Residential_Postal_Code");
                    /*
                     1. must follow regex
                     */
                    if (textVal.isEmpty()) {
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
                .dependsOn("Corresponding_Postal_Code", this.txtCorAddrPostalCode.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Corresponding_Postal_Code");
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
            this.txtBankAccOwnerName.setText(customer.getBankAccOwnerName());
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
        this.txtBankAccOwnerName.setDisable(disable);
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

            if (!validator.validate()) {
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
        // this.txtCustID.clear();
        this.cmbGender.clear();
        this.cmbMaritalStatus.clear();
        this.cmbHonorifics.clear();
        this.txtIC.clear();
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
        this.txtBankAccOwnerName.clear();
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
