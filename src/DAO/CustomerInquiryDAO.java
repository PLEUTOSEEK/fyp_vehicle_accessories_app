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
                    + "Bill_To_Cust, "
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
            ps.setString(9, customerInquiry.getPymtTerm().getPymtTermID());
            ps.setString(10, customerInquiry.getShipmentTerm().getShipmentTermID());
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
            System.out.println("CI Insert " + e.getMessage());
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

            query = "SELECT [CI_ID] "
                    + "      ,[Reference_Type] "
                    + "      ,[Reference] "
                    + "      ,[Bill_To_Cust] "
                    + "      ,[Deliver_To] "
                    + "      ,[Sales_Person] "
                    + "      ,[Currency_Code] "
                    + "      ,[Required_Delivery_Date] "
                    + "      ,[Payment_Term] "
                    + "      ,[Shipment_Term] "
                    + "      ,[Gross] "
                    + "      ,[Discount] "
                    + "      ,[Sub_Total] "
                    + "      ,[Nett] "
                    + "      ,[Issued_By] "
                    + "      ,[Status] "
                    + "      ,[Created_Date] "
                    + "      ,[Actual_Created_Date] "
                    + "      ,[Signed_Doc_Pic] "
                    + "      ,[Modified_Date_Time] "
                    + "  FROM [dbo].[CustomerInquiry] "
                    + "  WHERE [CI_ID] = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, code);

            rs = ps.executeQuery();

            if (rs.next()) {
                customerInquiry.setCode(rs.getString("CI_ID"));
                customerInquiry.setReferenceType(rs.getString("Reference_Type"));
                customerInquiry.setReference(rs.getString("Reference"));
                customerInquiry.setBillToCust(CustomerDAO.getCustomerByID(rs.getString("Bill_To_Cust")));
                customerInquiry.setDeliverToCust(CollectAddressDAO.getCollectAddressByID("Deliver_To"));
                customerInquiry.setSalesPerson(StaffDAO.getStaffByID(rs.getString("Sales_Person")));
                customerInquiry.setCurrencyCode(rs.getString("Currency_Code"));
                customerInquiry.setRequiredDeliveryDate(rs.getDate("Required_Delivery_Date"));
                customerInquiry.setPymtTerm(PaymentTermDAO.getPymtTermByID(rs.getString("Payment_Term")));
                customerInquiry.setShipmentTerm(ShipmentTermDAO.getShipmentTermByID(rs.getString("Shipment_Term")));
                customerInquiry.setGross(rs.getBigDecimal("Gross"));
                customerInquiry.setDiscount(rs.getBigDecimal("Discount"));
                customerInquiry.setSubTotal(rs.getBigDecimal("Sub_Total"));
                customerInquiry.setNett(rs.getBigDecimal("Nett"));
                customerInquiry.setIssuedBy(StaffDAO.getStaffByID("Issued_By"));
                customerInquiry.setStatus(rs.getString("Status"));
                customerInquiry.setCreatedDate(rs.getTimestamp("Created_Date"));
                customerInquiry.setActualCreatedDateTime(rs.getTimestamp("Actual_Created_Date"));
                customerInquiry.setSignedDocPic(rs.getString("Signed_Doc_Pic"));
                customerInquiry.setModifiedDateTime(rs.getTimestamp("Modified_Date_Time"));

                return customerInquiry;
            } else {
                return null;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
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
                return latestCode;
            } else {
                return "";
            }

        } catch (Exception e) {
            return "";
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
                    + "Bill_To_Cust = ?, "
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
            ps.setString(8, customerInquiry.getPymtTerm().getPymtTermID());
            ps.setString(9, customerInquiry.getShipmentTerm().getShipmentTermID());
            ps.setBigDecimal(10, customerInquiry.getGross());
            ps.setBigDecimal(11, customerInquiry.getDiscount());
            ps.setBigDecimal(12, customerInquiry.getSubTotal());
            ps.setBigDecimal(13, customerInquiry.getNett());
            ps.setString(14, customerInquiry.getIssuedBy().getStaffID());
            ps.setString(15, customerInquiry.getStatus());
            ps.setTimestamp(16, customerInquiry.getCreatedDate());
            ps.setString(17, customerInquiry.getSignedDocPic()); // need to change to encoded 64 string
            ps.setTimestamp(18, customerInquiry.getModifiedDateTime());
            ps.setString(19, customerInquiry.getCode());

            ps.execute();
            return customerInquiry.getCode();
        } catch (Exception e) {
            System.out.println("CI Update " + e.getMessage());
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
                    + "Status = ?,"
                    + "Modified_Date_Time = ? "
                    + "WHERE CI_ID = ? ";
            ps = conn.prepareStatement(query);
            // bind parameter
            ps.setString(1, customerInquiry.getStatus());
            ps.setTimestamp(2, customerInquiry.getModifiedDateTime());
            ps.setString(3, customerInquiry.getCode());

            ps.execute();
            return customerInquiry.getCode();
        } catch (Exception e) {
            System.out.println("CI Update " + e.getMessage());
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

            query = "SELECT [CI_ID] "
                    + "      ,[Reference_Type] "
                    + "      ,[Reference] "
                    + "      ,[Bill_To_Cust] "
                    + "      ,[Deliver_To] "
                    + "      ,[Sales_Person] "
                    + "      ,[Currency_Code] "
                    + "      ,[Required_Delivery_Date] "
                    + "      ,[Payment_Term] "
                    + "      ,[Shipment_Term] "
                    + "      ,[Gross] "
                    + "      ,[Discount] "
                    + "      ,[Sub_Total] "
                    + "      ,[Nett] "
                    + "      ,[Issued_By] "
                    + "      ,[Status] "
                    + "      ,[Created_Date] "
                    + "      ,[Actual_Created_Date] "
                    + "      ,[Signed_Doc_Pic] "
                    + "      ,[Modified_Date_Time] "
                    + "  FROM [dbo].[CustomerInquiry]";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                customerInquiry = new CustomerInquiry();

                customerInquiry.setCode(rs.getString("CI_ID"));
                customerInquiry.setReferenceType(rs.getString("Reference_Type"));
                customerInquiry.setReference(rs.getString("Reference"));
                customerInquiry.setBillToCust(CustomerDAO.getCustomerByID(rs.getString("Bill_To_Cust")));
                customerInquiry.setDeliverToCust(CollectAddressDAO.getCollectAddressByID(rs.getString("Deliver_To")));
                customerInquiry.setSalesPerson(StaffDAO.getStaffByID(rs.getString("Sales_Person")));
                customerInquiry.setCurrencyCode(rs.getString("Currency_Code"));
                customerInquiry.setRequiredDeliveryDate(rs.getDate("Required_Delivery_Date"));
                customerInquiry.setPymtTerm(PaymentTermDAO.getPymtTermByID(rs.getString("Payment_Term")));
                customerInquiry.setShipmentTerm(ShipmentTermDAO.getShipmentTermByID(rs.getString("Shipment_Term")));
                customerInquiry.setGross(rs.getBigDecimal("Gross"));
                customerInquiry.setDiscount(rs.getBigDecimal("Discount"));
                customerInquiry.setSubTotal(rs.getBigDecimal("Sub_Total"));
                customerInquiry.setNett(rs.getBigDecimal("Nett"));
                customerInquiry.setIssuedBy(StaffDAO.getStaffByID("Issued_By"));
                customerInquiry.setStatus(rs.getString("Status"));
                customerInquiry.setCreatedDate(rs.getTimestamp("Created_Date"));
                customerInquiry.setActualCreatedDateTime(rs.getTimestamp("Actual_Created_Date"));
                customerInquiry.setSignedDocPic(rs.getString("Signed_Doc_Pic"));
                customerInquiry.setModifiedDateTime(rs.getTimestamp("Modified_Date_Time"));

                customerInquiries.add(customerInquiry);
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
