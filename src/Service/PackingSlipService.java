/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.PackingSlipDAO;
import DAO.SQLDatabaseConnection;
import Entity.PackingSlip;
import Structures.CodeStructure;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Collection;
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
public class PackingSlipService {

    public static String generateID() {
        DateFormat df = new SimpleDateFormat("yy"); // Just the year, with 2 digits
        String currentYr = df.format(Timestamp.from(Instant.now()));
        df = new SimpleDateFormat("MM"); // Just the month, with 2 digits
        String currenMth = df.format(Timestamp.from(Instant.now()));

        String newID = "";

        CodeStructure latestIDStruct = new CodeStructure();
        CodeStructure newIDStruct = new CodeStructure();

        newIDStruct.setName("PS");
        newIDStruct.setYear(currentYr);
        newIDStruct.setMonth(currenMth);

        String latestID = PackingSlipDAO.getLatestCode();
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

    public static String saveNewPackingSlip(PackingSlip packingSlip) {
        packingSlip.setCode(generateID());
        return PackingSlipDAO.saveNewPackingSlip(packingSlip);
    }

    public static String updatePackingSlip(PackingSlip packingSlip) {
        return PackingSlipDAO.updatePackingSlip(packingSlip);
    }

    public static List<PackingSlip> getPSsByDOID(String code) {
        return PackingSlipDAO.getPSsByDOID(code);
    }

    public static List<PackingSlip> getPSsByTOID(String code) {
        return PackingSlipDAO.getPSsByTOID(code);
    }

    public static boolean updatePackingSlipsStatus(List<PackingSlip> packingSlips) {
        return PackingSlipDAO.updatePackingSlipsStatus(packingSlips);
    }

    public static Collection<? extends PackingSlip> getPSsBySOID(String code) {
        return PackingSlipDAO.getPSsBySOID(code);
    }

    public static List<PackingSlip> getAllPackingSlips() {
        return PackingSlipDAO.getAllPackingSlips();
    }

    public static void getPackingSlipSheet(String psCode) {
        try {
            String report = "src/Report/PackingSlip_Individual.jrxml";

            JasperReport jr = JasperCompileManager.compileReport(report);

            Map< String, Object> para = new HashMap<>();
            para.put("param_PS_ID", psCode);

            JasperPrint jp = JasperFillManager.fillReport(jr, para, SQLDatabaseConnection.openConn());
            JasperViewer.viewReport(jp, false);

        } catch (Exception e) {
            System.out.println("assasa" + e.getMessage());
        }
    }
}
