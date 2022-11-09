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
public class Invoice extends Document {

    private SalesOrder SO;
    private String referenceType; // understand the refernce is belong to what type of source document
    private String reference;
    private List<Item> items;
    private BigDecimal gross;
    private BigDecimal discount;
    private BigDecimal subTotal;
    private BigDecimal ttlPayable;
    private Staff issuedBy;
    private Staff releasedAVerifiedBy;
    private CollectAddress customerSignature;

    public Invoice() {
        this(null, null, "", null, null, "", null, "", "", null, new BigDecimal("0.00"), new BigDecimal("0.00"), new BigDecimal("0.00"), new BigDecimal("0.00"), null, null, null);
    }

    public Invoice(Timestamp createdDateTime, Timestamp modifiedDateTime, String code, Timestamp actualCreatedDateTime, String signedDocPic, String status, SalesOrder SO, String referenceType, String reference, List<Item> items, BigDecimal gross, BigDecimal discount, BigDecimal subTotal, BigDecimal ttlPayable, Staff issuedBy, Staff releasedAVerifiedBy, CollectAddress customerSignature) {
        super(createdDateTime, modifiedDateTime, code, actualCreatedDateTime, signedDocPic, status);
        this.SO = SO;
        this.referenceType = referenceType;
        this.reference = reference;
        this.items = items;
        this.gross = gross;
        this.discount = discount;
        this.subTotal = subTotal;
        this.ttlPayable = ttlPayable;
        this.issuedBy = issuedBy;
        this.releasedAVerifiedBy = releasedAVerifiedBy;
        this.customerSignature = customerSignature;
    }

    public SalesOrder getSO() {
        return SO;
    }

    public void setSO(SalesOrder SO) {
        this.SO = SO;
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

    public BigDecimal getGross() {
        return gross;
    }

    public void setGross(BigDecimal gross) {
        this.gross = gross;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public BigDecimal getTtlPayable() {
        return ttlPayable;
    }

    public void setTtlPayable(BigDecimal ttlPayable) {
        this.ttlPayable = ttlPayable;
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

    public CollectAddress getCustomerSignature() {
        return customerSignature;
    }

    public void setCustomerSignature(CollectAddress customerSignature) {
        this.customerSignature = customerSignature;
    }

}
