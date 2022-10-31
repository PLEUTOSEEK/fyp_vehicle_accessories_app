/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ExperimentArea;

import Entity.Item;
import Entity.Product;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class NewClass2 {

    public static void main(String[] args) {
        List<Item> item = new ArrayList<>();
        Item item1 = new Item();

        Product product = new Product();
        product.setProdID("P1001");
        item1.setProduct(product);
        LocalDate date = LocalDate.now();
        item1.setDlvrDate(java.sql.Date.valueOf(date));

        Item item2 = new Item();
        Product product2 = new Product();
        product2.setProdID("P1001");
        item2.setProduct(product2);
        LocalDate date2 = LocalDate.now();
        item2.setDlvrDate(java.sql.Date.valueOf(date));

        item.add(item1);

        System.out.println(item.get(0).getProduct().getProdID());
        System.out.println(item2.getProduct().getProdID());

        System.out.println(item.get(0).getDlvrDate().toString());
        System.out.println(item2.getDlvrDate().toString());

        if (item.contains(item2)) {
            System.out.println("it have item 2");
        } else {
            System.out.println("it don't have item 2");
        }
    }
}
