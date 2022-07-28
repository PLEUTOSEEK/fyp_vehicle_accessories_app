/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class Quotation extends Document {

    private CustomerInquiry CI;
    private String referenceType;
    private String reference;
    private Customer billToCust;
    private CollectAddress deliverToCust;
    private Staff salesPerson;
    private String currencyCode;
    private Date quotValidityDate; // Valid Until
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
    private CollectAddress customerSignature;

    public Quotation() {
        this(null, null, "", null, null, "", null, "", "", null, null, null, "", null, null, "", "", null, null, null, null, null, null, null);
    }

    public Quotation(Timestamp createdDateTime, Timestamp modifiedDateTime, String code, Timestamp actualCreatedDateTime, byte[] signedDocPic, String status, CustomerInquiry CI, String referenceType, String reference, Customer billToCust, CollectAddress deliverToCust, Staff salesPerson, String currencyCode, Date quotValidityDate, Date requiredDeliveryDate, String pymtTerm, String shipmentTerm, BigDecimal gross, BigDecimal discount, BigDecimal subTotal, BigDecimal nett, Staff issuedBy, Staff releasedAVerifiedBy, CollectAddress customerSignature) {
        super(createdDateTime, modifiedDateTime, code, actualCreatedDateTime, signedDocPic, status);
        this.CI = CI;
        this.referenceType = referenceType;
        this.reference = reference;
        this.billToCust = billToCust;
        this.deliverToCust = deliverToCust;
        this.salesPerson = salesPerson;
        this.currencyCode = currencyCode;
        this.quotValidityDate = quotValidityDate;
        this.requiredDeliveryDate = requiredDeliveryDate;
        this.pymtTerm = pymtTerm;
        this.shipmentTerm = shipmentTerm;
        this.gross = gross;
        this.discount = discount;
        this.subTotal = subTotal;
        this.nett = nett;
        this.issuedBy = issuedBy;
        this.releasedAVerifiedBy = releasedAVerifiedBy;
        this.customerSignature = customerSignature;
    }

    public CustomerInquiry getCI() {
        return CI;
    }

    public void setCI(CustomerInquiry CI) {
        this.CI = CI;
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

    public Date getQuotValidityDate() {
        return quotValidityDate;
    }

    public void setQuotValidityDate(Date quotValidityDate) {
        this.quotValidityDate = quotValidityDate;
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

    public CollectAddress getCustomerSignature() {
        return customerSignature;
    }

    public void setCustomerSignature(CollectAddress customerSignature) {
        this.customerSignature = customerSignature;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Timestamp getActualCreatedDateTime() {
        return actualCreatedDateTime;
    }

    public void setActualCreatedDateTime(Timestamp actualCreatedDateTime) {
        this.actualCreatedDateTime = actualCreatedDateTime;
    }

    public byte[] getSignedDocPic() {
        return signedDocPic;
    }

    public void setSignedDocPic(byte[] signedDocPic) {
        this.signedDocPic = signedDocPic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
