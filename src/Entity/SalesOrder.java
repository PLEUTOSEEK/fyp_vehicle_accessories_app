/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class SalesOrder extends Document {

    private Customer billToCust;
    private CollectAddress deliverToCust;
    private String custPOReference;
    private Quotation quotRef;
    private String referenceType; // understand the refernce is belong to what type of source document
    private String reference; // quotation source document
    private Staff salesPerson;
    private String currencyCode;
    private Date requiredDeliveryDate;
    private String pymtTerm;
    private String shipmentTerm;
    private List<Item> items;
    private BigDecimal gross;
    private BigDecimal discount;
    private BigDecimal subTotal;
    private BigDecimal nett;
    private Staff issuedBy;
    private Staff releasedAVerifiedBy;
    private Customer customerSignature;

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

    public String getCustPOReference() {
        return custPOReference;
    }

    public void setCustPOReference(String custPOReference) {
        this.custPOReference = custPOReference;
    }

    public Quotation getQuotRef() {
        return quotRef;
    }

    public void setQuotRef(Quotation quotRef) {
        this.quotRef = quotRef;
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

    public Staff getSalesPerson() {
        return salesPerson;
    }

    public void setSalesPerson(Staff salesPerson) {
        this.salesPerson = salesPerson;
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

    public String getPymtTerm() {
        return pymtTerm;
    }

    public void setPymtTerm(String pymtTerm) {
        this.pymtTerm = pymtTerm;
    }

    public String getShipmentTerm() {
        return shipmentTerm;
    }

    public void setShipmentTerm(String shipmentTerm) {
        this.shipmentTerm = shipmentTerm;
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

    public Staff getReleasedAVerifiedBy() {
        return releasedAVerifiedBy;
    }

    public void setReleasedAVerifiedBy(Staff releasedAVerifiedBy) {
        this.releasedAVerifiedBy = releasedAVerifiedBy;
    }

    public Customer getCustomerSignature() {
        return customerSignature;
    }

    public void setCustomerSignature(Customer customerSignature) {
        this.customerSignature = customerSignature;
    }

}
