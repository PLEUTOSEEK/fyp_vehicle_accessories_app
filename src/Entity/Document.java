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
public class Document extends Entity {

    protected String code;
    protected Timestamp actualCreatedDateTime;
    protected String signedDocPic; // prev iously is byte[]
    protected String status;

    public Document(Timestamp createdDateTime, Timestamp modifiedDateTime, String code, Timestamp actualCreatedDateTime, String signedDocPic, String status) {
        super(createdDateTime, modifiedDateTime);
        this.code = code;
        this.actualCreatedDateTime = actualCreatedDateTime;
        this.signedDocPic = signedDocPic;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Timestamp getActualCreatedDateTime() {
        return actualCreatedDateTime;
    }

    public void setActualCreatedDateTime(Timestamp actualCreatedDateTime) {
        this.actualCreatedDateTime = actualCreatedDateTime;
    }

    public String getSignedDocPic() {
        return signedDocPic;
    }

    public void setSignedDocPic(String signedDocPic) {
        this.signedDocPic = signedDocPic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
