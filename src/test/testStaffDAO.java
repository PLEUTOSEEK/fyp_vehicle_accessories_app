/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import DAO.StaffDAO;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class testStaffDAO {

    public static void main(String[] args) {
        System.out.println(StaffDAO.getStaffByID("S1902-001").getGender());

    }
}
