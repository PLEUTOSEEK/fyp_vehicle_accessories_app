/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.ShipmentTermDAO;
import Entity.ShipmentTerm;
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
public class ShipmentTermService {

    public static String generateID() {
        DateFormat df = new SimpleDateFormat("yy"); // Just the year, with 2 digits
        String currentYr = df.format(Timestamp.from(Instant.now()));
        df = new SimpleDateFormat("MM"); // Just the month, with 2 digits
        String currenMth = df.format(Timestamp.from(Instant.now()));

        String newID = "";

        CodeStructure latestIDStruct = new CodeStructure();
        CodeStructure newIDStruct = new CodeStructure();

        newIDStruct.setName("ST");
        newIDStruct.setYear(currentYr);
        newIDStruct.setMonth(currenMth);

        String latestID = ShipmentTermDAO.getLatestCode();
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

    public static List<ShipmentTerm> getAllShipmentTerms() {
        return ShipmentTermDAO.getAllShipmentTerms();
    }

    public static boolean updateInsertShipmentTerms(List<ShipmentTerm> shipmentTerms) {
        for (ShipmentTerm shipmentTerm : shipmentTerms) {
            if (shipmentTerm.getShipmentTermID().isEmpty()) {
                //autogenerateID
                shipmentTerm.setShipmentTermID(generateID());
                //insert
                ShipmentTermDAO.insertShipmentTerm(shipmentTerm);
            } else {
                //update
                ShipmentTermDAO.updateShipmentTerm(shipmentTerm);
            }
        }

        return true;
    }

}
