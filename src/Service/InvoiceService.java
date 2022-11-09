/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.InvoiceDAO;
import Entity.Invoice;
import Structures.CodeStructure;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class InvoiceService {

    public static String generateID() {
        DateFormat df = new SimpleDateFormat("yy"); // Just the year, with 2 digits
        String currentYr = df.format(Timestamp.from(Instant.now()));
        df = new SimpleDateFormat("MM"); // Just the month, with 2 digits
        String currenMth = df.format(Timestamp.from(Instant.now()));

        String newID = "";

        CodeStructure latestIDStruct = new CodeStructure();
        CodeStructure newIDStruct = new CodeStructure();

        newIDStruct.setName("INV");
        newIDStruct.setYear(currentYr);
        newIDStruct.setYear(currenMth);

        String latestID = InvoiceDAO.getLatestCode();
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

    public static String updateInvoice(Invoice invoice) {
        invoice.setCode(generateID());
        return InvoiceDAO.saveNewInvoice(invoice);
    }

    public static String saveNewInvoice(Invoice invoice) {
        return InvoiceDAO.updateInvoice(invoice);
    }

    public static Invoice getInvoiceByID(String code) {
        return InvoiceDAO.getInvoiceByID(code);
    }

    public static String updateInvoiceStatus(Invoice invoice) {
        return InvoiceDAO.updateInvoiceStatus(invoice);
    }

}
