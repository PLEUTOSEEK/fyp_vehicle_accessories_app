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
public class Place extends Entity {

    private String placeID;
    private String placeName;
    private Address placeAddr;
    private String description;
    private Contact contact;

    public Place(String placeID) {
        this.placeID = placeID;
    }

    public Place() {
        this(null, null, "", "", null, "", null);
    }

    public Place(Timestamp createdDateTime, Timestamp modifiedDateTime, String placeID, String placeName, Address placeAddr, String description, Contact contact) {
        super(createdDateTime, modifiedDateTime);
        this.placeID = placeID;
        this.placeName = placeName;
        this.placeAddr = placeAddr;
        this.description = description;
        this.contact = contact;
    }

    public String getPlaceID() {
        return placeID;
    }

    public void setPlaceID(String placeID) {
        this.placeID = placeID;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public Address getPlaceAddr() {
        return placeAddr;
    }

    public void setPlaceAddr(Address placeAddr) {
        this.placeAddr = placeAddr;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

}
