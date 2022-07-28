/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.sql.Timestamp;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class Inventory extends Entity {

    private String inventoryID;
    private Place storePlace;
    private Product product;
    private Integer reservedQty;
    private Integer readyQty;
    private Integer ttlQty;

    public Inventory(String inventoryID, Place storePlace, Product product, Integer reservedQty, Integer readyQty, Integer ttlQty, Timestamp createdDateTime, Timestamp modifiedDateTime) {
        super(createdDateTime, modifiedDateTime);
        this.inventoryID = inventoryID;
        this.storePlace = storePlace;
        this.product = product;
        this.reservedQty = reservedQty;
        this.readyQty = readyQty;
        this.ttlQty = ttlQty;
    }

}
