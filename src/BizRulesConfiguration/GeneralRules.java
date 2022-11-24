/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BizRulesConfiguration;

import DAO.GeneralRulesDAO;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class GeneralRules {

    private Integer inactivityLogoutTimeSecs;

    public GeneralRules() {
        inactivityLogoutTimeSecs = -1;
    }

    public Integer getInactivityLogoutTimeSecs() {

        if (inactivityLogoutTimeSecs < 0) {
            inactivityLogoutTimeSecs = GeneralRulesDAO.getInactivityLogoutTimeSecs();
        }
        return inactivityLogoutTimeSecs;
    }

    public void setInactivityLogoutTimeSecs(Integer inactivityLogoutTimeSecs) {
        this.inactivityLogoutTimeSecs = inactivityLogoutTimeSecs;
    }
}
