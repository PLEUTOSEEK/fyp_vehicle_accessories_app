/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.sql.Timestamp;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class Entity {

    protected Timestamp createdDateTime;
    protected Timestamp modifiedDateTime;

    public Timestamp getCreatedDate() {
        return createdDateTime;
    }

    public void setCreatedDate(Timestamp createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public Timestamp getModifiedDateTime() {
        return modifiedDateTime;
    }

    public void setModifiedDateTime(Timestamp modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }

}
