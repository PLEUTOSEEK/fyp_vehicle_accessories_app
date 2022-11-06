/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.CustomerInquiry;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class CustomerInquiryDAO {

    public static String saveNewCustomerInquiry(CustomerInquiry customerInquiry) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        customerInquiry.setActualCreatedDateTime(timestamp);
        customerInquiry.setModifiedDateTime(timestamp);

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "INSERT INTO CustomerInquiry("
                    + "CI_ID, "
                    + "Reference_Type, "
                    + "Reference, "
                    + "Bill_Tp_Cust, "
                    + "Deliver_To, "
                    + "Sales_Person, "
                    + "Currency_Code, "
                    + "Required_Delivery_Date, "
                    + "Payment_Term, "
                    + "Shipment_Term, "
                    + "Gross, "
                    + "Discount, "
                    + "Sub_Total, "
                    + "Nett, "
                    + "issued_By, "
                    + "Status, "
                    + "Created_Date, "
                    + "Actual_Created_date, "
                    + "Signed_Doc_Pic, "
                    + "Modified_Date_Time "
                    + ") "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(query);
            ps.setString(1, customerInquiry.getCode());
            ps.setString(2, customerInquiry.getReferenceType());
            ps.setString(3, customerInquiry.getReference());
            ps.setString(4, customerInquiry.getBillToCust().getCustID());
            ps.setString(5, customerInquiry.getDeliverToCust().getCollectAddrID());
            ps.setString(6, customerInquiry.getSalesPerson().getStaffID());
            ps.setString(7, customerInquiry.getCurrencyCode());
            ps.setDate(8, customerInquiry.getRequiredDeliveryDate());
            ps.setString(9, customerInquiry.getPymtTerm());
            ps.setString(10, customerInquiry.getShipmentTerm());
            ps.setBigDecimal(11, customerInquiry.getGross());
            ps.setBigDecimal(12, customerInquiry.getDiscount());
            ps.setBigDecimal(13, customerInquiry.getSubTotal());
            ps.setBigDecimal(14, customerInquiry.getNett());
            ps.setString(15, customerInquiry.getIssuedBy().getStaffID());
            ps.setString(16, customerInquiry.getStatus());
            ps.setTimestamp(17, customerInquiry.getCreatedDate());
            ps.setTimestamp(18, customerInquiry.getActualCreatedDateTime());
            ps.setString(19, customerInquiry.getSignedDocPic()); // need to change to encoded 64 string
            ps.setTimestamp(20, customerInquiry.getModifiedDateTime());
            ps.execute();

            return customerInquiry.getCode();

        } catch (Exception e) {
            return null;
        } finally {
            try {
                ps.close();
            } catch (Exception e) {
                /* ignored */
            }
            try {
                conn.close();
            } catch (Exception e) {
                /* ignored */
            }
        }
    }

    public static CustomerInquiry getCustomerInquiryByCode(String code) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
        CustomerInquiry customerInquiry = new CustomerInquiry();

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT * FROM View_Retrieve_All_CustomerInquriy WHERE CI_CI_ID = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, code);

            rs = ps.executeQuery();

            if (rs.next()) {
                customerInquiry = new CustomerInquiry(
                        rs.getTimestamp("Created_Date"),
                        rs.getTimestamp("Modified_Date_Time"),
                        rs.getString("CI_ID"),
                        rs.getTimestamp("CI_Actual_Created_Date"),
                        rs.getString("CI_Signed_Doc_Pic"),
                        rs.getString("CI_Status"),
                        rs.getString("CI_Reference_Type"),
                        rs.getString("CI_Reference"),
                        CustomerDAO.getCustomerByID(rs.getString("CI_Bill_To_Cust")),
                        CollectAddressDAO.getCollectAddressByID(rs.getString("CI_Deliver_To")),
                        rs.getString("CI_Currency_Code"),
                        rs.getDate("CI_Required_Delivery_Date"),
                        rs.getString("CI_Payment_Term"),
                        rs.getString("CI_Shipment_Term"),
                        StaffDAO.getStaffByID("CI_Sales_Person"),
                        null,
                        rs.getBigDecimal("CI_Gross"),
                        rs.getBigDecimal("CI_Discount"),
                        rs.getBigDecimal("CI_Sub_Total"),
                        rs.getBigDecimal("CI_Nett"),
                        StaffDAO.getStaffByID(rs.getString("CI_Issued_By"))
                );
                return customerInquiry;
            } else {
                return null;
            }

        } catch (Exception e) {
            return null;
        } finally {
            try {
                ps.close();
            } catch (Exception e) {
                /* ignored */
            }
            try {
                conn.close();
            } catch (Exception e) {
                /* ignored */
            }
        }
    }

    public static String getLatestCode() throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;

        String latestCode = null;

        try {
            conn = SQLDatabaseConnection.openConn();
            query = "SELECT * FROM View_CustomerInquiry_LatestID";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            if (rs.next()) {
                latestCode = rs.getString("CI_ID");
            }

            return latestCode;

        } catch (Exception e) {
            return null;
        } finally {
            try {
                ps.close();
            } catch (Exception e) {
                /* ignored */
            }
            try {
                conn.close();
            } catch (Exception e) {
                /* ignored */
            }
        }
    }

    public static String updateCustomerInquiry(CustomerInquiry customerInquiry) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        customerInquiry.setModifiedDateTime(timestamp);

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "UPDATE CustomerInquiry SET "
                    + "Reference_Type = ?, "
                    + "Reference = ?, "
                    + "Bill_Tp_Cust = ?, "
                    + "Deliver_To = ?, "
                    + "Sales_Person = ?, "
                    + "Currency_Code = ?, "
                    + "Required_Delivery_Date = ?, "
                    + "Payment_Term = ?, "
                    + "Shipment_Term = ?, "
                    + "Gross = ?, "
                    + "Discount = ?, "
                    + "Sub_Total = ?, "
                    + "Nett = ?, "
                    + "issued_By = ?, "
                    + "Status = ?, "
                    + "Created_Date = ?, "
                    + "Actual_Created_date = ?, "
                    + "Signed_Doc_Pic = ?, "
                    + "Modified_Date_Time = ? "
                    + "WHERE CI_ID = ? ";
            ps = conn.prepareStatement(query);
            // bind parameter
            ps.setString(1, customerInquiry.getReferenceType());
            ps.setString(2, customerInquiry.getReference());
            ps.setString(3, customerInquiry.getBillToCust().getCustID());
            ps.setString(4, customerInquiry.getDeliverToCust().getCollectAddrID());
            ps.setString(5, customerInquiry.getSalesPerson().getStaffID());
            ps.setString(6, customerInquiry.getCurrencyCode());
            ps.setDate(7, customerInquiry.getRequiredDeliveryDate());
            ps.setString(8, customerInquiry.getPymtTerm());
            ps.setString(9, customerInquiry.getShipmentTerm());
            ps.setBigDecimal(10, customerInquiry.getGross());
            ps.setBigDecimal(11, customerInquiry.getDiscount());
            ps.setBigDecimal(12, customerInquiry.getSubTotal());
            ps.setBigDecimal(13, customerInquiry.getNett());
            ps.setString(14, customerInquiry.getIssuedBy().getStaffID());
            ps.setString(15, customerInquiry.getStatus());
            ps.setTimestamp(16, customerInquiry.getCreatedDate());
            ps.setTimestamp(17, customerInquiry.getActualCreatedDateTime());
            ps.setString(18, customerInquiry.getSignedDocPic()); // need to change to encoded 64 string
            ps.setTimestamp(19, customerInquiry.getModifiedDateTime());
            ps.setString(20, customerInquiry.getCode());

            ps.execute();
            return customerInquiry.getCode();
        } catch (Exception e) {
            return null;
        } finally {
            try {
                ps.close();
            } catch (Exception e) {
                /* ignored */
            }
            try {
                conn.close();
            } catch (Exception e) {
                /* ignored */
            }
        }
    }

    public static String updateCustomerInquiryStatus(CustomerInquiry customerInquiry) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        customerInquiry.setModifiedDateTime(timestamp);

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "UPDATE CustomerInquiry SET "
                    + "Status = ? "
                    + "WHERE CI_ID = ? ";
            ps = conn.prepareStatement(query);
            // bind parameter
            ps.setString(1, customerInquiry.getStatus());
            ps.setString(2, customerInquiry.getCode());

            ps.execute();
            return customerInquiry.getCode();
        } catch (Exception e) {
            return null;
        } finally {
            try {
                ps.close();
            } catch (Exception e) {
                /* ignored */
            }
            try {
                conn.close();
            } catch (Exception e) {
                /* ignored */
            }
        }
    }

    public static List<CustomerInquiry> getAllCustomerInquiry() {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
        CustomerInquiry customerInquiry = new CustomerInquiry();
        List<CustomerInquiry> customerInquiries = new ArrayList<>();

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT * FROM View_Retrieve_All_CustomerInquriy";
            ps = conn.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                customerInquiries.add(new CustomerInquiry(
                        rs.getTimestamp("Created_Date"),
                        rs.getTimestamp("Modified_Date_Time"),
                        rs.getString("CI_ID"),
                        rs.getTimestamp("CI_Actual_Created_Date"),
                        rs.getString("CI_Signed_Doc_Pic"),
                        rs.getString("CI_Status"),
                        rs.getString("CI_Reference_Type"),
                        rs.getString("CI_Reference"),
                        CustomerDAO.getCustomerByID(rs.getString("CI_Bill_To_Cust")),
                        CollectAddressDAO.getCollectAddressByID(rs.getString("CI_Deliver_To")),
                        rs.getString("CI_Currency_Code"),
                        rs.getDate("CI_Required_Delivery_Date"),
                        rs.getString("CI_Payment_Term"),
                        rs.getString("CI_Shipment_Term"),
                        StaffDAO.getStaffByID("CI_Sales_Person"),
                        null,
                        rs.getBigDecimal("CI_Gross"),
                        rs.getBigDecimal("CI_Discount"),
                        rs.getBigDecimal("CI_Sub_Total"),
                        rs.getBigDecimal("CI_Nett"),
                        StaffDAO.getStaffByID(rs.getString("CI_Issued_By"))
                ));
            }

            return customerInquiries;

        } catch (Exception e) {
            return null;
        } finally {
            try {
                ps.close();
            } catch (Exception e) {
                /* ignored */
            }
            try {
                conn.close();
            } catch (Exception e) {
                /* ignored */
            }
        }
    }
}
