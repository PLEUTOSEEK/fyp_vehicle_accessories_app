/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Utils.ValidationUtils;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import net.synedra.validatorfx.Validator;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class FXMLCONTR implements Initializable {

    @FXML
    private MFXTextField txtNumericFields;
    private Validator validator = new Validator();

    public static void numericOnly(final MFXTextField field) {
        field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                System.out.println("I am here");
                if (!newValue.matches("\\d*")) {
                    field.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                List<MFXTextField> listOfControls = new ArrayList<MFXTextField>();
                ValidationUtils<MFXTextField> validationUtils = new ValidationUtils<>();
                int characterLimit = 0;
                //================================
                listOfControls.add(txtNumericFields);
                characterLimit = 21;
                validationUtils.validationCreator(validator, listOfControls, characterLimit, true, "Sales Person - Number Require", ValidationUtils.isCurrency);
                listOfControls.clear();
            }
        }
        );
    }
}
