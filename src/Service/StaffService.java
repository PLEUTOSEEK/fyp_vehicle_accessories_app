/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.StaffDAO;
import Entity.Staff;
import Structures.CodeStructure;
import Utils.GeneratePassword;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.List;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class StaffService {

    public static String generateID() {
        DateFormat df = new SimpleDateFormat("yy"); // Just the year, with 2 digits
        String currentYr = df.format(Timestamp.from(Instant.now()));
        df = new SimpleDateFormat("MM"); // Just the month, with 2 digits
        String currenMth = df.format(Timestamp.from(Instant.now()));

        String newID = "";

        CodeStructure latestIDStruct = new CodeStructure();
        CodeStructure newIDStruct = new CodeStructure();

        newIDStruct.setName("S");
        newIDStruct.setYear(currentYr);
        newIDStruct.setMonth(currenMth);

        String latestID = StaffDAO.getLatestID();
        if (latestID != "") {
            latestIDStruct = CodeStructure.strToStruct(latestID);
            if (latestIDStruct.getYear().equals(newIDStruct.getYear()) && latestIDStruct.getMonth().equals(newIDStruct.getMonth())) {
                newIDStruct.setSeqNum(String.format("%03d", Integer.parseInt(latestIDStruct.getSeqNum()) + 1));
            } else {
                newIDStruct.setSeqNum(String.format("%03d", 1));
            }
        } else {
            newIDStruct.setSeqNum(String.format("%03d", 1));
        }
        return CodeStructure.structToStr(newIDStruct);

    }

    public static String saveNewStaff(Staff staff) {
        staff.setIsFrozen(false);
        staff.setResetPassNextLogin(false);
        staff.setPassword(GeneratePassword.generateSecurePassword());
        staff.setStaffID(generateID());
        return StaffDAO.saveNewStaff(staff);
    }

    public static String updateStaff(Staff staff) {
        return StaffDAO.updateStaff(staff);
    }

    public static List<Staff> getAllStaff() {
        return StaffDAO.getAllStaff();
    }

    public static Object getStaffByID(String staffID) {
        return StaffDAO.getStaffByID(staffID);
    }

    public static String updateStaffAcc(Staff staff) {
        return StaffDAO.updateStaffAcc(staff);
    }

    public static String updateStaffPassword(Staff loginStaff) {
        return StaffDAO.updateStaffPassword(loginStaff);

    }
}
