/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.SQLDatabaseConnection;
import DAO.TransferOrderDAO;
import Entity.TransferOrder;
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
public class TransferOrderService {

    public static String generateID() {
        DateFormat df = new SimpleDateFormat("yy"); // Just the year, with 2 digits
        String currentYr = df.format(Timestamp.from(Instant.now()));
        df = new SimpleDateFormat("MM"); // Just the month, with 2 digits
        String currenMth = df.format(Timestamp.from(Instant.now()));

        String newID = "";

        CodeStructure latestIDStruct = new CodeStructure();
        CodeStructure newIDStruct = new CodeStructure();

        newIDStruct.setName("TO");
        newIDStruct.setYear(currentYr);
        newIDStruct.setMonth(currenMth);

        String latestID = TransferOrderDAO.getLatestCode();
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

    public static List<TransferOrder> getAllTO() {
        return TransferOrderDAO.getAllTO();
    }

    public static String saveNewTO(TransferOrder to) throws SQLException {
        to.setCode(generateID());
        return TransferOrderDAO.saveNewTransferOrder(to);
    }

    public static String updateTO(TransferOrder to) {
        return TransferOrderDAO.updateTO(to);
    }

    public static String updateTOStatus(TransferOrder to) {
        return TransferOrderDAO.updateTOStatus(to);
    }

    public static TransferOrder getTransferOrderByCode(String code) {
        return TransferOrderDAO.getTransferOrderByCode(code);
    }

    public static void getTransferOrderSheet(String toCode) {
        try {
            String report = "src/Report/TransferOrder_Individual.jrxml";

            JasperReport jr = JasperCompileManager.compileReport(report);

            Map< String, Object> para = new HashMap<>();
            para.put("param_TO_ID", toCode);

            JasperPrint jp = JasperFillManager.fillReport(jr, para, SQLDatabaseConnection.openConn());
            JasperViewer.viewReport(jp, false);

        } catch (Exception e) {
            System.out.println("assasa" + e.getMessage());
        }
    }
}
