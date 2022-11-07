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
public class ReturnDeliveryNote extends Document {

    private SalesOrder SO;
    private Place collBackTo; // company = Thir Shen
    private CollectAddress collBckFr;
    private Date collectDate;
    private String inspectorMsg;
    private List<Item> items;
    private Staff issuedBy;
    private Staff inspectedBy;
    private Staff collectBackBy;
    private CollectAddress itemPassedBackBy;
    private Staff itemReceivedBy;

    public ReturnDeliveryNote() {
        this(null, null, "", null, null, "", null, null, null, null, "", null, null, null, null, null, null);
    }

    public ReturnDeliveryNote(Timestamp createdDateTime, Timestamp modifiedDateTime, String code, Timestamp actualCreatedDateTime, String signedDocPic, String status, SalesOrder SO, Place collBackTo, CollectAddress collBckFr, Date collectDate, String inspectorMsg, List<Item> items, Staff issuedBy, Staff inspectedBy, Staff collectBackBy, CollectAddress itemPassedBackBy, Staff itemReceivedBy) {
        super(createdDateTime, modifiedDateTime, code, actualCreatedDateTime, signedDocPic, status);
        this.SO = SO;
        this.collBackTo = collBackTo;
        this.collBckFr = collBckFr;
        this.collectDate = collectDate;
        this.inspectorMsg = inspectorMsg;
        this.items = items;
        this.issuedBy = issuedBy;
        this.inspectedBy = inspectedBy;
        this.collectBackBy = collectBackBy;
        this.itemPassedBackBy = itemPassedBackBy;
        this.itemReceivedBy = itemReceivedBy;
    }

    public SalesOrder getSO() {
        return SO;
    }

    public void setSO(SalesOrder SO) {
        this.SO = SO;
    }

    public Place getCollBackTo() {
        return collBackTo;
    }

    public void setCollBackTo(Place collBackTo) {
        this.collBackTo = collBackTo;
    }

    public CollectAddress getCollBckFr() {
        return collBckFr;
    }

    public void setCollBckFr(CollectAddress collBckFr) {
        this.collBckFr = collBckFr;
    }

    public Date getCollectDate() {
        return collectDate;
    }

    public void setCollectDate(Date collectDate) {
        this.collectDate = collectDate;
    }

    public String getInspectorMsg() {
        return inspectorMsg;
    }

    public void setInspectorMsg(String inspectorMsg) {
        this.inspectorMsg = inspectorMsg;
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

    public Staff getInspectedBy() {
        return inspectedBy;
    }

    public void setInspectedBy(Staff inspectedBy) {
        this.inspectedBy = inspectedBy;
    }

    public Staff getCollectBackBy() {
        return collectBackBy;
    }

    public void setCollectBackBy(Staff collectBackBy) {
        this.collectBackBy = collectBackBy;
    }

    public CollectAddress getItemPassedBackBy() {
        return itemPassedBackBy;
    }

    public void setItemPassedBackBy(CollectAddress itemPassedBackBy) {
        this.itemPassedBackBy = itemPassedBackBy;
    }

    public Staff getItemReceivedBy() {
        return itemReceivedBy;
    }

    public void setItemReceivedBy(Staff itemReceivedBy) {
        this.itemReceivedBy = itemReceivedBy;
    }

}
