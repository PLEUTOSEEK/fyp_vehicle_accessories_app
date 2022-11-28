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
    private PaymentTerm pymtTerm;
    private ShipmentTerm shipmentTerm;
    private List<Item> items;
    private BigDecimal gross;
    private BigDecimal discount;
    private BigDecimal subTotal;
    private BigDecimal nett;
    private Staff issuedBy;
    private Staff releasedAVerifiedBy;
    private CollectAddress customerSignature;

    public SalesOrder() {
        this(null, null, "", null, null, "", null, null, "", null, "", "", null, "", null, null, null, null, null, null, null, null, null, null, null);
    }

    public SalesOrder(Timestamp createdDateTime, Timestamp modifiedDateTime, String code, Timestamp actualCreatedDateTime, String signedDocPic, String status, Customer billToCust, CollectAddress deliverToCust, String custPOReference, Quotation quotRef, String referenceType, String reference, Staff salesPerson, String currencyCode, Date requiredDeliveryDate, PaymentTerm pymtTerm, ShipmentTerm shipmentTerm, List<Item> items, BigDecimal gross, BigDecimal discount, BigDecimal subTotal, BigDecimal nett, Staff issuedBy, Staff releasedAVerifiedBy, CollectAddress customerSignature) {
        super(createdDateTime, modifiedDateTime, code, actualCreatedDateTime, signedDocPic, status);
        this.billToCust = billToCust;
        this.deliverToCust = deliverToCust;
        this.custPOReference = custPOReference;
        this.quotRef = quotRef;
        this.referenceType = referenceType;
        this.reference = reference;
        this.salesPerson = salesPerson;
        this.currencyCode = currencyCode;
        this.requiredDeliveryDate = requiredDeliveryDate;
        this.pymtTerm = pymtTerm;
        this.shipmentTerm = shipmentTerm;
        this.items = items;
        this.gross = gross;
        this.discount = discount;
        this.subTotal = subTotal;
        this.nett = nett;
        this.issuedBy = issuedBy;
        this.releasedAVerifiedBy = releasedAVerifiedBy;
        this.customerSignature = customerSignature;
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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public BigDecimal getGross() {
        gross = gross.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        return gross;
    }

    public void setGross(BigDecimal gross) {
        this.gross = gross.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    public BigDecimal getDiscount() {
        discount = discount.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    public BigDecimal getSubTotal() {
        subTotal = this.gross.subtract(this.discount);
        subTotal = subTotal.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal.setScale(2, BigDecimal.ROUND_HALF_EVEN);;
    }

    public BigDecimal getNett() {
        nett = nett.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        return nett;
    }

    public void setNett(BigDecimal nett) {
        this.nett = nett.setScale(2, BigDecimal.ROUND_HALF_EVEN);
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
