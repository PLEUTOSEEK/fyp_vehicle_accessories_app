/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.sql.Date;
import java.util.List;

/**
 *
 * @author Tee Zhuo Xuan
 */
// multi purpose document
public class DeliveryOrder extends Document {

    private Place deliverFr; // company = Thir Shen
    private SalesOrder SO;
    private Date deliveryDate;
    private ReturnDeliveryNote RDN;
    private String referenceType; // understand the refernce is belong to what type of source document
    private String reference;
    private List<Item> items;
    private Staff issuedBy;
    private Staff releasedAVerifiedBy;
    private Staff deliveryBy;
    private Customer itemReceivedBy;

    public Place getDeliverFr() {
        return deliverFr;
    }

    public void setDeliverFr(Place deliverFr) {
        this.deliverFr = deliverFr;
    }

    public SalesOrder getSO() {
        return SO;
    }

    public void setSO(SalesOrder SO) {
        this.SO = SO;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public ReturnDeliveryNote getRDN() {
        return RDN;
    }

    public void setRDN(ReturnDeliveryNote RDN) {
        this.RDN = RDN;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Staff getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(Staff issuedBy) {
        this.issuedBy = issuedBy;
    }

    public Staff getReleasedAVerifiedBy() {
        return releasedAVerifiedBy;
    }

    public void setReleasedAVerifiedBy(Staff releasedAVerifiedBy) {
        this.releasedAVerifiedBy = releasedAVerifiedBy;
    }

    public Staff getDeliveryBy() {
        return deliveryBy;
    }

    public void setDeliveryBy(Staff deliveryBy) {
        this.deliveryBy = deliveryBy;
    }

    public Customer getItemReceivedBy() {
        return itemReceivedBy;
    }

    public void setItemReceivedBy(Customer itemReceivedBy) {
        this.itemReceivedBy = itemReceivedBy;
    }

}
