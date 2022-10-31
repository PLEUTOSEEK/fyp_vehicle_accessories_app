/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import BizRulesConfiguration.AccountingRules;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class Item {

    private Product product;
    private Inventory inventory;
    private String remark;
    private Integer qty;
    private BigDecimal unitPrice;
    private Date dlvrDate;
    private BigDecimal exclTaxAmt;
    private BigDecimal discAmt;
    private BigDecimal inclTaxAmt;

    public Item() {
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Date getDlvrDate() {
        return dlvrDate;
    }

    public void setDlvrDate(Date dlvrDate) {
        this.dlvrDate = dlvrDate;
    }

    public BigDecimal getExclTaxAmt() {
        exclTaxAmt = BigDecimal.valueOf(Double.valueOf(qty) * unitPrice.doubleValue());
        return exclTaxAmt;
    }

    public BigDecimal getDiscAmt() {
        return discAmt;
    }

    public void setDiscAmt(BigDecimal discAmt) {
        this.discAmt = discAmt;
    }

    public BigDecimal getInclTaxAmt() {
        AccountingRules accRules = new AccountingRules();
        inclTaxAmt = BigDecimal.valueOf((exclTaxAmt.doubleValue() - discAmt.doubleValue()) * (1 + (accRules.getTaxRate() / 100.0)));
        return inclTaxAmt;
    }

    public void setExclTaxAmt(BigDecimal exclTaxAmt) {
        this.exclTaxAmt = exclTaxAmt;
    }

    public void setInclTaxAmt(BigDecimal inclTaxAmt) {
        this.inclTaxAmt = inclTaxAmt;
    }

//    @Override
//    public boolean equals(Object obj) {
//        System.out.println("I am in equals");
//        if (obj instanceof CollectAddress) {
//            Item item = ((Item) obj);
//            if (this.getProduct().getProdID().equals(item.getProduct().getProdID())
//                    && this.getDlvrDate().equals(item.getDlvrDate())) {
//                return true;
//            }
//        }
//        return false;
//    }
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + Objects.hashCode(this.product);
        hash = 43 * hash + Objects.hashCode(this.dlvrDate);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        System.out.println("I am in equals");
        if (obj instanceof Item) {
            Item item = (Item) obj;
            if (this.getProduct().getProdID().equals(item.getProduct().getProdID())
                    && this.getDlvrDate().equals(item.getDlvrDate())) {
                return true;
            }
        }
        return false;
    }
}
