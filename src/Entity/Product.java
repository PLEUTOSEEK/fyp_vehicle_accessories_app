/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class Product extends Entity {

    private String prodID;
    private String prodName;
    private String partNo;
    private String uom; // Unit Of Measure
    private String description;
    private String color;
    private BigDecimal sellPrice;
    private BigDecimal MSRP;
    private Integer maxLvl;
    private Integer avgLvl;
    private Integer minLvl;
    private Integer dangerLvl;
    private Integer reorderLvl;

    public Product() {

    }

    public Product(String prodID) {
        this.prodID = prodID;
    }

    public Product(Timestamp createdDateTime, Timestamp modifiedDateTime) {
        super(createdDateTime, modifiedDateTime);
    }

    public Product(Timestamp createdDateTime, Timestamp modifiedDateTime, String prodID, String prodName, String partNo, String description, String color, BigDecimal sellPrice, BigDecimal MSRP) {
        super(createdDateTime, modifiedDateTime);
        this.prodID = prodID;
        this.prodName = prodName;
        this.partNo = partNo;
        this.description = description;
        this.color = color;
        this.sellPrice = sellPrice;
        this.MSRP = MSRP;
    }

    public String getProdID() {
        return prodID;
    }

    public void setProdID(String prodID) {
        this.prodID = prodID;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getPartNo() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    public BigDecimal getMSRP() {
        return MSRP;
    }

    public void setMSRP(BigDecimal MSRP) {
        this.MSRP = MSRP;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public Integer getMaxLvl() {
        return maxLvl;
    }

    public void setMaxLvl(Integer maxLvl) {
        this.maxLvl = maxLvl;
    }

    public Integer getAvgLvl() {
        return avgLvl;
    }

    public void setAvgLvl(Integer avgLvl) {
        this.avgLvl = avgLvl;
    }

    public Integer getMinLvl() {
        return minLvl;
    }

    public void setMinLvl(Integer minLvl) {
        this.minLvl = minLvl;
    }

    public Integer getDangerLvl() {
        return dangerLvl;
    }

    public void setDangerLvl(Integer dangerLvl) {
        this.dangerLvl = dangerLvl;
    }

    public Integer getReorderLvl() {
        return reorderLvl;
    }

    public void setReorderLvl(Integer reorderLvl) {
        this.reorderLvl = reorderLvl;
    }

    public String getPartNoDesc() {
        return this.partNo + " " + this.description;
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final Product other = (Product) obj;
        return Objects.equals(this.prodID, other.prodID);
    }
}
