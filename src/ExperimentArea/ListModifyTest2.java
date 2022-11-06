/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ExperimentArea;

import Entity.Inventory;
import Entity.Item;
import Entity.Product;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class ListModifyTest2 {

    public static void main(String[] args) {

        Product product = new Product();
        product.setProdID("P1001");

        Inventory inventory = new Inventory();
        inventory.setInventoryID("INV1001");

        Item item = new Item();
        item.setProduct(product);
        item.setInventory(inventory);
        item.setQty(10);
        item.setRemark("This is item 1");
        // ==========================================
        Product product2 = new Product();
        product2.setProdID("P1001");

        Inventory inventory2 = new Inventory();
        inventory2.setInventoryID("INV1001");

        Item item2 = new Item();
        item2.setProduct(product2);
        item2.setInventory(inventory2);
        item2.setQty(90);
        item2.setOriQty(100);
        item2.setRemark("This is item 2");
        item2.setProduct(null);
        // ==========================================

        List<Item> items = new ArrayList<>();
        items.add(item);

        items.set(items.indexOf(item), item2);
        System.out.println(items.indexOf(item2));

        // OUTPUT:
        /*
        run:
            used item equals
            used item equals
            0
            BUILD SUCCESSFUL (total time: 1 second)
         */
    }
}
