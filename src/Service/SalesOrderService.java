/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.SalesOrderDAO;
import Entity.SalesOrder;
import Structures.CodeStructure;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.List;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class SalesOrderService {

    public static String generateID() {
        DateFormat df = new SimpleDateFormat("yy"); // Just the year, with 2 digits
        String currentYr = df.format(Timestamp.from(Instant.now()));
        df = new SimpleDateFormat("MM"); // Just the month, with 2 digits
        String currenMth = df.format(Timestamp.from(Instant.now()));

        String newID = "";

        CodeStructure latestIDStruct = new CodeStructure();
        CodeStructure newIDStruct = new CodeStructure();

        newIDStruct.setName("SO");
        newIDStruct.setYear(currentYr);
        newIDStruct.setYear(currenMth);

        String latestID = SalesOrderDAO.getLatestCode();
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

    public static String saveNewSalesOrder(SalesOrder so) throws SQLException {
        so.setCode(generateID());
        return SalesOrderDAO.saveNewSalesOrder(so);
    }

    public static String updateSalesOrder(SalesOrder so) {
        return SalesOrderDAO.updateSalesOrder(so);
    }

    public static SalesOrder getSOByID(String code) {
        return SalesOrderDAO.getSalesOrderByID(code);

    }

    public static String updateSalesOrderStatus(SalesOrder salesOrder) {
        return SalesOrderDAO.updateSalesOrderStatus(salesOrder);
    }

    public static List<SalesOrder> getAllSalesOrders() {
        return SalesOrderDAO.getAllSalesOrder();
    }

}
