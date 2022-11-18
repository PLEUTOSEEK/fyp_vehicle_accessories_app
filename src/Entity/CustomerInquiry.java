/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class CustomerInquiry extends Document {

    private String referenceType;
    private String reference;
    private Customer billToCust;
    private CollectAddress deliverToCust;
    private String currencyCode;
    private Date requiredDeliveryDate;
    private PaymentTerm pymtTerm;
    private ShipmentTerm shipmentTerm;
    private Staff salesPerson;
    private List<Item> items;
    private BigDecimal gross;
    private BigDecimal discount;
    private BigDecimal subTotal;
    private BigDecimal nett;
    private Staff issuedBy;

    public CustomerInquiry() {
        this(null, null, "", null, null, "", "", "", null, null, "", null, null, null, null, null, null, null, null, null, null);
    }

    public CustomerInquiry(Timestamp createdDateTime, Timestamp modifiedDateTime, String code, Timestamp actualCreatedDateTime, String signedDocPic, String status, String referenceType, String reference, Customer billToCust, CollectAddress deliverToCust, String currencyCode, Date requiredDeliveryDate, PaymentTerm pymtTerm, ShipmentTerm shipmentTerm, Staff salesPerson, List<Item> items, BigDecimal gross, BigDecimal discount, BigDecimal subTotal, BigDecimal nett, Staff issuedBy) {
        super(createdDateTime, modifiedDateTime, code, actualCreatedDateTime, signedDocPic, status);
        this.referenceType = referenceType;
        this.reference = reference;
        this.billToCust = billToCust;
        this.deliverToCust = deliverToCust;
        this.currencyCode = currencyCode;
        this.requiredDeliveryDate = requiredDeliveryDate;
        this.pymtTerm = pymtTerm;
        this.shipmentTerm = shipmentTerm;
        this.salesPerson = salesPerson;
        this.items = items;
        this.gross = gross;
        this.discount = discount;
        this.subTotal = subTotal;
        this.nett = nett;
        this.issuedBy = issuedBy;
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

    public Customer getBillToCust() {
        return billToCust;
    }

    public void setBillToCust(Customer billToCust) {
        this.billToCust = billToCust;
    }

    public CollectAddress getDeliverToCust() {
        return deliverToCust;
    }

    public void setDeliverToCust(CollectAddress deliverToCust) {
        this.deliverToCust = deliverToCust;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Date getRequiredDeliveryDate() {
        return requiredDeliveryDate;
    }

    public void setRequiredDeliveryDate(Date requiredDeliveryDate) {
        this.requiredDeliveryDate = requiredDeliveryDate;
    }

    public PaymentTerm getPymtTerm() {
        return pymtTerm;
    }

    public void setPymtTerm(PaymentTerm pymtTerm) {
        this.pymtTerm = pymtTerm;
    }

    public ShipmentTerm getShipmentTerm() {
        return shipmentTerm;
    }

    public void setShipmentTerm(ShipmentTerm shipmentTerm) {
        this.shipmentTerm = shipmentTerm;
    }

    public Staff getSalesPerson() {
        return salesPerson;
    }

    public void setSalesPerson(Staff salesPerson) {
        this.salesPerson = salesPerson;
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

    public BigDecimal getNett() {
        return nett;
    }

    public void setNett(BigDecimal nett) {
        this.nett = nett;
    }

    public Staff getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(Staff issuedBy) {
        this.issuedBy = issuedBy;
    }

}
