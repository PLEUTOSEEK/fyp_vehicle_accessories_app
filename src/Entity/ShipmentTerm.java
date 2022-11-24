/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.util.Objects;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class ShipmentTerm extends Entity implements Cloneable {

    private String shipmentTermID;
    private String shipmentTermName;
    private String description;

    public String getShipmentTermID() {
        return shipmentTermID;
    }

    public void setShipmentTermID(String shipmentTermID) {
        this.shipmentTermID = shipmentTermID;
    }

    public String getShipmentTermName() {
        return shipmentTermName;
    }

    public void setShipmentTermName(String shipmentTermName) {
        this.shipmentTermName = shipmentTermName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public ShipmentTerm clone() {
        ShipmentTerm clonedShipmentTerm = null;
        try {
            clonedShipmentTerm = (ShipmentTerm) super.clone();

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return clonedShipmentTerm;
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
        final ShipmentTerm other = (ShipmentTerm) obj;
        return Objects.equals(this.shipmentTermID, other.shipmentTermID);
    }
}
