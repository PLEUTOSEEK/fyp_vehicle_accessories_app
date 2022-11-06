/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BizRulesConfiguration;

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

}
