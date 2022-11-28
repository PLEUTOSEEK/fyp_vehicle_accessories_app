/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.ProductDAO;
import Entity.Product;
import java.util.List;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class ProductService {

    public static List<Product> getAllProducts() {
        return ProductDAO.getAllProducts();
    }

}
