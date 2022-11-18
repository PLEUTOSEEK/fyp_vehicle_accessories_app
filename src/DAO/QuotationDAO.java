/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.Quotation;
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
public class QuotationDAO {

    public static String saveNewQuotation(Quotation quotation) throws SQLException {

        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        quotation.setActualCreatedDateTime(timestamp);
        quotation.setModifiedDateTime(timestamp);
        try {
            conn = SQLDatabaseConnection.openConn();

            query = "INSERT INTO Quotation("
                    + "Quot_ID, "
                    + "CI_ID, "
                    + "Reference_Type, "
                    + "Reference, "
                    + "Bill_To_Cust, "
                    + "Deliver_To, "
                    + "Sales_Person, "
                    + "Currency_Code, "
                    + "Quot_Validity_Date, "
                    + "Required_Delivery_Date, "
                    + "Payment_Term, "
                    + "Shipment_Term, "
                    + "Gross, "
                    + "Discount, "
                    + "Sub_Total, "
                    + "Nett, "
                    + "Issued_By, "
                    + "Released_And_Verified_By, "
                    + "Customer_Signed, "
                    + "Status, "
                    + "Created_Date, "
                    + "Actual_Created_Date, "
                    + "Signed_Doc_Pic, "
                    + "Modified_Date_Time "
                    + ") "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            ps = conn.prepareStatement(query);
            // bind parameter
            ps.setString(1, quotation.getCode());
            ps.setString(2, quotation.getCI().getCode());
            ps.setString(3, quotation.getReferenceType());
            ps.setString(4, quotation.getReference());
            ps.setString(5, quotation.getBillToCust().getCustID());
            ps.setString(6, quotation.getDeliverToCust().getCollectAddrID());
            ps.setString(7, quotation.getSalesPerson().getStaffID());
            ps.setString(8, quotation.getCurrencyCode());
            ps.setDate(9, quotation.getQuotValidityDate());
            ps.setDate(10, quotation.getRequiredDeliveryDate());
            ps.setString(11, quotation.getPymtTerm().getPymtTermID());
            ps.setString(12, quotation.getShipmentTerm().getShipmentTermID());
            ps.setBigDecimal(13, quotation.getGross());
            ps.setBigDecimal(14, quotation.getDiscount());
            ps.setBigDecimal(15, quotation.getSubTotal());
            ps.setBigDecimal(16, quotation.getNett());
            ps.setString(17, quotation.getIssuedBy().getStaffID());
            ps.setString(18, quotation.getReleasedAVerifiedBy().getStaffID());
            ps.setString(19, quotation.getCustomerSignature().getCollectAddrID());
            ps.setString(20, quotation.getStatus());
            ps.setTimestamp(21, quotation.getCreatedDate());
            ps.setTimestamp(22, quotation.getActualCreatedDateTime());
            ps.setString(23, quotation.getSignedDocPic()); // need to change to encoded 64 string
            ps.setTimestamp(24, quotation.getModifiedDateTime());

            ps.execute();
            return quotation.getCode();
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

    //code = ID
    public static Quotation getQuotationByCode(String code) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
        Quotation quotation = new Quotation();

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT [QUOT_ID] "
                    + "      ,[CI_ID] "
                    + "      ,[Reference_Type] "
                    + "      ,[Reference] "
                    + "      ,[Bill_To_Cust] "
                    + "      ,[Deliver_To] "
                    + "      ,[Sales_Person] "
                    + "      ,[Currency_Code] "
                    + "      ,[Quot_Validity_Date] "
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
                    + "  FROM [dbo].[Quotation]"
                    + "  WHERE [CI_ID] = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, code);

            rs = ps.executeQuery();

            if (rs.next()) {
                quotation = new Quotation();
                quotation.setCode(rs.getString("QUOT_ID"));
                quotation.setCI(CustomerInquiryDAO.getCustomerInquiryByCode(rs.getString("CI_ID")));
                quotation.setReferenceType(rs.getString("Reference_Type"));
                quotation.setReference(rs.getString("Reference"));
                quotation.setBillToCust(CustomerDAO.getCustomerByID(rs.getString("Bill_To_Cust")));
                quotation.setDeliverToCust(CollectAddressDAO.getCollectAddressByID(rs.getString("Deliver_To")));
                quotation.setSalesPerson(StaffDAO.getStaffByID(rs.getString("Sales_Person")));
                quotation.setCurrencyCode(rs.getString("Currency_Code"));
                quotation.setQuotValidityDate(rs.getDate("Quot_Validity_Date"));
                quotation.setRequiredDeliveryDate(rs.getDate("Required_Delivery_Date"));
                quotation.setPymtTerm(PaymentTermDAO.getPymtTermByID(rs.getString("Payment_Term")));
                quotation.setShipmentTerm(ShipmentTermDAO.getShipmentTermByID(rs.getString("Shipment_Term")));
                quotation.setGross(rs.getBigDecimal("Gross"));
                quotation.setDiscount(rs.getBigDecimal("Discount"));
                quotation.setSubTotal(rs.getBigDecimal("Sub_Total"));
                quotation.setNett(rs.getBigDecimal(rs.getString("Nett")));
                quotation.setIssuedBy(StaffDAO.getStaffByID(rs.getString("Issued_By")));
                quotation.setReleasedAVerifiedBy(StaffDAO.getStaffByID(rs.getString("Released_And_Verified_By")));
                quotation.setCustomerSignature(CollectAddressDAO.getCollectAddressByID(rs.getString("Customer_Signed")));
                quotation.setStatus(rs.getString("Status"));
                quotation.setCreatedDate(rs.getTimestamp("Created_Date"));
                quotation.setActualCreatedDateTime(rs.getTimestamp("Actual_Created_Date"));
                quotation.setSignedDocPic(rs.getString("Signed_Doc_Pic"));
                quotation.setModifiedDateTime(rs.getTimestamp("Modified_Date_Time"));
                return quotation;
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
            query = "SELECT * FROM View_Quotation_LatestID";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            if (rs.next()) {
                latestCode = rs.getString("Quot_ID");
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

    public static List<Quotation> getAllQuotation() {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
        Quotation quotation = new Quotation();
        List<Quotation> quotations = new ArrayList<>();

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT [QUOT_ID] "
                    + "      ,[CI_ID] "
                    + "      ,[Reference_Type] "
                    + "      ,[Reference] "
                    + "      ,[Bill_To_Cust] "
                    + "      ,[Deliver_To] "
                    + "      ,[Sales_Person] "
                    + "      ,[Currency_Code] "
                    + "      ,[Quot_Validity_Date] "
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
                    + "  FROM [dbo].[Quotation]";

            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                quotation = new Quotation();
                quotation.setCode(rs.getString("QUOT_ID"));
                quotation.setCI(CustomerInquiryDAO.getCustomerInquiryByCode(rs.getString("CI_ID")));
                quotation.setReferenceType(rs.getString("Reference_Type"));
                quotation.setReference(rs.getString("Reference"));
                quotation.setBillToCust(CustomerDAO.getCustomerByID(rs.getString("Bill_To_Cust")));
                quotation.setDeliverToCust(CollectAddressDAO.getCollectAddressByID(rs.getString("Deliver_To")));
                quotation.setSalesPerson(StaffDAO.getStaffByID(rs.getString("Sales_Person")));
                quotation.setCurrencyCode(rs.getString("Currency_Code"));
                quotation.setQuotValidityDate(rs.getDate("Quot_Validity_Date"));
                quotation.setRequiredDeliveryDate(rs.getDate("Required_Delivery_Date"));
                quotation.setPymtTerm(PaymentTermDAO.getPymtTermByID(rs.getString("Payment_Term")));
                quotation.setShipmentTerm(ShipmentTermDAO.getShipmentTermByID(rs.getString("Shipment_Term")));
                quotation.setGross(rs.getBigDecimal("Gross"));
                quotation.setDiscount(rs.getBigDecimal("Discount"));
                quotation.setSubTotal(rs.getBigDecimal("Sub_Total"));
                quotation.setNett(rs.getBigDecimal(rs.getString("Nett")));
                quotation.setIssuedBy(StaffDAO.getStaffByID(rs.getString("Issued_By")));
                quotation.setReleasedAVerifiedBy(StaffDAO.getStaffByID(rs.getString("Released_And_Verified_By")));
                quotation.setCustomerSignature(CollectAddressDAO.getCollectAddressByID(rs.getString("Customer_Signed")));
                quotation.setStatus(rs.getString("Status"));
                quotation.setCreatedDate(rs.getTimestamp("Created_Date"));
                quotation.setActualCreatedDateTime(rs.getTimestamp("Actual_Created_Date"));
                quotation.setSignedDocPic(rs.getString("Signed_Doc_Pic"));
                quotation.setModifiedDateTime(rs.getTimestamp("Modified_Date_Time"));

                quotations.add(quotation);
            }

            return quotations;

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

    public static String updateQuotation(Quotation quotation) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        quotation.setModifiedDateTime(timestamp);
        try {
            conn = SQLDatabaseConnection.openConn();

            query = "UPDATE Quotation SET"
                    + "CI_ID = ?, "
                    + "Reference_Type = ?, "
                    + "Reference = ?, "
                    + "Bill_To_Cust = ?, "
                    + "Deliver_To = ?, "
                    + "Sales_Person = ?, "
                    + "Currency_Code = ?, "
                    + "Quot_Validity_Date = ?, "
                    + "Required_Delivery_Date = ?, "
                    + "Payment_Term = ?, "
                    + "Shipment_Term = ?, "
                    + "Gross = ?, "
                    + "Discount = ?, "
                    + "Sub_Total = ?, "
                    + "Nett = ?, "
                    + "Issued_By = ?, "
                    + "Released_And_Verified_By = ?, "
                    + "Customer_Signed = ?, "
                    + "Status = ?, "
                    + "Created_Date = ?, "
                    + "Actual_Created_Date = ?, "
                    + "Signed_Doc_Pic = ?, "
                    + "Modified_Date_Time = ?"
                    + "WHERE "
                    + "Quot_ID = ?";

            ps = conn.prepareStatement(query);
            // bind parameter
            ps.setString(1, quotation.getCI().getCode());
            ps.setString(2, quotation.getReferenceType());
            ps.setString(3, quotation.getReference());
            ps.setString(4, quotation.getBillToCust().getCustID());
            ps.setString(5, quotation.getDeliverToCust().getCollectAddrID());
            ps.setString(6, quotation.getSalesPerson().getStaffID());
            ps.setString(7, quotation.getCurrencyCode());
            ps.setDate(8, quotation.getQuotValidityDate());
            ps.setDate(9, quotation.getRequiredDeliveryDate());
            ps.setString(10, quotation.getPymtTerm().getPymtTermID());
            ps.setString(11, quotation.getShipmentTerm().getShipmentTermID());
            ps.setBigDecimal(12, quotation.getGross());
            ps.setBigDecimal(13, quotation.getDiscount());
            ps.setBigDecimal(14, quotation.getSubTotal());
            ps.setBigDecimal(15, quotation.getNett());
            ps.setString(16, quotation.getIssuedBy().getStaffID());
            ps.setString(17, quotation.getReleasedAVerifiedBy().getStaffID());
            ps.setString(18, quotation.getCustomerSignature().getCollectAddrID());
            ps.setString(19, quotation.getStatus());
            ps.setTimestamp(20, quotation.getCreatedDate());
            ps.setTimestamp(21, quotation.getActualCreatedDateTime());
            ps.setString(22, quotation.getSignedDocPic()); // need to change to encoded 64 string
            ps.setTimestamp(23, quotation.getModifiedDateTime());
            ps.setString(24, quotation.getCode());

            ps.execute();
            return quotation.getCode();
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

    public static String updateQuotationStatus(Quotation quotation) {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        quotation.setModifiedDateTime(timestamp);

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "UPDATE Quotation SET "
                    + "Status = ? "
                    + "WHERE QUOT_ID = ? ";
            ps = conn.prepareStatement(query);
            // bind parameter
            ps.setString(1, quotation.getStatus());
            ps.setString(2, quotation.getCode());

            ps.execute();
            return quotation.getCode();
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
