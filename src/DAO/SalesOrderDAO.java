/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.CollectAddress;
import Entity.Quotation;
import Entity.SalesOrder;
import Entity.Staff;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class SalesOrderDAO {

    public static String saveNewSalesOrder(SalesOrder salesOrder) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        salesOrder.setActualCreatedDateTime(timestamp);
        salesOrder.setModifiedDateTime(timestamp);

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
            ps.setString(11, salesOrder.getPymtTerm().getPymtTermID());
            ps.setString(12, salesOrder.getShipmentTerm().getShipmentTermID());
            ps.setBigDecimal(13, salesOrder.getGross());
            ps.setBigDecimal(14, salesOrder.getDiscount());
            ps.setBigDecimal(15, salesOrder.getSubTotal());
            ps.setBigDecimal(16, salesOrder.getNett());
            ps.setString(17, salesOrder.getIssuedBy().getStaffID());
            ps.setString(18, salesOrder.getReleasedAVerifiedBy().getStaffID());
            ps.setString(19, salesOrder.getCustomerSignature().getCollectAddrID());
            ps.setString(20, salesOrder.getStatus());
            ps.setTimestamp(21, salesOrder.getCreatedDate());
            ps.setTimestamp(22, salesOrder.getActualCreatedDateTime());
            ps.setString(23, salesOrder.getSignedDocPic());
            ps.setTimestamp(24, salesOrder.getModifiedDateTime());
            ps.execute();

            return salesOrder.getCode();
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

    public static SalesOrder getSalesOrderByID(String code) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
        SalesOrder salesOrder = new SalesOrder();

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT [SO_ID] "
                    + "      ,[Quot_ID] "
                    + "      ,[Bill_To_Cust] "
                    + "      ,[Deliver_To] "
                    + "      ,[Cust_PO_Reference] "
                    + "      ,[Reference_Type] "
                    + "      ,[Reference] "
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
                    + "      ,[Released_And_Verified_By] "
                    + "      ,[Customer_Signed] "
                    + "      ,[Status] "
                    + "      ,[Created_Date] "
                    + "      ,[Actual_Created_Date] "
                    + "      ,[Signed_Doc_Pic] "
                    + "      ,[Modified_Date_Time] "
                    + "  FROM [dbo].[SalesOrder] "
                    + "  WHERE [SO_ID] = ?";
            ps = conn.prepareStatement(query);

            // bind parameter
            ps.setString(1, code);

            rs = ps.executeQuery();

            if (rs.next()) {
                salesOrder.setCode(rs.getString("ID"));
                salesOrder.setQuotRef(QuotationDAO.getQuotationByCode(rs.getString("Quot_ID")));
                salesOrder.setBillToCust(CustomerDAO.getCustomerByID(rs.getString("Bill_To_Cust")));
                salesOrder.setDeliverToCust(CollectAddressDAO.getCollectAddressByID(rs.getString("Deliver_To")));
                salesOrder.setCustPOReference(rs.getString("Cust_PO_Reference"));
                salesOrder.setReferenceType(rs.getString("Reference_Type"));
                salesOrder.setReference(rs.getString("Reference"));
                salesOrder.setSalesPerson(StaffDAO.getStaffByID(rs.getString("Sales_Person")));
                salesOrder.setCurrencyCode(rs.getString("Currency_Code"));
                salesOrder.setRequiredDeliveryDate(rs.getDate("Required_Delivery_Date"));
                salesOrder.setPymtTerm(PaymentTermDAO.getPymtTermByID(rs.getString("Payment_Term")));
                salesOrder.setShipmentTerm(ShipmentTermDAO.getShipmentTermByID(rs.getString("Shipment_Term")));
                salesOrder.setGross(rs.getBigDecimal("Gross"));
                salesOrder.setDiscount(rs.getBigDecimal("Discount"));
                salesOrder.setSubTotal(rs.getBigDecimal("Sub_Total"));
                salesOrder.setNett(rs.getBigDecimal("Nett"));
                salesOrder.setIssuedBy(StaffDAO.getStaffByID(rs.getString("Issued_By")));
                salesOrder.setReleasedAVerifiedBy(StaffDAO.getStaffByID(rs.getString("Released_AnVerified_By")));
                salesOrder.setCustomerSignature(CollectAddressDAO.getCollectAddressByID(rs.getString("Customer_Signed")));
                salesOrder.setStatus(rs.getString("Status"));
                salesOrder.setCreatedDate(rs.getTimestamp("Created_Date"));
                salesOrder.setActualCreatedDateTime(rs.getTimestamp("Actual_Created_Date"));
                salesOrder.setSignedDocPic(rs.getString("Signed_Doc_Pic"));
                salesOrder.setModifiedDateTime(rs.getTimestamp("Modified_Date_Time"));

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

    public static List<SalesOrder> getAllSalesOrder() {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
        List<SalesOrder> SOs = new ArrayList<>();

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT  [SO_ID] "
                    + "      ,[Quot_ID] "
                    + "      ,[Bill_To_Cust] "
                    + "      ,[Deliver_To] "
                    + "      ,[Cust_PO_Reference] "
                    + "      ,[Reference_Type] "
                    + "      ,[Reference] "
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
                    + "      ,[Released_And_Verified_By] "
                    + "      ,[Customer_Signed] "
                    + "      ,[Status] "
                    + "      ,[Created_Date] "
                    + "      ,[Actual_Created_Date] "
                    + "      ,[Signed_Doc_Pic] "
                    + "      ,[Modified_Date_Time] "
                    + "  FROM SalesOrder";
            ps = conn.prepareStatement(query);

            // bind parameter
            rs = ps.executeQuery();

            while (rs.next()) {
                SalesOrder so = new SalesOrder();

                so.setCode(rs.getString("SO_ID"));
                so.setQuotRef(new Quotation());
                so.getQuotRef().setCode(rs.getString("Quot_ID"));
                so.setBillToCust(CustomerDAO.getCustomerByID(rs.getString("Bill_To_Cust")));
                so.setDeliverToCust(CollectAddressDAO.getCollectAddressByID(rs.getString("Deliver_To")));
                so.setCustPOReference(rs.getString("Cust_PO_Reference"));
                so.setReferenceType(rs.getString("Reference_Type"));
                so.setReference(rs.getString("Reference"));
                so.setSalesPerson(StaffDAO.getStaffByID(rs.getString("Sales_Person")));
                so.setCurrencyCode(rs.getString("Currency_Code"));
                so.setRequiredDeliveryDate(rs.getDate("Required_Delivery_Date"));
                so.setPymtTerm(PaymentTermDAO.getPymtTermByID(rs.getString("Payment_Term")));
                so.setShipmentTerm(ShipmentTermDAO.getShipmentTermByID(rs.getString("Shipment_Term")));

                so.setGross(rs.getBigDecimal("Gross"));
                so.setDiscount(rs.getBigDecimal("Discount"));
                so.setSubTotal(rs.getBigDecimal("Sub_Total"));
                so.setNett(rs.getBigDecimal("Nett"));

                so.setIssuedBy(new Staff());
                so.getIssuedBy().setStaffID("Issued_By");

                so.setReleasedAVerifiedBy(new Staff());
                so.getReleasedAVerifiedBy().setStaffID("Released_And_Verified_By");

                so.setCustomerSignature(new CollectAddress());
                so.getCustomerSignature().setCollectAddrID(rs.getString("Customer_Signed"));

                so.setStatus(rs.getString("Status"));
                so.setCreatedDate(rs.getTimestamp("Created_Date"));
                so.setActualCreatedDateTime(rs.getTimestamp("Actual_Created_Date"));
                so.setSignedDocPic(rs.getString("Signed_Doc_Pic"));
                so.setModifiedDateTime(rs.getTimestamp("Modified_Date_Time"));

                SOs.add(so);
            }

            //return object
            return SOs;
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

    public static String updateSalesOrder(SalesOrder so) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        so.setModifiedDateTime(timestamp);

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "UPDATE SalesOrder SET "
                    + "Quot_ID = ?, "
                    + "Bill_To_Cust = ?, "
                    + "Deliver_To = ?, "
                    + "Cust_PO_Reference = ?, "
                    + "Reference_Type = ?, "
                    + "Reference = ?, "
                    + "Sales_Person = ?, "
                    + "Currency_Code = ?, "
                    + "Required_Delivery_Date = ?, "
                    + "Payment_Term = ?, "
                    + "Shipment_Term = ?, "
                    + "Gross = ?, "
                    + "Discount = ?, "
                    + "Sub_Total = ?, "
                    + "Nett = ?, "
                    + "Issued_By = ?, "
                    + "Released_AnVerified_By = ?, "
                    + "Customer_Signed = ?, "
                    + "Status = ?, "
                    + "Created_Date = ?, "
                    + "Actual_Created_Date = ?, "
                    + "Signed_Doc_Pic = ?, "
                    + "Modified_Date_Time = ? "
                    + "WHERE "
                    + "SO_ID = ? ";

            ps = conn.prepareStatement(query);
            // bind parameter
            ps.setString(1, so.getQuotRef().getCode());
            ps.setString(2, so.getBillToCust().getCustID());
            ps.setString(3, so.getDeliverToCust().getCollectAddrID());
            ps.setString(4, so.getCustPOReference());
            ps.setString(5, so.getReference());
            ps.setString(6, so.getReference());
            ps.setString(7, so.getSalesPerson().getStaffID());
            ps.setString(8, so.getCurrencyCode());
            ps.setDate(9, so.getRequiredDeliveryDate());
            ps.setString(10, so.getPymtTerm().getPymtTermID());
            ps.setString(11, so.getShipmentTerm().getShipmentTermID());
            ps.setBigDecimal(12, so.getGross());
            ps.setBigDecimal(13, so.getDiscount());
            ps.setBigDecimal(14, so.getSubTotal());
            ps.setBigDecimal(15, so.getNett());
            ps.setString(16, so.getIssuedBy().getStaffID());
            ps.setString(17, so.getReleasedAVerifiedBy().getStaffID());
            ps.setString(18, so.getCustomerSignature().getCollectAddrID());
            ps.setString(19, so.getStatus());
            ps.setTimestamp(20, so.getCreatedDate());
            ps.setTimestamp(21, so.getActualCreatedDateTime());
            ps.setString(22, so.getSignedDocPic());
            ps.setTimestamp(23, so.getModifiedDateTime());
            ps.setString(24, so.getCode());

            ps.execute();
            return so.getCode();
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

    public static String updateSalesOrderStatus(SalesOrder salesOrder) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        salesOrder.setModifiedDateTime(timestamp);

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "UPDATE SalesOrder SET "
                    + "Status = ? "
                    + "WHERE "
                    + "SO_ID = ? ";

            ps = conn.prepareStatement(query);
            // bind parameter
            ps.setString(1, salesOrder.getStatus());
            ps.setString(2, salesOrder.getCode());

            ps.execute();
            return salesOrder.getCode();
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
