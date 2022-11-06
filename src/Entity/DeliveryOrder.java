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
// multi purpose document
public class DeliveryOrder<T> extends Document {

    private Place deliverFr; // company = Thir Shen
    private Date deliveryDate;
    private String referenceType; // understand the refernce is belong to what type of source document
    private T reference; // RDN / TO
    private List<T> items;
    private Staff issuedBy;
    private Staff releasedAVerifiedBy;
    private Staff deliveryBy;
    private CollectAddress itemReceivedBy;

    public DeliveryOrder() {
        this(null, null, "", null, null, "", null, null, "", null, null, null, null, null);
    }

    public DeliveryOrder(Timestamp createdDateTime, Timestamp modifiedDateTime, String code, Timestamp actualCreatedDateTime, String signedDocPic, String status, Place deliverFr, Date deliveryDate, String referenceType, T reference, Staff issuedBy, Staff releasedAVerifiedBy, Staff deliveryBy, CollectAddress itemReceivedBy) {
        super(createdDateTime, modifiedDateTime, code, actualCreatedDateTime, signedDocPic, status);
        this.deliverFr = deliverFr;
        this.deliveryDate = deliveryDate;
        this.referenceType = referenceType;
        this.reference = reference;
        this.issuedBy = issuedBy;
        this.releasedAVerifiedBy = releasedAVerifiedBy;
        this.deliveryBy = deliveryBy;
        this.itemReceivedBy = itemReceivedBy;
    }

    public Place getDeliverFr() {
        return deliverFr;
    }

    public void setDeliverFr(Place deliverFr) {
        this.deliverFr = deliverFr;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    public T getReference() {
        return reference;
    }

    public void setReference(T reference) {
        this.reference = reference;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
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

    public CollectAddress getItemReceivedBy() {
        return itemReceivedBy;
    }

    public void setItemReceivedBy(CollectAddress itemReceivedBy) {
        this.itemReceivedBy = itemReceivedBy;
    }

}
