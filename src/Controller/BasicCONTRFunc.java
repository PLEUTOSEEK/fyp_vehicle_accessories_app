/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Controller;

import PassObjs.BasicObjs;

/**
 *
 * @author Tee Zhuo Xuan
 */
public interface BasicCONTRFunc {

    public void switchScene(String fxmlPath, BasicObjs passObj, String direction);

    public BasicObjs sendData(BasicObjs passObj, String direction);

    public void receiveData();
}
