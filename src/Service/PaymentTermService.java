/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.PaymentTermDAO;
import Entity.PaymentTerm;
import java.util.List;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class PaymentTermService {

    public static List<PaymentTerm> getAllPaymentTerms() {
        return PaymentTermDAO.getAllPaymentTerms();
    }

}
