/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BizRulesConfiguration;

import Service.SalesRulesService;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class SalesRules {

    private List<CIStatus> ciStatuses;
    private List<QuotStatus> quotStatuses;
    private List<SOStatus> soStatuses;
    private Integer maxQuotationValidityPeriod;
    private BigDecimal maxOrderAmtperSO;
    private Double upperLimitPercentageDiscount;

    public enum CIStatus {
        NEW,
        UNRESPONSIVE,
        COMPLETED
    }

    public enum QuotStatus {
        NEW,
        LOST,
        WON,
        COMPLETED
    }

    public enum SOStatus {
        NEW,
        ON_HOLD,
        IN_PROGRESS,
        PARTIALLY_COMPLETED,
        COMPLETED
    }

    public SalesRules() {
        ciStatuses = Arrays.asList(CIStatus.values());
        quotStatuses = Arrays.asList(QuotStatus.values());
        soStatuses = Arrays.asList(SOStatus.values());

        maxQuotationValidityPeriod = -1;
        maxOrderAmtperSO = new BigDecimal("-1");
        upperLimitPercentageDiscount = -1.00;

    }

    public List<CIStatus> getCiStatuses() {
        return ciStatuses;
    }

    public List<QuotStatus> getQuotStatuses() {
        return quotStatuses;
    }

    public List<SOStatus> getSOStatuses() {
        return soStatuses;
    }

    public List<SOStatus> getSoStatuses() {
        return soStatuses;
    }

    public Integer getMaxQuotationValidityPeriod() {
        if (maxQuotationValidityPeriod < 0) {
            maxQuotationValidityPeriod = SalesRulesService.getMaxQuotationValidityPeriod();
        }
        return maxQuotationValidityPeriod;
    }

    public BigDecimal getMaxOrderAmtperSO() {
        if (maxOrderAmtperSO.doubleValue() < 0) {
            maxOrderAmtperSO = SalesRulesService.getMaxOrderAmtperSO();

        }
        return maxOrderAmtperSO;
    }

    public Double getUpperLimitPercentageDiscount() {
        if (upperLimitPercentageDiscount < 0) {
            upperLimitPercentageDiscount = SalesRulesService.getUpperLimitPercentageDiscount();

        }
        return upperLimitPercentageDiscount;
    }

}
