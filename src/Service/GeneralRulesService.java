/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.GeneralRulesDAO;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class GeneralRulesService {

    public static Integer getSessionTimeOut() {
        return GeneralRulesDAO.getInactivityLogoutTimeSecs();
    }

    public static boolean updateSessionTimeOut(String text) {
        return GeneralRulesDAO.updateInactivityLogoutTimeSecs(text);
    }

}
