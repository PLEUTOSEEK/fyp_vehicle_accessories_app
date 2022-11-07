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
public class Item<E> implements Cloneable {

    private Product product;
    private Inventory inventory;
    private E refDoc;
    private String reason;
    private String remark;
    private Integer qty;
    private Integer oriQty;
    private Integer qtyNotYetBill;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

    public Integer getOriQty() {
        return oriQty;
    }

    public void setOriQty(Integer oriQty) {
        this.oriQty = oriQty;
    }

    public Integer getQtyNotYetBill() {
        return qtyNotYetBill;
    }

    public void setQtyNotYetBill(Integer qtyNotYetBill) {
        this.qtyNotYetBill = qtyNotYetBill;
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
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Item<?> other = (Item<?>) obj;
        if (!Objects.equals(this.product, other.product)) {
            return false;
        }
        if (!Objects.equals(this.inventory, other.inventory)) {
            return false;
        }
        return Objects.equals(this.dlvrDate, other.dlvrDate);
    }

    public E getRefDoc() {
        return refDoc;
    }

    public void setRefDoc(E refDoc) {
        this.refDoc = refDoc;
    }

    @Override
    public Item clone() {
        Item clonedItem = null;
        try {
            clonedItem = (Item) super.clone();
            clonedItem.setDlvrDate(this.dlvrDate == null ? null : (Date) this.dlvrDate.clone());

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return clonedItem;
    }
}
