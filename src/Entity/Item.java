/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.math.BigDecimal;
import java.sql.Date;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class Item {

    private Product product;
    private Inventory inventory;
    private String remark;
    private Integer qty;
    private BigDecimal unitPrice;
    private Date dlvrDate;
    private BigDecimal exclTaxAmt;
    private BigDecimal discAmt;
    private BigDecimal inclTaxAmt;
}
