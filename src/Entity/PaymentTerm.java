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
public class PaymentTerm extends Entity implements Cloneable {

    private String pymtTermID;
    private String pymtTermName;
    private String description;
    private String baseLineDocumentDate; // SO/DO/INV
    private int daysLimit;

    public String getPymtTermID() {
        return pymtTermID;
    }

    public void setPymtTermID(String pymtTermID) {
        this.pymtTermID = pymtTermID;
    }

    public String getPymtTermName() {
        return pymtTermName;
    }

    public void setPymtTermName(String pymtTermName) {
        this.pymtTermName = pymtTermName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBaseLineDocumentDate() {
        return baseLineDocumentDate;
    }

    public void setBaseLineDocumentDate(String baseLineDocumentDate) {
        this.baseLineDocumentDate = baseLineDocumentDate;
    }

    public int getDaysLimit() {
        return daysLimit;
    }

    public void setDaysLimit(int daysLimit) {
        this.daysLimit = daysLimit;
    }

    @Override
    public PaymentTerm clone() {
        PaymentTerm clonedPymtTerm = null;
        try {
            clonedPymtTerm = (PaymentTerm) super.clone();

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return clonedPymtTerm;
    }

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
        final PaymentTerm other = (PaymentTerm) obj;
        if (!Objects.equals(this.pymtTermID, other.pymtTermID)) {
            return false;
        }
        return Objects.equals(this.pymtTermName, other.pymtTermName);
    }

}
