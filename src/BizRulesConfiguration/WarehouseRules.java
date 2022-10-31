/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BizRulesConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class WarehouseRules {

    private List<String> shipmentTerms;

    public WarehouseRules() {
        shipmentTerms = new ArrayList<>();

        shipmentTerms.add("EXW");
        shipmentTerms.add("DDP");
    }

    public List<String> getShipmentTerms() {
        return shipmentTerms;
    }

    public void setShipmentTerms(List<String> shipmentTerms) {
        this.shipmentTerms = shipmentTerms;
    }
}
