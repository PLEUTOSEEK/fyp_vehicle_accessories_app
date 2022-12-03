/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.InventoryDAO;
import Entity.Inventory;
import Entity.Item;
import Entity.PackingSlip;
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

}
