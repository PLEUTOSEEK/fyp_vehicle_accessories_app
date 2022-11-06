/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class PackingSlip extends Document implements Cloneable {

    private Staff PIC;
    private TransferOrder TO;
    private DeliveryOrder DO;
    private List<Item> items;

    public PackingSlip() {
        this(null, null, "", null, null, null, null, null, null);
    }

    public PackingSlip(Timestamp createdDateTime, Timestamp modifiedDateTime, String code, Timestamp actualCreatedDateTime, String signedDocPic, String status, Staff PIC, TransferOrder TO, List<Item> items) {
        super(createdDateTime, modifiedDateTime, code, actualCreatedDateTime, signedDocPic, status);
        this.PIC = PIC;
        this.TO = TO;
        this.items = items;
    }

    public Staff getPIC() {
        return PIC;
    }

    public void setPIC(Staff PIC) {
        this.PIC = PIC;
    }

    public TransferOrder getTO() {
        return TO;
    }

    public void setTO(TransferOrder TO) {
        this.TO = TO;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public DeliveryOrder getDO() {
        return DO;
    }

    public void setDO(DeliveryOrder DO) {
        this.DO = DO;
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
        final PackingSlip other = (PackingSlip) obj;
        return Objects.equals(this.code, other.code);
    }

    @Override
    public PackingSlip clone() {
        PackingSlip clonedPackingSlip = null;
        try {
            clonedPackingSlip = (PackingSlip) super.clone();

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return clonedPackingSlip;
    }
}
