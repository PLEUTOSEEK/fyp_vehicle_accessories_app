/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.sql.Timestamp;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class CollectAddress extends Entity {

    private String collectAddrID;
    private Customer customer;
    private Person person;
    private Address addr;

    public CollectAddress(Timestamp createdDateTime, Timestamp modifiedDateTime, String collectAddrID, Customer customerID, Person person, Address addr) {
        super(createdDateTime, modifiedDateTime);
        this.collectAddrID = collectAddrID;
        this.customer = customerID;
        this.person = person;
        this.addr = addr;
    }

    public CollectAddress() {
        this(null, null, "", null, null, null);
    }

    public String getCollectAddrID() {
        return collectAddrID;
    }

    public void setCollectAddrID(String collectAddrID) {
        this.collectAddrID = collectAddrID;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Address getAddr() {
        return addr;
    }

    public void setAddr(Address addr) {
        this.addr = addr;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CollectAddress) {
            CollectAddress collAddr = ((CollectAddress) obj);
            if (this.getCollectAddrID().equals(collAddr.getCollectAddrID())) {
                return true;
            }
        }
        return false;
    }
}
