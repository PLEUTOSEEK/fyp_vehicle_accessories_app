/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExperimentArea;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import net.synedra.validatorfx.Check;
import net.synedra.validatorfx.Validator;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private TextField txttTestTextField;
    @FXML
    private Button button;
    Validator validator = new Validator();
    @FXML
    private MFXDatePicker dtDatePicker;
    @FXML
    private MFXComboBox<?> cmbComboBox;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");

        if (!validator.validate()) {
            System.out.println("we are bad");
        } else {
            System.out.println("we are good");
        }
    }

    private void inputValidation() {
        //        validator.createCheck()
        //                .dependsOn("testTxtField", txttTestTextField.textProperty())
        //                .withMethod(c -> {
        //                    String textVal = c.get("testTxtField");
        //                    if (textVal.isBlank()) {
        //                        c.error("Required Field");
        //                    }
        //                })
        //                .decorates(txttTestTextField);
        Check validatorCheck = (new Validator()).createCheck();

        validatorCheck
                .dependsOn("test TxtField", txttTestTextField.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("test TxtField");
                    if (!textVal.matches("^\\d{5}$")) {
                        c.error("Bill To Postal Code - Format not zzz");
                        return;
                    }
                })
                .decorates(txttTestTextField);

        validator.add(validatorCheck);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.US);

        validatorCheck = (new Validator()).createCheck();

        validatorCheck.dependsOn(
                "dtPicker", this.dtDatePicker.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("dtPicker");

                    if (textVal.isEmpty()) {
                        c.error("Required Field");
                        return;
                    }
                    System.out.println(textVal);
                    LocalDate date = LocalDate.parse(textVal, formatter);

                    System.out.println(date.getMonth());
                })
                .decorates(dtDatePicker);

        validator.add(validatorCheck);

        validatorCheck = (new Validator()).createCheck();
        validatorCheck.dependsOn(
                "cmbComboBox", this.cmbComboBox.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("cmbComboBox");

                    if (textVal.isEmpty()) {
                        c.error("Required Field");
                        return;
                    }
                })
                .decorates(cmbComboBox);

        validator.add(validatorCheck);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                inputValidation();
            }
        });
    }

}
