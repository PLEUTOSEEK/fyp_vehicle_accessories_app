/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ExperimentArea;

import DAO.SQLDatabaseConnection;
import Entity.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class ReportOpener {

    public static void main(String[] args) throws JRException {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
        Customer customer = new Customer();

        conn = SQLDatabaseConnection.openConn();

        String jr = "src\\ExperimentArea\\report1.jrxml";

        JasperReport jReport = JasperCompileManager.compileReport(jr);

        Map<String, Object> para = new HashMap<>();
        para.put("XUanParameter", null);

        JasperPrint jprint = JasperFillManager.fillReport(jReport, para, conn);

        JasperViewer.viewReport(jprint, false);
    }
}
