/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.QuotationDAO;
import DAO.SQLDatabaseConnection;
import Entity.Quotation;
import Structures.CodeStructure;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class QuotationService {

    public static String generateID() {
        DateFormat df = new SimpleDateFormat("yy"); // Just the year, with 2 digits
        String currentYr = df.format(Timestamp.from(Instant.now()));
        df = new SimpleDateFormat("MM"); // Just the month, with 2 digits
        String currenMth = df.format(Timestamp.from(Instant.now()));

        String newID = "";

        CodeStructure latestIDStruct = new CodeStructure();
        CodeStructure newIDStruct = new CodeStructure();

        newIDStruct.setName("QUOT");
        newIDStruct.setYear(currentYr);
        newIDStruct.setMonth(currenMth);

        try {
            String latestID = QuotationDAO.getLatestCode();

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
        } catch (SQLException ex) {
            Logger.getLogger(QuotationService.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }

    }

    public static List<Quotation> getAllQuotation() {
        return QuotationDAO.getAllQuotation();
    }

    public static String saveNewQuotation(Quotation quotation) throws SQLException {
        quotation.setCode(generateID());
        return QuotationDAO.saveNewQuotation(quotation);
    }

    public static String updateQuotation(Quotation quotation) {
        return QuotationDAO.updateQuotation(quotation);
    }

    public static String updateQuotationStatus(Quotation quotation) {
        return QuotationDAO.updateQuotationStatus(quotation);
    }

    public static Quotation getQuotationByID(String code) throws SQLException {
        return QuotationDAO.getQuotationByCode(code);
    }

    public static void getQuotationSheet(String quotCode) {
        try {
            String report = "src/Report/Quotation_Individual.jrxml";
            JasperReport jr = JasperCompileManager.compileReport(report);

            Map< String, Object> para = new HashMap<>();
            para.put("param_QUOT_ID_Header", quotCode);

            JasperPrint jp = JasperFillManager.fillReport(jr, para, SQLDatabaseConnection.openConn());
            JasperViewer.viewReport(jp, false);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
