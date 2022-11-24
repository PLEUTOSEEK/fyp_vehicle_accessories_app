/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.SalesRulesDAO;
import java.math.BigDecimal;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class SalesRulesService {

    public static Integer getMaxQuotationValidityPeriod() {
        return SalesRulesDAO.getMaxQuotationValidityPeriod();
    }

    public static Double getUpperLimitPercentageDiscount() {
        return SalesRulesDAO.getUpperLimitPercentageDiscount();
    }

    public static BigDecimal getMaxOrderAmtperSO() {
        return SalesRulesDAO.getMaxOrderAmtperSO();
    }

    public static boolean updateMaxQuotationValidityPeriod(String value) {
        return SalesRulesDAO.updateMaxQuotationValidityPeriod(value);
    }

    public static boolean updateUpperLimitPercentageDiscount(String value) {
        return SalesRulesDAO.updateUpperLimitPercentageDiscount(value);
    }

    public static boolean updateMaxOrderAmtperSO(String value) {
        return SalesRulesDAO.updateMaxOrderAmtperSO(value);
    }
}
