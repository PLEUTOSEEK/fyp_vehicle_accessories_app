/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.StaffDAO;
import Entity.Staff;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class LoginService {

    //true = validated user; false = invalid user
    public static Staff validateUserAuthenticity(String userID, String pass) {
        Staff staff = StaffDAO.getStaffByID(userID);

        //Check is staff exist by using staff ID
        if (staff == null) {
            return null;
        }

        //Check is password correct for the staff
        if (staff.getPassword().equals(pass)) {
            return staff;
        }

        return null;
    }
}
