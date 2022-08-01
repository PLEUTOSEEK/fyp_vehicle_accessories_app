/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ExperimentArea;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class DateToString {

    public static void main(String args[]) throws ParseException {
        //Retrieving the current date
        LocalDate localDate = LocalDate.of(2014, 9, 1);
        //Converting LocalDate object to Date
        Date date = java.sql.Date.valueOf(localDate);
        //Instantiating the SimpleDateFormat class
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        //Formatting the obtained date
        String formattedDate = formatter.format(date);
        System.out.println("Formatted date: " + formattedDate);
    }
}
