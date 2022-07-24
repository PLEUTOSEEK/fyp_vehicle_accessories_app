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
    protected byte[] signedDocPic;
    protected String status;

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

    public byte[] getSignedDocPic() {
        return signedDocPic;
    }

    public void setSignedDocPic(byte[] signedDocPic) {
        this.signedDocPic = signedDocPic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
