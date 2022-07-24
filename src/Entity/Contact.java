/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class Contact {

    private String email;
    private String mobileNo; // mobile number
    private String ext; // extension number
    private String offPhNo; // office phone number
    private String homePhNo; // home phone number

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getOffPhNo() {
        return offPhNo;
    }

    public void setOffPhNo(String offPhNo) {
        this.offPhNo = offPhNo;
    }

    public String getHomePhNo() {
        return homePhNo;
    }

    public void setHomePhNo(String homePhNo) {
        this.homePhNo = homePhNo;
    }

}
