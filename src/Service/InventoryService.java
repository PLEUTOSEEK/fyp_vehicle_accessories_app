/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.InventoryDAO;
import Entity.Inventory;
import Entity.Item;
import Entity.PackingSlip;
import Structures.CodeStructure;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.List;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class InventoryService {

    public static Integer getInventoryReadyQtyByProdID(String code) {
        return InventoryDAO.getInventoryReadyQtyByProdID(code);
    }

    public static Integer getOriginalReservedQty(String code) {
        return InventoryDAO.getOriginalReservedQtyByProdID(code);
    }

    public static List<Inventory> getReadyInventoriesByProdID(String code, Integer ttlDemandQty) {
        return InventoryDAO.getReadyInventoriesByProdID(code, ttlDemandQty);
    }

    public static boolean freeUpReservedQtyByInventoryID(Inventory inventory, int qty) {
        return InventoryDAO.freeUpReservedQtyByInventoryID(inventory, qty);
    }

    public static boolean reserveQtyForSalesDoc(Item item) {
        return InventoryDAO.reserveQtyForSalesDoc(item);
    }

    public static void postGoodsIssue(List<PackingSlip> packingSlips) throws Exception {
        for (PackingSlip packingSlip : packingSlips) {
            InventoryDAO.postGoodsIssues(packingSlip);
        }
    }

    public static List<Inventory> getInventoryByProdID(String prodID) {
        return InventoryDAO.getInventoryByProdID(prodID);
    }

    public static void putBackInventory(Item item, String destination) throws SQLException, Exception {
        InventoryDAO.putBackInventory(item, destination, generateID());
    }

    public static String generateID() throws SQLException {
        DateFormat df = new SimpleDateFormat("yy"); // Just the year, with 2 digits
        String currentYr = df.format(Timestamp.from(Instant.now()));
        df = new SimpleDateFormat("MM"); // Just the month, with 2 digits
        String currenMth = df.format(Timestamp.from(Instant.now()));

        String newID = "";

        CodeStructure latestIDStruct = new CodeStructure();
        CodeStructure newIDStruct = new CodeStructure();

        newIDStruct.setName("INVT");
        newIDStruct.setYear(currentYr);
        newIDStruct.setMonth(currenMth);

        String latestID = InventoryDAO.getLatestCode();
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

}
