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
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class CustomerInquiryDAO {

    public static boolean saveNewCustomerInquiry(CustomerInquiry customerInquiry) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
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
            ps.setString(19, Base64.encodeBase64String(customerInquiry.getSignedDocPic())); // need to change to encoded 64 string
            ps.setTimestamp(20, customerInquiry.getModifiedDateTime());
            ps.execute();

            return true;

        } catch (Exception e) {
            return false;
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
                customerInquiry.setCode(rs.getString("CI_CI_ID"));
                customerInquiry.setReferenceType(rs.getString("CI_Reference_Type"));
                customerInquiry.setReference(rs.getString("CI_Reference"));
                customerInquiry.setBillToCust(CustomerDAO.getCustomerByID(rs.getString("CI_Bill_To_Cust")));
                customerInquiry.setDeliverToCust(CollectAddressDAO.getCollectAddressByID(rs.getString("CI_Deliver_To")));
                customerInquiry.setSalesPerson(StaffDAO.getStaffByID(rs.getString("CI_Sales_Person")));
                customerInquiry.setCurrencyCode(rs.getString("CI_Currency_Code"));
                customerInquiry.setRequiredDeliveryDate(rs.getDate("CI_Required_Delivery_Date"));
                customerInquiry.setPymtTerm(rs.getString("CI_Payment_Term"));
                customerInquiry.setShipmentTerm(rs.getString("CI_Shipment_Term"));
                customerInquiry.setGross(rs.getBigDecimal("CI_Gross"));
                customerInquiry.setDiscount(rs.getBigDecimal("CI_Discount"));
                customerInquiry.setSubTotal(rs.getBigDecimal("CI_Sub_Total"));
                customerInquiry.setNett(rs.getBigDecimal("CI_Nett"));
                customerInquiry.setIssuedBy(StaffDAO.getStaffByID(rs.getString("CI_Issued_By")));
                customerInquiry.setStatus(rs.getString("CI_Status"));
                customerInquiry.setCreatedDate(rs.getTimestamp("CI_Created_Date"));
                customerInquiry.setActualCreatedDateTime(rs.getTimestamp("CI_Actual_Created_Date"));
                customerInquiry.setSignedDocPic(Base64.decodeBase64(rs.getString("CI_Signed_Doc_Pic")));
                customerInquiry.setModifiedDateTime(rs.getTimestamp("CI_Modified_Date_Time"));
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

    public static String getLatestCode(String code) throws SQLException {
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
}
