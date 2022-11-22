/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.ShipmentTermDAO;
import Entity.ShipmentTerm;
import java.util.List;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class ShipmentTermService {

    public static List<ShipmentTerm> getAllShipmentTerms() {
        return ShipmentTermDAO.getAllShipmentTerms();
    }

}
