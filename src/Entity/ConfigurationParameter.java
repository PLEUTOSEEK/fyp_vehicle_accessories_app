/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.math.BigDecimal;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class ConfigurationParameter {

    //Sales
    private Integer maxQuotationValidityPeriod;
    private BigDecimal maxOrderAmtperSO;
    private Double upperLimitPercentageDiscount;

    //Warehouse
    private Integer maxRequiredDeliveryDate;

    //Accounting
    private Double taxRate;

    //General
    private Integer inactivityLogoutTimeSecs;

    public Integer getMaxQuotationValidityPeriod() {
        return maxQuotationValidityPeriod;
    }

    public void setMaxQuotationValidityPeriod(Integer maxQuotationValidityPeriod) {
        this.maxQuotationValidityPeriod = maxQuotationValidityPeriod;
    }

    public BigDecimal getMaxOrderAmtperSO() {
        return maxOrderAmtperSO;
    }

    public void setMaxOrderAmtperSO(BigDecimal maxOrderAmtperSO) {
        this.maxOrderAmtperSO = maxOrderAmtperSO;
    }

    public Double getUpperLimitPercentageDiscount() {
        return upperLimitPercentageDiscount;
    }

    public void setUpperLimitPercentageDiscount(Double upperLimitPercentageDiscount) {
        this.upperLimitPercentageDiscount = upperLimitPercentageDiscount;
    }

    public Integer getMaxRequiredDeliveryDate() {
        return maxRequiredDeliveryDate;
    }

    public void setMaxRequiredDeliveryDate(Integer maxRequiredDeliveryDate) {
        this.maxRequiredDeliveryDate = maxRequiredDeliveryDate;
    }

    public Double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    public Integer getInactivityLogoutTimeSecs() {
        return inactivityLogoutTimeSecs;
    }

    public void setInactivityLogoutTimeSecs(Integer inactivityLogoutTimeSecs) {
        this.inactivityLogoutTimeSecs = inactivityLogoutTimeSecs;
    }

}
