/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.sql.Timestamp;
import java.util.Objects;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class CollectAddress extends Entity implements Cloneable {

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
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.collectAddrID);
        hash = 89 * hash + Objects.hashCode(this.person);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CollectAddress other = (CollectAddress) obj;
        if (!Objects.equals(this.collectAddrID, other.collectAddrID)) {
            return false;
        }
        return Objects.equals(this.person, other.person);
    }

    @Override
    public CollectAddress clone() {
        CollectAddress clonedCollectAddr = null;
        try {
            clonedCollectAddr = (CollectAddress) super.clone();

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return clonedCollectAddr;
    }
}
