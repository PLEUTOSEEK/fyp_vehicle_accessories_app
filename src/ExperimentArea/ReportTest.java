/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ExperimentArea;

import DAO.SQLDatabaseConnection;
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
public class ReportTest {

    public static void main(String[] args) {
        try {
            String rpt = "src/Report/Accounting_Report_Main.jrxml";
            String rptPieChart = "src/Report/Accounting_Report_Sub_Pie_Chart.jrxml";

            JasperReport jr = JasperCompileManager.compileReport(rpt);
            JasperReport jrPieChart = JasperCompileManager.compileReport(rptPieChart);

            Map< String, Object> para = new HashMap<>();
            para.put("rptPieChart", jrPieChart);

            JasperPrint jp = JasperFillManager.fillReport(jr, para, SQLDatabaseConnection.openConn());
            JasperViewer.viewReport(jp, false);

        } catch (Exception e) {
            System.out.println("assasa" + e.getMessage());
        }
    }
}
