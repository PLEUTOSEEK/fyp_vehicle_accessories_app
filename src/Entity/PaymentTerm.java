/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class PaymentTerm extends Entity {

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

}
