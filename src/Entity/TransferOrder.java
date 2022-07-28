/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author Tee Zhuo Xuan
 */
//multi purpose document
public class TransferOrder<T> extends Document {

    private Staff PIC;
    private Place destination;
    private String reqType;// understand the reference is belong to what type of source document
    private T reqTypeRef;// SO/ PO
    private List<Item> items;
    private Staff issuedBy;
    private Staff transferBy;
    private Customer itemReceivedBy;

    public TransferOrder() {
        this(null, null, "", null, null, "", null, null, "", null, null, null, null, null);
    }

    public TransferOrder(Timestamp createdDateTime, Timestamp modifiedDateTime, String code, Timestamp actualCreatedDateTime, byte[] signedDocPic, String status, Staff PIC, Place destination, String reqType, T reqTypeRef, List<Item> items, Staff issuedBy, Staff transferBy, Customer itemReceivedBy) {
        super(createdDateTime, modifiedDateTime, code, actualCreatedDateTime, signedDocPic, status);
        this.PIC = PIC;
        this.destination = destination;
        this.reqType = reqType;
        this.reqTypeRef = reqTypeRef;
        this.items = items;
        this.issuedBy = issuedBy;
        this.transferBy = transferBy;
        this.itemReceivedBy = itemReceivedBy;
    }

    public Staff getPIC() {
        return PIC;
    }

    public void setPIC(Staff PIC) {
        this.PIC = PIC;
    }

    public Place getDestination() {
        return destination;
    }

    public void setDestination(Place destination) {
        this.destination = destination;
    }

    public String getReqType() {
        return reqType;
    }

    public void setReqType(String reqType) {
        this.reqType = reqType;
    }

    public T getReqTypeRef() {
        return reqTypeRef;
    }

    public void setReqTypeRef(T reqTypeRef) {
        this.reqTypeRef = reqTypeRef;
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

    public Staff getTransferBy() {
        return transferBy;
    }

    public void setTransferBy(Staff transferBy) {
        this.transferBy = transferBy;
    }

    public Customer getItemReceivedBy() {
        return itemReceivedBy;
    }

    public void setItemReceivedBy(Customer itemReceivedBy) {
        this.itemReceivedBy = itemReceivedBy;
    }

}
