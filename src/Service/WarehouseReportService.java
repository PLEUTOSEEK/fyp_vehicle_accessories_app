/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

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
public class WarehouseReportService {

    public static void getInventoryControlRptSheet() {
        try {
            String rpt = "src/Report/Warehous_Report_Main.jrxml";
            String rptBody = "src/Report/Warehouse_Report_Sub_Body.jrxml";
            String rptSubLvlStatus = "src/Report/Warehouse_Report_Sub_Level_Status.jrxml";
            String rptPieChart = "src/Report/Warehouse_Report_Sub_Pie_Chart.jrxml";
            String rptSubQty = "src/Report/Warehouse_Report_Sub_Quantities.jrxml";

            JasperReport jr = JasperCompileManager.compileReport(rpt);
            JasperReport jrBody = JasperCompileManager.compileReport(rptBody);
            JasperReport jrSubLvlStatus = JasperCompileManager.compileReport(rptSubLvlStatus);
            JasperReport jrPieChart = JasperCompileManager.compileReport(rptPieChart);
            JasperReport jrSubQty = JasperCompileManager.compileReport(rptSubQty);

            Map< String, Object> para = new HashMap<>();
            para.put("rptBody", jrBody);
            para.put("rptSubLvl", jrSubLvlStatus);
            para.put("rptPieChart", jrPieChart);
            para.put("rptSubQty", jrSubQty);

            JasperPrint jp = JasperFillManager.fillReport(jr, para, SQLDatabaseConnection.openConn());
            JasperViewer.viewReport(jp, false);

        } catch (Exception e) {
            System.out.println("assasa" + e.getMessage());
        }
    }
}
