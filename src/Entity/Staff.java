/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.sql.Date;

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
    private String accountStatus; // haven't put in class diagram

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
}
