/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BizRulesConfiguration;

import Service.AccountingRulesService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class AccountingRules {

    public enum InvoiceStatus {
        NEW,
        PARTIALLY_PAID,
        COMPLETED
    }
    private List<String> pymtTerms;
    private List<String> currencyCodes;
    private List<InvoiceStatus> invoiceStatuses;
    private Double taxRate;

    public AccountingRules() {
        pymtTerms = new ArrayList<>();
        currencyCodes = new ArrayList<>();

        pymtTerms.add("Net Due 30");
        pymtTerms.add("Net Due 60");
        pymtTerms.add("Net Due 90");
        pymtTerms.add("PIA");
        pymtTerms.add("EOM");
        pymtTerms.add("COD");

        currencyCodes.add("MYR");
        taxRate = -1.00;
        invoiceStatuses = Arrays.asList(InvoiceStatus.values());
    }

    public Double getTaxRate() {
        if (taxRate < 0) {
            taxRate = AccountingRulesService.getTaxRate();
        }
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    public List<String> getPymtTerms() {
        return pymtTerms;
    }

    public void setPymtTerms(List<String> pymtTerms) {
        this.pymtTerms = pymtTerms;
    }

    public List<String> getCurrencyCodes() {
        return currencyCodes;
    }

    public void setCurrencyCodes(List<String> currencyCodes) {
        this.currencyCodes = currencyCodes;
    }

    public List<InvoiceStatus> getInvoiceStatuses() {
        return invoiceStatuses;
    }

    public void setInvoiceStatuses(List<InvoiceStatus> invoiceStatuses) {
        this.invoiceStatuses = invoiceStatuses;
    }

}
