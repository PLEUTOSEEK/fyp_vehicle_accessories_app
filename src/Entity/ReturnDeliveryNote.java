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
    private Person itemPassedBackBy;
    private Staff itemReceivedBy;

    public ReturnDeliveryNote() {
        this(null, null, "", null, null, "", null, null, null, null, "", null, null, null, null, null, null);
    }

    public ReturnDeliveryNote(Timestamp createdDateTime, Timestamp modifiedDateTime, String code, Timestamp actualCreatedDateTime, String signedDocPic, String status, SalesOrder SO, Place collBackTo, CollectAddress collBckFr, Date collectDate, String inspectorMsg, List<Item> items, Staff issuedBy, Staff inspectedBy, Staff collectBackBy, Person itemPassedBackBy, Staff itemReceivedBy) {
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

}
