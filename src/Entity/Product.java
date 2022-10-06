/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class Product extends Entity {

    private String prodID;
    private String prodName;
    private String partNo;
    private String description;
    private String color;
    private BigDecimal sellPrice;
    private BigDecimal MSRP;
    private int maxLvl;
    private int avgLvl;
    private int minLvl;
    private int dangerLvl;
    private int reorderLvl;

    public Product() {

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

    public int getMaxLvl() {
        return maxLvl;
    }

    public void setMaxLvl(int maxLvl) {
        this.maxLvl = maxLvl;
    }

    public int getAvgLvl() {
        return avgLvl;
    }

    public void setAvgLvl(int avgLvl) {
        this.avgLvl = avgLvl;
    }

    public int getMinLvl() {
        return minLvl;
    }

    public void setMinLvl(int minLvl) {
        this.minLvl = minLvl;
    }

    public int getDangerLvl() {
        return dangerLvl;
    }

    public void setDangerLvl(int dangerLvl) {
        this.dangerLvl = dangerLvl;
    }

    public int getReorderLvl() {
        return reorderLvl;
    }

    public void setReorderLvl(int reorderLvl) {
        this.reorderLvl = reorderLvl;
    }

}
