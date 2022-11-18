/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.util.List;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import net.synedra.validatorfx.Check;
import net.synedra.validatorfx.Validator;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class Validation<T> {

    public Check validatorCheckCreator(Validator validator, List<T> control, String uuid) {

        Check validatorCheck = (new Validator()).createCheck();

        //Step 1 - check the class of the pass in generic type UI control
        if (control.get(0) instanceof MFXTextField) {
            validatorCheck
                    .dependsOn(uuid, ((MFXTextField) control.get(0)).textProperty())
                    .decorates((MFXTextField) control.get(0));
        } else if (control.get(0) instanceof MFXComboBox) {
            validatorCheck
                    .dependsOn(uuid, ((MFXComboBox) control.get(0)).textProperty())
                    .decorates((MFXComboBox) control.get(0));
        } else if (control.get(0) instanceof TextArea) {
            validatorCheck
                    .dependsOn(uuid, ((TextArea) control.get(0)).textProperty())
                    .decorates((TextArea) control.get(0));
        } else if (control.get(0) instanceof MFXDatePicker) {
            validatorCheck
                    .dependsOn(uuid, ((MFXDatePicker) control.get(0)).textProperty())
                    .decorates((MFXDatePicker) control.get(0));
        } else if (control.get(0) instanceof TextField) {
            validatorCheck
                    .dependsOn(uuid, ((TextField) control.get(0)).textProperty())
                    .decorates((TextField) control.get(0));
        }

        return validatorCheck;
    }
}
