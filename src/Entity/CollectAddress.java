/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class CollectAddress extends Entity {

    private String collectAddrID;
    private Person person;
    private Address addr;

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

}
