/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.ReturnDeliveryNoteDAO;
import Entity.ReturnDeliveryNote;
import Structures.CodeStructure;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class RDNService {

    public static String generateID() {
        DateFormat df = new SimpleDateFormat("yy"); // Just the year, with 2 digits
        String currentYr = df.format(Timestamp.from(Instant.now()));
        df = new SimpleDateFormat("MM"); // Just the month, with 2 digits
        String currenMth = df.format(Timestamp.from(Instant.now()));

        String newID = "";

        CodeStructure latestIDStruct = new CodeStructure();
        CodeStructure newIDStruct = new CodeStructure();

        newIDStruct.setName("RDN");
        newIDStruct.setYear(currentYr);
        newIDStruct.setYear(currenMth);

        try {
            String latestID = ReturnDeliveryNoteDAO.getLatestCode();

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

    public static String updateRDNStatus(ReturnDeliveryNote returnDeliveryNote) {
        return ReturnDeliveryNoteDAO.updateRDNStatus(returnDeliveryNote);
    }

    public static String saveNewRDN(ReturnDeliveryNote returnDeliveryNote) {
        returnDeliveryNote.setCode(generateID());
        return ReturnDeliveryNoteDAO.saveNewRDN(returnDeliveryNote);
    }

    public static String updateRDN(ReturnDeliveryNote returnDeliveryNote) {
        return ReturnDeliveryNoteDAO.updateRDN(returnDeliveryNote);
    }

    public static List<ReturnDeliveryNote> getAllRDN() {
        return ReturnDeliveryNoteDAO.getAllRDN();
    }

}
