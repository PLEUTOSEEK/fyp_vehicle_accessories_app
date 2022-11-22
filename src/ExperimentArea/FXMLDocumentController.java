/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExperimentArea;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.cell.MFXDateCell;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Function;
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
        this.dtDatePicker.setCellFactory(new Function<>() {
            @Override
            public MFXDateCell apply(LocalDate t) {
                return new MFXDateCell(dtDatePicker, t) {
                    @Override
                    public void updateItem(LocalDate item) {
                        super.updateItem(item);

                        if (item.isBefore(LocalDate.now())) {
                            setDisable(true);
                        } else {
                            setDisable(false);
                        }
                    }
                };

            }
        });

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

        validatorCheck.dependsOn(
                "test TxtField", txttTestTextField.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("test TxtField");
                    textVal = textVal.trim();
                    /*
                     1.
                     */
                    if (textVal.isEmpty()) {
                        c.error("Unit Price - Required Field");
                        return;
                    }

                    try {

                        Double unitPrice = Double.parseDouble(textVal);

                        if (unitPrice < 0) {
                            c.error("Unit Price - Must be positive");
                            return;
                        }

                    } catch (Exception ex) {
                        c.error("Unit Price - Must be double");
                        return;
                    }
                }
                )
                .decorates(txttTestTextField);

        validator.add(validatorCheck);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.US);

        validatorCheck = (new Validator()).createCheck();

        validatorCheck.dependsOn(
                "dtPicker", this.dtDatePicker.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("dtPicker");
                    textVal = textVal.trim();

                    if (textVal.isEmpty()) {
                        c.error("Required Field");
                        return;
                    }
                    System.out.println(textVal);
                    LocalDate date = LocalDate.parse(textVal, formatter);

                    System.out.println(date.getMonth());
                }
                )
                .decorates(dtDatePicker);

        validator.add(validatorCheck);

        validatorCheck = (new Validator()).createCheck();

        validatorCheck.dependsOn(
                "cmbComboBox", this.cmbComboBox.textProperty())
                .withMethod(c -> {
                    String textVal = c.get("cmbComboBox");
                    textVal = textVal.trim();

                    if (textVal.isEmpty()) {
                        c.error("Required Field");
                        return;
                    }
                }
                )
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
