/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.SQLDatabaseConnection;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashMap;
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
public class SalesReportService {

    public static void getSalesReportSheet(String staffID, LocalDate startDate, LocalDate endDate) {
        try {
            String rpt = "src/Report/Sales_Report_Main.jrxml";
            String rptBody = "src/Report/Sales_Report_Sub_Body.jrxml";
            String timeSeriesChart = "src/Report/Sales_Report_Sub_Time_Series_Chart.jrxml";
            String rptTtlCases = "src/Report/Sales_Report_Sub_Ttl_Cases.jrxml";
            String rptCompletedCases = "src/Report/Sales_Report_Sub_Ttl_Completed_Cases.jrxml";
            String rptProgressCases = "src/Report/Sales_Report_Sub_Ttl_In_Progress_Cases.jrxml";
            String rptNewCases = "src/Report/Sales_Report_Sub_Ttl_New_Cases.jrxml";
            String rptOnHoldCases = "src/Report/Sales_Report_Sub_Ttl_On_Hold_Cases.jrxml";
            String rptPartiallyCompleted = "src/Report/Sales_Report_Sub_Ttl_Partially_Completed_Cases.jrxml";

            JasperReport jr = JasperCompileManager.compileReport(rpt);
            JasperReport jrBody = JasperCompileManager.compileReport(rptBody);
            JasperReport jrChart = JasperCompileManager.compileReport(timeSeriesChart);
            JasperReport jrTtlCase = JasperCompileManager.compileReport(rptTtlCases);
            JasperReport jrCompletedCase = JasperCompileManager.compileReport(rptCompletedCases);
            JasperReport jrProgressCase = JasperCompileManager.compileReport(rptProgressCases);
            JasperReport jrNewCase = JasperCompileManager.compileReport(rptNewCases);
            JasperReport jrOnHoldCase = JasperCompileManager.compileReport(rptOnHoldCases);
            JasperReport jrPartiallyCase = JasperCompileManager.compileReport(rptPartiallyCompleted);

            Map< String, Object> para = new HashMap<>();
            para.put("param_Main_Staff_ID", staffID);
            para.put("Start_Date_Range", Timestamp.valueOf(startDate.atStartOfDay()));
            para.put("End_Date_Range", Timestamp.valueOf(endDate.atStartOfDay()));
            para.put("rptBody", jrBody);
            para.put("timeSeriesChart", jrChart);
            para.put("rptTtlCases", jrTtlCase);
            para.put("rptCompletedCases", jrCompletedCase);
            para.put("rptProgressCases", jrProgressCase);
            para.put("rptNewCases", jrNewCase);
            para.put("rptOnHoldCases", jrOnHoldCase);
            para.put("rptPartiallyCompleted", jrPartiallyCase);

            JasperPrint jp = JasperFillManager.fillReport(jr, para, SQLDatabaseConnection.openConn());
            JasperViewer.viewReport(jp, false);

        } catch (Exception e) {
            System.out.println("assasa" + e.getMessage());
        }
    }
}
