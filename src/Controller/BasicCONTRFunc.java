/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Controller;

import PassObjs.BasicObjs;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author Tee Zhuo Xuan
 */
public interface BasicCONTRFunc {

    public void inputValidation();

    public boolean clearAllFieldsValue();

    public ButtonType alertDialog(Alert.AlertType alertType, String title, String headerTxt, String contentTxt);

    public void switchScene(String fxmlPath, BasicObjs passObj, String direction);

    public BasicObjs sendData(BasicObjs passObj, String direction);

    public void receiveData();
}
