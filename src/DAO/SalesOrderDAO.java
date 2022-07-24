/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.SalesOrder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class SalesOrderDAO {

    public static boolean saveNewSalesOrder(SalesOrder salesOrder) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "INSERT INTO SalesOrder("
                    + "SO_ID, "
                    + "Quot_ID, "
                    + "Bill_To_Cust, "
                    + "Deliver_To, "
                    + "Cust_PO_Reference, "
                    + "Reference_Type, "
                    + "Reference, "
                    + "Sales_Person, "
                    + "Currency_Code, "
                    + "Required_Delivery_Date, "
                    + "Payment_Term, "
                    + "Shipment_Term, "
                    + "Gross, "
                    + "Discount, "
                    + "Sub_Total, "
                    + "Nett, "
                    + "Issued_By, "
                    + "Released_AnVerified_By, "
                    + "Customer_Signed, "
                    + "Status, "
                    + "Created_Date, "
                    + "Actual_Created_Date, "
                    + "Signed_Doc_Pic, "
                    + "Modified_Date_Time"
                    + ") "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(query);
            // bind parameter
            ps.setString(1, salesOrder.getCode());
            ps.setString(2, salesOrder.getQuotRef().getCode());
            ps.setString(3, salesOrder.getBillToCust().getCustID());
            ps.setString(4, salesOrder.getDeliverToCust().getCollectAddrID());
            ps.setString(5, salesOrder.getCustPOReference());
            ps.setString(6, salesOrder.getReference());
            ps.setString(7, salesOrder.getReference());
            ps.setString(8, salesOrder.getSalesPerson().getStaffID());
            ps.setString(9, salesOrder.getCurrencyCode());
            ps.setDate(10, salesOrder.getRequiredDeliveryDate());
            ps.setString(11, salesOrder.getPymtTerm());
            ps.setString(12, salesOrder.getShipmentTerm());
            ps.setBigDecimal(13, salesOrder.getGross());
            ps.setBigDecimal(14, salesOrder.getDiscount());
            ps.setBigDecimal(15, salesOrder.getSubTotal());
            ps.setBigDecimal(16, salesOrder.getNett());
            ps.setString(17, salesOrder.getIssuedBy().getStaffID());
            ps.setString(18, salesOrder.getReleasedAVerifiedBy().getStaffID());
            ps.setString(19, salesOrder.getCustomerSignature().getCustID());
            ps.setString(20, salesOrder.getStatus());
            ps.setTimestamp(21, salesOrder.getCreatedDate());
            ps.setTimestamp(22, salesOrder.getActualCreatedDateTime());
            ps.setString(23, Base64.encodeBase64String(salesOrder.getSignedDocPic()));
            ps.setTimestamp(24, salesOrder.getModifiedDateTime());
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

    public static SalesOrder getSalesOrderByCode(String code) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
        SalesOrder salesOrder = new SalesOrder();

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT * FROM View_Retrieve_All_SalesOrder WHERE SO_SO_ID = ?";
            ps = conn.prepareStatement(query);

            // bind parameter
            ps.setString(1, code);

            rs = ps.executeQuery();

            if (rs.next()) {
                salesOrder.setCode(rs.getString("SO_SO_ID"));
                salesOrder.setQuotRef(QuotationDAO.getQuotationByCode(rs.getString("SO_Quot_ID")));
                salesOrder.setBillToCust(CustomerDAO.getCustomerByID(rs.getString("SO_Bill_To_Cust")));
                salesOrder.setDeliverToCust(CollectAddressDAO.getCollectAddressByID(rs.getString("SO_Deliver_To")));
                salesOrder.setCustPOReference(rs.getString("SO_Cust_PO_Reference"));
                salesOrder.setReferenceType(rs.getString("SO_Reference_Type"));
                salesOrder.setReference(rs.getString("SO_Reference"));
                salesOrder.setSalesPerson(StaffDAO.getStaffByID(rs.getString("SO_Sales_Person")));
                salesOrder.setCurrencyCode(rs.getString("SO_Currency_Code"));
                salesOrder.setRequiredDeliveryDate(rs.getDate("SO_Required_Delivery_Date"));
                salesOrder.setPymtTerm(rs.getString("SO_Payment_Term"));
                salesOrder.setShipmentTerm(rs.getString("SO_Shipment_Term"));
                salesOrder.setGross(rs.getBigDecimal("SO_Gross"));
                salesOrder.setDiscount(rs.getBigDecimal("SO_Discount"));
                salesOrder.setSubTotal(rs.getBigDecimal("SO_Sub_Total"));
                salesOrder.setNett(rs.getBigDecimal("SO_Nett"));
                salesOrder.setIssuedBy(StaffDAO.getStaffByID(rs.getString("SO_Issued_By")));
                salesOrder.setReleasedAVerifiedBy(StaffDAO.getStaffByID(rs.getString("SO_Released_AnVerified_By")));
                salesOrder.setCustomerSignature(CustomerDAO.getCustomerByID(rs.getString("SO_Customer_Signed")));
                salesOrder.setStatus(rs.getString("SO_Status"));
                salesOrder.setCreatedDate(rs.getTimestamp("SO_Created_Date"));
                salesOrder.setActualCreatedDateTime(rs.getTimestamp("SO_Actual_Created_Date"));
                salesOrder.setSignedDocPic(Base64.decodeBase64(rs.getString("SO_Signed_Doc_Pic")));
                salesOrder.setModifiedDateTime(rs.getTimestamp("SO_Modified_Date_Time"));

                return salesOrder;
            } else {
                return null;
            }

            //return object
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

    public static String getLatestCode() {

        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
        String latestCode = null;

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT * FROM View_SalesOrder_LatestID";
            ps = conn.prepareStatement(query);

            // bind parameter
            rs = ps.executeQuery();

            if (rs.next()) {
                latestCode = rs.getString("SO_ID");
            }

            //return object
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
