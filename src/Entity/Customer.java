/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

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
