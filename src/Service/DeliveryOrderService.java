/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.DeliveryOrderDAO;
import Entity.DeliveryOrder;
import java.util.List;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class DeliveryOrderService {

    public static String saveNewDeliveryOrder(DeliveryOrder doInDraft) {
        return DeliveryOrderDAO.saveNewDeliveryOrder(doInDraft);
    }

    public static String updateDeliveryOrder(DeliveryOrder doInDraft) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static List<DeliveryOrder> getAllDOs() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
