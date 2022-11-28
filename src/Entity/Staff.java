/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class Staff extends Person {

    private String staffID;
    private Place workPlace; // or department
    private Date entryDate; // or join date, when he start to work within this company
    private Staff reportTo;
    private String empType; // full/ part time
    private String password;
    private String role;
    private boolean isFrozen;
    private boolean resetPassNextLogin;

    public Staff() {
        this(null, null, "", "", "", null, "", "", "", "", null, null, null, "", "", "", "", null, null, null, null, "", "", "");
    }

    public Staff(Timestamp createdDateTime, Timestamp modifiedDateTime, String avatarImg, String name, String gender, Date DOB, String IC, String maritalStatus, String nationality, String honorifics, Address residentialAddr, Address corAddr, Contact contact, String occupation, String race, String religion, String status, String staffID, Place workPlace, Date entryDate, Staff reportTo, String empType, String password, String role) {
        super(createdDateTime, modifiedDateTime, avatarImg, name, gender, DOB, IC, maritalStatus, nationality, honorifics, residentialAddr, corAddr, contact, occupation, race, religion, status);
        this.staffID = staffID;
        this.workPlace = workPlace;
        this.entryDate = entryDate;
        this.reportTo = reportTo;
        this.empType = empType;
        this.password = password;
        this.role = role;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public Place getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(Place workPlace) {
        this.workPlace = workPlace;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Staff getReportTo() {
        return reportTo;
    }

    public void setReportTo(Staff reportTo) {
        this.reportTo = reportTo;
    }

    public String getEmpType() {
        return empType;
    }

    public void setEmpType(String empType) {
        this.empType = empType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean getIsFrozen() {
        return isFrozen;
    }

    public void setIsFrozen(boolean isFrozen) {
        this.isFrozen = isFrozen;
    }

    public boolean getResetPassNextLogin() {
        return resetPassNextLogin;
    }

    public void setResetPassNextLogin(boolean resetPassNextLogin) {
        this.resetPassNextLogin = resetPassNextLogin;
    }

}
