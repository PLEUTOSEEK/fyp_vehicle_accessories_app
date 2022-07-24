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
import java.sql.Types;
import java.util.List;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class QuotationDAO {

    public static boolean saveNewQuotation(Quotation quotation) throws SQLException {

        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";

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
                    + "Modified_Date_Time"
                    + ")"
                    + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            ps = conn.prepareStatement(query);
            // bind parameter
            ps.setString(1, quotation.getCode());
            ps.setString(2, quotation.getCI().getCode());
            ps.setString(3, quotation.getReferenceType());
            ps.setNull(4, Types.VARCHAR);
            ps.setString(5, quotation.getBillToCust().getCustID());
            ps.setString(6, quotation.getDeliverToCust().getCollectAddrID());
            ps.setString(7, quotation.getSalesPerson().getStaffID());
            ps.setString(8, quotation.getCurrencyCode());
            ps.setDate(9, quotation.getQuotValidityDate());
            ps.setDate(10, quotation.getRequiredDeliveryDate());
            ps.setString(11, quotation.getPymtTerm());
            ps.setString(12, quotation.getShipmentTerm());
            ps.setBigDecimal(13, quotation.getGross());
            ps.setBigDecimal(14, quotation.getDiscount());
            ps.setBigDecimal(15, quotation.getSubTotal());
            ps.setBigDecimal(16, quotation.getNett());
            ps.setString(17, quotation.getIssuedBy().getStaffID());
            ps.setString(18, quotation.getReleasedAVerifiedBy().getStaffID());
            ps.setString(19, quotation.getCustomerSignature().getCustID());
            ps.setString(20, quotation.getStatus());
            ps.setTimestamp(21, quotation.getCreatedDate());
            ps.setTimestamp(22, quotation.getActualCreatedDateTime());
            ps.setString(23, Base64.encodeBase64String(quotation.getSignedDocPic())); // need to change to encoded 64 string
            ps.setTimestamp(24, quotation.getModifiedDateTime());

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

    //code = ID
    public static Quotation getQuotationByCode(String code) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "";
        ResultSet rs = null;
        Quotation quotation = new Quotation();

        try {
            conn = SQLDatabaseConnection.openConn();

            query = "SELECT * FROM View_Retrieve_All_Quotation WHERE QUOT_Quot_ID = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, code);

            rs = ps.executeQuery();

            if (rs.next()) {
                quotation.setCode(rs.getString("QUOT_Quot_ID"));
                quotation.setCI(CustomerInquiryDAO.getCustomerInquiryByCode(rs.getString("QUOT_CI_ID")));
                quotation.setReferenceType(rs.getString("QUOT_Reference_Type"));
                quotation.setReference(rs.getString("QUOT_Reference"));
                quotation.setBillToCust(CustomerDAO.getCustomerByID(rs.getString("QUOT_Bill_To_Cust")));
                quotation.setDeliverToCust(CollectAddressDAO.getCollectAddressByID(rs.getString("QUOT_Deliver_To")));
                quotation.setSalesPerson(StaffDAO.getStaffByID(rs.getString("QUOT_Sales_Person")));
                quotation.setCurrencyCode(rs.getString("QUOT_Currency_Code"));
                quotation.setQuotValidityDate(rs.getDate("QUOT_Quot_Validity_Date"));
                quotation.setRequiredDeliveryDate(rs.getDate("QUOT_Required_Delivery_Date"));
                quotation.setPymtTerm(rs.getString("QUOT_Payment_Term"));
                quotation.setShipmentTerm(rs.getString("QUOT_Shipment_Term"));
                quotation.setGross(rs.getBigDecimal("QUOT_Gross"));
                quotation.setDiscount(rs.getBigDecimal("QUOT_Discount"));
                quotation.setSubTotal(rs.getBigDecimal("QUOT_Sub_Total"));
                quotation.setNett(rs.getBigDecimal("QUOT_Nett"));
                quotation.setIssuedBy(StaffDAO.getStaffByID(rs.getString("QUOT_Issued_By")));
                quotation.setReleasedAVerifiedBy(StaffDAO.getStaffByID(rs.getString("QUOT_Released_And_Verified_By")));
                quotation.setCustomerSignature(CustomerDAO.getCustomerByID(rs.getString("QUOT_Customer_Signed")));
                quotation.setStatus(rs.getString("QUOT_Status"));
                quotation.setCreatedDate(rs.getTimestamp("QUOT_Created_Date"));
                quotation.setActualCreatedDateTime(rs.getTimestamp("QUOT_Actual_Created_Date"));
                quotation.setSignedDocPic(Base64.decodeBase64(rs.getString("QUOT_Signed_Doc_Pic")));
                quotation.setModifiedDateTime(rs.getTimestamp("QUOT_Modified_Date_Time"));
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
