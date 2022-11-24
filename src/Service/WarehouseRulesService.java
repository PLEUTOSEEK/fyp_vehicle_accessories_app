/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.WarehouseRulesDAO;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class WarehouseRulesService {

    public static Integer getMaxRequiredDeliveryDate() {
        return WarehouseRulesDAO.getMaxRequiredDeliveryDate();
    }

    public static boolean updateMaxRequiredDeliveryDays(String text) {
        return WarehouseRulesDAO.updateMaxRequiredDeliveryDate(text);
    }

}
