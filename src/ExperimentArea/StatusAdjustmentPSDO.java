/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ExperimentArea;

import BizRulesConfiguration.WarehouseRules;
import Entity.DeliveryOrder;
import Entity.PackingSlip;
import Entity.TransferOrder;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class StatusAdjustmentPSDO {

    public static void main(String[] args) {

        TransferOrder transferOrder = new TransferOrder();
        transferOrder.setCode("TO1001");

        PackingSlip packingSlip1 = new PackingSlip();
        packingSlip1.setTO(transferOrder);
        packingSlip1.setCode("PS1001");
        packingSlip1.setStatus(WarehouseRules.PSStatus.NOT_YET_REFERED.toString());

        PackingSlip packingSlip2 = new PackingSlip();
        packingSlip2.setTO(transferOrder);
        packingSlip2.setCode("PS1002");
        packingSlip2.setStatus(WarehouseRules.PSStatus.NOT_YET_REFERED.toString());

        PackingSlip packingSlip3 = new PackingSlip();
        packingSlip3.setTO(transferOrder);
        packingSlip3.setCode("PS1003");
        packingSlip3.setStatus(WarehouseRules.PSStatus.NOT_YET_REFERED.toString());

        PackingSlip packingSlip4 = new PackingSlip();
        packingSlip4.setTO(transferOrder);
        packingSlip4.setCode("PS1004");
        packingSlip4.setStatus(WarehouseRules.PSStatus.NOT_YET_REFERED.toString());

        PackingSlip packingSlip5 = new PackingSlip();
        packingSlip5.setTO(transferOrder);
        packingSlip5.setCode("PS1005");
        packingSlip5.setStatus(WarehouseRules.PSStatus.NOT_YET_REFERED.toString());

        PackingSlip packingSlip6 = new PackingSlip();
        packingSlip6.setTO(transferOrder);
        packingSlip6.setCode("PS1006");
        packingSlip6.setStatus(WarehouseRules.PSStatus.NOT_YET_REFERED.toString());

        PackingSlip packingSlip7 = new PackingSlip();
        packingSlip7.setTO(transferOrder);
        packingSlip7.setCode("PS1007");
        packingSlip7.setStatus(WarehouseRules.PSStatus.NOT_YET_REFERED.toString());

        List<PackingSlip> tempPackingSlips = new ArrayList<>();
        tempPackingSlips.add(packingSlip1);
        tempPackingSlips.add(packingSlip2);
        tempPackingSlips.add(packingSlip3);
        tempPackingSlips.add(packingSlip4);
        tempPackingSlips.add(packingSlip5);
        tempPackingSlips.add(packingSlip6);
        tempPackingSlips.add(packingSlip7);

        //===================================
        DeliveryOrder<PackingSlip> deliveryOrder = new DeliveryOrder<>();
        deliveryOrder.setItems(new ArrayList<>());
        deliveryOrder.getItems().add(tempPackingSlips.get(0).clone());
        deliveryOrder.getItems().add(tempPackingSlips.get(2).clone());
        deliveryOrder.getItems().add(tempPackingSlips.get(5).clone());
        deliveryOrder.getItems().add(tempPackingSlips.get(6).clone());

        PackingSlip packingSlipInTO1 = tempPackingSlips.get(tempPackingSlips.indexOf(deliveryOrder.getItems().get(0)));
        PackingSlip packingSlipInTO3 = tempPackingSlips.get(tempPackingSlips.indexOf(deliveryOrder.getItems().get(1)));
        PackingSlip packingSlipInTO6 = tempPackingSlips.get(tempPackingSlips.indexOf(deliveryOrder.getItems().get(2)));
        PackingSlip packingSlipInTO7 = tempPackingSlips.get(tempPackingSlips.indexOf(deliveryOrder.getItems().get(3)));

        PackingSlip packingSlipInDO1 = deliveryOrder.getItems().get(0);
        PackingSlip packingSlipInDO2 = deliveryOrder.getItems().get(1);
        PackingSlip packingSlipInDO3 = deliveryOrder.getItems().get(2);
        PackingSlip packingSlipInDO4 = deliveryOrder.getItems().get(3);

        packingSlipInTO1.setStatus(WarehouseRules.PSStatus.REFERED.toString());
        packingSlipInTO3.setStatus(WarehouseRules.PSStatus.REFERED.toString());
        packingSlipInTO6.setStatus(WarehouseRules.PSStatus.REFERED.toString());
        packingSlipInTO7.setStatus(WarehouseRules.PSStatus.REFERED.toString());

        System.out.println("==================");
        for (PackingSlip packingSlip : tempPackingSlips) {
            System.out.println(packingSlip.getCode() + " " + packingSlip.getStatus());
        }

        //remove
        packingSlipInTO3.setStatus(WarehouseRules.PSStatus.NOT_YET_REFERED.toString());
        deliveryOrder.getItems().remove(packingSlipInDO3);

        System.out.println("==================");
        for (PackingSlip packingSlip : tempPackingSlips) {
            System.out.println(packingSlip.getCode() + " " + packingSlip.getStatus());
        }

        //add
        deliveryOrder.getItems().add(tempPackingSlips.get(3).clone());

        PackingSlip packingSlipInTO4 = tempPackingSlips.get(tempPackingSlips.indexOf(deliveryOrder.getItems().get(3)));

        packingSlipInDO4 = deliveryOrder.getItems().get(3);

        packingSlipInTO4.setStatus(WarehouseRules.PSStatus.REFERED.toString());

        System.out.println("==================");
        for (PackingSlip packingSlip : tempPackingSlips) {
            System.out.println(packingSlip.getCode() + " " + packingSlip.getStatus());
        }

        //add
        deliveryOrder.getItems().add(tempPackingSlips.get(1).clone());

        PackingSlip packingSlipInTO5 = tempPackingSlips.get(tempPackingSlips.indexOf(deliveryOrder.getItems().get(4)));

        PackingSlip packingSlipInDO5 = deliveryOrder.getItems().get(1);

        packingSlipInTO5.setStatus(WarehouseRules.PSStatus.REFERED.toString());

        System.out.println("==================");
        for (PackingSlip packingSlip : tempPackingSlips) {
            System.out.println(packingSlip.getCode() + " " + packingSlip.getStatus());
        }

    }
}

/*
Conclusion
    Remove
        packingSlipInTO3.setStatus(WarehouseRules.PSStatus.NOT_YET_REFERED.toString());
        deliveryOrder.getItems().remove(packingSlipInDO3);
    Add
        deliveryOrder.getItems().add(tempPackingSlips.get(3).clone());

        PackingSlip packingSlipInTO4 = tempPackingSlips.get(tempPackingSlips.indexOf(deliveryOrder.getItems().get(3)));

        packingSlipInDO4 = deliveryOrder.getItems().get(3);

        packingSlipInTO4.setStatus(WarehouseRules.PSStatus.REFERED.toString());
 */
