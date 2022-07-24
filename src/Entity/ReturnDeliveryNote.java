/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.sql.Date;
import java.util.List;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class ReturnDeliveryNote extends Document {

    private SalesOrder SO;
    private Place collBackTo; // company = Thir Shen
    private CollectAddress collBckFr;
    private Date collectDate;
    private String inspectorMsg;
    private List<Item> items;
    private Staff issuedBy;
    private Staff inspectedBy;
    private Staff collectBackBy;
    private Person itemPassedBackBy;
    private Staff itemReceivedBy;

}
