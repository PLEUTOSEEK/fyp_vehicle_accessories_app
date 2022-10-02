/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.CustomerDAO;
import Entity.Customer;
import static Service.StaffService.generateID;
import java.util.List;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class CustomerService {

    public static String saveNewCustomer(Customer customer) {
        customer.setCustID(generateID());
        return CustomerDAO.saveNewCustomer(customer);
    }

    public static String updateCustomer(Customer customer) {
        return CustomerDAO.updateCustomer(customer);
    }

    public static List<Customer> getAllCustomers() {
        return CustomerDAO.getAllCustomers();

    }

}
