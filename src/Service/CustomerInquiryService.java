/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.CustomerInquiryDAO;
import DAO.SQLDatabaseConnection;
import Entity.CustomerInquiry;
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
public class CustomerInquiryService {

    public static String saveNewCustomerInquiry(CustomerInquiry customerInquiry) throws SQLException {
        customerInquiry.setCode(generateID());
        return CustomerInquiryDAO.saveNewCustomerInquiry(customerInquiry);
    }

    public static String updateCustomerInquiry(CustomerInquiry customerInquiry) {
        return CustomerInquiryDAO.updateCustomerInquiry(customerInquiry);
    }

    public static String generateID() throws SQLException {
        DateFormat df = new SimpleDateFormat("yy"); // Just the year, with 2 digits
        String currentYr = df.format(Timestamp.from(Instant.now()));
        df = new SimpleDateFormat("MM"); // Just the month, with 2 digits
        String currenMth = df.format(Timestamp.from(Instant.now()));

        String newID = "";

        CodeStructure latestIDStruct = new CodeStructure();
        CodeStructure newIDStruct = new CodeStructure();

        newIDStruct.setName("CI");
        newIDStruct.setYear(currentYr);
        newIDStruct.setMonth(currenMth);

        String latestID = CustomerInquiryDAO.getLatestCode();
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

    public static CustomerInquiry getCustomerInquiryByCode(String code) throws SQLException {
        return CustomerInquiryDAO.getCustomerInquiryByCode(code);
    }

    public static String updateCustomerInquiryStatus(CustomerInquiry ci) {
        return CustomerInquiryDAO.updateCustomerInquiryStatus(ci);
    }

    public static List<CustomerInquiry> getAllCustomerInquiry() {
        return CustomerInquiryDAO.getAllCustomerInquiry();
    }

    public static void getCustomerInquirySheet(String ciCode) {
        try {
            String report = "src/Report/CustomerInquiry_Individual.jrxml";
            JasperReport jr = JasperCompileManager.compileReport(report);

            Map< String, Object> para = new HashMap<>();
            para.put("param_CI_ID_Header", ciCode);

            JasperPrint jp = JasperFillManager.fillReport(jr, para, SQLDatabaseConnection.openConn());
            JasperViewer.viewReport(jp, false);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
