/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.SQLDatabaseConnection;
import DAO.SalesOrderDAO;
import Entity.SalesOrder;
import Structures.CodeStructure;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

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
        newIDStruct.setMonth(currenMth);

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

    public static void salesOrderSheet(String soCode) {
        try {
            String report = "src/Report/SalesOrder_Header_Individual.jrxml";
            String report2 = "src/Report/SalesOrder_Body_Individual.jrxml";

            JasperReport jr = JasperCompileManager.compileReport(report);
            JasperReport jrBody = JasperCompileManager.compileReport(report2);

            Map< String, Object> para = new HashMap<>();
            para.put("param_SO_ID_Header", soCode);
            para.put("subreportParameter", jrBody);

            JasperPrint jp = JasperFillManager.fillReport(jr, para, SQLDatabaseConnection.openConn());
            JasperViewer.viewReport(jp, false);

        } catch (Exception e) {
            System.out.println("assasa" + e.getMessage());
        }
    }

}
