/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.sql.Date;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class Person extends Entity {

    protected byte[] avatarImg;
    protected String name;
    protected String gender;
    protected Date DOB; // date of birth
    protected String IC;
    protected String maritalStatus;
    protected String nationality;
    protected String honorifics;
    protected Address residentialAddr; // or permanent address
    protected Address corAddr; // corresponding address
    protected Contact contact;
    protected String occupation;
    protected String race;
    protected String religion;
    protected String status;

    public byte[] getAvatarImg() {
        return avatarImg;
    }

    public void setAvatarImg(byte[] avatarImg) {
        this.avatarImg = avatarImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDOB() {
        return DOB;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    public String getIC() {
        return IC;
    }

    public void setIC(String IC) {
        this.IC = IC;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getHonorifics() {
        return honorifics;
    }

    public void setHonorifics(String honorifics) {
        this.honorifics = honorifics;
    }

    public Address getResidentialAddr() {
        return residentialAddr;
    }

    public void setResidentialAddr(Address residentialAddr) {
        this.residentialAddr = residentialAddr;
    }

    public Address getCorAddr() {
        return corAddr;
    }

    public void setCorAddr(Address corAddr) {
        this.corAddr = corAddr;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String Status) {
        this.status = Status;
    }

}
