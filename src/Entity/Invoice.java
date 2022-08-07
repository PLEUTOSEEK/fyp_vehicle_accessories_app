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
    private Customer customerSignature;

    public Invoice() {
        this(null, null, "", null, null, "", null, "", "", null, new BigDecimal("0.00"), new BigDecimal("0.00"), new BigDecimal("0.00"), new BigDecimal("0.00"), null, null, null);
    }

    public Invoice(Timestamp createdDateTime, Timestamp modifiedDateTime, String code, Timestamp actualCreatedDateTime, byte[] signedDocPic, String status, SalesOrder SO, String referenceType, String reference, List<Item> items, BigDecimal gross, BigDecimal discount, BigDecimal subTotal, BigDecimal ttlPayable, Staff issuedBy, Staff releasedAVerifiedBy, Customer customerSignature) {
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

}
