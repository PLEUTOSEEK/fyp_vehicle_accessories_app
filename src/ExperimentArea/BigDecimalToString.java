/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ExperimentArea;

import java.math.BigDecimal;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class BigDecimalToString {

    public static void main(String[] args) {
        BigDecimal a = new BigDecimal("9879588600.00");
        String aa = new java.text.DecimalFormat("$ #,##0.00").format(a);
        System.out.println(aa);

        "Yes".toUpperCase().equals("");

        String query = "UPDATE [dbo].[Receipt] "
                + "   SET [INV_ID] =  ? "
                + "      ,[Reference_Type] =  ? "
                + "      ,[Reference] =  ? "
                + "      ,[Total_Payable = ? "
                + "      ,[Paid_Amount] = ? "
                + "      ,[Prev_Paid_Amount] = ? "
                + "      ,[Bal_Unpaid] = ? "
                + "      ,[Created_Date] =  ? "
                + "      ,[Actual_Created_Date] =  ? "
                + "      ,[Signed_Doc_Pic] =  ? "
                + "      ,[Modified_Date_Time] =  ? "
                + " WHERE [RCPT_ID] =  ? ";

    }
}
