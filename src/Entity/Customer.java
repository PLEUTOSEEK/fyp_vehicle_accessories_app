/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class Customer extends Person {

    private String custID;
    private String bankAccProvider;
    private String bankAccNo;
    private Address billToAddr;
    private List<CollectAddress> dlvrAddrs;
    private String custType; // organization or personal

    public Customer(Timestamp createdDateTime, Timestamp modifiedDateTime, String avatarImg, String name, String gender, Date DOB, String IC, String maritalStatus, String nationality, String honorifics, Address residentialAddr, Address corAddr, Contact contact, String occupation, String race, String religion, String status, String custID, String bankAccProvider, String bankAccNo, Address billToAddr, List<CollectAddress> dlvrAddrs, String custType) {
        super(createdDateTime, modifiedDateTime, avatarImg, name, gender, DOB, IC, maritalStatus, nationality, honorifics, residentialAddr, corAddr, contact, occupation, race, religion, status);
        this.custID = custID;
        this.bankAccProvider = bankAccProvider;
        this.bankAccNo = bankAccNo;
        this.billToAddr = billToAddr;
        this.dlvrAddrs = dlvrAddrs;
        this.custType = custType;
    }

    public Customer() {
        this(null, null, null, "", "", null, "", "", "", "", null, null, null, "", "", "", "", "", "", "", null, null, "");
    }

    public String getCustID() {
        return custID;
    }

    public void setCustID(String custID) {
        this.custID = custID;
    }

    public String getBankAccProvider() {
        return bankAccProvider;
    }

    public void setBankAccProvider(String bankAccProvider) {
        this.bankAccProvider = bankAccProvider;
    }

    public String getBankAccNo() {
        return bankAccNo;
    }

    public void setBankAccNo(String bankAccNo) {
        this.bankAccNo = bankAccNo;
    }

    public Address getBillToAddr() {
        return billToAddr;
    }

    public void setBillToAddr(Address billToAddr) {
        this.billToAddr = billToAddr;
    }

    public List<CollectAddress> getDlvrAddrs() {
        return dlvrAddrs;
    }

    public void setDlvrAddrs(List<CollectAddress> dlvrAddrs) {
        this.dlvrAddrs = dlvrAddrs;
    }

    public String getCustType() {
        return custType;
    }

    public void setCustType(String custType) {
        this.custType = custType;
    }

}
