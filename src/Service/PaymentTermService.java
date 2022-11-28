/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.PaymentTermDAO;
import Entity.PaymentTerm;
import Structures.CodeStructure;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.List;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class PaymentTermService {

    public static String generateID() {
        DateFormat df = new SimpleDateFormat("yy"); // Just the year, with 2 digits
        String currentYr = df.format(Timestamp.from(Instant.now()));
        df = new SimpleDateFormat("MM"); // Just the month, with 2 digits
        String currenMth = df.format(Timestamp.from(Instant.now()));

        String newID = "";

        CodeStructure latestIDStruct = new CodeStructure();
        CodeStructure newIDStruct = new CodeStructure();

        newIDStruct.setName("PT");
        newIDStruct.setYear(currentYr);
        newIDStruct.setMonth(currenMth);

        String latestID = PaymentTermDAO.getLatestCode();
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

    public static List<PaymentTerm> getAllPaymentTerms() {
        return PaymentTermDAO.getAllPaymentTerms();
    }

    public static boolean updateInsertPaymentTerms(List<PaymentTerm> pymtTerms) {

        for (PaymentTerm pymtTerm : pymtTerms) {
            if (pymtTerm.getPymtTermID().isEmpty()) {
                //autogenerateID
                pymtTerm.setPymtTermID(generateID());
                //insert
                PaymentTermDAO.insertPymtTerm(pymtTerm);
            } else {
                //update
                PaymentTermDAO.updatePymtTerm(pymtTerm);
            }
        }

        return true;
    }
}
