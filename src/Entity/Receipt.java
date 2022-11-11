/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class Receipt extends Document {

    private Invoice INV;
    private String referenceType; // understand the refernce is belong to what type of source document
    private String reference;
    private List<Item> items;
    private BigDecimal ttlPayable;
    private BigDecimal paidAmt;
    private BigDecimal paidAmtPrev;
    private BigDecimal balUnpaid;

    public Receipt() {
        this(null, null, "", null, null, "", null, "", "", null, new BigDecimal("0.00"), new BigDecimal("0.00"), new BigDecimal("0.00"), new BigDecimal("0.00"));
    }

    public Receipt(Timestamp createdDateTime, Timestamp modifiedDateTime, String code, Timestamp actualCreatedDateTime, String signedDocPic, String status, Invoice INV, String referenceType, String reference, List<Item> items, BigDecimal ttlPayable, BigDecimal paidAmt, BigDecimal paidAmtPrev, BigDecimal balUnpaid) {
        super(createdDateTime, modifiedDateTime, code, actualCreatedDateTime, signedDocPic, status);
        this.INV = INV;
        this.referenceType = referenceType;
        this.reference = reference;
        this.items = items;
        this.ttlPayable = ttlPayable;
        this.paidAmt = paidAmt;
        this.paidAmtPrev = paidAmtPrev;
        this.balUnpaid = balUnpaid;
    }

    public Invoice getINV() {
        return INV;
    }

    public void setINV(Invoice INV) {
        this.INV = INV;
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

    public BigDecimal getTtlPayable() {
        return ttlPayable;
    }

    public void setTtlPayable(BigDecimal ttlPayable) {
        this.ttlPayable = ttlPayable;
    }

    public BigDecimal getPaidAmt() {
        return paidAmt;
    }

    public void setPaidAmt(BigDecimal paidAmt) {
        this.paidAmt = paidAmt;
    }

    public BigDecimal getPaidAmtPrev() {
        return paidAmtPrev;
    }

    public void setPaidAmtPrev(BigDecimal paidAmtPrev) {
        this.paidAmtPrev = paidAmtPrev;
    }

    public BigDecimal getBalUnpaid() {
        return balUnpaid;
    }

    public void setBalUnpaid(BigDecimal balUnpaid) {
        this.balUnpaid = balUnpaid;
    }

}
