/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.sql.Timestamp;
import java.util.Objects;

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

    public Inventory() {
        this(null, null, "", null, null, 0, 0, 0);
    }

    public Inventory(Timestamp createdDateTime, Timestamp modifiedDateTime, String inventoryID, Place storePlace, Product product, Integer reservedQty, Integer readyQty, Integer ttlQty) {
        super(createdDateTime, modifiedDateTime);
        this.inventoryID = inventoryID;
        this.storePlace = storePlace;
        this.product = product;
        this.reservedQty = reservedQty;
        this.readyQty = readyQty;
        this.ttlQty = ttlQty;
    }

    public String getInventoryID() {
        return inventoryID;
    }

    public void setInventoryID(String inventoryID) {
        this.inventoryID = inventoryID;
    }

    public Place getStorePlace() {
        return storePlace;
    }

    public void setStorePlace(Place storePlace) {
        this.storePlace = storePlace;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getReservedQty() {
        return reservedQty;
    }

    public void setReservedQty(Integer reservedQty) {
        this.reservedQty = reservedQty;
    }

    public Integer getReadyQty() {
        return readyQty;
    }

    public void setReadyQty(Integer readyQty) {
        this.readyQty = readyQty;
    }

    public Integer getTtlQty() {
        return this.reservedQty + this.readyQty;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Inventory other = (Inventory) obj;
        return Objects.equals(this.inventoryID, other.inventoryID);
    }

}
