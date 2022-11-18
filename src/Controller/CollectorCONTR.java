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
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCircleToggleNode;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import net.synedra.validatorfx.Check;
import net.synedra.validatorfx.Validator;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class CollectorCONTR implements Initializable, BasicCONTRFunc {

    //<editor-fold defaultstate="collapsed" desc="data fields">
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
    //</editor-fold>
    @FXML
    private MFXButton btnRemove;

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
                    try {
                        fieldFillIn();
                    } catch (IOException ex) {
                        Logger.getLogger(CollectorCONTR.class.getName()).log(Level.SEVERE, null, ex);
                    }
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

    private void fieldFillIn() throws IOException {
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

            this.imgAvatarImg.setImage(ImageUtils.byteToImg(
                    ImageUtils.encodedStrToByte(
                            collAddr.getPerson().getAvatarImg().isEmpty() == true
                            ? null : collAddr.getPerson().getAvatarImg()
                    )));

        }
    }
    //<editor-fold defaultstate="collapsed" desc="comment">

    @Override
    public void inputValidation() {
        Check validatorCheck = (new Validator()).createCheck();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.US);

        /*
        No need include:
        1. Honorifics
        2. Gender
         */
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
                .dependsOn("DOB", this.dtDOB.textProperty())
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
                .decorates(this.dtDOB);

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

                    if (textVal.isEmpty()) {
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
                .dependsOn("Collect_Location_Name", this.txtCollectAddrLocationName.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Collect_Location_Name");
                    /*
                     1. Cannot be null
                     */
                    if (textVal.isEmpty()) {
                        c.error("Collect Location Name - Required Field");
                        return;
                    }
                })
                .decorates(this.txtCollectAddrLocationName);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Collect_Address", this.txtCollectAddrAddress.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Collect_Address");
                    /*
                     1. Cannot be null
                     */
                    if (textVal.isEmpty()) {
                        c.error("Collect Address - Required Field");
                        return;
                    }
                })
                .decorates(this.txtCollectAddrAddress);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Collect_City", this.txtCollectAddrCity.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Collect_City");
                    /*
                     1. Cannot be null
                     2. alphabet and spaces allowed ONLY
                     */
                    if (textVal.isEmpty()) {
                        c.error("Collect City - Required Field");
                        return;
                    }

                    if (!textVal.matches("^[a-zA-Z ]*$")) {
                        c.error("Collect City - ONLY letter and spaces");
                        return;
                    }
                })
                .decorates(this.txtCollectAddrCity);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Collect_Postal_Code", this.txtCollectAddrPostalCode.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Collect_Postal_Code");
                    /*
                     1. Cannot be null
                     2. must follow regex
                     */
                    if (textVal.isEmpty()) {
                        c.error("Collect Postal Code - Required Field");
                        return;
                    }

                    if (!textVal.matches("^\\d{5}$")) {
                        c.error("Collect Postal Code - Format not matched");
                        return;
                    }
                })
                .decorates(this.txtCollectAddrPostalCode);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Collect_State", this.cmbCollectAddrState.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Collect_State");
                    /*
                     1. Cannot be null
                     2. alphabet and spaces allowed ONLY
                     */
                    if (textVal.isEmpty()) {
                        c.error("Collect State - ONLY letter and spaces");
                        return;
                    }

                    if (!textVal.matches("^[a-zA-Z ]*$")) {
                        c.error("Collect State - Format not matched");
                        return;
                    }
                })
                .decorates(this.cmbCollectAddrState);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Collect_Country", this.cmbCollectAddrCountry.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Collect_Country");
                    /*
                     1. Cannot be null
                     2. alphabet and spaces allowed ONLY
                     */
                    if (textVal.isEmpty()) {
                        c.error("Collect Country - ONLY letter and spaces");
                        return;
                    }

                    if (!textVal.matches("^[a-zA-Z ]*$")) {
                        c.error("Collect Country - Format not matched");
                        return;
                    }
                })
                .decorates(this.cmbCollectAddrCountry);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Residential_City", this.txtResidentialAddrCity.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Residential_City");
                    /*
                     1. alphabet and spaces allowed ONLY
                     */
                    if (textVal.isEmpty()) {
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
                .dependsOn("Residential_State", this.cmbResidentialAddrState.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Residential_State");
                    /*
                     1. alphabet and spaces allowed ONLY
                     */
                    if (textVal.isEmpty()) {
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
                    /*
                     1. alphabet and spaces allowed ONLY
                     */
                    if (textVal.isEmpty()) {
                        return;
                    }

                    if (!textVal.matches("^[a-zA-Z ]*$")) {
                        c.error("Residential Country - Format not matched");
                        return;
                    }
                })
                .decorates(this.cmbResidentialAddrCountry);

        validator.add(validatorCheck);

        //=====================================
        //=====================================
        validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("Corresponding_City", this.txtCorAddrCity.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("Corresponding_City");
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
    }
//</editor-fold>

    @Override
    public boolean clearAllFieldsValue() {
        this.cmbHonorifics.clear();
        this.txtName.clear();
        this.cmbGender.clear();
        this.txtOccupation.clear();
        this.dtDOB.clear();
        this.txtIC.clear();
        this.cmbNationality.clear();
        this.cmbRace.clear();
        this.cmbReligion.clear();
        this.cmbMaritalStatus.clear();
        this.txtMobileNo.clear();
        this.txtEmail.clear();
        this.txtOffPhNo.clear();
        this.txtHomePhNo.clear();
        this.txtExt.clear();
        this.txtCollectAddrLocationName.clear();
        this.txtCollectAddrAddress.clear();
        this.txtCollectAddrCity.clear();
        this.txtCollectAddrPostalCode.clear();
        this.cmbCollectAddrState.clear();
        this.cmbCollectAddrCountry.clear();
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
        // Unused
        return;
    }

    @Override
    public BasicObjs sendData(BasicObjs passObj, String direction) {
        // Unused
        return new BasicObjs();
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

        collAddr.getPerson().setAvatarImg(this.imgAvatarImg.getImage() == null ? "" : ImageUtils.byteToEncodedStr(ImageUtils.imgToByte(this.imgAvatarImg.getImage())));
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

    @FXML
    private void removeCurrentCollector(MouseEvent event) {
        Stage stage = (Stage) btnDiscard.getScene().getWindow();
        BasicObjs passObj = new BasicObjs();
        passObj.setObj(null);
        stage.setUserData(passObj);
        stage.close();
    }

}
