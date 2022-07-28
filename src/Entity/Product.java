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
class Product extends Entity {

    private String prodID;
    private String prodName;
    private String partNo;
    private String description;
    private String color;
    private BigDecimal sellPrice;
    private BigDecimal MSRP;

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

}
