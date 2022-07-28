/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class PackingSlip extends Document {

    private Staff PIC;
    private TransferOrder TO;
    private List<Item> items;

    public PackingSlip(Timestamp createdDateTime, Timestamp modifiedDateTime, String code, Timestamp actualCreatedDateTime, byte[] signedDocPic, String status, Staff PIC, TransferOrder TO, List<Item> items) {
        super(createdDateTime, modifiedDateTime, code, actualCreatedDateTime, signedDocPic, status);
        this.PIC = PIC;
        this.TO = TO;
        this.items = items;
    }

}
