/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ExperimentArea;

import Entity.Item;
import Entity.Product;
import Entity.SalesOrder;
import Entity.TransferOrder;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class QtyAdjustmentSOTO {

    public static void main(String[] args) {
        SalesOrder salesOrder = new SalesOrder();

        Item item1 = new Item();
        item1.setProduct(new Product("P1001"));
        item1.setQty(30);
        item1.setOriQty(30);

        Item item2 = new Item();
        item2.setProduct(new Product("P1002"));
        item2.setQty(70);
        item2.setOriQty(70);

        Item item3 = new Item();
        item3.setProduct(new Product("P1003"));
        item3.setQty(20);
        item3.setOriQty(20);

        List<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        items.add(item3);

        salesOrder.setItems(items);

        // ===========================
        List<Item> tempItems = new ArrayList<>();

        for (Item item : salesOrder.getItems()) {
            tempItems.add(item.clone());
        }

        TransferOrder transferOrder = new TransferOrder();
        transferOrder.setItems(new ArrayList<>());
        transferOrder.getItems().add(tempItems.get(0).clone());
        transferOrder.getItems().add(tempItems.get(1).clone());

        Item item1InSO = tempItems.get(tempItems.indexOf(transferOrder.getItems().get(0)));
        Item item2InSO = tempItems.get(tempItems.indexOf(transferOrder.getItems().get(1)));

        Item item1InTO = (Item) transferOrder.getItems().get(0);
        Item item2InTO = (Item) transferOrder.getItems().get(1);

        item1InTO.setQty(0);
        item2InTO.setQty(0);

        item1InTO.setOriQty(item1InTO.getQty());
        item1InTO.setQty(10);

        item2InTO.setOriQty(item2InTO.getQty());
        item2InTO.setQty(50);

        System.out.println(item1InSO.getQty());
        System.out.println(item1InTO.getQty());
        System.out.println(item1InTO.getOriQty());

        item1InSO.setQty(item1InSO.getQty() - item1InTO.getQty() + item1InTO.getOriQty());
        item2InSO.setQty(item2InSO.getQty() - item2InTO.getQty() + item2InTO.getOriQty());

        item1InTO.setOriQty(item1InTO.getQty());
        item1InTO.setQty(20);

        item2InTO.setOriQty(item2InTO.getQty());
        item2InTO.setQty(50);

        item1InSO.setQty(item1InSO.getQty() - item1InTO.getQty() + item1InTO.getOriQty());
        item2InSO.setQty(item2InSO.getQty() - item2InTO.getQty() + item2InTO.getOriQty());

        item1InTO.setOriQty(item1InTO.getQty());
        item1InTO.setQty(5);

        item2InTO.setOriQty(item2InTO.getQty());
        item2InTO.setQty(50);

        item1InSO.setQty(item1InSO.getQty() - item1InTO.getQty() + item1InTO.getOriQty());
        item2InSO.setQty(item2InSO.getQty() - item2InTO.getQty() + item2InTO.getOriQty());

        //remove
        item1InSO.setQty(item1InSO.getQty() + item1InTO.getQty());
        transferOrder.getItems().remove(item1InTO);

        //add
        transferOrder.getItems().add(tempItems.get(0).clone());

        item1InTO = (Item) transferOrder.getItems().get(1);

        item1InTO.setQty(0);

        item1InTO.setOriQty(item1InTO.getQty());
        item1InTO.setQty(7);

        item1InSO.setQty(item1InSO.getQty() - item1InTO.getQty() + item1InTO.getOriQty());

        //Save
        salesOrder.getItems().clear();
        salesOrder.getItems().addAll(tempItems);

        item1InSO = salesOrder.getItems().get(0);
        item2InSO = salesOrder.getItems().get(1);

        item1InTO = (Item) transferOrder.getItems().get(0);
        item2InTO = (Item) transferOrder.getItems().get(1);

        System.out.println("-------------");
        System.out.println(item1InSO.getQty());
        System.out.println(item2InSO.getQty());

        System.out.println(item1InTO.getQty());
        System.out.println(item2InTO.getQty());
    }

    /*
    conclusion
    Add

        transferOrder.getItems().add(tempItems.get(0).clone());

        item1InTO = (Item) transferOrder.getItems().get(1);

        item1InTO.setQty(0);

        item1InTO.setOriQty(item1InTO.getQty());
        item1InTO.setQty(7);

        item1InSO.setQty(item1InSO.getQty() - item1InTO.getQty() + item1InTO.getOriQty());

    Remove
        item1InSO.setQty(item1InSO.getQty() + item1InTO.getQty());
        transferOrder.getItems().remove(item1InTO);

    Update
        item1InTO.setOriQty(item1InTO.getQty());
        item1InTO.setQty(20);

        item1InSO.setQty(item1InSO.getQty() - item1InTO.getQty() + item1InTO.getOriQty());
     */
}
