/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.math.BigDecimal;
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
    private Customer customerSignature;

}
