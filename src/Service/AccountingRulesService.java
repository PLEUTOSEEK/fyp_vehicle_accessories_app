/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.AccountingRulesDAO;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class AccountingRulesService {

    public static Double getTaxRate() {
        return AccountingRulesDAO.getTaxRate();
    }

}
