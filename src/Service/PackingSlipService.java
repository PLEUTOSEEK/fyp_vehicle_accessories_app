/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.PackingSlipDAO;
import Entity.PackingSlip;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class PackingSlipService {

    public static String saveNewPackingSlip(PackingSlip packingSlip) {
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
