/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PassObjs;

import Entity.Staff;
import adt.DoublyLinkedList;

/**
 *
 * @author Tee Zhuo Xuan
 */
//Usually use to pass object between home page and entity view UI
public class BasicObjs {

    public static final String forward = "F";
    public static final String back = "B";

    private Staff loginStaff;
    private Staff staff;
    private Object obj;
    private DoublyLinkedList<String> fxmlPaths = new DoublyLinkedList<>();
    private String passDirection;

    public BasicObjs() {
    }

    public BasicObjs(Object obj) {
        //<editor-fold defaultstate="collapsed" desc="comment">
        /*
        1. HomePage_UI.fxml
         */
        //</editor-fold>
        this.staff = staff;
        this.obj = obj;
    }

    public Staff getLoginStaff() {
        return loginStaff;
    }

    public void setLoginStaff(Staff loginStaff) {
        this.loginStaff = loginStaff;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public DoublyLinkedList<String> getFxmlPaths() {
        return fxmlPaths;
    }

    public void setFxmlPaths(DoublyLinkedList<String> fxmlPath) {
        this.fxmlPaths = fxmlPath;
    }

    public String getPassDirection() {
        return passDirection;
    }

    public void setPassDirection(String passDirection) {
        this.passDirection = passDirection;
    }

}
